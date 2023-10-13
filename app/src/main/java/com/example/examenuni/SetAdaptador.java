package com.example.examenuni;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SetAdaptador extends RecyclerView.Adapter<SetAdaptador.viewHolder>{

    ArrayList<SetModelo>list;
    Context context;

    public SetAdaptador(ArrayList<SetModelo> list,Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sets,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final SetModelo model = list.get(position);

        holder.setText.setText(model.getSetText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PdfViewActivity.class);
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  viewHolder extends RecyclerView.ViewHolder {

        TextView setText;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            setText = itemView.findViewById(R.id.setText);
        }
    }
}
