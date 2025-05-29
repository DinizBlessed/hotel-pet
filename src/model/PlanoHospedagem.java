package model;

public enum PlanoHospedagem {
    STANDARD(10.0),
    PREMIUM(20.0),
    VIP(30.0);

    private final double precoPorHora;

    PlanoHospedagem(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }
}
