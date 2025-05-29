package model;

public class ServicoPorPlano extends Servico {
    private double precoStandard;
    private double precoPremium;
    private double precoVip;

    public ServicoPorPlano(String nome, int duracaoMinutos, double precoStandard, double precoPremium, double precoVip) {
        super(nome, duracaoMinutos);
        this.precoStandard = precoStandard;
        this.precoPremium = precoPremium;
        this.precoVip = precoVip;
    }

    @Override
    public double calcularPreco(Pet pet) {
        switch (pet.getPlano()) {
            case STANDARD:
                return precoStandard;
            case PREMIUM:
                return precoPremium;
            case VIP:
                return precoVip;
            default:
                return precoStandard;
        }
    }
}