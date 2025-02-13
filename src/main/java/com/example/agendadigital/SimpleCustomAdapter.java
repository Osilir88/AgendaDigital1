package com.example.agendadigital;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SimpleCustomAdapter extends RecyclerView.Adapter<SimpleCustomAdapter.ViewHolder>{

    public interface ItemClickListener {
        public void onClick(View view, int position);
    }

    private ArrayList<Contacto> dataSet;
    private ItemClickListener clicListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView identidadContacto;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            identidadContacto = (TextView) view.findViewById(R.id.identidadContacto);
        }

        public TextView getIdentidadContacto() {
            return identidadContacto;
        }

        @Override
        public void onClick(View view) {
            clicListener.onClick(view, getAdapterPosition());
        }
    }

    public SimpleCustomAdapter(ArrayList<Contacto> dataSet) {
        this.dataSet = dataSet;
    }

    public void setOnClickListener(ItemClickListener clicListener){
        this.clicListener = clicListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getIdentidadContacto().setText(dataSet.get(position).getNombre());
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}