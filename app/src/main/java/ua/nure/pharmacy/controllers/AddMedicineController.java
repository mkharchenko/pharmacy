package ua.nure.pharmacy.controllers;

import android.content.Context;

import java.util.ArrayList;

import ua.nure.pharmacy.dto.Medicine;
import ua.nure.pharmacy.dto.MedicineLeftover;
import ua.nure.pharmacy.file_read.JsonManipulator;

public class AddMedicineController {
    public AddMedicineController(Context context) {
        json_manipulator_ = new JsonManipulator(context);
    }

    public void AddMedicineToJson(String title, double price, int leftover) {
        ArrayList<MedicineLeftover> meds = json_manipulator_.ReadMedicinesFromJsonFile("medicines.json");
        int next_id = 0;
        for (int i = 0; i < meds.size(); ++i) {
            if (meds.get(i).GetMedicine().GetId() > next_id)
                next_id = meds.get(i).GetMedicine().GetId();
        }
        next_id += 1;
        MedicineLeftover new_med = new MedicineLeftover(new Medicine(next_id, title, price), leftover);
        meds.add(new_med);
        json_manipulator_.WriteMedicinesToJsonFile("medicines.json", meds);
    }

    private JsonManipulator json_manipulator_;
}
