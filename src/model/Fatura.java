package model;


import java.util.List;

public class Fatura {
    private Pet pet;
    private List<Agendamento> agendamentos;

    public Fatura(Pet pet, List<Agendamento> agendamentos) {
        this.pet = pet;
        this.agendamentos = agendamentos;
    }

    public double calcularTotal() {
        double total = pet.getHorasHospedadas() * pet.getPlano().getPrecoPorHora();
        for (Agendamento ag : agendamentos) {
            if (ag.getPet().equals(pet)) {
                total += ag.getServico().calcularPreco(pet);
            }
        }
        return total;
    }

    public void emitir() {
        System.out.println("\n=========== FATURA ===========");
        System.out.println("Pet: " + pet.getNome());
        System.out.println("Tutor: " + pet.getTutor().getNome());
        System.out.println("Plano: " + pet.getPlano());
        System.out.println("Entrada: " + pet.getHoraEntrada());
        System.out.println("Saída: " + pet.getHoraSaida());
        System.out.println("\nServiços Realizados:");
        for (Agendamento ag : agendamentos) {
            if (ag.getPet().equals(pet)) {
                System.out.printf(" - %s em %s | Duração: %d min | Preço: R$%.2f\n",
                        ag.getServico().getNome(),
                        ag.getDataHora(),
                        ag.getServico().getDuracaoMinutos(),
                        ag.getServico().calcularPreco(pet));
            }
        }
        System.out.printf("\nTotal a pagar: R$%.2f\n", calcularTotal());
        System.out.println("===============================\n");
    }
}
