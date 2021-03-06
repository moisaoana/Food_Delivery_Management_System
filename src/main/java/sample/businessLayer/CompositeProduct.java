package sample.businessLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeProduct extends MenuItem{
    private String title;
    private List<MenuItem> productsList=new ArrayList<MenuItem>();
    private double price;
    private String printableList;
    public CompositeProduct(String name) {
        this.title = name;
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
        return title;
   }

    public void setName(String name) {
        this.title = name;
    }

    public List<MenuItem> getProductsList() {
      return productsList;
    }

    public void setProductsList(List<MenuItem> productsList) {
        this.productsList = productsList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeProduct that = (CompositeProduct) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(title, that.title) &&
                Objects.equals(productsList, that.productsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, productsList, price);
    }

    public String getPrintableList() {
        return printableList;
    }

    public void setPrintableList() {
        StringBuilder stringBuilder=new StringBuilder();
        for(MenuItem menuItem :productsList){
            if(menuItem instanceof BaseProduct){
                stringBuilder.append("| ").append(((BaseProduct) menuItem).getTitle());
            }else if(menuItem instanceof  CompositeProduct){
                stringBuilder.append("| ").append(((CompositeProduct) menuItem).getTitle());
            }

        }
        printableList=stringBuilder.toString();
    }
}
