package sample.businessLayer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    /**
     * Method that loads the initial set of products into the allBaseProducts Set
     * @pre @true
     * @post !allBaseProducts.isEmpty()
     * @post !allMenuItems.isEmpty()
     * @invariant isWellFormed()
     */
    void loadBaseProducts();

    /**
     * Method for placing an order. A new order is created and mapped into the HashMap.
     * @pre !orderedItems.isEmpty()
     * @pre user!=null
     * @post date!=null
     * @post orders.size()=orders.size()@pre+1
     * @param orderedItems observable list of menu items ordered
     * @param user the user who placed the order
     * @return the date at which the order was placed
     * @invariant isWellFormed()
     */
    Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user);

    /**
     * Method for creating a new CompositeProduct and adding it to the allMenuItems Set.
     * @pre menuItems.size()>0
     * @pre name!=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @param menuItems an ObservableList of menuItems that compose the new product
     * @param name a String representing the name of the new product
     * @return the new CompositeProduct
     * @invariant isWellFormed()
     */
    CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems, String name);

    /**
     * Method for creating a new BaseProduct and adding it to the allMenuItems Set.
     * @pre baseProduct !=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @post allMenuItems.contains(baseProduct)
     * @param baseProduct the new BaseProduct object
     * @invariant isWellFormed()
     */
    void addNewBaseProduct(BaseProduct baseProduct);

    /**
     * Method for removing an element from the menu.
     * @pre menuItem!=null
     * @pre allMenuItems.size()>0
     * @pre allMenuItems.contains(menuItem)
     * @post allMenuItems.size() == allMenuItems.size()@pre - 1
     * @post !allMenuItems.contains(menuItem)
     * @param menuItem the MenuItem object to be removed from the menu
     * @invariant isWellFormed()
     *
     */
    void removeProduct(MenuItem menuItem);

    /**
     * Method for deleting from the menu the item to be modified.
     * @pre menuItemOld!=null
     * @pre allMenuItems.contains(menuItemOld)
     * @post allMenuItems.size()==allMenuItems.size()@pre-1
     * @post !allMenuItems.contains(menuItemOld)
     * @param menuItemOld the MenuItem object to be modified (removed from the menu)
     * @invariant isWellFormed()
     */
    void modifyProductDelete(MenuItem menuItemOld);

    /**
     * Method for adding to the menu the modified item, taken as a parameter.
     * @pre menuItemNew!=null
     * @post allMenuItems.size()==allMenuItems.size()@pre+1
     * @post  allMenuItems.contains(menuItemNew)
     * @param menuItemNew the MenuItem newly modified that is inserted in the menu
     * @invariant isWellFormed()
     */
    void modifyProductAdd( MenuItem menuItemNew);

    /**
     * Method for searching Base Products in the TableView.
     * @pre @true
     * @post @noChange
     * @param menuItems an ObservableList of menuItems from the table
     * @param tableView the TableView with the menuItems
     * @param ratingTF the TextField for entering the rating
     * @param caloriesTF the TextField for entering the value of the calories
     * @param proteinTF the TextField for entering the value of the protein
     * @param fatTF the TextField for entering the value of the fats
     * @param sodiumTF the TextField for entering the value of the sodium
     * @param priceTF the TextField for entering the price
     * @param titleTF the TextField for entering the title of the menuItem
     * @invariant isWellFormed()
     */
    void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF);

    /**
     * Method for searching CompositeProducts in the TableView.
     * @pre @true
     * @post @noChange
     * @param menuItems an ObservableList of menuItems from the table
     * @param tableView the TableView with the menuItems
     * @param titleTF the TextField for entering the title of the menuItem
     * @param priceTF the TextField for entering the price
     * @invariant isWellFormed()
     */
    void searchComposite(ObservableList<MenuItem>menuItems, TableView<MenuItem>tableView, TextField titleTF, TextField priceTF);

    /**
     * Method for writing a bill to a text file, after an order is placed.
     * @pre !menuItems.isEmpty()
     * @pre user!=null
     * @pre date!=null
     * @post @noChange
     * @param menuItems the ObservableList of products ordered
     * @param user the User that placed the order
     * @param date the date of the order
     * @invariant isWellFormed()
     */
    void createBill(ObservableList<MenuItem> menuItems,User user,Date date);

    /**
     * Method for generating a type 1 report to a text file.
     * @pre startHour>0 &&  endHour<24
     * @pre startHour < endHour
     * @post @noChange
     * @param startHour the start hour for the report
     * @param endHour the end hour for the report
     * @invariant isWellFormed()
     */
    void generateReport1(int startHour, int endHour);

    /**
     * Method for generating a type 2 report to a text file.
     * @pre number>=0
     * @post @noChange
     * @param number the number of orders for the products
     * @invariant isWellFormed()
     */
    void generateReport2(int number);

    /**
     * Method for generating a type 3 report to a text file.
     * @pre nrOfOrders>=0 &&  sum>0
     * @post @noChange
     * @param nrOfOrders the number of orders for the clients
     * @param sum the sum limit for the orders
     * @invariant isWellFormed()
     */
    void generateReport3(int nrOfOrders, double sum);

    /**
     * Method for generating a type 4 report to a text file.
     * @pre day>0 && day<32
     * @pre month>0 && month<13
     * @post @noChange
     * @param day the day an order was placed
     * @param month the month an order was placed
     * @param year the year an order was placed
     * @invariant isWellFormed()
     */
    void generateReport4(int day, int month, int year);
    /**
     * Method for computing the total sum of an order.
     * @pre !menuItems.isEmpty()
     * @post sum!=0
     * @param menuItems an arraylist of menu items in a specific order
     * @return a double representing the total sum of the order
     * @invariant isWellFormed()
     */
    double computeOrderTotal(ArrayList<MenuItem>menuItems);

    /**
     * Method for generating the id of a new order.
     * @pre @true
     * @post @noChange
     * @return the id of the  next order
     * @invariant isWellFormed()
     */
     int generateOrderId();

    /**
     * Method for modifying the name of an already existing CompositeProduct.
     * @pre string!=null && !string.isEmpty()
     * @pre allMenuItems.size()>0
     * @pre allMenuItems.contains(menuItem)
     * @post allMenuItems.size()==allMenuItems.size()@pre-1
     * @param string a String representing the new name of the product
     * @param menuItem the MenuItem to be modified
     *  @invariant isWellFormed()
     */
    void modifyCompositeItem(String string,CompositeProduct menuItem);
}
