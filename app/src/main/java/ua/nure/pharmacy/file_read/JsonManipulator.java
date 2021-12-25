package ua.nure.pharmacy.file_read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.nure.pharmacy.dto.Medicine;
import ua.nure.pharmacy.dto.MedicineLeftover;

public class JsonManipulator {
    public JsonManipulator(Context context) {
        context_ = context;
    }

    public ArrayList<MedicineLeftover> ReadMedicinesFromJsonFile(String file_name) {
        try {
            JSONObject obj = new JSONObject(ReadJsonStringFromFile(file_name));
            JSONArray medicines_array = obj.getJSONArray("medicines");
            ArrayList<MedicineLeftover> meds = new ArrayList<MedicineLeftover>();
            for (int i = 0; i < medicines_array.length(); ++i) {
                JSONObject med = medicines_array.getJSONObject(i);
                meds.add(new MedicineLeftover(new Medicine(med.getInt("id"), med.getString("title"), med.getDouble("price")), med.getInt("leftover")));
            }
            return meds;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void WriteSaleToJsonFile(String file_name, int med_id, int amount, long date) {
        JSONObject sale_object = new JSONObject();
        try {
            sale_object.put("id", med_id);
            sale_object.put("amount", amount);
            sale_object.put("date", String.valueOf(date));

            JSONArray medicines_array = ReadSalesJsonArray(file_name);

            medicines_array.put(sale_object);

            JSONObject obj = new JSONObject();
            obj.put("sales", medicines_array);

            WriteJsonObjectToFile("sales.json", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void WriteMedicinesToJsonFile(String file_name, ArrayList<MedicineLeftover> medicines) {
        JSONArray medicines_array_json = new JSONArray();
        for (int i = 0; i < medicines.size(); ++i) {
            medicines_array_json.put(medicines.get(i).toJsonObject());
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("medicines", medicines_array_json);
            WriteJsonObjectToFile(file_name, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void WriteJsonObjectToFile(String file_name, JSONObject json_object) {
        try {
            File file = new File(context_.getFilesDir(), file_name);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json_object.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray ReadSalesJsonArray(String file_name) {
        try {
            JSONObject obj = new JSONObject(ReadJsonStringFromFile(file_name));
            JSONArray medicines_array = obj.getJSONArray("sales");
            return medicines_array;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String ReadJsonStringFromFile(String file_name) {
        try {
            File file = new File(context_.getFilesDir(), file_name);
            FileReader file_reader = new FileReader(file);
            BufferedReader buffered_reader = new BufferedReader(file_reader);
            StringBuilder string_builder = new StringBuilder();
            String line = buffered_reader.readLine();
            while (line != null){
                string_builder.append(line).append("\n");
                line = buffered_reader.readLine();
            }
            buffered_reader.close();
            return string_builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private Context context_;
}
