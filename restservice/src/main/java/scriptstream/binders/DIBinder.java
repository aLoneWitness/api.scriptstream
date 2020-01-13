package scriptstream.binders;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;

import javax.crypto.KeyGenerator;

public class DIBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserAuthLogic.class).to(UserAuthLogic.class);
        bind(MatchmakingLogic.class).to(MatchmakingLogic.class);
        bind(KeyGenerator.class).to(KeyGenerator.class);
    }
}
