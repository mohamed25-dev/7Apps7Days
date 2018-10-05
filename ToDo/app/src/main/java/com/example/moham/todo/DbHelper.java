package com.example.moham.todo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 1;
    public static final String DB_TABLE = "Task";
    public static final String COLUMN_NAME = "TaskName";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = ("CREATE TABLE " + DB_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL )");
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertTask(String task) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, task);

        database.insertWithOnConflict(DB_TABLE, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);

        database.close();
    }

    public void deleteTask (String task) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DB_TABLE, COLUMN_NAME + " = ?", new String[] {task});
        database.close();
    }

    public List<String> getTasks () {
        List<String> tasksList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, new String[] {COLUMN_NAME},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME);
            String task = cursor.getString(index);

            tasksList.add(task);
        }

        cursor.close();
        database.close();
        return tasksList;

    }
}
