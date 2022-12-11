import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Tunnel {
    Tunnel(int maxShipCount) {
        this.maxShipCount = maxShipCount;
    }
    synchronized void addShip(Ship ship) {
        if (maxShipCount > shipsInTunnelCount.get()) {

        }
    }

    synchronized void removeShip() {

    }

    int maxShipCount;
    AtomicInteger shipsInTunnelCount = new AtomicInteger(0);
    ArrayDeque<Ship> shipsInTunnel;
}
