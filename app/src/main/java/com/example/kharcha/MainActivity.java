package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.kharcha.fragment.DashboardFragment;
import com.example.kharcha.fragment.DashboardViewModel;
import com.example.kharcha.fragment.NotesFragment;
import com.example.kharcha.room.Expense;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity implements AddExpenseDialog.DashboardDialogListener {

    SpaceNavigationView navigationView;
    DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.space);
        dashboardViewModel = new DashboardViewModel(getApplication());

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Dashboard", R.drawable.ic_dashboard));
        navigationView.addSpaceItem(new SpaceItem("Notes", R.drawable.ic_note));
        setUpFragment(new DashboardFragment(), "dashboard");

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                openDialog();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0 && !getVisibleFragment("dashboard")) {
                    setUpFragment(new DashboardFragment(), "dashboard");
                } else if (!getVisibleFragment("notes")) {
                    setUpFragment(new NotesFragment(), "notes");
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });
    }

    private void setUpFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigationView.onSaveInstanceState(outState);
    }

    //this helps us to prevent loading the same fragment again when the layout is clicked
    public boolean getVisibleFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
        assert currentFragment.getTag() != null;

        if (currentFragment.getTag().equals(tag)) {
            return true;
        }
        return false;

    }

    public void openDialog() {
        AddExpenseDialog addExpenseDialog = new AddExpenseDialog();
        addExpenseDialog.show(getSupportFragmentManager(), "Add Expense Dialog");
    }

    @Override
    public void applyTexts(String title, String description, String amount) {
        Expense expense = new Expense(title, description, Integer.parseInt(amount));
        dashboardViewModel.insert(expense);
    }


}
