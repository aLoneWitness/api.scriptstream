package scriptstream.logic;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import scriptstream.entities.User;
import scriptstream.util.EncryptionManager;

import javax.crypto.KeyGenerator;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

public class UserAuthLogic {
    private final String CLIENT_ID = "156633696944-p3ud87unloob34dobt770plf00hliqhu.apps.googleusercontent.com";
    private GoogleIdTokenVerifier verifier;

    private EncryptionManager encryptionManager;

    public UserAuthLogic(EncryptionManager encryptionManager) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(Collections.singletonList(CLIENT_ID)).build();
        this.encryptionManager = encryptionManager;
    }

    public String login(User user) {
        try {
            GoogleIdToken idToken = verifier.verify(user.gToken);
            if(idToken.verify(verifier)) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                user.name = (String) payload.get("name");
//                PersistencyManager.getEntityManager().insert(user);
                return issueToken(user.gToken);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String issueToken(String login) {
        try{
            Key key = encryptionManager.getEncryptionKey();

            Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            Instant expiration = issuedAt.plus(15, ChronoUnit.MINUTES);

            return Jwts.builder()
                    .setSubject(login)
                    .setIssuer(InetAddress.getLocalHost().toString())
                    .setIssuedAt(Date.from(issuedAt))
                    .setExpiration(Date.from(expiration))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }

        return null;
    }
}
