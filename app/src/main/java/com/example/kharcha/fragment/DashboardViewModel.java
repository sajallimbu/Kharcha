package com.example.kharcha.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kharcha.repository.Repository;
import com.example.kharcha.room.Expense;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Expense>> allExpense;
    private LiveData total;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allExpense = repository.getAllExpense();
        total = repository.getTotal();
    }

    public void insert(Expense expense){
        repository.insert(expense);
    }

    public void deleteAllExpense(Expense expense){
        repository.deleteAllExpense();
    }

    public LiveData<Integer> getTotal(){
        return total;
    }

    public LiveData<List<Expense>> getAllExpense() {
        return allExpense;
    }
}
