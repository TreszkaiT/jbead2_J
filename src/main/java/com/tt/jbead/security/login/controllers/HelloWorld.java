package com.tt.jbead.security.login.controllers;


import com.tt.jbead.security.login.models.AuthenticationRequest;
import com.tt.jbead.security.login.models.AuthenticationResponse;
import com.tt.jbead.security.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorld {

                            // AuthenticationManager: azért kell, mert eltérünk a Spring automatikus belépésétől, hiszen tokent használunk. De a belépés leellenőrzéséhez mégiscsak kell majd lentebb
                            //  SecurityConfigurer class-ban még @Bean-ként ezt fel kell venni: AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;                            // Spring modulja, mely a bejelentkezési logikát csinálja; ezzel ellenőrizzük le, hogy mi valós felhasználó-jelszó párossal akarunk-e bejelentkezni v. sem.... Mivel eltérünk a Spring gyári bejelentkeztető rendszerétől, ui. tokenekkel jelentkezünk be. ezért kell ez

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping({ "/hello" })
    public String hello() {
        return "Hello World";
    }

    /**
     * TOKEN GENERALASHOZ // BELEPESHEZ SZUKSEGES API   --  Ez csak a tokent generálja le belépéskor
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)                                               // ResponseEntity<?> == ResponseEntity<Object> azaz bármi mehet bele. De itt: ResponseEntity<AuthenticationResponse>
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {      // beolvassuk a AuthenticationRequest : felhasználónév-jelszó páros, melyet elküldtünk
        // AUTHENTIKÁLJUK A USERT
        // HIBAS CREDENTAILS ESETEN BadCredentailsException
        // HA A KOVETKEZO SOR LEMEGY SIKERESEN AKKOR TUDJUK HOGY AUTHENTIKALVA VAGYUNK
        authenticationManager.authenticate(                                                                             // jó-e a felhasználónév-jelszó páros
                new UsernamePasswordAuthenticationToken(                                                                // Spring ezt az obj-ot használja az authentikációhoz
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // BETOLTJUK A BEAUTHENTIKALT USER A DB-BŐL.
        final UserDetails userDetails
                = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());                           // ha jó, akkor felhasználót kikérjük a userservice-ből

        // UTIL SEGITSEGEVEL LEGENERALJUK A TOKENT
        final String jwt = jwtUtil.generateToken(userDetails);                                                          // a felhasználóból generál nekem egy tokent

        // TOKEN-T A RESPONSE-BAN VISSZAADJUK
        return ResponseEntity.ok(new AuthenticationResponse(jwt));                                                      // és a tokent visszaadjuk : ResponseEntity -- a válasznak adhatok beállítást   .ok() = 200  .status(500) = saját status
    }
}
