package groupxii.server.security;

//TODO imports(Database, compare passwords, etc)

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication provider that verifies the validity of the provided credentials
 * from the database. (WIP)
 */
public class CredentialsDbAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
                                           throws AuthenticationException { 
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        if (principal == null || credentials == null) {
            throw new InsufficientAuthenticationException("Username or password are null");
        }

        String username = principal.toString();
        String password = credentials.toString();

        //TODO fetch users from DB
        if (username.equals("0day")) { // VERY VERY BAD
            return new UsernamePasswordAuthenticationToken(username, password);
        }

        throw new BadCredentialsException("Authentication failed");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
               UsernamePasswordAuthenticationToken.class);
    }
}
