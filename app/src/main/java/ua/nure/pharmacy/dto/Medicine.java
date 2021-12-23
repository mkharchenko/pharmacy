package ua.nure.pharmacy.dto;

public class Medicine {
    public Medicine(int id, String title, double price) {
        id_ = id;
        title_ = title;
        price_ = price;
    }

    public int GetId() { return id_; }

    public String GetTitle() {
        return title_;
    }

    public double GetPrice() {
        return price_;
    }

    @Override
    public String toString() {
        return title_ + ", Цена: " + String.valueOf(price_);
    }

    private int id_;
    private String title_;
    private double price_;
}
