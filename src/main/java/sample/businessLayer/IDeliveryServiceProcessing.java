package sample.businessLayer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    /**
     * @pre
     * @post !allBaseProducts.isEmpty()
     * @post !allMenuItems.isEmpty()
     */
    void loadBaseProducts();

    /**
     * @pre !orderedItems.isEmpty()
     * @pre user!=null
     * @post date!=null
     * @post orders.size()=orders.size()@pre+1
     * @param orderedItems observable list of menu items ordered
     * @param user the user who placed the order
     * @return the date at which the order was placed
     */
    Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user);

    /**
     * @pre menuItems.size()>0
     * @pre name!=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @param menuItems an ObservableList of menuItems that compose the new product
     * @param name a String representing the name of the new product
     * @return the new CompositeProduct
     */
    CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems, String name);

    /**
     * @pre baseProduct !=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @post allMenuItems.contains(baseProduct)
     * @param baseProduct the new BaseProduct object
     */
    void addNewBaseProduct(BaseProduct baseProduct);

    /**
     * @pre menuItem!=null
     * @pre allMenuItems.size()>0
     * @pre allMenuItems.contains(menuItem)
     * @post allMenuItems.size() == allMenuItems.size()@pre - 1
     * @post !allMenuItems.contains(menuItem)
     * @param menuItem the MenuItem object to be removed from the menu
     *
     */
    void removeProduct(MenuItem menuItem);

    /**
     * @pre menuItemOld!=null
     * @pre allMenuItems.contains(menuItemOld)
     * @post allMenuItems.size()==allMenuItems.size()@pre-1
     * @post !allMenuItems.contains(menuItemOld)
     * @param menuItemOld the MenuItem object to be modified (removed from the menu)
     */
    void modifyProductDelete(MenuItem menuItemOld);

    /**
     * @pre menuItemNew!=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @post  allMenuItems.contains(menuItemNew)
     * @param menuItemNew the MenuItem newly modified that is inserted in the menu
     */
    void modifyProductAdd( MenuItem menuItemNew);

    /**
     * @pre true
     * @post
     * @param menuItems an ObservableList of menuItems from the table
     * @param tableView the TableView with the menuItems
     * @param ratingTF the TextField for entering the rating
     * @param caloriesTF the TextField for entering the value of the calories
     * @param proteinTF the TextField for entering the value of the protein
     * @param fatTF the TextField for entering the value of the fats
     * @param sodiumTF the TextField for entering the value of the sodium
     * @param priceTF the TextField for entering the price
     * @param titleTF the TextField for entering the title of the menuItem
     */
    void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF);

    /**
     * @pre true
     * @post
     * @param menuItems an ObservableList of menuItems from the table
     * @param tableView the TableView with the menuItems
     * @param titleTF the TextField for entering the title of the menuItem
     * @param priceTF the TextField for entering the price
     */
    void searchComposite(ObservableList<MenuItem>menuItems, TableView<MenuItem>tableView, TextField titleTF, TextField priceTF);

    /**
     * @pre !menuItems.isEmpty()
     * @pre user!=null
     * @pre date!=null
     * @post
     * @param menuItems the ObservableList of products ordered
     * @param user the User that placed the order
     * @param date the date of the order
     */
    void createBill(ObservableList<MenuItem> menuItems,User user,Date date);

    /**
     * @pre startHour>0 &&  endHour<24
     * @pre startHour < endHour
     * @post
     * @param startHour the start hour for the report
     * @param endHour the end hour for the report
     */
    void generateReport1(int startHour, int endHour);

    /**
     * @pre number>=0
     * @post
     * @param number the number of orders for the products
     */
    void generateReport2(int number);

    /**
     * @pre nrOfOrders>=0 &&  sum>0
     * @post
     * @param nrOfOrders the number of orders for the clients
     * @param sum the sum limit for the orders
     */
    void generateReport3(int nrOfOrders, double sum);

    /**
     * @pre day>0 && day<32
     * @pre month>0 && month<13
     * @post
     * @param day the day an order was placed
     * @param month the month an order was placed
     * @param year the year an order was placed
     */
    void generateReport4(int day, int month, int year);
    /**
     * @pre !menuItems.isEmpty()
     * @post sum!=0
     * @param menuItems an arraylist of menu items in a specific order
     * @return a double representing the total sum of the order
     */
    double computeOrderTotal(ArrayList<MenuItem>menuItems);

    /**
     * @pre
     * @post
     * @return the id of the  next order
     */
     int generateOrderId();

    void modifyCompositeItem(String string,CompositeProduct menuItem);
}
