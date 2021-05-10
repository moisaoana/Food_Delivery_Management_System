package sample.businessLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.dataLayer.Deserializator;
import sample.dataLayer.Serializator;
import sample.presentationLayer.EmployeeController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements  IDeliveryServiceProcessing{
    public static List<User> listOfUsers=new ArrayList<>();
    private Map<Order,ArrayList<MenuItem>> orders=new HashMap<>();
    public static Set<MenuItem> allMenuItems=new TreeSet<>(new OrderAlphabetically());
    public static Set<BaseProduct> allBaseProducts=new TreeSet<>(new OrderAlphabetically());
    private PropertyChangeSupport propertyChangeSupport;
    public static List<EmployeeController>observers=new ArrayList<>();
    private Order alert;
    //private ArrayList<MenuItem> alert2;

    public DeliveryService(){
        listOfUsers= Deserializator.loadInfoUsers();
        allMenuItems=Deserializator.loadInfoMenuItems();
        propertyChangeSupport=new PropertyChangeSupport(this);
        observers=Deserializator.loadInfoEmployees();
        for(EmployeeController employeeController: observers){
            addListener(employeeController);
        }
        orders=Deserializator.loadInfoOrders();
    }
    public void addListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }
    public void setAlert(Order order) {
        propertyChangeSupport.firePropertyChange("alert", this.alert, order);
       // propertyChangeSupport.firePropertyChange("alert2", this.alert2, list);
        this.alert = order;
       // this.alert2=list;
    }

    @Override
    public void loadBaseProducts(){
        allBaseProducts.clear();
        allMenuItems.clear();
        try (Stream<String> stream = Files.lines(Paths.get( "products.csv"))) {
           allBaseProducts=stream.filter(line -> !line.startsWith("Title")).map(s->s.split(",")).map(BaseProduct::new).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        allMenuItems.addAll(allBaseProducts);
        Serializator.writeToFileSet(allMenuItems,"menuitems.txt");

    }

    @Override
    public  CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems,String name) {
        CompositeProduct compositeProduct=new CompositeProduct(name);
        for(MenuItem menuItem: menuItems) {
            compositeProduct.addProduct(menuItem);
        }
        compositeProduct.setPrice(compositeProduct.computePrice());
        allMenuItems.add(compositeProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        return compositeProduct;
    }

    @Override
    public void addNewBaseProduct(BaseProduct baseProduct) {
        assert baseProduct!=null;
        int preSize=DeliveryService.allMenuItems.size();
        DeliveryService.allMenuItems.add(baseProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        assert allMenuItems.size()==preSize+1;
    }

    @Override
    public void removeProduct(MenuItem menuItem) {
        DeliveryService.allMenuItems.remove(menuItem);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
    }

    @Override
    public void modifyProductDelete(MenuItem menuItemOld) {
        DeliveryService.allMenuItems.remove(menuItemOld);
    }
    @Override
    public void modifyProductAdd( MenuItem menuItemNew) {
        DeliveryService.allMenuItems.add(menuItemNew);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
    }

    @Override
    public void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF) {
        ObservableList<MenuItem>selectedItemsObservable= FXCollections.observableArrayList();
        List<MenuItem>selectedItems;
        selectedItems= menuItems.stream().filter(product-> product instanceof BaseProduct)
                .filter(!ratingTF.getText().isEmpty()? p->((BaseProduct) p).getRating()==Double.parseDouble(ratingTF.getText()) : p->true)
                .filter(!caloriesTF.getText().isEmpty()?p->((BaseProduct) p).getCalories()==Integer.parseInt(caloriesTF.getText()):p->true)
                .filter(!proteinTF.getText().isEmpty()?p->((BaseProduct) p).getProtein()==Integer.parseInt(proteinTF.getText()):p->true)
                .filter(!fatTF.getText().isEmpty()?p->((BaseProduct) p).getFat()==Integer.parseInt(fatTF.getText()):p->true)
                .filter(!sodiumTF.getText().isEmpty()?p->((BaseProduct) p).getSodium()==Integer.parseInt(sodiumTF.getText()):p->true)
                .filter(!priceTF.getText().isEmpty()?p->((BaseProduct) p).getPrice()==Double.parseDouble(priceTF.getText()):p->true)
                .filter(!titleTF.getText().isEmpty()?p->((BaseProduct) p).getTitle().startsWith(titleTF.getText()):p->true)
                .collect(Collectors.toList());
       selectedItemsObservable.addAll(selectedItems);
       tableView.setItems(selectedItemsObservable);
       tableView.refresh();

    }

    @Override
    public void searchComposite(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField titleTF, TextField priceTF) {
        ObservableList<MenuItem>selectedItemsObservable= FXCollections.observableArrayList();
        List<MenuItem>selectedItems;
        selectedItems= menuItems.stream().filter(product-> product instanceof CompositeProduct)
                .filter(!priceTF.getText().isEmpty()?p->((CompositeProduct) p).getPrice()==Double.parseDouble(priceTF.getText()):p->true)
                .filter(!titleTF.getText().isEmpty()?p->((CompositeProduct) p).getTitle().startsWith(titleTF.getText()):p->true)
                .collect(Collectors.toList());
        selectedItemsObservable.addAll(selectedItems);
        tableView.setItems(selectedItemsObservable);
        tableView.refresh();
    }

    @Override
    public void createBill(ObservableList<MenuItem> menuItems, User user,Date date) {
        try {
            FileWriter fileWriter1 = new FileWriter("bill.txt",false);
            fileWriter1.write("Order bill\n");
            fileWriter1.close();
            FileWriter fileWriter = new FileWriter("bill.txt",true);
            fileWriter.write("Date: "+date.getDay()+"."+date.getMonth()+"."+date.getYear()+" "+date.getHour()+":"+date.getMinutes()+":"+date.getSeconds()+"\n");
            fileWriter.write("Client: "+user.getUsername()+"\nProducts:\n");
            double total=0;
            for(MenuItem menuItem: menuItems){
                if(menuItem instanceof BaseProduct){
                    fileWriter.write(((BaseProduct) menuItem).getTitle()+", Price: "+((BaseProduct) menuItem).getPrice()+"\n");
                    total+=((BaseProduct) menuItem).getPrice();
                }else if(menuItem instanceof CompositeProduct){
                    fileWriter.write(((CompositeProduct) menuItem).getTitle()+", Price: "+((CompositeProduct) menuItem).getPrice()+"\n");
                    total+=((CompositeProduct) menuItem).getPrice();
                }
            }
            fileWriter.write("Total: "+total+" RON");
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateReport1(int startHour, int endHour) {
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Orders between "+startHour+" and "+endHour+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        orders.entrySet().stream().filter(entry->entry.getKey().getOrderDate().getHour()>=startHour && entry.getKey().getOrderDate().getHour()<endHour)
                                 .forEach(entry->writeOrderToFile(entry.getKey(),entry.getValue()));
    }

    @Override
    public void generateReport2(int number) {
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Products ordered more than "+number+" times\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //allMenuItems.stream().filter(item->orders.entrySet().stream().filter(entry->entry.getValue().stream().filter(p->p.equals(item)).collect(Collectors.toList()))).forEach();
       // count =orders.values().stream().forEach(list->list.stream().filter(item-> item.equals())).count();
        int count = 0;
        //allMenuItems.stream().forEach(mi->orders.values().stream().filter(list-> list.stream().filter(item->item.equals(mi))).count());
    }

    @Override
    public void generateReport3() {

    }

    @Override
    public void generateReport4() {

    }
    private void writeOrderToFile(Order order, ArrayList<MenuItem>menuItems){
        try {
            FileWriter fileWriter = new FileWriter("report.txt",true);
            fileWriter.write("Order ID: "+order.getOrderID()+"\n");
            fileWriter.write("Date: "+order.getOrderDate().getDay()+"."+order.getOrderDate().getMonth()+"."+order.getOrderDate().getYear()+" "+order.getOrderDate().getHour()+":"+order.getOrderDate().getMinutes()+":"+order.getOrderDate().getSeconds()+"\n");
            fileWriter.write("Client: "+order.getClientID()+"\nProducts: ");
            int total=0;
            for(MenuItem menuItem:menuItems){
                if(menuItem instanceof BaseProduct) {
                    fileWriter.write(((BaseProduct) menuItem).getTitle() + ", ");
                    total+=((BaseProduct) menuItem).getPrice();
                }else if(menuItem instanceof CompositeProduct) {
                    fileWriter.write(((CompositeProduct) menuItem).getTitle() + ", ");
                    total+=((CompositeProduct) menuItem).getPrice();
                }
            }
            fileWriter.write("\nTotal: "+total+"\n");
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user) {//not done
        int orderId = generateOrderId();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime= LocalTime.now();
        Date date=new Date(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear(),localTime.getHour(),localTime.getMinute(),localTime.getSecond());

        Order newOrder=new Order(orderId,user.getID(),date);
        ArrayList<MenuItem> listOfOrderedItems = new ArrayList<>(orderedItems);
        orders.put(newOrder,listOfOrderedItems);
        Serializator.writeToFileMap(orders,"orders.txt");

        for(EmployeeController employeeController: observers){
           // System.out.println(newOrder.getOrderID());
            setAlert(newOrder);
        }
        return date;

    }
    private int generateOrderId(){
        if(orders.size()==0){
            return 1;
        }else{
            return orders.size()+1;
        }
    }

}
