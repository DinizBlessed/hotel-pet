package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Pet implements Registravel {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private double peso;
    private Tutor tutor;
    private PlanoHospedagem plano;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    public Pet(int id, String nome, String especie, String raca, int idade, double peso,
               Tutor tutor, PlanoHospedagem plano, LocalDateTime horaEntrada) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;
        this.tutor = tutor;
        this.plano = plano;
        this.horaEntrada = horaEntrada;
        this.horaSaida = null; 
    }

    @Override
    public void registrarSaida() {
        this.horaSaida = LocalDateTime.now();
    }

    public long getHorasHospedadas() {
        LocalDateTime saida = (horaSaida != null) ? horaSaida : LocalDateTime.now();
        return java.time.Duration.between(horaEntrada, saida).toHours();
    }

    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public PlanoHospedagem getPlano() {
        return plano;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return id == pet.id &&
                Objects.equals(nome, pet.nome) &&
                Objects.equals(tutor, pet.tutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tutor);
    }

    @Override
    public String toString() {
        return String.format("Pet{id=%d, nome='%s', especie='%s', tutor='%s'}", id, nome, especie, tutor.getNome());
    }
}
