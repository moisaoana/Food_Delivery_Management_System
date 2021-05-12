package sample.businessLayer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {

    void loadBaseProducts();
    Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user);

    /**
     * @pre menuItems.size()>0
     * @pre name!=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @param menuItems
     * @param name
     * @return
     */
    CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems, String name);
    /**
     * @pre baseProduct !=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @post allMenuItems.contains(baseProduct)
     */
    void addNewBaseProduct(BaseProduct baseProduct);

    /**
     * @pre menuItem!=null
     * @pre allMenuItems.size()>0
     * @pre allMenuItems.contains(menuItem)
     * @post allMenuItems.size() == allMenuItems.size()@pre - 1
     * @post allMenuItems.contains(menuItem)==false
     *
     */
    void removeProduct(MenuItem menuItem);
    void modifyProductDelete(MenuItem menuItemOld);
    void modifyProductAdd( MenuItem menuItemNew);
    void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF);
    void searchComposite(ObservableList<MenuItem>menuItems, TableView<MenuItem>tableView, TextField titleTF, TextField priceTF);
    void createBill(ObservableList<MenuItem> menuItems,User user,Date date);
    void generateReport1(int startHour, int endHour);
    void generateReport2(int number);
    void generateReport3(int nrOfOrders, double sum);
    void generateReport4(int day, int month, int year);
    double computeOrderTotal(ArrayList<MenuItem>menuItems);


}
