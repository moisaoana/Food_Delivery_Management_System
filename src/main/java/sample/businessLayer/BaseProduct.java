package sample.businessLayer;

import java.util.Objects;

public class BaseProduct extends MenuItem{
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private double price;
    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, double price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }
    public BaseProduct(String[] arguments){
        this.title=arguments[0];
        this.rating=Double.parseDouble(arguments[1]);
        this.calories=Integer.parseInt(arguments[2]);
        this.protein=Integer.parseInt(arguments[3]);
        this.fat=Integer.parseInt(arguments[4]);
        this.sodium=Integer.parseInt(arguments[5]);
        this.price=Double.parseDouble(arguments[6]);
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double computePrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return Double.compare(that.rating, rating) == 0 &&
                calories == that.calories &&
                protein == that.protein &&
                fat == that.fat &&
                sodium == that.sodium &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, calories, protein, fat, sodium, price);
    }
}
