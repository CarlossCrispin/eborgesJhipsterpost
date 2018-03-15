package org.jhipster.cinvestav.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    // agregabdo el rol de Investigador  
    //5;investigador;$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K;Investigador;Investigador;investigador@localhost;;true;es;system;system
    //5;investigador;$2a$10$bVPU2cSbyJIPN4u7mYRXieOfcAi1r4/odba/KiGD9c.uqt.k2Yf62;Investigador;Investigador;investigador@localhost;;true;es;system;system
    public static final String INVESTIGADOR = "ROLE_INVESTIGADOR";

    private AuthoritiesConstants() {
    }
}
