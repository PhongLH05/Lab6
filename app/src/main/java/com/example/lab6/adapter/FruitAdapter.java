package com.example.lab6.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab6.R;
import com.example.lab6.databinding.ItemFruitBinding;
import com.example.lab6.model.Fruit;
import com.example.lab6.services.IClickItemProduct;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Fruit> list;
    private FruitClick fruitClick;
    private ArrayList<Fruit> ds = new ArrayList<>();
    private IClickItemProduct iClickItemProduct;

    public FruitAdapter(Context context, ArrayList<Fruit> list, FruitClick fruitClick) {
        this.context = context;
        this.list = list;
        this.fruitClick = fruitClick;
    }

    public FruitAdapter(Context context, ArrayList<Fruit> list, IClickItemProduct iClickItemProduct) {
        this.context = context;
        this.list = list;
        this.iClickItemProduct = iClickItemProduct;
    }

    public interface FruitClick {
        void delete(Fruit fruit);
        void edit(Fruit fruit);

        void showDetail(Fruit fruit);
    }

    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.binding.tvName.setText(fruit.getName());
        holder.binding.tvPriceQuantity.setText("price :" +fruit.getPrice()+" - quantity:" +fruit.getQuantity());
        holder.binding.tvDes.setText(fruit.getDescription());
        String url  = fruit.getImage().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        holder.itemView.setOnClickListener(v -> {
            iClickItemProduct.onClickItem(fruit);
        });
        Glide.with(context)
                .load(newUrl)
                .thumbnail(Glide.with(context).load(R.drawable.baseline_broken_image_24))
                .into(holder.binding.img);

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitClick.edit(fruit);
                notifyDataSetChanged();
            }
        });
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitClick.delete(fruit);
                notifyDataSetChanged();
            }
        });


        Log.d("321321", "onBindViewHolder: "+list.get(position).getImage().get(0));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFruitBinding binding;
        public ViewHolder(ItemFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
