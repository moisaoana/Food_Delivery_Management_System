package sample.dataLayer;

import com.sun.source.tree.Tree;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.MenuItem;
import sample.businessLayer.OrderAlphabetically;
import sample.businessLayer.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    public static Set<MenuItem> loadInfoMenuItems(){
        Set menuItems=new TreeSet<>(new OrderAlphabetically());
        FileInputStream file = null;
        try {
            file = new FileInputStream("menuitems.txt");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        try {
            if(file.available()>0) {
                ObjectInputStream in = new ObjectInputStream(file);
               menuItems = (Set) in.readObject();
                //DeliveryService.listOfUsers = users;
                in.close();
            }

            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menuItems;
    }
}
