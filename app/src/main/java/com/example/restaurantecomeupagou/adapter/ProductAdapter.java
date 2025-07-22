package com.example.restaurantecomeupagou.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.R;
import com.example.restaurantecomeupagou.model.Produto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProdutoViewHolder> {
    private List<Produto> produtos;

    public ProductAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagemProduto;
        public TextView nomeProduto;
        public TextView descricaoProduto;
        public TextView precoProduto;

        public ProdutoViewHolder(View itemView) {
            super(itemView);
            imagemProduto = itemView.findViewById(R.id.img_item);
            nomeProduto = itemView.findViewById(R.id.txt_nome_prato);
            descricaoProduto = itemView.findViewById(R.id.txt_desc_prato);
            precoProduto = itemView.findViewById(R.id.txt_preco_prato);

        }
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.imagemProduto.setImageResource(produto.getImagemurl());
        holder.nomeProduto.setText(produto.getNome());
        holder.descricaoProduto.setText(produto.getDescricao());

        NumberFormat precoBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        holder.precoProduto.setText(precoBr.format(produto.getPreco()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}

