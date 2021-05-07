package sample.businessLayer;

import javafx.collections.ObservableList;
import sample.dataLayer.Deserializator;
import sample.dataLayer.Serializator;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements  IDeliveryServiceProcessing{
    public static List<User> listOfUsers=new ArrayList<>();
    private Map<Order,ArrayList<MenuItem>> order=new HashMap<>();
    public static Set<MenuItem> allMenuItems=new TreeSet<>(new OrderAlphabetically());
    public static Set<BaseProduct> allBaseProducts=new TreeSet<>(new OrderAlphabetically());
    public DeliveryService(){
        listOfUsers= Deserializator.loadInfoUsers();
        allBaseProducts=loadBaseProducts();
        allMenuItems.addAll(allBaseProducts);
    }
    @Override
    public Set<BaseProduct> loadBaseProducts(){
        Set<BaseProduct>allBaseProducts=new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get( "products.csv"))) {
           allBaseProducts=stream.filter(line -> !line.startsWith("Title")).map(s->s.split(",")).map(BaseProduct::new).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allBaseProducts;
    }

    @Override
    public void makeAnOrder(List<MenuItem> orderedItems) {
    }

    @Override
    public  CompositeProduct addNewCompositeItem(ObservableList<MenuItem> menuItems,String name) {
        CompositeProduct compositeProduct=new CompositeProduct(name);
        for(MenuItem menuItem: menuItems) {
            compositeProduct.addProduct(menuItem);
        }
        compositeProduct.setPrice(compositeProduct.computePrice());
        allMenuItems.add(compositeProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
        return compositeProduct;
    }

    @Override
    public void addNewBaseProduct(BaseProduct baseProduct) {
        DeliveryService.allMenuItems.add(baseProduct);
        Serializator.writeToFileSet(DeliveryService.allMenuItems,"menuitems.txt");
    }

    @Override
    public void removeProduct() {

    }

    @Override
    public void modifyProduct() {

    }
}
