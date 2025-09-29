package com.metehanayhan.TaskTracker.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        // YENİ KURAL: OPTIONS isteklerine kimlik sormadan her zaman izin ver.
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ESKİ KURALIMIZ: Görevler API'ı için rol kontrolü yap.
                        .requestMatchers("/api/tasks/**").hasRole("app_user")

                        // DİĞER TÜM İSTEKLER: Kimlik doğrulaması gerektirir.
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    // Bu metod, Keycloak'tan gelen Rolleri Spring Security'nin anlayacağı formata çevirir.
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null || realmAccess.isEmpty()) {
                return Set.of();
            }
            return realmAccess.get("roles").stream()
                    .map(roleName -> "ROLE_" + roleName) // Spring Security'nin beklediği "ROLE_" ön ekini ekliyoruz.
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        });
        return jwtConverter;
    }
}