import java.util.List;

import static java.lang.Thread.sleep;

public class Ship implements Runnable {

    Ship(String _name, long _capacity, List<String> _cargo) {
        name = _name;
        capacity = _capacity;
        cargo = _cargo;
    }

    public String getName() {
        return name;
    }

    long getCapacity() {
        return capacity;
    }

    public List<String> getCargo() {
        return cargo;
    }

    public void Sink() {

    }

    private final String name;
    private final long capacity;
    private final List<String> cargo;
    private boolean ableToJoinTunnel = true;

    public void setAbleToJoinTunnel(boolean ableToJoinTunnel) {
        this.ableToJoinTunnel = ableToJoinTunnel;
    }

    @Override
    public void run() {
        System.out.println(Controller.getInstance().GetTimeInFormat() + " " + name + " ship started");
        try {
            SwimToTunnel();
            Controller.getInstance().AddShipToTunnel(this);
            if (!ableToJoinTunnel) {
                System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " drown");
                return;
            }
            SwimInTunnel();
            Controller.getInstance().RemoveShipFromTunnel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void SwimInTunnel() throws InterruptedException {
        System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " in Tunnel");
        sleep(5000);
        System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " got out of Tunnel");
    }

    private void SwimToTunnel() throws InterruptedException {
        sleep(5000);
        System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " got to Tunnel");
    }
}
