package sample.businessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    public abstract double computePrice();
}
