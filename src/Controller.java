import java.util.List;

import static java.lang.Thread.sleep;

public class Controller {
    Controller(long _generatingTime) throws InterruptedException {
        generatingTime = _generatingTime;
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

    private final long generatingTime;
    private List<Ship> ships;
    private List<Product> enableInHarbor;
    private List<Product> inDocks;
    private List<Hobo> hobos;
    private List<Product> stolenProducts;
    private List<Cargo> ableCargos;
}
