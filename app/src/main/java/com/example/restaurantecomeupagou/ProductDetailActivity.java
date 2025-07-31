package com.example.restaurantecomeupagou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.restaurantecomeupagou.data.remote.ProdutoApiClient;
import com.example.restaurantecomeupagou.model.Produto;
import com.example.restaurantecomeupagou.utils.Constants;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView textViewDetailName;
    private TextView textViewDetailDescription;
    private TextView textViewDetailPrice;
    private TextView textViewDetailCategory;
    private ImageView imageViewDetailProduct;
    private Button buttonIncreaseQuantity;
    private Button buttonDecreaseQuantity;
    private TextView textViewQuantity;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        textViewDetailName = findViewById(R.id.text_view_detail_name);
        textViewDetailDescription = findViewById(R.id.text_view_detail_description);
        textViewDetailPrice = findViewById(R.id.text_view_detail_price);
        textViewDetailCategory = findViewById(R.id.text_view_detail_category);
        imageViewDetailProduct = findViewById(R.id.image_view_detail_product);
        buttonIncreaseQuantity = findViewById(R.id.button_increase_quantity);
        buttonDecreaseQuantity = findViewById(R.id.button_decrease_quantity);
        textViewQuantity = findViewById(R.id.text_view_quantity);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.INTENT_PRODUTO_ID)) {
            int produtoId = Integer.parseInt(intent.getStringExtra(Constants.INTENT_PRODUTO_ID));
            obterDetalhesProduto(produtoId);
        }

        initListener();
    }

    private void initListener() {
        buttonIncreaseQuantity.setOnClickListener(v -> {
            if (quantity < 10) {
                quantity++;
                textViewQuantity.setText(String.valueOf(quantity));
            } else {
                Toast.makeText(this, "Quantidade máxima atingida", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDecreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                textViewQuantity.setText(String.valueOf(quantity));
            }
        });
    }

    public void obterDetalhesProduto(int produtoId) {
        ProdutoApiClient.getInstance(this).obterProdutoPorId(produtoId, new ProdutoApiClient.ProdutoPorIdCallback() {
            @Override
            public void onSuccess(Produto produto) {
                textViewDetailName.setText(produto.getNome());
                textViewDetailDescription.setText(produto.getDescricao());
                textViewDetailPrice.setText(String.format("R$ %.2f", produto.getPreco()));
                if (produto.getCategoria().equals("L")) {
                    textViewDetailCategory.setText("Lanche");
                } else if (produto.getCategoria().equals("B")) {
                    textViewDetailCategory.setText("Bebida");
                } else if (produto.getCategoria().equals("P")) {
                    textViewDetailCategory.setText("Porção");
                }

                Glide.with(ProductDetailActivity.this)
                        .load(produto.getImagemUrl())
                        .placeholder(R.drawable.product_image_placeholder)
                        .into(imageViewDetailProduct);
            }
            @Override
            public void onError(String errorMessage) {
                Log.e("ProductDetailActivity", "Erro ao obter detalhes do produto: " + errorMessage);
                Toast.makeText(ProductDetailActivity.this, "Erro ao obter detalhes do produto: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}