import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Thread.sleep;

public class Controller {
    Controller(long _generatingTime) throws IOException {
        generatingTime = _generatingTime;
        controller = this;
        tunnel = new Tunnel(2);
        timer = new Timer();
        dock = new Dock();
        FileHandler fh = new FileHandler("src/logs.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
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
    public Dock getDock() {
        return dock;
    }

    synchronized public String GetTimeInFormat() {
        return timer.GetCurrentTimeInFormat();
    }

    public void MakeInfoConsoleAndFileLog(String message) {
        logger.setUseParentHandlers(true);
        logger.info(message);
    }
    public void MakeWarnConsoleAndFileLog(String message) {
        logger.setUseParentHandlers(true);
        logger.warning(message);
    }
    public void MakeInfoFileLog(String message) {
        logger.setUseParentHandlers(false);
        logger.info(message);
    }
    public void MakeWarnFileLog(String message) {
        logger.setUseParentHandlers(false);
        logger.warning(message);
    }

    private static Controller controller;
    private final long generatingTime;
    private final Tunnel tunnel;
    private final Timer timer;
    private List<Ship> ships;
    private final Dock dock;
    private final Logger logger =  Logger.getLogger(this.getClass().getName());
}
