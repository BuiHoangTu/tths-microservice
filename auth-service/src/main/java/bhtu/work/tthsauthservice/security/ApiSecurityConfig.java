package bhtu.work.tthsauthservice.security;

import bhtu.work.tthsauthservice.security.jwt.AuthEntryPointJwt;
import bhtu.work.tthsauthservice.security.jwt.AuthTokenFilter;
import bhtu.work.tthsauthservice.security.services.MyUserDetailsService;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
// @Order(SecurityProperties.IGNORED_ORDER)
public class ApiSecurityConfig {
    private final MyUserDetailsService myUserDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    @Value("${gui.uri}")
    private String[] guiUrl;

    @Autowired
    public ApiSecurityConfig(MyUserDetailsService myUserDetailsService, AuthEntryPointJwt unauthorizedHandler,
            AuthTokenFilter authenticationJwtTokenFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }

    @Bean
    public String[] getGuiUrl() {
        return this.guiUrl;
    }

    /**
     * define how to encrypt password
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        var provider = this.authenticationProvider();
        return new ProviderManager(provider);
    }

    private static final String[] openUrls = { "/api/open/**", "/api/auth/login", "/api/auth/signup" };

    @Bean
    @Autowired
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((configure) -> {
            configure.configurationSource(corsConfigurationSource());
        }); // allow all diff ports access

        http.csrf((httpSecurityCsrfConfigurer) -> {
            httpSecurityCsrfConfigurer.disable();
        });

        http.exceptionHandling((x) -> x.authenticationEntryPoint(unauthorizedHandler));
        http.sessionManagement((x) -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((requests) -> {
            requests
                    .antMatchers(openUrls).permitAll() // open, auth apis are for all
                    .antMatchers("/api/**").authenticated() // any other request to /api must be
                                                                // authenticated first
                    .anyRequest().permitAll(); // non api prefix is not in our control, pass it
        });

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configure allowed origins, methods, headers, and credentials
        configuration.setAllowedOrigins(List.of(getGuiUrl()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
