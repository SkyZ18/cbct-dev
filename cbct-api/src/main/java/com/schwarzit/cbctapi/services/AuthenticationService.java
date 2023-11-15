package com.schwarzit.cbctapi.services;

import com.schwarzit.cbctapi.dtos.AuthenticationRequest;
import com.schwarzit.cbctapi.dtos.AuthenticationResponse;
import com.schwarzit.cbctapi.dtos.RegisterRequest;
import com.schwarzit.cbctapi.enums.Role;
import com.schwarzit.cbctapi.models.CustomerModel;
import com.schwarzit.cbctapi.repositories.CustomerRepository;
import com.schwarzit.cbctapi.token.Token;
import com.schwarzit.cbctapi.token.TokenRepository;
import com.schwarzit.cbctapi.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var customer = CustomerModel.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .country(request.getCountry())
                .role(Role.USER)
                .build();
        var savedCustomer = customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        saveUserToken(savedCustomer, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(CustomerModel customerModel, String jwtToken) {
        var token = Token.builder()
                .customerModel(customerModel)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(customer);
        revokeAllUserTokens(customer);
        saveUserToken(customer, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(CustomerModel customerModel) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(customerModel.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
