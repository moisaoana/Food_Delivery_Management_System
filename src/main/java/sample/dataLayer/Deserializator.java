package sample.dataLayer;

import sample.businessLayer.DeliveryService;
import sample.businessLayer.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Deserializator implements Serializable {
    public static List<User>loadInfoUsers(){
        List<User> users=new ArrayList<>();
        FileInputStream file = null;
        try {
            file = new FileInputStream("users.txt");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        try {
            if(file.available()>0) {
                ObjectInputStream in = new ObjectInputStream(file);
                users = (List) in.readObject();
                //DeliveryService.listOfUsers = users;
                in.close();
            }

            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
