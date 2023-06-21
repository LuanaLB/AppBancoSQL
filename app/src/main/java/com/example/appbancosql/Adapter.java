package com.example.appbancosql;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Pessoa> mDataset;
    public BdSqlite bd;
    public Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter(List<Pessoa> myDataset, BdSqlite bd, Context context) {
        this.mDataset = myDataset;
        this.bd = bd;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case

        public TextView id;
        public TextView nome;
        public TextView idade;
        public Button editar;
        public Button excluir;


        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            nome = itemView.findViewById(R.id.nome);
            idade = itemView.findViewById(R.id.idade);
            editar = itemView.findViewById(R.id.btnUpdate);
            excluir = itemView.findViewById(R.id.btnDelete);


        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pessoa p = mDataset.get(position);

        holder.id.setText(String.valueOf(p.getId()));
        holder.nome.setText(p.getNome());
        holder.idade.setText(String.valueOf(p.getIdade()));
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pessoa p = mDataset.get(position);
                int id =  p.getId();
                Intent it = new Intent(context, UpdateActivity.class);
                it.putExtra("id", id);
                it.putExtra("nome", p.getNome());
                it.putExtra("idade", p.getIdade());
                context.startActivity(it);
            }
        });
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa p = mDataset.get(position);
                int id = p.getId();
                bd.excluir(id);
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());

            }
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}