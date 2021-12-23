package ua.nure.pharmacy.dto;

public class MedicinePurchase {
    public MedicinePurchase(Medicine medicine, int amount) {
        medicine_ = medicine;
        amount_ = amount;
    }

    public Medicine GetMedicine() {
        return medicine_;
    }

    public int GetAmount() { return amount_; }

    @Override
    public String toString() {
        return medicine_.toString() + ", Сумма: " + String.valueOf(medicine_.GetPrice() * amount_);
    }

    private Medicine medicine_;
    private int amount_;
}
