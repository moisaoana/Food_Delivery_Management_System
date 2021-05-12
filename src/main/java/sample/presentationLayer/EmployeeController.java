package sample.presentationLayer;
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
    private Order alert;
    private List<Order> pendingOrders= new ArrayList<>();
    private List<ArrayList<MenuItem>>pendingItems=new ArrayList<>();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setAlert((Order) evt.getNewValue());
        System.out.println(alert.getOrderID());
        pendingOrders.add((Order)evt.getNewValue());
       // pendingItems.add((ArrayList<MenuItem>) evt.getNewValue());
        Serializator.writeToFileEmployees(DeliveryService.observers,"employees.txt");
    }

    public EmployeeController(User user){
        this.user=user;

    }
    public void showScene(){
        TableView tableView=new TableView();
        TableColumn<Order,Integer> columnOrderID = new TableColumn<>("Order ID");
        columnOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        TableColumn<Order,Integer> columnClientID = new TableColumn<>("Client ID");
        columnClientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        tableView.getColumns().add(columnOrderID);
        tableView.getColumns().add(columnClientID);
        ObservableList<Order> orderObs=FXCollections.observableArrayList();
        orderObs.addAll(pendingOrders);
        tableView.setItems(orderObs);
        addButtonsDone(tableView,orderObs);
        this.setHeight(500);
        this.setWidth(500);
        Scene scene = new Scene(tableView);
        this.setTitle("Pending Orders");
        this.setScene(scene);
        this.show();
    }

    public Order getAlert() {
        return alert;
    }

    public void setAlert(Order alert) {
        this.alert = alert;
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
                            for(EmployeeController employeeController:DeliveryService.observers){
                                employeeController.pendingOrders.remove(order);
                            }
                            Serializator.writeToFileEmployees(DeliveryService.observers,"employees.txt");
                            pendingOrders.remove(order);
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
}
