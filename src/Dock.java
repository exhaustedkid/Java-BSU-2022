import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Dock {
    Dock() {
    }

    private void GenerateHobos() {
        if (firstProductArrived) {
            firstProductArrived = false;
            ChooseCookers();
            for (Hobo hobo : hobos) {
                Thread thread = new Thread(hobo);
                thread.start();
            }
        }
    }

    public void SendReadySignal() throws InterruptedException {
        ++readySignals;
        if (readySignals == hobos.size() - 2) {
            StartCooking();
        }
    }

    public void StartCooking() throws InterruptedException {
        Controller.getInstance().MakeInfoConsoleAndFileLog("+(" +
                Controller.getInstance().GetTimeInFormat() +
                ") " + "Crowd start eating");
        sleep(eatingTime);
        for (int i = 0; i < hobosProducts.size(); ++i) {
            hobosProducts.get(i).DecreaseCount(ingredients.get(i).getCount());
        }
        Controller.getInstance().MakeWarnConsoleAndFileLog("+(" +
                Controller.getInstance().GetTimeInFormat() +
                ") " + "Crowd hungry");
        firstProductArrived = true;
        readySignals = 0;
        System.out.println("Hobos have:");
        for (Product product : hobosProducts) {
            System.out.print(product + " ");
        }
        System.out.println("\n");
        for (Hobo hobo : hobos) {
            hobo.sendSteal();
        }
        GenerateHobos();
    }

    private void ChooseCookers() {
        int num1 = 0;
        int num2 = 0;
        while (num1 == num2) {
            num1 = (int) (Math.random() * (hobos.size()));
            num2 = (int) (Math.random() * (hobos.size()));
        }
        hobos.get(num1).sendCook();
        hobos.get(num2).sendCook();
        Controller.getInstance().MakeInfoConsoleAndFileLog(hobos.get(num1).getName()
                + " and " + hobos.get(num2).getName() + " are cookers");
    }

    synchronized public void AddCargo(Cargo newCargo) {
        for (Cargo cargo : cargosInDocks) {
            if (Objects.equals(cargo.getName(), newCargo.getName())) {
                cargo.IncreaseCount(newCargo.getCount());
            }
        }
        GenerateHobos();
    }

    public void setIngredients(List<Product> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCargosInDocks(List<Cargo> cargosInDocks) {
        this.cargosInDocks = cargosInDocks;
    }

    synchronized public boolean CheckCookingPossibility() {
        for (int i = 0; i < hobosProducts.size(); ++i) {
            if (hobosProducts.get(i).getCount() < ingredients.get(i).getCount()) {
                return false;
            }
        }
        return true;
    }

    public void setHobosProducts(List<Product> hobosProducts) {
        this.hobosProducts = hobosProducts;
    }

    synchronized public void setHobos(List<Hobo> hobos) {
        this.hobos = hobos;
    }

    public void setEatingTime(long eatingTime) {
        this.eatingTime = eatingTime;
    }

    synchronized public void stealCargo(Hobo hobo) {
        int cargoNumber = (int) (Math.random() * (cargosInDocks.size()));
        if (cargosInDocks.get(cargoNumber).getCount() > 0) {
            Controller.getInstance().MakeWarnFileLog("+(" +
                    Controller.getInstance().GetTimeInFormat() +
                    ") " + hobo.getName() +
                    " stolen 1 " +
                    cargosInDocks.get(cargoNumber).getName());
            hobosProducts.get(cargoNumber).IncreaseCount(1);
            cargosInDocks.get(cargoNumber).DecreaseCount(1);
        }
    }

    private List<Cargo> cargosInDocks;
    private List<Product> ingredients;
    private List<Product> hobosProducts;
    private List<Hobo> hobos;
    private boolean firstProductArrived = true;
    private int readySignals = 0;
    private long eatingTime;
}
