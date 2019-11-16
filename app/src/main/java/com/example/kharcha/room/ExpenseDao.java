package com.example.kharcha.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Query("DELETE FROM expense_table")
    void deleteAllExpense();

    @Query("SELECT SUM(amount) as amount FROM expense_table")
    LiveData<Integer> getTotal();

    @Query("SELECT * FROM expense_table ORDER BY id DESC")
    LiveData<List<Expense>> getAllExpense();
}
