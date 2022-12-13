import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Dock {
    Dock() {
    }

    private void GenerateHobos() {
        if (firstProductArrived) {
            firstProductArrived = false;
            for (Hobo hobo : hobos) {
                Thread thread = new Thread(hobo);
                thread.start();
            }
        }
    }

    synchronized public boolean IsCrowdHungry() {
        return isCrowdHungry;
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

    synchronized public void stealCargo(Hobo hobo) {
        int cargoNumber = (int) (Math.random() * (cargosInDocks.size()));
        if (cargosInDocks.get(cargoNumber).getCount() > 0) {
            System.out.println(hobo.getName() + " stolen 1 " + cargosInDocks.get(cargoNumber).getName());
            hobosProducts.get(cargoNumber).IncreaseCount(1);
            cargosInDocks.get(cargoNumber).DecreaseCount(1);
        }
    }

    private List<Cargo> cargosInDocks;
    private List<Product> ingredients;
    private List<Product> hobosProducts;
    private List<Hobo> hobos;
    private boolean firstProductArrived = true;
    private boolean isCrowdHungry = true;
}
