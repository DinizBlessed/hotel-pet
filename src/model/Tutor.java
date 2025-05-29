package model;
import java.util.Objects;

public class Tutor {
    private int id;
    private String nome;
    private String contato;

    public Tutor(int id, String nome, String contato) {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getContato() {
        return contato;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tutor tutor = (Tutor) obj;
        return nome.equals(tutor.nome) && contato.equals(tutor.contato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, contato);
    }
}