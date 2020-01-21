package scriptstream.binders;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import scriptstream.logic.*;
import scriptstream.logic.repositories.ProjectRepository;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.logic.repositories.contexts.memory.ProjectMemoryContext;
import scriptstream.logic.repositories.contexts.memory.UserMemoryContext;
import scriptstream.util.EncryptionManager;

import javax.crypto.KeyGenerator;

public class DIBinder extends AbstractBinder {
    @Override
    protected void configure() {
        EncryptionManager encryptionManager = new EncryptionManager();
        UserRepository userRepository = new UserRepository(new UserMemoryContext());
        UserAuthLogic userAuthLogic = new UserAuthLogic(encryptionManager, userRepository);
        ProjectRepository projectRepository = new ProjectRepository(new ProjectMemoryContext());

        bind(new ProjectLogic(projectRepository, userRepository)).to(ProjectLogic.class);
        bind(new UserLogic(userRepository)).to(UserLogic.class);
        bind(userAuthLogic).to(UserAuthLogic.class);
        bind(new MatchmakingLogic()).to(MatchmakingLogic.class);
        bind(KeyGenerator.class).to(KeyGenerator.class);

        bind(encryptionManager).to(EncryptionManager.class);
    }
}
