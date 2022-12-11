import java.util.List;

import static java.lang.Thread.sleep;

public class Controller {
    Controller(long _generatingTime) throws InterruptedException {
        generatingTime = _generatingTime;
        controller = this;
        tunnel = new Tunnel(1);
        timer = new Timer();
    }

    public static Controller getInstance() {
        return controller;
    }

    synchronized public void RemoveShipFromTunnel() {
        tunnel.removeShip();
    }

    synchronized public void AddShipToTunnel(Ship ship) {
        tunnel.addShip(ship);
    }

    private void GenerateShips() throws InterruptedException {
        for (Ship ship : ships) {
            Thread thread = new Thread(ship);
            thread.start();
            sleep(generatingTime);
        }
    }

    public void setShips(List<Ship> ships) throws InterruptedException {
        this.ships = ships;
        GenerateShips();
    }

    synchronized public long GetTime() {
        return timer.GetCurrentTime();
    }

    synchronized public String GetTimeInFormat() {
        return timer.GetCurrentTimeInFormat();
    }

    private static Controller controller;
    private final long generatingTime;
    private final Tunnel tunnel;
    private final Timer timer;
    private List<Ship> ships;
    private List<Product> enableInHarbor;
    private List<Product> inDocks;
    private List<Hobo> hobos;
    private List<Product> stolenProducts;
    private List<Cargo> ableCargos;
}
