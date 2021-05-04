package sample.presentationLayer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorMessage extends Stage {
    public ErrorMessage(String string) {
        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color: darkseagreen");
        this.setWidth(400);
        this.setHeight(100);
        this.setTitle("Error");
        Text text = new Text(string);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.WHITE);
        borderPane.setCenter(text);
        Scene scene = new Scene(borderPane);
        this.setScene(scene);
        this.show();
    }
}
