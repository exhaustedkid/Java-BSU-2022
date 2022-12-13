import static java.lang.Thread.sleep;

public class Hobo implements Runnable {
    Hobo(String _name, long _stealingTime) {
        name = _name;
        stealingTime = _stealingTime;
    }

    public String getName() {
        return name;
    }

    public void sendCook() {
        isCooker = true;
    }

    public void sendSteal() {
        isCooker = false;
    }
    synchronized private void Steal() throws InterruptedException {
        if (!isCooker) {
            System.out.println(name + " started stealing");
            while (!Controller.getInstance().getDock().CheckCookingPossibility()) {
                sleep(stealingTime);
                Controller.getInstance().getDock().stealCargo(this);
            }
        }
            Controller.getInstance().getDock().SendReadySignal();
    }

    private final String name;
    private final long stealingTime;
    boolean isCooker = false;
    @Override
    public void run() {
        try {
            Steal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
