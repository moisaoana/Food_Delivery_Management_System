package sample.businessLayer;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

public interface IDeliveryServiceProcessing {
    Set<BaseProduct> loadBaseProducts();
    void makeAnOrder(List<MenuItem> orderedItems);
    CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems, String name);
    void addNewBaseProduct(BaseProduct baseProduct);
    void removeProduct();
    void modifyProduct();
}
