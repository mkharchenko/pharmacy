package ua.nure.pharmacy.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class MedicineLeftover {
    public MedicineLeftover(Medicine medicine, int leftover) {
        medicine_ = medicine;
        leftover_ = leftover;
    }

    public Medicine GetMedicine() {
        return medicine_;
    }

    public int GetLeftover() {
        return leftover_;
    }

    public Boolean ChangeLeftover(int change) {
        if (leftover_ + change < 0) {
            return false;
        }
        leftover_ += change;
        return true;
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject med_object = new JSONObject();
            med_object.put("id", medicine_.GetId());
            med_object.put("title", medicine_.GetTitle());
            med_object.put("price", medicine_.GetPrice());
            med_object.put("leftover", leftover_);
            return med_object;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return medicine_.toString() + ", Остаток: " + String.valueOf(leftover_);
    }

    private Medicine medicine_;
    private int leftover_;
}
