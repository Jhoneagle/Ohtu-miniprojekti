package ohtu;

import ohtu.mocks.KommenttiDaoForTests;
import ohtu.mocks.UserDaoForTests;
import ohtu.mocks.VinkkiDaoForTests;
import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);
        Main.userDao = new UserDaoForTests();
        Main.vinkkiDao = new VinkkiDaoForTests();
        Main.kommenttiDao = new KommenttiDaoForTests();
        Main.main(null);
    }

    @Override
    protected void after() {
        Spark.stop();
    }
}
