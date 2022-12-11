public class Product {
    Product (String _name, int _count) {
        count = _count;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    final private int count;
    final private String name;
}
