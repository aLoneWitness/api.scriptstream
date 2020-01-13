package scriptstream.filters;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    private KeyGenerator keyGenerator;

    public JWTTokenNeededFilter() {
        try {
            this.keyGenerator = KeyGenerator.getInstance("AES (128)");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        String token = authHeader.substring("Bearer".length()).trim();

        try {
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            System.out.println("#### VALID TOKEN : " + token);
        } catch (Exception e) {
            System.out.println("#### INVALID TOKEN : " + token);
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
