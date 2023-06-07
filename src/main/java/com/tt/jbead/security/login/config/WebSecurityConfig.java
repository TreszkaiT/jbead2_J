package com.tt.jbead.security.login.config;

import com.tt.jbead.security.login.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig{

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;                                                      // a Filterem alul kell beregisztrálni a Stringnek, hogy tudjon róla


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

//    @Bean
//    AuthenticationManager configProviders(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthenticationProvider());
//        authenticationManagerBuilder.authenticationProvider(smsAuthenticationProvider());
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
//        return authConfiguration.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
        return authenticationConfiguration.getAuthenticationManager();
    }


//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {         // alapból nem férhető hozzá, csak így. De ezután már tudjuk használni a congtrollerben ellenőrzésre
//        return super.authenticationManagerBean();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()                                                                           // csrf token: hidden mezőbe tenne küldene egy generált tokent. De RestApi-nál ez nem kell
//                .authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//                .anyRequest().authenticated()                                                           // minden más végpontot csak a beauthenticált felhasználók nézhetik meg
//                .and().sessionManagement()                                                              // session kezelést kapcsolja ki az applikáció kezelést (session: böngészőbe tárol bizonyos adatokat. Felhasználót pl. Így megjegyzi, és legközelebb már bejelentkezve lépek be.)  Itt apiknál nem kell. Itt a kliens feladata, hogy cookie-ba tárolja a tokeneket.
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);             // a Filterünket kell beregisztrálnunk, hogy a Spring ezt tudja használni...  Filer típusa: UsernamePasswordAuthenticationFilter
//        //http.addFilterAfter(jwtRequestFilter, AnonymousAuthenticationFilter.class);
//    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()                                                           // minden más végpontot csak a beauthenticált felhasználók nézhetik meg
                .and().sessionManagement()                                                              // session kezelést kapcsolja ki az applikáció kezelést (session: böngészőbe tárol bizonyos adatokat. Felhasználót pl. Így megjegyzi, és legközelebb már bejelentkezve lépek be.)  Itt apiknál nem kell. Itt a kliens feladata, hogy cookie-ba tárolja a tokeneket.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);             // a Filterünket kell beregisztrálnunk, hogy a Spring ezt tudja használni...  Filer típusa: UsernamePasswordAuthenticationFilter
        //http.addFilterAfter(jwtRequestFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
