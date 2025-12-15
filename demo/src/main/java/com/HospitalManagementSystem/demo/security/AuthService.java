package com.HospitalManagementSystem.demo.security;


import com.HospitalManagementSystem.demo.dto.LoginRequestDto;
import com.HospitalManagementSystem.demo.dto.LoginResponseDto;
import com.HospitalManagementSystem.demo.dto.SignupResponseDto;
import com.HospitalManagementSystem.demo.entity.User;
import com.HospitalManagementSystem.demo.entity.type.AuthProviderType;
import com.HospitalManagementSystem.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user= (User) authentication.getPrincipal();

        assert user != null;
        String token= authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());

    }

    public Optional<User> signupInternal(
            LoginRequestDto signupRequestDto,
            AuthProviderType authProviderType,
            String providerId
    ) {

        Optional<User> existingUser =
                userRepository.findByUsername(signupRequestDto.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .build();

        if (authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        return Optional.of(userRepository.save(user));
    }


    // Auth controller
    public SignupResponseDto signup(LoginRequestDto signupRequestDto){

      Optional<User> user = signupInternal(signupRequestDto, AuthProviderType.EMAIL, null);

    return new SignupResponseDto(user.get().getId(),  user.get().getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        //fetch provider type and providerId

        AuthProviderType providerType= authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email= oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser== null){
            //signup flow:
            String username= authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user= signupInternal(new LoginRequestDto(username, null), providerType, providerId).get();
        } else if (user != null) {
            if(email != null && !email.isBlank() &&!email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("This email is already registered with provider %s".formatted(emailUser.getProviderType()));
        }

        LoginResponseDto loginResponseDto= new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

        return ResponseEntity.ok(loginResponseDto);
        //save the providertype and providerId info with user
        // if the use has an account login directly, if not direct to signup page

    }
}























