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

    private final String name;
    private final long capacity;
    private final List<String> cargo;
    private boolean waitingForTunnel = false;

    @Override
    public void run() {
        System.out.println(name + " ship started");
        try {
            Swim();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void Swim() throws InterruptedException {
        sleep(5000);
        System.out.println("ship " + name + " got to Tunnel \n");
    }
}
