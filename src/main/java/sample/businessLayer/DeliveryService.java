package sample.businessLayer;

import java.util.ArrayList;
import java.util.List;

public class DeliveryService {
    public static List<User> listOfUsers=new ArrayList<>();

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

}
