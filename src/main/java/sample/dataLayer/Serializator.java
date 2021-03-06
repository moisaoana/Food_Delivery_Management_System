package sample.dataLayer;

import sample.businessLayer.MenuItem;
import sample.businessLayer.Order;
import sample.businessLayer.User;
import sample.presentationLayer.EmployeeController;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Serializator {
    public static void writeToFile(List<User> users, String filename){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(file);
            out.writeObject(users);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void writeToFileSet(Set<MenuItem> menuItems, String filename){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(file);
            out.writeObject(menuItems);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void writeToFileMap(Map<Order,ArrayList<MenuItem>> orders, String filename){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(file);
            out.writeObject(orders);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void writeToFileEmployees(List<EmployeeController> users, String filename){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(file);
            out.writeObject(users);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
