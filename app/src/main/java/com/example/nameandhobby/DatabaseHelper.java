package com.example.nameandhobby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String HOBBY_TABLE = "HOBBY_TABLE";
    private static final String COLUMN_ID = "COLUMN_ID";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COLUMN_HOBBY = "COLUMN_HOBBY";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "hobby.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Create Table");
        String createTableStatement = "CREATE TABLE " + HOBBY_TABLE + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_HOBBY + " TEXT)";
        try{
            db.execSQL(createTableStatement);
        }catch (Exception e){
            System.out.println("error bro");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(DataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,dataModel.getNama());
        cv.put(COLUMN_HOBBY,dataModel.getHobby());

        long insert = db.insert(HOBBY_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData(String id, String nama, String hobby){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_NAME, nama);
        cv.put(COLUMN_HOBBY, hobby);

        db.update(HOBBY_TABLE,cv,"COLUMN_ID = ?",new String[]{ id });
        return true;
    }

    public boolean deleteOne(DataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ HOBBY_TABLE + " WHERE " + COLUMN_ID + " = " + dataModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<DataModel> getEveryone(){
        List<DataModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String stringQuery = "SELECT * FROM "+ HOBBY_TABLE;
        Cursor cursor = db.rawQuery(stringQuery,null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nama = cursor.getString(1);
                String hobby = cursor.getString(2);

                DataModel dataModel = new DataModel(id,nama,hobby);
                returnList.add(dataModel);
            }while(cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
