public class Hobo {
    Hobo(String _name, int _stealingTime) {
        name = _name;
        stealingTime = _stealingTime;
    }

    public String getName() {
        return name;
    }

    public int getStealingTime() {
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

    private final String name;
    private final int stealingTime;
    boolean isCooker = false;
}
