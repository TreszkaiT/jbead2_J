package com.tt.jbead.security.login.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Ebben van a válasz, amit kapunk... egy db. jwt - kulcs értékpár
 * jwt : lkédfhjdshfihjkfsdékldkslkgdkhdé...
 */
@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private final String jwt;

}
