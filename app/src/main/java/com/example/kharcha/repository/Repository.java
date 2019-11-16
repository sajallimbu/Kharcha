package com.example.kharcha.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kharcha.room.Expense;
import com.example.kharcha.room.ExpenseDao;
import com.example.kharcha.room.ExpenseDatabase;

import java.util.List;

public class Repository {

    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpense;
    private LiveData total;

    public Repository(Application application) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpense = expenseDao.getAllExpense();
        total = expenseDao.getTotal();
    }

    public void insert(Expense expense) {
        new InsertExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void deleteAllExpense() {
        new DeleteAllExpenseAsyncTask(expenseDao).execute();
    }

    public LiveData<List<Expense>> getAllExpense() {
        return allExpense;
    }

    public LiveData<Integer> getTotal() {
        return total;
    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {

        private ExpenseDao expenseDao;

        private InsertExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void, Void> {

        private ExpenseDao expenseDao;

        private DeleteAllExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.deleteAllExpense();
            return null;
        }
    }


}
