package pojo;

public class AcceptOrder {
    private int id;
    private int courierId;

    public AcceptOrder() {
    }

    public AcceptOrder(int id, int courierId) {
        this.id = id;
        this.courierId = courierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }
}
