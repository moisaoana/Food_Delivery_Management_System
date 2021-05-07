package sample.businessLayer;

import java.io.Serializable;
import java.util.Comparator;

public class OrderAlphabetically implements Comparator<MenuItem>, Serializable {
    @Override
    public int compare(MenuItem o1, MenuItem o2) {
        if(o1 instanceof BaseProduct && o2 instanceof BaseProduct)
         return ((BaseProduct) o1).getTitle().compareTo(((BaseProduct) o2).getTitle());
        else if(o1 instanceof BaseProduct && o2 instanceof CompositeProduct ){
            return ((BaseProduct) o1).getTitle().compareTo(((CompositeProduct) o2).getTitle());
        }else if(o1 instanceof  CompositeProduct && o2 instanceof  BaseProduct){
            return ((CompositeProduct) o1).getTitle().compareTo(((BaseProduct) o2).getTitle());
        }else if (o1 instanceof CompositeProduct && o2 instanceof CompositeProduct){
            return ((CompositeProduct) o1).getTitle().compareTo(((CompositeProduct) o2).getTitle());
        }else{
            return 0;
        }
    }
}
