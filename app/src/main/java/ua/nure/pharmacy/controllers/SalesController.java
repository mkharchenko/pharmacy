package ua.nure.pharmacy.controllers;

import android.content.Context;
import android.icu.util.Calendar;

import java.util.ArrayList;

import ua.nure.pharmacy.dto.MedicineLeftover;
import ua.nure.pharmacy.file_read.JsonManipulator;

public class SalesController {
    public SalesController(Context context) {
        json_manipulator_ = new JsonManipulator(context);
        medicines_left_ = json_manipulator_.ReadMedicinesFromJsonFile("medicines.json");
        json_manipulator_.WriteMedicinesToJsonFile("medicines.json", medicines_left_);
    }

    public ArrayList<MedicineLeftover> GetMedicineLeftover() {
        return medicines_left_;
    }

    public void AddSale(MedicineLeftover med, int amount) {
        if (amount <= med.GetLeftover()) {
            med.ChangeLeftover(amount * -1);
            json_manipulator_.WriteMedicinesToJsonFile("medicines.json", medicines_left_);
            json_manipulator_.WriteSaleToJsonFile("sales.json", med.GetMedicine().GetId(), amount, Calendar.getInstance().getTimeInMillis());
        }
    }

    private JsonManipulator json_manipulator_;

    private ArrayList<MedicineLeftover> medicines_left_;
}
