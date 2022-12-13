import java.util.List;

import static java.lang.Thread.sleep;

public class Ship implements Runnable {

    Ship(String name, long capacity, Cargo cargo) {
        this.name = name;
        this.capacity = capacity;
        this.cargo = cargo;
    }

    public String getName() {
        return name;
    }

    long getCapacity() {
        return capacity;
    }

    public Cargo getCargo() {
        return cargo;
    }

    private final String name;
    private final long capacity;
    private final Cargo cargo;
    private boolean ableToJoinTunnel = true;

    public void setAbleToJoinTunnel(boolean ableToJoinTunnel) {
        this.ableToJoinTunnel = ableToJoinTunnel;
    }

    @Override
    public void run() {
        System.out.println(Controller.getInstance().GetTimeInFormat() + " " + name + " ship started with " + cargo.getName() + " " + cargo.getCount());
        try {
            SwimToTunnel();
            Controller.getInstance().AddShipToTunnel(this);
            if (!ableToJoinTunnel) {
                System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " drown");
                return;
            }
            SwimInTunnel();
            Controller.getInstance().RemoveShipFromTunnel();
            SwimToDock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void SwimToDock() throws InterruptedException {
        sleep(7000);
        System.out.println(Controller.getInstance().GetTimeInFormat() + " ship " + name + " got to dock");
        Controller.getInstance().getDock().AddCargo(cargo);
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
