package sample.presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sample.businessLayer.BaseProduct;
import sample.businessLayer.CompositeProduct;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.MenuItem;
import sample.start.Main;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ClientController {// implements Initializable {
    private Main main;
    private Scene startScene;
    private Scene registerScene;
    private Scene loginScene;
    private Scene adminScene;
    private ObservableList<MenuItem> observableListOrder = FXCollections.observableArrayList();
    private ObservableList<MenuItem> observableListSimple = FXCollections.observableArrayList();
    private ObservableList<MenuItem> observableListComposite = FXCollections.observableArrayList();
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene1){
        this.startScene = scene1;
    }
    public void setRegisterScene(Scene scene1){
        this.registerScene = scene1;
    }
    public void setLoginScene(Scene scene1){
        this.loginScene = scene1;
    }
    public void setAdminScene(Scene scene1){
        this.adminScene = scene1;
    }
    @FXML
    private Label titleLabel;
    @FXML
    private Label menuLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private TableView<MenuItem> composedTableView;

    @FXML
    private TableColumn<MenuItem, String> composedTitleColumn;

    @FXML
    private TableColumn<MenuItem, Double> composedPriceColumn;

    @FXML
    private TableColumn<MenuItem,String> compositionComposedColumn;

    @FXML
    private TableView<MenuItem> menuTableView;
    @FXML
    private Label specialMenuLabel;

    @FXML
    private TableView<sample.businessLayer.MenuItem> orderTableView;

    @FXML
    private TableColumn<sample.businessLayer.MenuItem, String> orderTitleColumn;

    @FXML
    private TableColumn<sample.businessLayer.MenuItem, Double> orderPriceColumn;
    @FXML
    private TableColumn<MenuItem, String> titleColumn;

    @FXML
    private TableColumn<MenuItem, Double> ratingColumn;

    @FXML
    private TableColumn<MenuItem, Integer> caloriesColumn;

    @FXML
    private TableColumn<MenuItem, Integer> proteinColumn;

    @FXML
    private TableColumn<MenuItem, Integer> fatColumn;

    @FXML
    private TableColumn<MenuItem, Integer> sodiumColumn;

    @FXML
    private TableColumn<MenuItem, Double> priceColumn;
    @FXML
    private Button orderButton;
    @FXML
    private TextField totalTextField;

    @FXML
    private Label totalLabel;
    @FXML
    private Button backButton;

    @FXML
    void clickBack(ActionEvent event) {
        main.setScene(startScene);
    }


    @FXML
    void clickOrder(ActionEvent event) {

    }

    @FXML
    public void populateMenuTable(Set<sample.businessLayer.MenuItem> menuItems){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodiumColumn.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        composedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        composedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //compositionComposedColumn.setCellValueFactory(new PropertyValueFactory<>("productsList"));
        orderTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
       orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        for(MenuItem menuItem: menuItems){
            if(menuItem instanceof BaseProduct){
                observableListSimple.add( menuItem);
            }else{

                observableListComposite.add(menuItem);
            }
        }
        menuTableView.setItems(observableListSimple);
        composedTableView.setItems(observableListComposite);
        orderTableView.setItems(observableListOrder);

    }
    public void  addButtons(TableView<MenuItem> tableView,TableView<MenuItem>tableView2,ObservableList<MenuItem>observableList)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Order");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("+");{
                        styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                           MenuItem data = getTableView().getItems().get(getIndex());
                           observableList.add(data);
                           tableView2.refresh();
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


   // @Override
    public void initialize() {
        populateMenuTable(DeliveryService.allMenuItems);
        addButtons(menuTableView,orderTableView,observableListOrder);
        addButtons(composedTableView,orderTableView,observableListOrder);
        addButtonsRemove(orderTableView,observableListOrder);
    }
    public static void styleButton(Button button){
        button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: darksalmon");
    }
    public static void  addButtonsRemove(TableView<MenuItem> tableView,ObservableList<MenuItem>observableList)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Remove");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("-");
                    {
                        styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                           MenuItem menuItem = getTableView().getItems().get(getIndex());
                            observableList.remove(menuItem);
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