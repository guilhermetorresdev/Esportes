package br.gov.betim.esporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.http.HttpStatus;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> {
                    var repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
                    repository.setCookiePath("/");
                    csrf.csrfTokenRepository(repository)
                            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                            .ignoringRequestMatchers("/login", "/api/inscricoes", "/api/suporte/mensagens");
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/api/eventos/**",
                                "/api/noticias/**",
                                "/api/modalidades/**",
                                "/api/locais-esportivos/**",
                                "/api/fotos/**",
                                "/api/regionais/catalogo",
                                "/api/conquistas/**",
                                "/api/imagens/exibindo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/inscricoes", "/api/suporte/mensagens").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/suporte/status").permitAll()
                        .requestMatchers("/api/auth/me").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/csrf").permitAll()
                        .requestMatchers("/api/horto/**", "/api/eventos/**", "/api/almoxarifado/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/fotos/atualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/regionais/catalogo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/conquistas/atualizar").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value()))
                        .failureHandler((request, response, exception) -> response.sendError(HttpStatus.UNAUTHORIZED.value()))
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value())))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}
