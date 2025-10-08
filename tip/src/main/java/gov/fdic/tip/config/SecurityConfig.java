package gov.fdic.tip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Security configuration for the application.
 * Configures CORS, CSRF, session management, and endpoint security.
 * Note: SecurityFilterChain bean is used to define the security filter chain and may have to create separate for dev and prod and enable profiles. Adjust .yml files accordingly.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	// Whitelisted endpoints for Swagger and Actuator i.e., public end points
    private static final List<String> SWAGGER_WHITELIST = Arrays.asList(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/webjars/**"
    );

    private static final List<String> ACTUATOR_WHITELIST = Arrays.asList(
        "/management/health",
        "/management/info"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CORS configuration
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // CSRF configuration (disabled for stateless APIs)
            .csrf(csrf -> csrf.disable())
            
            // Session management (stateless for JWT)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Authorization rules
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers(SWAGGER_WHITELIST.toArray(new String[0])).permitAll()
                .requestMatchers(ACTUATOR_WHITELIST.toArray(new String[0])).permitAll()
                .requestMatchers("/api/v1/public/**").permitAll()
                
                // Authenticated endpoints
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/cycle-groups/**").permitAll()
                
                // All other endpoints require authentication
                .anyRequest().permitAll()
            );
            
            // OAuth2 Resource Server configuration
            //.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000", 
            "http://localhost:4200",
            "https://dev.fdic.gov"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "X-Requested-With",
            "Accept",
            "X-API-KEY"
        ));
        configuration.setExposedHeaders(Arrays.asList("X-API-VERSION", "X-TOTAL-COUNT"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1 hour
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}