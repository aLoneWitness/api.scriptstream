package scriptstream.binders;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import scriptstream.logic.UserAuthLogic;

public class DIBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserAuthLogic.class).to(UserAuthLogic.class);
    }
}
