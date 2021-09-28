package Data;

public class CartData {
    int id;
    int img;
    String name;
    int price;
    String size;
    String cup;
    //String cream;
    int count;
    int total_price;
    boolean isSelected;

    public CartData(int id, int img, String name, int price, String size, String cup, int count, int total_price){
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.size = size;
        this.cup = cup;
//        this.cream = cream;
        this.count = count;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getsize() { return size; }
    public void setsize(String size) {
        this.size = size;
    }

    public String getcup() {
        return cup;
    }
    public void setcup(String cup) {
        this.cup = cup;
    }

    public int getCount() { return count; }
    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal_price() {
        return total_price;
    }
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

//    public String getcream() {
//        return cream;
//    }
//    public void setCream(String cream) {
//        this.cream = cream;
//    }

    public boolean getSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
}
