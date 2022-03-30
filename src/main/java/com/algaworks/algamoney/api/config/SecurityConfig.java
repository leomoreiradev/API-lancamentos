package com.algaworks.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration // Indicando que é uma classe de configuracao
@EnableWebSecurity // Habilita segurança
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    //Configurar autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ROLE");
    }

    //Configurar autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                        .antMatchers("/categorias").permitAll() // não precisa de autorização
                        .anyRequest().authenticated() // precisa de autorização
                        .and()
                    .httpBasic() // tipo de autorizacao
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sem sessao
                    .and()
                    .csrf().disable();
    }
}
