package scriptstream.logic;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import scriptstream.entities.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class UserAuthLogic {
    private final String CLIENT_ID = "156633696944-p3ud87unloob34dobt770plf00hliqhu.apps.googleusercontent.com";
    private GoogleIdTokenVerifier verifier;

    public UserAuthLogic() {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(Collections.singletonList(CLIENT_ID)).build();
    }

    public boolean login(User user){
        try {
            GoogleIdToken idToken = verifier.verify(user.gToken);
            if(idToken.verify(verifier)) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                user.name = (String) payload.get("name");
//                PersistencyManager.getEntityManager().insert(user);
                return true;
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
