package sample.businessLayer;

public class Order {
    private int orderID;
    private int clientID;
    private Date orderDate;
    public Order(int orderID,int clientID,Date orderDate){
        this.orderID=orderID;
        this.clientID=clientID;
        this.orderDate=orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
