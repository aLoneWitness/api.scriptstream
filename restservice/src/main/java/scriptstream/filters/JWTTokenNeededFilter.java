package scriptstream.filters;

import io.jsonwebtoken.Jwts;
import scriptstream.util.EncryptionManager;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
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


    private EncryptionManager encryptionManager;

    @Inject
    public JWTTokenNeededFilter(EncryptionManager encryptionManager) {
        this.encryptionManager = encryptionManager;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        try {
            String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring("Bearer".length()).trim();
            try {
                Key key = encryptionManager.getEncryptionKey();
                Jwts.parser().setSigningKey(key).parseClaimsJws(token);
                System.out.println("#### VALID TOKEN : " + token);
            } catch (Exception e) {
                System.out.println("#### INVALID TOKEN : " + token);
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }catch (Exception e) {
            System.out.println("#### NO TOKEN PRESENT WHEN ATTEMPTED AUTHENTICATION REQUEST");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
