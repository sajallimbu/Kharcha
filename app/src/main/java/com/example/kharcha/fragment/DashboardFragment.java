package com.example.kharcha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha.R;
import com.example.kharcha.room.Expense;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ImageView reset;
    private ImageView addExpenseImage;
    private TextView addExpenseDescription;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment_new, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addExpenseImage = getView().findViewById(R.id.add_image);
        addExpenseDescription = getView().findViewById(R.id.add_description);

        reset = getView().findViewById(R.id.reset_btn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.deleteAllExpense();
            }
        });

        final TickerView tickerView = getView().findViewById(R.id.tickerView);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());

        RecyclerView recyclerView = getView().findViewById(R.id.recent_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        final DashboardAdapter adapter = new DashboardAdapter();
        recyclerView.setAdapter(adapter);

        dashboardViewModel = ViewModelProviders.of(getActivity()).get(DashboardViewModel.class);
        dashboardViewModel.getAllExpense().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                //update our recycler view
                if (!expenses.isEmpty()) {
                    addExpenseDescription.setVisibility(View.INVISIBLE);
                    addExpenseImage.setVisibility(View.INVISIBLE);
                } else {
                    addExpenseImage.setVisibility(View.VISIBLE);
                    addExpenseDescription.setVisibility(View.VISIBLE);
                }
                adapter.submitList(expenses);

            }
        });


        dashboardViewModel.getTotal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) {
                    tickerView.setText("Rs.0");
                } else {
                    String concatenate = "Rs." + integer;
                    tickerView.setText(concatenate);
                }
            }
        });
    }
}
