package com.example.kharcha.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Expense.class},version = 1,exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExpenseDatabase.class, "expense_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
