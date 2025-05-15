package com.example.zybertest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zybertest.Models.Discount;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zybertest.Models.DiscountDisplay;
import com.example.zybertest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {

    private Context context;
    private List<DiscountDisplay> discounts;

    public DiscountAdapter(Context context, List<DiscountDisplay> discounts) {
        this.context = context;
        this.discounts = discounts;
    }

    public void setDiscounts(List<DiscountDisplay> discounts) {
        this.discounts = discounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discount, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        DiscountDisplay discount = discounts.get(position);

        holder.titleTextView.setText(discount.getGameTitle());

        // Скидка
        String discountText = discount.getDiscountPercentage();
        if (!discountText.endsWith("%")) {
            discountText += "%";
        }
        holder.discountTextView.setText("-" + discountText);

        // Даты
        String start = discount.getStartDate();
        String end = discount.getEndDate();
        if (start != null && end != null) {
            holder.dateTextView.setText(" по " + end);
        } else {
            holder.dateTextView.setText("Дата не указана");
        }

        // Картинка
        if (discount.getImageUrl() != null && !discount.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(discount.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    static class DiscountViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, discountTextView, dateTextView;
        ImageView imageView;

        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.discountGameTitle);
            discountTextView = itemView.findViewById(R.id.discountPercentage);
            dateTextView = itemView.findViewById(R.id.originalData);
            imageView = itemView.findViewById(R.id.discountImage);
        }
    }
}
