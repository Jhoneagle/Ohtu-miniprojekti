package ohtu;

import ohtu.logistic.KommenttiLogic;
import ohtu.logistic.VinkkiLogic;
import ohtu.mocks.*;
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
        Main.vinkkiController = new VinkkiLogic(new VinkkiDaoForTests());
        Main.kommenttiController = new KommenttiLogic(new KommenttiDaoForTests());
        Main.main(null);
    }

    @Override
    protected void after() {
        Spark.stop();
    }
}
