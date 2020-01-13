package scriptstream.binders;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;
import scriptstream.util.EncryptionManager;

import javax.crypto.KeyGenerator;

public class DIBinder extends AbstractBinder {
    @Override
    protected void configure() {
        EncryptionManager encryptionManager = new EncryptionManager();
        UserAuthLogic userAuthLogic = new UserAuthLogic(encryptionManager);

        bind(userAuthLogic).to(UserAuthLogic.class);
        bind(MatchmakingLogic.class).to(MatchmakingLogic.class);
        bind(KeyGenerator.class).to(KeyGenerator.class);
        bind(encryptionManager).to(EncryptionManager.class);
    }
}
