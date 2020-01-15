package scriptstream.authentication;

import org.junit.jupiter.api.Test;
import scriptstream.entities.User;
import scriptstream.logic.UserAuthLogic;
import scriptstream.repositories.UserRepository;
import scriptstream.util.EncryptionManager;

public class AuthLogicTests {


    @Test
    public void twoAccountsWithSameGTokenShouldReturnSameUser() {
        UserAuthLogic authLogic = new UserAuthLogic(new EncryptionManager(), new UserRepository());

    }
}
