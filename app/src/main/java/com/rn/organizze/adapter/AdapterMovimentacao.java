package com.rn.organizze.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rn.organizze.R;
import com.rn.organizze.model.Movimentacao;

import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder>{

    List<Movimentacao> movimentacoes;
    Context context;

    public AdapterMovimentacao(List<Movimentacao> movimentacoes, Context context){
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(com.rn.organizze.R.layout.adapter_movimentacao, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movimentacao movimentacao = movimentacoes.get(position);

        holder.titulo.setText(movimentacao.getDescricao());
        holder.valor.setText(String.valueOf(movimentacao.getValor()));
        holder.categoria.setText(movimentacao.getCategoria());

        if(movimentacao.getTipo().equals("d")){
            holder.valor.setTextColor(context.getResources().getColor(com.rn.organizze.R.color.colorAccentDespesa));
            holder.valor.setText("-"+movimentacao.getValor());
        }


    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, valor, categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(com.rn.organizze.R.id.textAdapterTitulo);
            valor = itemView.findViewById(com.rn.organizze.R.id.textAdapterValor);
            categoria = itemView.findViewById(com.rn.organizze.R.id.textAdapterCategoria);
        }
    }

}
