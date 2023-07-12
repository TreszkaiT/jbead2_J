package com.tt.jbead.security.login.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Tokeneket kezeli
 * - újakat generál
 * - kinyer belőlük felhasználónevet
 * - kinyeri belőlük, hogy meddig érvényes a token
 *
 * Tokenben bele van generálva: felhasználónév, lejárati dátum, ip-cím
 */
@Service
public class JwtUtil {

    /**
     * SECRET USED FOR JWT GENERATION
     */
    //private final String SECRET_KEY = "something4rDerplzDsdfsadfdsaadsdfadfdafdssdfsf";                         // salting: azaz a token generálásához/kódolásához használja.  Biztonságos, ha minél bonyolultabb ez a kulcs Egyszer kell csak megadni, utána ne változtasd meg sosem.
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String extractUsername(String token) {                                           // tokenben lévő felhasználónév
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {                                           // token lejárati ideje
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * eyJ0eXAiOiJKV1QiLA0KICJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb2UiLA0
     * Egy token 2 "."-al elválasztott egységből áll
     * az egyes egységek a Claim-ek.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {           // ezt nem használjuk, beépített; a többi metódus használja őket
        final Claims claims = extractAllClaims(token);                                      // a Claim-ek-ből tudja kiszedni az adatokat
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {                                          // lejárt-e a token v. sem
        return extractExpiration(token).before(new Date());                                 // ha a mostani időpont a token lejárati ideje előtt van, akkor true
    }

    public String generateToken(UserDetails userDetails) {                                  // ez generál egy tokent --> meghívja az alatta lévő metódust -->
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {                // ez gyártja le ténylegesen a tokent
        return Jwts.builder()                                                               // egy builder készíti
                .setClaims(claims)
                .setSubject(subject)                                                        // eu a Username, az egyel felette lévő generateToken() return 2. paramétere
                .setIssuedAt(new Date(System.currentTimeMillis()))                              // token létrejötte
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))      // meddig érvényes  (jelenlegi 1000 ms *... = 10 óra összesen)
                .signWith(SECRET_KEY).compact();                      // kódolás milyen legyen hozzá
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);                                             // kinyeri a tokenből a Usernevet, és
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));              // ezt összehasonlítja a kapott Usernévvel, és megnézi, hogy nincs-e lejárva
    }
}
