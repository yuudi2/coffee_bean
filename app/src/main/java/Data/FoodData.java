package Data;

public class FoodData {
    int img;
    String name;
    int price;

    public FoodData(int img, String name, int price) {
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return img;
    }
    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getprice() {
        return price;
    }
    public void setprice(int price) {
        this.price = price;
    }
}
