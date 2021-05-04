package sample.businessLayer;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem{
    private String name;
    private List<MenuItem> productsList=new ArrayList<MenuItem>();

    public CompositeProduct(String name) {
        this.name = name;
    }
    public void  addProduct(MenuItem menuItem){
        productsList.add(menuItem);
    }
    public void removeProduct(MenuItem menuItem){
        productsList.remove(menuItem);
    }
    @Override
    public double computePrice() {
        int totalPrice=0;
       for(MenuItem menuItem: productsList){
           totalPrice+=menuItem.computePrice();
       }
       return totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<MenuItem> productsList) {
        this.productsList = productsList;
    }
}
