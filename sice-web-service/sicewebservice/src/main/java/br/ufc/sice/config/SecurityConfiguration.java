package br.ufc.sice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("SecurityConfiguration --- globalUserDetails");
        auth.inMemoryAuthentication().withUser("marissa").password("koala").roles("USER").and().withUser("paul")
                .password("emu").roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	System.out.println("SecurityConfiguration --- configure");
        web.ignoring().antMatchers("/webjars/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	System.out.println("SecurityConfiguration --- authenticationManagerBean");
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("SecurityConfiguration --- configure");
        // @formatter:off
    	http
            .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest().hasRole("USER")
            .and()
//            .exceptionHandling()
//                .accessDeniedPage("/login.jsp?authorization_error=true")
//                .and()
            // TODO: put CSRF protection back into this endpoint
            .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable();
//            .logout()
//            	.logoutUrl("/logout")
//                .logoutSuccessUrl("/login.jsp")
//                .and()
//            .formLogin()
//            	.loginProcessingUrl("/login")
//                .failureUrl("/login.jsp?authentication_error=true")
//                .loginPage("/login.jsp");
        // @formatter:on
    }
}
