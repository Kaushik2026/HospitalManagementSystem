package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.dtos.LoginRequestDto;
import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import com.backendlld.hospitalManagement.dtos.SignUpRequestDto;
import com.backendlld.hospitalManagement.dtos.SignupResponseDto;
import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.model.enums.AuthProviderType;
import com.backendlld.hospitalManagement.model.enums.RoleType;
import com.backendlld.hospitalManagement.repository.PatientRepository;
import com.backendlld.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

//        authentication manager needs an object of UsernamePasswordAuthenticationToken. why usernamepassword
//        bcoz we are using username and password authentication(asking username and password from user to login).
        Authentication authentication = authenticationManager.authenticate(
//                object of UsernamePasswordAuthenticationToken
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);
        user.setLoggedIn(true);

        return new LoginResponseDto(token, user.getId());
    }
    public User signUpInternal(SignUpRequestDto signupRequestDto,AuthProviderType authProviderType,String providerId) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("User already exists");

        user =  User.builder()
                .username(signupRequestDto.getUsername())
                .providerType(authProviderType)
                .providerId(providerId)
                .roles(Set.of(RoleType.PATIENT))
                .build();

        if(authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        user = userRepository.save(user);
        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
        patientRepository.save(patient);
        return user;


    }

    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto,AuthProviderType.EMAIL,null);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }


    @Override
    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
//        fetch providertype and providerId from oAuth2User and save it in our user.
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);

        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

//        check if user already exists with this providerType and providerId
        User user = userRepository.findByProviderTypeAndProviderId(providerType,providerId).orElse(null);
//        for future we can take email also from oAuth2User and update our user details like name,profile pic etc.
//        if provided by auth server. some auth server provide email,name,profile pic etc. some don't.
        String email = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
        String name = oAuth2User.getAttribute("name");

//        if a user login with google and then with github using same email then this should not be valid
//        so we will check that here.
        User userByEmail = userRepository.findByUsername(email).orElse(null);
        if(user == null && userByEmail == null){
//            send to signup flow as this user don't exist in our db
            String username = email;
            user = signUpInternal(new SignUpRequestDto(name,username, null),providerType,providerId);

        }else if(user != null){
//            if email is not null so we should update username(email) of user if not already set
//            bcoz it can happen like if some providers previously didnt provide email by now they did.
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }

        }else{
            throw new BadCredentialsException("User already exists with this email "+email);
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user),user.getId());
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

    }

    @Override
    public void assignRoleToUser(Long userId, RoleType role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void logOut(User user) {
        user.setLoggedIn(false);
        userRepository.save(user);
    }


}