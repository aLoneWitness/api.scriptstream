package scriptstream.logic;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import scriptstream.entities.User;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.util.EncryptionManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class UserAuthLogic {
    private final String CLIENT_ID = "156633696944-p3ud87unloob34dobt770plf00hliqhu.apps.googleusercontent.com";
    private GoogleIdTokenVerifier verifier;

    private EncryptionManager encryptionManager;

    private UserRepository userRepository;

    public UserAuthLogic(EncryptionManager encryptionManager, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(Collections.singletonList(CLIENT_ID)).build();
        this.encryptionManager = encryptionManager;
    }

    public AbstractMap.SimpleEntry<String, User> login(User user) {
        try {
            GoogleIdToken idToken = verifier.verify(user.gToken);
            if(idToken.verify(verifier)) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                user.name = (String) payload.get("name");
                UUID uuid = UUID.nameUUIDFromBytes(payload.getSubject().getBytes());
//                String uId = payload.getSubject();
//                user.uuid = UUID.nameUUIDFromBytes(uId.getBytes());
                user.uuid = uuid;
                if(!userRepository.exists(user)){
                    userRepository.create(user);
                }

                return new AbstractMap.SimpleEntry<>(issueToken(uuid.toString()), user);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public User getUserByGToken(String gToken) {
//        User user = new User();
//        user.gToken = gToken;
//        return userRepository.read(user);
//    }

    private String issueToken(String uuid) {
        try{
            Key key = encryptionManager.getEncryptionKey();

            Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            Instant expiration = issuedAt.plus(9999, ChronoUnit.MINUTES);

            return Jwts.builder()
                    .setSubject(uuid)
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
