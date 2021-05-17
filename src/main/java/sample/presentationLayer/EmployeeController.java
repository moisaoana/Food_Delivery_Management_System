package sample.presentationLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.MenuItem;
import sample.businessLayer.Order;
import sample.businessLayer.User;
import sample.dataLayer.Serializator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class EmployeeController extends Stage implements PropertyChangeListener, Serializable {
    private User user;
    private List<Order> pendingOrders= new ArrayList<>();
    private List<ArrayList<MenuItem>>pendingItems=new ArrayList<>();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getNewValue() instanceof Order)
            pendingOrders.add((Order)evt.getNewValue());
        else if(evt.getNewValue() instanceof ArrayList)
            pendingItems.add((ArrayList<MenuItem>) evt.getNewValue());
        Serializator.writeToFileEmployees(DeliveryService.observers,"employees.txt");
    }

    public EmployeeController(User user){
        this.user=user;
    }
    public void showScene(){
        TableView<Order> tableView=new TableView<>();
        TableColumn<Order,Integer> columnOrderID = new TableColumn<>("Order ID");
        columnOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        TableColumn<Order,Integer> columnClientID = new TableColumn<>("Client ID");
        columnClientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        TableColumn<Order,String> columnDate = new TableColumn<>("Date");
        columnDate.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getOrderDate().getDay()+"."+cellData.getValue().getOrderDate().getMonth()+"."+cellData.getValue().getOrderDate().getYear()+" "+cellData.getValue().getOrderDate().getHour()+":"+cellData.getValue().getOrderDate().getMinutes()+":"+cellData.getValue().getOrderDate().getSeconds()));
        tableView.getColumns().addAll(columnOrderID,columnClientID,columnDate);
        ObservableList<Order> orderObs=FXCollections.observableArrayList();
        orderObs.addAll(pendingOrders);
        tableView.setItems(orderObs);
        addButtonsDone(tableView,orderObs);
        addButtonsView(tableView);
        this.setHeight(500);
        this.setWidth(500);
        Scene scene = new Scene(tableView);
        this.setTitle("Pending Orders");
        this.setScene(scene);
        this.show();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public  void  addButtonsDone(TableView tableView,ObservableList<Order>obs)
    {
        TableColumn<Order, Void> buttons = new TableColumn<>("Done");
        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("Done");
                    {
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {

                            Order order = getTableView().getItems().get(getIndex());
                            int index=0;
                            for(int i=0;i<pendingOrders.size();i++){
                                if(pendingOrders.get(i).getOrderID()==order.getOrderID()){
                                    index=i;
                                }
                            }
                            for(EmployeeController employeeController:DeliveryService.observers){
                                employeeController.pendingOrders.remove(order);
                                employeeController.pendingItems.remove(index);
                            }
                            Serializator.writeToFileEmployees(DeliveryService.observers,"employees.txt");
                            obs.remove(order);
                            tableView.refresh();

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(newButton);
                        }
                    }
                };
            }
        };
        buttons.setCellFactory(cellFactory);
        tableView.getColumns().add(buttons);
    }
    public  void  addButtonsView(TableView tableView)
    {
        TableColumn<Order, Void> buttons = new TableColumn<>("View");
        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("View");
                    {
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {

                            Order order = getTableView().getItems().get(getIndex());
                            int index=0;
                            for(int i=0;i<pendingOrders.size();i++){
                                if(pendingOrders.get(i).getOrderID()==order.getOrderID()){
                                    index=i;
                                }
                            }
                            new ViewItems(index,pendingItems);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(newButton);
                        }
                    }
                };
            }
        };
        buttons.setCellFactory(cellFactory);
        tableView.getColumns().add(buttons);
    }
}
