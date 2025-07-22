package com.example.restaurantecomeupagou.model;

public class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private int imagemurl;

    public Produto(String nome, String descricao, double preco, int imagemurl) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemurl = imagemurl;
    }

    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public double getPreco() {
        return preco;
    }
    public int getImagemurl() {
        return imagemurl;
    }

}
