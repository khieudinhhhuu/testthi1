package com.khieuthichien.food;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{

    Context context;
    List<Food> foodList;
    DatabaseHelper databaseHelper;


    public FoodAdapter(Context context, List<Food> foodList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.foodList = foodList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodHolder holder, final int position) {
        NumberFormat format=NumberFormat.getCurrencyInstance();
        //databaseHelper = new DatabaseHelper(context);

        final Food food = foodList.get(position);
        holder.tvidfood.setText(food.getIdfood()+"");
        holder.tvnamefood.setText(food.getNamefood());
        holder.tvprice.setText(format.format(food.getPrice())+"");

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    //SQLiteDatabase db = new SQLiteDatabase(this);

                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteFood(foodList.get(position).getIdfood());
                        foodList.get(position);
                        foodList.remove(food);
                        notifyDataSetChanged();
                        Toast.makeText(context, "xoa", Toast.LENGTH_SHORT).show();

                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();


            }
        });

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle(foodList.get(position).getNamefood());
                dialog.setContentView(R.layout.dialog_edit_food);

                final EditText edtnamefood;
                final EditText edtprice;
                final Button btnedit;
                final Button btncancel;

                edtnamefood = dialog.findViewById(R.id.edtnamefood);
                edtprice = dialog.findViewById(R.id.edtprice);
                btnedit = dialog.findViewById(R.id.btnedit);
                btncancel = dialog.findViewById(R.id.btncancel);

                edtnamefood.setText(foodList.get(position).getNamefood());
                edtprice.setText(String.valueOf(foodList.get(position).getPrice()));

                btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Food food1 = new Food();
                        food1.setNamefood(edtnamefood.getText().toString().trim());
                        food1.setPrice(Long.parseLong(edtprice.getText().toString().trim()));

                        databaseHelper.updateFood(food.getIdfood(),food1);

                        foodList.get(position).setNamefood(edtnamefood.getText().toString().trim());
                        foodList.get(position).setPrice(Long.parseLong(edtprice.getText().toString().trim()));

                        notifyItemChanged(position);
                        dialog.dismiss();
                        Toast.makeText(context, "sua", Toast.LENGTH_SHORT).show();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        if (foodList == null){
            return 0;
        }
        return foodList.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {

        TextView tvidfood;
        TextView tvnamefood;
        TextView tvprice;
        ImageView imgedit;
        ImageView imgdelete;

        public FoodHolder(View itemView) {
            super(itemView);

            tvidfood = itemView.findViewById(R.id.tvidfood);
            tvnamefood = itemView.findViewById(R.id.tvnamefood);
            tvprice = itemView.findViewById(R.id.tvprice);
            imgedit = itemView.findViewById(R.id.imgedit);
            imgdelete = itemView.findViewById(R.id.imgdelete);

        }
    }
}
