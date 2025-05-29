package model;
public abstract class Servico {
    protected String nome;
    protected int duracaoMinutos;

    public Servico(String nome, int duracaoMinutos) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
    }

    public abstract double calcularPreco(Pet pet);

    public String getNome() {
        return nome;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
}