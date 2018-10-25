package com.khieuthichien.food;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private FloatingActionButton fabthem;

    private List<Food> foodList;
    private FoodAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerview);
        fabthem = findViewById(R.id.fabthem);

        databaseHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();

        foodList = databaseHelper.getAllFood();

        adapter = new FoodAdapter(this, foodList, databaseHelper);
        recyclerview.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        fabthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_add_food, null);
                builder.setView(dialogView);
                final Dialog dialog = builder.show();

                final EditText edtidfood;
                final EditText edtnamefood;
                final EditText edtprice;
                final Button btnadd;
                final Button btncancel;

                edtidfood = dialogView.findViewById(R.id.edtidfood);
                edtnamefood = dialogView.findViewById(R.id.edtnamefood);
                edtprice = dialogView.findViewById(R.id.edtprice);
                btnadd = dialogView.findViewById(R.id.btnadd);
                btncancel = dialogView.findViewById(R.id.btncancel);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtnamefood.getText().toString().trim();

                        if (edtidfood.getText().toString().isEmpty() || name.isEmpty() || edtprice.getText().toString().isEmpty()){
                            if (edtidfood.getText().toString().isEmpty()){
                                edtidfood.setError(getString(R.string.notify_name));
                                return;
                            }
                            if (name.isEmpty()){
                                edtnamefood.setError(getString(R.string.notify_name));
                                return;
                            }
                            if (edtprice.getText().toString().isEmpty()){
                                edtprice.setError(getString(R.string.notify_name));
                                return;
                            }
                        }else {
                            if (edtidfood.getText().toString().length() > 5 || name.length() < 5 || name.length() > 10 || edtprice.getText().toString().length() < 0 ){
                                if (edtidfood.getText().toString().length() > 5){
                                    edtidfood.setError(getString(R.string.notify_do_dai));
                                    return;
                                }
                                if (name.length() < 5 || name.length() > 10 ){
                                    edtnamefood.setError(getString(R.string.notify_name_dodai));
                                    return;
                                }
                                if (edtprice.getText().toString().length() < 0){
                                    edtprice.setError(getString(R.string.notify_giatien));
                                    return;
                                }
                            }else {
                                int id = Integer.parseInt(edtidfood.getText().toString().trim());
                                long price = Long.parseLong(edtprice.getText().toString().trim());

                                Food food2 = new Food();
                                foodList = new ArrayList<>();
                                for (int i = 0; i < edtidfood.length(); i++) {
                                    if (edtidfood.getText().toString().equalsIgnoreCase("")){
                                        edtidfood.setError(getString(R.string.notify_trung_mhau));
                                        return;
                                    }else {
                                        Food food = new Food();
                                        food.setIdfood(id);
                                        food.setNamefood(name);
                                        food.setPrice(price);

                                        databaseHelper.insertFood(food);
                                        databaseHelper.getAllFood();

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                }

                            }
                        }
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


    }
}
