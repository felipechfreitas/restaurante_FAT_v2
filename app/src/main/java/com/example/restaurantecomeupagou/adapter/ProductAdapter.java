package com.example.restaurantecomeupagou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantecomeupagou.ProductDetailActivity;
import com.example.restaurantecomeupagou.R;
import com.example.restaurantecomeupagou.data.remote.CarrinhoSingleton;
import com.example.restaurantecomeupagou.model.Produto;
import com.example.restaurantecomeupagou.utils.Constants;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProdutoViewHolder> {
    private List<Produto> produtos;
    private Context context;

    public ProductAdapter(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagemProduto;
        public TextView nomeProduto;
        public TextView descricaoProduto;
        public TextView precoProduto;
        public Button buttonAddToCart;

        public ProdutoViewHolder(View itemView) {
            super(itemView);
            imagemProduto = itemView.findViewById(R.id.img_item);
            nomeProduto = itemView.findViewById(R.id.txt_nome_produto);
            descricaoProduto = itemView.findViewById(R.id.txt_desc_produto);
            precoProduto = itemView.findViewById(R.id.txt_preco_produto);
            buttonAddToCart = itemView.findViewById(R.id.button_add_to_cart);
        }
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        Glide.with(holder.itemView.getContext())
                .load(produto.getImagemUrl())
                .placeholder(R.drawable.product_image_placeholder)
                .into(holder.imagemProduto);

        holder.nomeProduto.setText(produto.getNome());
        holder.descricaoProduto.setText(produto.getDescricao());

        NumberFormat precoBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        holder.precoProduto.setText(precoBr.format(produto.getPreco()));


        holder.nomeProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.INTENT_PRODUTO_ID, String.valueOf(produto.getId()));
                intent.putExtra("produto", produto);
                context.startActivity(intent);
            }
        });

        holder.buttonAddToCart.setOnClickListener(v -> {
            CarrinhoSingleton.getInstance(context).adicionarProduto(produto);
            Toast.makeText(context, produto.getNome() + " adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}

