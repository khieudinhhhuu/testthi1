package com.khieuthichien.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "food_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_FOOD = "food";

    public static final String COLUMN_ID_FOOD = "id";
    public static final String COLUMN_NAME_FOOD = "name";
    public static final String COLUMN_PRICE_FOOD = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = " CREATE TABLE " + TABLE_FOOD + "(" +
                "" + COLUMN_ID_FOOD + " INTEGER PRIMARY KEY, " +
                "" + COLUMN_NAME_FOOD + " TEXT, " +
                "" + COLUMN_PRICE_FOOD + " LONG " +
                ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public void insertFood(Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_FOOD, food.getIdfood());
        values.put(COLUMN_NAME_FOOD, food.getNamefood());
        values.put(COLUMN_PRICE_FOOD, food.getPrice());

        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    public Food getFood(String food){
        Food food1 = null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_FOOD ,
                new String[]{COLUMN_ID_FOOD, COLUMN_NAME_FOOD, COLUMN_PRICE_FOOD},
                COLUMN_ID_FOOD + "=?",
                new String[]{String.valueOf(food)},
                null, null, null );

        if (cursor != null && cursor.moveToFirst()){

            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_FOOD));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FOOD));

            long price = cursor.getLong(cursor.getColumnIndex(COLUMN_PRICE_FOOD));

            food1 = new Food(id,name,price);
        }
        cursor.close();
        return food1;
    }

    public List<Food> getAllFood(){

        List<Food> foodList = new ArrayList<>();

        String SECLECT_ALL_FOOD = " SELECT * FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(SECLECT_ALL_FOOD , null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_FOOD));

                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FOOD));

                long price = cursor.getLong(cursor.getColumnIndex(COLUMN_PRICE_FOOD));

                Food food = new Food();
                food.setIdfood(id);
                food.setNamefood(name);
                food.setPrice(price);

                foodList.add(food);

            }while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return foodList;
    }

    public int updateFood(int id,Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_FOOD, food.getNamefood());
        values.put(COLUMN_PRICE_FOOD, food.getPrice());

        return db.update(TABLE_FOOD, values, COLUMN_ID_FOOD + "=?", new String[]{String.valueOf(id)});

    }

    public void deleteFood(int idfood){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, COLUMN_ID_FOOD + "=?", new String[]{String.valueOf(idfood)});
        db.close();
    }

}
