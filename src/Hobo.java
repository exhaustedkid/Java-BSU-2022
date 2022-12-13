import static java.lang.Thread.sleep;

public class Hobo implements Runnable {
    Hobo(String _name, long _stealingTime) {
        name = _name;
        stealingTime = _stealingTime;
        eatingTime = 10000;
    }

    public String getName() {
        return name;
    }

    public long getStealingTime() {
        return stealingTime;
    }

    public boolean IsCooker() {
        return isCooker;
    }

    public void sendCook() {
        isCooker = true;
    }

    public void sendSteal() {
        isCooker = false;
    }

    private void Cook() throws InterruptedException {
        sleep(3000);
        System.out.println("Food ready");
    }

    private void Eat() throws InterruptedException {
        sleep(eatingTime);
        System.out.println(name + " finished eating");
    }
    synchronized private void Steal() throws InterruptedException {
        if (!isCooker) {
            System.out.println(name + " started stealing");
            while (!Controller.getInstance().getDock().CheckCookingPossibility()) {
                sleep(stealingTime);
                Controller.getInstance().getDock().stealCargo(this);
            }
        }
    }

    private final String name;
    private final long stealingTime;
    private final long eatingTime;
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
