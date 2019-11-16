package com.example.kharcha.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha.R;
import com.example.kharcha.room.Expense;

public class DashboardAdapter extends ListAdapter<Expense,DashboardAdapter.DashboardHolder> {

    public DashboardAdapter() {
        super(DIFF_CALLBACK);
    }

    //adds animation to the items in recyclerview
    private static final DiffUtil.ItemCallback<Expense> DIFF_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getAmount() == newItem.getAmount();
        }
    };

    @NonNull
    @Override
    public DashboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);
        return new DashboardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardHolder holder, int position) {
        Expense currentExpense = getItem(position);
        holder.title.setText(currentExpense.getTitle());
        holder.description.setText(currentExpense.getDescription());
        holder.amount.setText(String.valueOf(currentExpense.getAmount()));
    }

    class DashboardHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView amount;

        public DashboardHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_expense_title);
            description = itemView.findViewById(R.id.text_expense_description);
            amount = itemView.findViewById(R.id.text_expense_amount);
        }
    }
}
