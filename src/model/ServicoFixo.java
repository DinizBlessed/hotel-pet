package model;
public class ServicoFixo extends Servico{
    private final double preco;

    public ServicoFixo(String nome, int duracaoMinutos, double preco) {
        super(nome, duracaoMinutos);
        this.preco = preco;
    }

    @Override
    public double calcularPreco(Pet pet){
        return preco;

    }
}
