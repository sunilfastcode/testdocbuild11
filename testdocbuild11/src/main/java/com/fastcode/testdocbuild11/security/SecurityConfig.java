package com.fastcode.testdocbuild11.security;

import static com.fastcode.testdocbuild11.security.SecurityConstants.CONFIRM;
import static com.fastcode.testdocbuild11.security.SecurityConstants.REGISTER;

import com.fastcode.testdocbuild11.domain.core.authorization.user.IUserManager;
import javax.naming.AuthenticationNotSupportedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private final UserDetailsServiceImpl userDetailsService;

    @NonNull
    private final Environment env;

    @Qualifier("userManager")
    @NonNull
    private final IUserManager userManager;

    @NonNull
    private final ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .ignoringAntMatchers("/auth", "/auth/logout")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(
                "/auth",
                "/reporting/viewResource/**",
                "/register/**",
                "/password/forgot",
                "/password/reset",
                "/v2/api-docs",
                "/actuator/**",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/browser/index.html#",
                "/browser/**"
            )
            .permitAll()
            .antMatchers(HttpMethod.POST, REGISTER)
            .permitAll()
            .antMatchers(HttpMethod.POST, CONFIRM)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), context, userManager))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), context));
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (env.getProperty("fastCode.auth.method").equalsIgnoreCase("database")) {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        } else {
            throw new AuthenticationNotSupportedException();
        }
    }
}
