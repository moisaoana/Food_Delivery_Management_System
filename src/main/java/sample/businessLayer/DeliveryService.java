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
    private ArrayList<MenuItem> alert2;

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
    public boolean isWellFormed(){
        return listOfUsers!=null;
    }
    public void addListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }
    public void setAlert(Order order) {
        propertyChangeSupport.firePropertyChange("alert", this.alert, order);
        this.alert = order;
    }
    public void setAlert2(ArrayList<MenuItem> list){
        propertyChangeSupport.firePropertyChange("alert2", this.alert2, list);
        this.alert2=list;
    }

    @Override
    public void loadBaseProducts(){
        assert isWellFormed();
        allBaseProducts.clear();
        allMenuItems.clear();
        try (Stream<String> stream = Files.lines(Paths.get( "products.csv"))) {
           allBaseProducts=stream.filter(line -> !line.startsWith("Title")).map(s->s.split(",")).map(BaseProduct::new).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        allMenuItems.addAll(allBaseProducts);
        Serializator.writeToFileSet(allMenuItems,"menuitems.txt");
        assert !allBaseProducts.isEmpty() && !allMenuItems.isEmpty();
        assert isWellFormed();
    }

    @Override
    public  CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems,String name) {
        assert isWellFormed();
        assert  menuItems.size()>0 && name!=null;
        int oldSize=allMenuItems.size();
        CompositeProduct compositeProduct=new CompositeProduct(name);
        for(MenuItem menuItem: menuItems) {
            compositeProduct.addProduct(menuItem);
        }
        compositeProduct.setPrice(compositeProduct.computePrice());
        compositeProduct.setPrintableList();
        allMenuItems.add(compositeProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        assert allMenuItems.size()==oldSize+1;
        assert isWellFormed();
        return compositeProduct;

    }

    @Override
    public void addNewBaseProduct(BaseProduct baseProduct) {
        assert isWellFormed();
        assert baseProduct!=null;
        int preSize=DeliveryService.allMenuItems.size();
        DeliveryService.allMenuItems.add(baseProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        assert allMenuItems.size()==preSize+1 && allMenuItems.contains(baseProduct);
        assert isWellFormed();
    }

    @Override
    public void removeProduct(MenuItem menuItem) {
        assert isWellFormed();
        assert menuItem!=null && allMenuItems.size()>0 && allMenuItems.contains(menuItem);
        int oldSize=allMenuItems.size();
        DeliveryService.allMenuItems.remove(menuItem);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        assert allMenuItems.size() ==oldSize - 1 && !allMenuItems.contains(menuItem);
        assert isWellFormed();
    }

    @Override
    public void modifyProductDelete(MenuItem menuItemOld) {
        assert isWellFormed();
        assert menuItemOld!=null && allMenuItems.contains(menuItemOld);
        int oldSize=allMenuItems.size();
        DeliveryService.allMenuItems.remove(menuItemOld);
        assert !allMenuItems.contains(menuItemOld) && allMenuItems.size()==oldSize-1;
        assert isWellFormed();
    }
    @Override
    public void modifyProductAdd( MenuItem menuItemNew) {
        assert isWellFormed();
        assert menuItemNew!=null;
        int oldSize=allMenuItems.size();
        DeliveryService.allMenuItems.add(menuItemNew);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        assert allMenuItems.contains(menuItemNew) && allMenuItems.size()==oldSize+1;
        assert isWellFormed();
    }

    @Override
    public void search(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField ratingTF,TextField caloriesTF, TextField proteinTF, TextField fatTF, TextField sodiumTF, TextField priceTF, TextField titleTF) {
        assert isWellFormed();
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
        assert isWellFormed();

    }

    @Override
    public void searchComposite(ObservableList<MenuItem> menuItems, TableView<MenuItem> tableView, TextField titleTF, TextField priceTF) {
        assert isWellFormed();
        ObservableList<MenuItem>selectedItemsObservable= FXCollections.observableArrayList();
        List<MenuItem>selectedItems;
        selectedItems= menuItems.stream().filter(product-> product instanceof CompositeProduct)
                .filter(!priceTF.getText().isEmpty()?p->((CompositeProduct) p).getPrice()==Double.parseDouble(priceTF.getText()):p->true)
                .filter(!titleTF.getText().isEmpty()?p->((CompositeProduct) p).getTitle().startsWith(titleTF.getText()):p->true)
                .collect(Collectors.toList());
        selectedItemsObservable.addAll(selectedItems);
        tableView.setItems(selectedItemsObservable);
        tableView.refresh();
        assert isWellFormed();
    }

    @Override
    public void createBill(ObservableList<MenuItem> menuItems, User user,Date date) {
        assert isWellFormed();
        assert !menuItems.isEmpty() && user!=null && date!=null;
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
        assert isWellFormed();
    }

    @Override
    public void generateReport1(int startHour, int endHour) {
        assert isWellFormed();
        assert startHour>0 && endHour<24 && startHour<endHour;
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Orders between "+startHour+" and "+endHour+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        orders.entrySet().stream().filter(entry->entry.getKey().getOrderDate().getHour()>=startHour && entry.getKey().getOrderDate().getHour()<endHour)
                                 .forEach(entry-> sample.dataLayer.FileWriter.writeOrderToFile(entry.getKey(),entry.getValue()));
        assert isWellFormed();
    }

    @Override
    public void generateReport2(int number) {
        assert isWellFormed();
        assert number>=0;
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Products ordered more than "+number+" times\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MenuItem> allItems=new ArrayList<>();
        List<MenuItem>menuItemsUnique;
        orders.values().stream().forEach(allItems::addAll);
        menuItemsUnique=allItems.stream().distinct().collect(Collectors.toList());
        menuItemsUnique.stream().forEach((i)->{long c=allItems.stream().filter(p->p.equals(i)).count();if(c>=number)
            sample.dataLayer.FileWriter.writeReport4(i,c);});
        assert isWellFormed();
    }

    @Override
    public void generateReport3(int nrOfOrders, double sum) {
        assert isWellFormed();
        assert nrOfOrders>=0 &&  sum>0;
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Clients that have ordered more than "+nrOfOrders+" times and\n value of the order is greater than "+sum+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Integer> clientsUnique;
        clientsUnique=orders.entrySet().stream().filter(e->computeOrderTotal(e.getValue())>sum).map(Map.Entry::getKey).map(Order::getClientID).distinct().collect(Collectors.toList());
        clientsUnique.stream().forEach((i)->{long c= orders.keySet().stream().map(Order::getClientID).filter(id->id.equals(i)).count();if(c>nrOfOrders)
            sample.dataLayer.FileWriter.writeReport3(i,c);});
        assert isWellFormed();
    }

    @Override
    public void generateReport4(int day,int month,int year) {
        assert isWellFormed();
        assert day>0 && day<32 && month>0 && month<13;
        try {
            FileWriter fileWriter = new FileWriter("report.txt",false);
            fileWriter.write("REPORT-"+"Products ordered at "+day+"."+month+"."+year+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MenuItem>menuItemsLocal=new ArrayList<>();
        List<MenuItem>menuItemsUnique;
        orders.entrySet().stream().filter(entry->entry.getKey().getOrderDate().getDay()==day && entry.getKey().getOrderDate().getMonth()==month && entry.getKey().getOrderDate().getYear()==year)
                .map(Map.Entry::getValue)
                .forEach(menuItemsLocal::addAll);
        menuItemsUnique=menuItemsLocal.stream().distinct().collect(Collectors.toList());
        menuItemsUnique.stream().forEach((i)->{long c=menuItemsLocal.stream().filter(p->p.equals(i)).count();
            sample.dataLayer.FileWriter.writeReport4(i,c);});
        assert isWellFormed();

    }

    @Override
    public double computeOrderTotal(ArrayList<MenuItem> menuItems) {
        assert isWellFormed();
        assert !menuItems.isEmpty();
        double sum=0;
        for(MenuItem menuItem: menuItems){
            if(menuItem instanceof BaseProduct){
                sum+=((BaseProduct) menuItem).getPrice();
            }else if(menuItem instanceof CompositeProduct){
                sum+=((CompositeProduct) menuItem).getPrice();
            }
        }
        assert sum!=0;
        assert isWellFormed();
        return sum;
    }
    @Override
    public Date makeAnOrder(ObservableList<MenuItem> orderedItems, User user) {//not done
        assert isWellFormed();
        int orderId = generateOrderId();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime= LocalTime.now();
        Date date=new Date(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear(),localTime.getHour(),localTime.getMinute(),localTime.getSecond());
        Order newOrder=new Order(orderId,user.getID(),date);
        ArrayList<MenuItem> listOfOrderedItems = new ArrayList<>(orderedItems);
        orders.put(newOrder,listOfOrderedItems);
        Serializator.writeToFileMap(orders,"orders.txt");
        for(EmployeeController employeeController: observers){
            setAlert(newOrder);
            setAlert2(listOfOrderedItems);
        }
        assert isWellFormed();
        return date;

    }
    @Override
    public int generateOrderId(){
        assert isWellFormed();
        if(orders.size()==0){
            assert isWellFormed();
            return 1;
        }else{
            assert isWellFormed();
            return orders.size()+1;
        }

    }

    @Override
    public void modifyCompositeItem(String string,CompositeProduct menuItem) {
        for(MenuItem m:DeliveryService.allMenuItems){
            if(m instanceof CompositeProduct){
                if(((CompositeProduct) m).getTitle().equals(((CompositeProduct) menuItem).getTitle())){
                    ((CompositeProduct) m).setTitle(string);
                }
            }
        }
    }

}
