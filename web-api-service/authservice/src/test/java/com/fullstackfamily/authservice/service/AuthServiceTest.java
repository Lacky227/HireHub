package com.fullstackfamily.authservice.service;

import com.fullstackfamily.authservice.BasePgSqlIT;
import com.fullstackfamily.authservice.dto.*;
import com.fullstackfamily.authservice.models.Token;
import com.fullstackfamily.authservice.models.User;
import com.fullstackfamily.authservice.models.UserRole;
import com.fullstackfamily.authservice.repository.AuthRepository;
import com.fullstackfamily.authservice.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.jpa.show-sql=true")
class AuthServiceTest extends BasePgSqlIT {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        authRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @Test
    void givenValidCredentials_login_shouldReturnAuthResponse() {
        User user = TestResources.buildUser_JohnDoe();
        String encodedPassword = passwordEncoder.encode(TestResources.PASSWORD);
        user.setPassword(encodedPassword);
        authRepository.save(user);
        when(jwtService.generateJwtToken(TestResources.EMAIL, TestResources.ROLE.toString()))
                .thenReturn(TestResources.ACCESS_TOKEN);

        ResponseEntity<?> response = authService.login(TestResources.buildLoginRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(TestResources.buildAuthResponse());
        assertThat(tokenRepository.findByToken(TestResources.REFRESH_TOKEN)).isPresent();
    }

    @Test
    void givenInvalidEmail_login_shouldReturnUnauthorized() {
        ResponseEntity<?> response = authService.login(
                TestResources.buildLoginRequest(TestResources.INVALID_EMAIL, TestResources.PASSWORD));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(new MessageResponse("Invalid email or password"));
    }

    @Test
    void givenInvalidPassword_login_shouldReturnUnauthorized() {
        User user = TestResources.buildUser_JohnDoe();
        String encodedPassword = passwordEncoder.encode(TestResources.PASSWORD);
        user.setPassword(encodedPassword);
        authRepository.save(user);

        ResponseEntity<?> response = authService.login(
                TestResources.buildLoginRequest(TestResources.EMAIL, "wrongPassword"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(new MessageResponse("Invalid email or password"));
    }

    @Test
    void givenUniqueEmail_register_shouldReturnAuthResponse() {
        when(jwtService.generateJwtToken(TestResources.EMAIL, TestResources.ROLE.toString()))
                .thenReturn(TestResources.ACCESS_TOKEN);

        ResponseEntity<?> response = authService.register(TestResources.buildRegisterRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(TestResources.buildAuthResponse());
        Optional<User> savedUser = authRepository.findByEmail(TestResources.EMAIL);
        assertThat(savedUser).isPresent();
        assertThat(tokenRepository.findByToken(TestResources.REFRESH_TOKEN)).isPresent();
    }

    @Test
    void givenExistingEmail_register_shouldReturnConflict() {
        User user = TestResources.buildUser_JohnDoe();
        authRepository.save(user);

        ResponseEntity<?> response = authService.register(TestResources.buildRegisterRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(new MessageResponse("Email already exists"));
    }

    @ParameterizedTest
    @MethodSource("provideRefreshTestArguments")
    void givenRefreshToken_refresh_shouldHandleTokenScenarios(RefreshTokenRequest request, Token token,
                                                              ResponseEntity<?> expectedResponse) {
        if (token != null) {
            tokenRepository.save(token);
        }
        if (expectedResponse.getStatusCode() == HttpStatus.OK) {
            when(jwtService.generateJwtToken(TestResources.EMAIL, TestResources.ROLE.toString()))
                    .thenReturn(TestResources.ACCESS_TOKEN);
        }

        ResponseEntity<?> response = authService.refresh(request);

        assertThat(response.getStatusCode()).isEqualTo(expectedResponse.getStatusCode());
        if (expectedResponse.getBody() != null) {
            assertThat(response.getBody())
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse.getBody());
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            assertThat(tokenRepository.findByToken(TestResources.REFRESH_TOKEN)).isPresent();
        }
    }

    @Test
    void givenInvalidRefreshToken_refresh_shouldThrowUnauthorized() {
        RefreshTokenRequest request = new RefreshTokenRequest("invalidToken");

        assertThatThrownBy(() -> authService.refresh(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid token");
    }

    static Stream<Arguments> provideRefreshTestArguments() {
        return Stream.of(
                Arguments.of(
                        TestResources.buildRefreshTokenRequest(),
                        TestResources.buildValidToken(),
                        ResponseEntity.ok(TestResources.buildAuthResponse())
                ),
                Arguments.of(
                        TestResources.buildRefreshTokenRequest(),
                        TestResources.buildRevokedToken(),
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                ),
                Arguments.of(
                        TestResources.buildRefreshTokenRequest(),
                        TestResources.buildExpiredToken(),
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                )
        );
    }

    static class TestResources {
        public static final String FIRST_NAME = "John";
        public static final String LAST_NAME = "Doe";
        public static final String EMAIL = "john.doe@example.com";
        public static final String INVALID_EMAIL = "invalid@example.com";
        public static final String PASSWORD = "password123";
        public static final UserRole ROLE = UserRole.CANDIDATE;
        public static final String ACCESS_TOKEN = "jwt.access.token";
        public static final String REFRESH_TOKEN = "refresh-token-uuid";

        public static User buildUser_JohnDoe() {
            User user = new User();
            user.setFirstName(FIRST_NAME);
            user.setLastName(LAST_NAME);
            user.setEmail(EMAIL);
            user.setRole(ROLE);
            return user;
        }

        public static LoginRequest buildLoginRequest() {
            return new LoginRequest(EMAIL, PASSWORD);
        }

        public static LoginRequest buildLoginRequest(String email, String password) {
            return new LoginRequest(email, password);
        }

        public static RegisterRequest buildRegisterRequest() {
            return new RegisterRequest(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE);
        }

        public static RefreshTokenRequest buildRefreshTokenRequest() {
            return new RefreshTokenRequest(REFRESH_TOKEN);
        }

        public static AuthResponse buildAuthResponse() {
            return new AuthResponse(ACCESS_TOKEN, REFRESH_TOKEN, ROLE.toString());
        }

        public static Token buildValidToken() {
            Token token = new Token();
            token.setToken(REFRESH_TOKEN);
            token.setCreatedAt(LocalDateTime.now());
            token.setExpiredAt(LocalDateTime.now().plusDays(30));
            token.setRevoked(false);
            token.setUser(buildUser_JohnDoe());
            return token;
        }

        public static Token buildRevokedToken() {
            Token token = buildValidToken();
            token.setRevoked(true);
            return token;
        }

        public static Token buildExpiredToken() {
            Token token = buildValidToken();
            token.setExpiredAt(LocalDateTime.now().minusDays(1));
            return token;
        }
    }
}
