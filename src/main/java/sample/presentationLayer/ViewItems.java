package sample.presentationLayer;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.businessLayer.BaseProduct;
import sample.businessLayer.CompositeProduct;
import sample.businessLayer.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ViewItems extends Stage {
    public ViewItems(int index, List<ArrayList<MenuItem>> pendingItems){
        ArrayList<MenuItem> items= pendingItems.get(index);
        GridPane gridPane=new GridPane();
        gridPane.setStyle("-fx-background-color: darkseagreen");
        this.setWidth(500);
        this.setHeight(300);
        this.setTitle("Ordered Items");
        int pos=0;
        for(MenuItem menuItem:items) {
            if (menuItem instanceof BaseProduct){
                Text text=new Text(((BaseProduct) menuItem).getTitle());
                styleText(text);
                gridPane.add(text,0,pos);
                pos++;
            }else if(menuItem instanceof CompositeProduct){
                Text text=new Text(((CompositeProduct) menuItem).getTitle());
                 styleText(text);
                gridPane.add(text,0,pos);
                pos++;
            }
        }
        Scene scene = new Scene(gridPane);
        this.setScene(scene);
        this.show();
    }
    private void styleText(Text text)
    {
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.WHITE);
    }
}
