package com.example.thesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class DatabaseSchema extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DroneFlight.db";
    private static final String TABLE_NAME = "tb_operation";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GSON_OPERATION = "operation_gson";
    private static final String COLUMN_SYNC_DATE = "sync_date";

    public DatabaseSchema(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_GSON_OPERATION + " TEXT, " +
                        COLUMN_SYNC_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addOperation(String gson, String syncDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GSON_OPERATION, gson);
        cv.put(COLUMN_SYNC_DATE, syncDate);
        db.insert(TABLE_NAME, null, cv);
    }

    Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateOperation(String row_id, String gson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GSON_OPERATION, gson);

        db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
    }

    void updateSyncDate(String row_id, String sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SYNC_DATE, sync);

        db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
    }
}
