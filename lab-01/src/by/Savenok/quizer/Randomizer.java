package by.Savenok.quizer;

public class Randomizer {
    public static int GenerateNumber(int lower_bound, int upper_bound) {
        double psi = Math.random() / Math.nextDown(1.0);
        return (int) (lower_bound * (1.0 - psi) + upper_bound * psi);
    }
}
