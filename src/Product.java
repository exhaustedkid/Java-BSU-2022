public class Product {
    Product (String _name, long _count) {
        count = _count;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

    synchronized public void DecreaseCount(long value) {
        count -= value;
    }
    synchronized public void IncreaseCount(long value) {
        count += value;
    }


    private long count;
    final private String name;
}
