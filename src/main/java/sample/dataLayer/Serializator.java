package sample.dataLayer;

import sample.businessLayer.DeliveryService;
import sample.businessLayer.User;

import java.io.*;

public class Serializator implements Serializable {
    public static void loadInfoUsers(){
        User user = new User();
        FileInputStream file = null;
        try {
            file = new FileInputStream("users.txt");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        try {
            ObjectInputStream in = new ObjectInputStream(file);
            user = (User)in.readObject();
            System.out.println(user.getUsername());
            DeliveryService.listOfUsers.add(user);
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
