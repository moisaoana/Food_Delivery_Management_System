package sample.businessLayer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Set;

public interface IDeliveryServiceProcessing {

    void loadBaseProducts();
    Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user);
    CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems, String name);
    /**
     * @pre baseProduct !=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     */
    void addNewBaseProduct(BaseProduct baseProduct);
    void removeProduct(MenuItem menuItem);
    void modifyProductDelete(MenuItem menuItemOld);
    void modifyProductAdd( MenuItem menuItemNew);
    void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF);
    void searchComposite(ObservableList<MenuItem>menuItems, TableView<MenuItem>tableView, TextField titleTF, TextField priceTF);
    void createBill(ObservableList<MenuItem> menuItems,User user,Date date);
    void generateReport1(int startHour, int endHour);
    void generateReport2(int number);
    void generateReport3();
    void generateReport4();


}
