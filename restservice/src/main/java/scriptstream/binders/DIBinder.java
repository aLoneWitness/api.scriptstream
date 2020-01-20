package scriptstream.binders;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import scriptstream.logic.IMatchmakingLogic;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;
import scriptstream.logic.UserLogic;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.logic.repositories.contexts.memory.UserMemoryContext;
import scriptstream.util.EncryptionManager;

import javax.crypto.KeyGenerator;

public class DIBinder extends AbstractBinder {
    @Override
    protected void configure() {
        EncryptionManager encryptionManager = new EncryptionManager();
        UserRepository userRepository = new UserRepository(new UserMemoryContext());
        UserAuthLogic userAuthLogic = new UserAuthLogic(encryptionManager, userRepository);

        bind(new UserLogic(userRepository)).to(UserLogic.class);
        bind(userAuthLogic).to(UserAuthLogic.class);
        bind(MatchmakingLogic.class).to(IMatchmakingLogic.class);
        bind(KeyGenerator.class).to(KeyGenerator.class);
        bind(encryptionManager).to(EncryptionManager.class);
    }
}
