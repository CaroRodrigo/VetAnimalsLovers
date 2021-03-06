package com.api.vet.security.config;


import com.api.vet.config.RESTAuthenticationEntryPoint;
import com.api.vet.security.UserDetailServiceImpl;
import com.api.vet.security.filter.TokenAuthenticationFilter;
import com.api.vet.security.filter.TokenAuthorizationFilter;
import com.api.vet.security.token.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

/**
 *
 * @author Rodrigo Caro
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
        // -- Swagger UI v2
        "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
        "/configuration/security", "/swagger-ui.html", "/webjars/**",
        // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs/**", "/swagger-ui/**",
        // other public endpoints of your API may be appended to this array
        "/auth/**"};

    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Value("${security.token.validator-secret}")
    private String tokenSecret;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try {
            TokenValidator tokenValidator = new TokenValidator(tokenSecret);

            // Set Filter URL
            TokenAuthenticationFilter tokenAuthenticationFilter
                    = new TokenAuthenticationFilter(authenticationManagerBean(), tokenValidator);
            tokenAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            // Habilita CORS y desactiva CSRF
            http.cors().and().csrf().disable();

            // Permitir todo en /**/auth/**
            http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest()
                    // El Resto de las rutas, requeriran autenticaci??n
                    .authenticated();

            // Seteo de session management a stateless
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests().antMatchers(HttpMethod.GET, "/**")
                    .hasAnyAuthority("ROL_ADMIN", "ROL_USER").antMatchers(HttpMethod.POST, "/**")
                    .hasAuthority("ROL_ADMIN").antMatchers(HttpMethod.PUT, "/**").hasAuthority("ROL_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ROL_ADMIN").and().requestCache()
                    .requestCache(new NullRequestCache()).and().httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint);

            // Add JWT Token Authorization filter
            http.addFilterBefore(new TokenAuthorizationFilter(tokenValidator),
                    UsernamePasswordAuthenticationFilter.class);

            // Add JWT Token Authentication filter
            http.addFilter(tokenAuthenticationFilter);

        } catch (Exception ex) {
            ex.getMessage();
        }

    }
}
