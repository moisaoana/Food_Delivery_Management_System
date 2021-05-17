package sample.dataLayer;

import sample.businessLayer.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class FileWriter {
    public static void writeOrderToFile(Order order, ArrayList<MenuItem> menuItems){
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter("report.txt",true);
            fileWriter.write("Order ID: "+order.getOrderID()+"\n");
            fileWriter.write("Date: "+order.getOrderDate().getDay()+"."+order.getOrderDate().getMonth()+"."+order.getOrderDate().getYear()+" "+order.getOrderDate().getHour()+":"+order.getOrderDate().getMinutes()+":"+order.getOrderDate().getSeconds()+"\n");
            fileWriter.write("Client: "+order.getClientID()+"\nProducts: ");
            int total=0;
            for(MenuItem menuItem:menuItems){
                if(menuItem instanceof BaseProduct) {
                    fileWriter.write(((BaseProduct) menuItem).getTitle() + ", ");
                    total+=((BaseProduct) menuItem).getPrice();
                }else if(menuItem instanceof CompositeProduct) {
                    fileWriter.write(((CompositeProduct) menuItem).getTitle() + ", ");
                    total+=((CompositeProduct) menuItem).getPrice();
                }
            }
            fileWriter.write("\nTotal: "+total+"\n");
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
   public static void writeReport4(MenuItem menuItem, long nr){
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter("report.txt",true);
            if(menuItem instanceof BaseProduct) {
                fileWriter.write(((BaseProduct) menuItem).getTitle()+", ordered "+nr+" times\n");
            }else if(menuItem instanceof CompositeProduct){
                fileWriter.write(((CompositeProduct) menuItem).getTitle()+", ordered "+nr+" times\n");
            }
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeReport3(int i, long nr){
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter("report.txt",true);
            Optional<User> client=DeliveryService.listOfUsers.stream().filter(u->u.getID()==i).findFirst();
            fileWriter.write(client.get().getUsername()+" ordered "+nr+" times\n");
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
