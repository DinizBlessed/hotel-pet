package model;
import java.time.LocalDateTime;

public class Agendamento {
    private Pet pet;
    private Servico servico;
    private LocalDateTime dataHora;

    public Agendamento(Pet pet, Servico servico, LocalDateTime dataHora) {
        this.pet = pet;
        this.servico = servico;
        this.dataHora = dataHora;
    }

    public Pet getPet() {
        return pet;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}