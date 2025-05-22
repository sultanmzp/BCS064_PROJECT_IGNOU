package com.saffat.examconductor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {

    Context context;
    ArrayList<MainModel> mainModels;

    public MainAdapter(Context context, ArrayList<MainModel> mainModels) {
        this.context = context;
        this.mainModels = mainModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        viewHolder my=new viewHolder(view);
        return my;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        final MainModel mModel=mainModels.get(position);
        holder.imageView.setImageResource(mModel.getNumImage());
        holder.textView.setText(mModel.getNumWord());

    }


    @Override
    public int getItemCount() {
        return mainModels.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cardIcon);
            textView=itemView.findViewById(R.id.cardText);
        }
    }
}
