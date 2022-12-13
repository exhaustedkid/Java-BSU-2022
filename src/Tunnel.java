import java.util.ArrayDeque;

public class Tunnel {
    Tunnel(int maxShipCount) {
        this.maxShipCount = maxShipCount;
        shipsInTunnel = new ArrayDeque<>();
    }

    synchronized public void addShip(Ship ship) {
        if (maxShipCount == shipsInTunnelCount) {
            ship.setAbleToJoinTunnel(false);
        } else {
            shipsInTunnel.add(ship);
            ++shipsInTunnelCount;
        }
    }

    synchronized public void removeShip() {
        if (shipsInTunnelCount > 0) {
            shipsInTunnel.pop();
            --shipsInTunnelCount;
        }
    }

    int maxShipCount;
    int shipsInTunnelCount;
    ArrayDeque<Ship> shipsInTunnel;
}
