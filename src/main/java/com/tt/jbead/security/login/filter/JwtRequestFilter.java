package com.tt.jbead.security.login.filter;

import com.tt.jbead.security.login.config.MyUserDetailsService;
import com.tt.jbead.security.login.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * - Filterek egy request szerverre való beérkezése előtt/után tudnak lefutni!
 *
 * Filter szerepe: ha beérkezik egy kérés az api-mba, mielőtt még a Contorller beindul, tudunk előtte végezni extra műveleteket
 *
 * Ha már van egy generált token-ünk, akkor egy másik oldal megnyitásakor ez hasonlítja össze a kapott tokennel, hogy beléphetek-e vagy sem
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {                                                // OncePerRequestFilter -- ettől lesz filter

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * - Itt történik a request feldolgozása!
     * - UsernamePasswordAuthenticationToken -> olyasmi mint usereknél a UserDetails volt, tehát egy a Spring-ben
     *   definialt objektum amit az autentikaciohoz hasznal. Tartalmazza a felhasznalo objektumot (principal),
     *   [credentialoket], es a felhasznalo jogkoreit, illetve beallithato neki hogy milyen IP alol erkezett a keres,
     *   es hogy milyen sessionID tartozik a klienshez.
     * - WebAuthenticationDetailsSource -> o az objektum ami tartalmazza az IP-t es a session ID-t.
     *
     * Ezt a Filtert még a SecurityConfigurer-ben be kell regisztrálni a Spring tudja, hogy használnia kell
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {       // FilterChain : láncolt listába vannak a filterek... és ez tartalmazza az összes többi filtert
        final String authHeader = request.getHeader("Authorization");                                               // a request Header részéből kinyerjük a tokent, melynek neve(kulcsa) ugye Authorization

        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {                                                   // sikerült-e kinyerni, és ez a token a Bearer szóval kezdődik-e
            jwt = authHeader.substring(7);                                                                     // ha igen, akkor kivágjuk a 7. karaktertől a végéig (azaz a Bearer szót vágjuk le előről)
            username = jwtUtil.extractUsername(jwt);                                                                    // usernevet is kiszedjük a tokenből
        }

        // VAN FELHASZNALONEV ES NEM VAGYUNK BEJELENTKEZVEKM,
        if (username != null &&  SecurityContextHolder.getContext().getAuthentication() == null) {                      // sikerült-e kinyerni felhasználót,... és be vagyunk-e jelentkezve: SecurityContextHolder()-ez tartalmazza a bejelentkezett felhasználót
            UserDetails user = this.userDetailsService.loadUserByUsername(username);                                    // DB-ből kikérjük a Usr obj-ot

            if (jwtUtil.validateToken(jwt, user)) {                                                                     // ez a token valóban ehhez a user-hez tartozik-e --> ha igen, akkor beauthentikál a Springbe
                // HA VALID A TOKEN BE KELL JELENTKEZTETNI SPRINGBE, EZ TORTENIK A TOKENNEL.
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());                                                    // null helyett itt user passwordjét is get-elhetjük, de nem gond

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                           // WebAuthenticationDetails--- a token a mi ip-címünkhöz kapcsolódik innentől

                SecurityContextHolder.getContext().setAuthentication(token);                                            // Springnek jelezzük, hogy ez a felhasználó be van jelentkezve
            }
        }

        filterChain.doFilter(request, response);                                                                        // a filterchain láncolt listában ugorhat a következő filterre ezután, ha van még benne
    }
}
