package model;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import util.CSVUtil;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Pet> pets = new ArrayList<>();
    private static final List<Agendamento> agendamentos = new ArrayList<>();
    private static int petIdCounter = 1;
    private static int tutorIdCounter = 1;

    
    private static final List<Servico> servicos = Arrays.asList(
            new ServicoFixo("Banho", 30, 50.0),
            new ServicoFixo("Tosa", 45, 70.0),
            new ServicoPorPlano("Passeio", 60, 30.0, 50.0, 70.0),
            new ServicoFixo("Alimentação especial", 15, 40.0),
            new ServicoFixo("Consulta veterinária", 60, 120.0)
    );

    public static void main(String[] args) {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n--- Hotel Pet Menu ;) ---");
            System.out.println("1. Cadastrar Pet");
            System.out.println("2. Listar Pets");
            System.out.println("3. Agendar Serviço");
            System.out.println("4. Registrar saída de Pet");
            System.out.println("5. Emitir fatura");
            System.out.println("6. Relatórios");
            System.out.println("7. Exportar Pets");
            System.out.println("8. Importar Pets");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarPet();
                case 2 -> listarPets();
                case 3 -> agendarServico();
                case 4 -> registrarSaidaPet();
                case 5 -> emitirFatura();
                case 6 -> gerarRelatorios();
                case 7 -> exportarPets();
                case 8 -> importarPets();
                case 0 -> {
                    rodando = false;
                    System.out.println("Encerrando sistema...");
                }
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
        scanner.close();
    }
    private static void exportarPets() {
        System.out.print("Informe o caminho do arquivo CSV para exportar: ");
        String caminho = scanner.nextLine();

        try {
            CSVUtil.exportarPets(pets, caminho);
            System.out.println("Exportação realizada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar: " + e.getMessage());
        }
    }

    private static void importarPets() {
        System.out.print("Informe o caminho do arquivo CSV para importar: ");
        String caminho = scanner.nextLine();

        try {
            List<Pet> importados = CSVUtil.importarPets(caminho);
            pets.clear();
            pets.addAll(importados);
            System.out.println("Importação realizada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao importar: " + e.getMessage());



        }
    }
    private static void cadastrarPet() {
        if (pets.size() >= 50) {
            System.out.println("Limite máximo de pets hospedados atingido.");
            return;
        }

        try {
            System.out.print("Nome do Pet: ");
            String nomePet = scanner.nextLine();

            System.out.print("Espécie: ");
            String especie = scanner.nextLine();

            System.out.print("Raça: ");
            String raca = scanner.nextLine();

            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine());

            System.out.print("Peso (kg): ");
            double peso = Double.parseDouble(scanner.nextLine());

            System.out.print("Nome do Tutor: ");
            String nomeTutor = scanner.nextLine();

            System.out.print("Contato do Tutor: ");
            String contatoTutor = scanner.nextLine();

            Tutor tutor = new Tutor(tutorIdCounter++, nomeTutor, contatoTutor);

            System.out.println("Planos disponíveis: STANDARD, PREMIUM, VIP");
            System.out.print("Escolha o plano: ");
            PlanoHospedagem plano = PlanoHospedagem.valueOf(scanner.nextLine().toUpperCase());

            LocalDateTime horaEntrada = LocalDateTime.now();

            Pet pet = new Pet(petIdCounter++, nomePet, especie, raca, idade, peso, tutor, plano, horaEntrada);
            pets.add(pet);

            System.out.println("Pet cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: entrada inválida. Tente novamente.");
        }
    }

    private static void listarPets() {
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }
        System.out.println("\nPets hospedados:");
        for (Pet pet : pets) {
            System.out.printf("ID: %d | Nome: %s | Espécie: %s | Tutor: %s | Plano: %s | Entrada: %s\n",
                    pet.getId(),
                    pet.getNome(),
                    pet.getEspecie(),
                    pet.getTutor().getNome(),
                    pet.getPlano(),
                    pet.getHoraEntrada());
        }
    }

    private static void agendarServico() {
        if (pets.isEmpty()) {
            System.out.println("Não há pets cadastrados para agendar serviços.");
            return;
        }
        try {
            System.out.print("ID do Pet para agendamento: ");
            int petId = Integer.parseInt(scanner.nextLine());

            Pet pet = pets.stream().filter(p -> p.getId() == petId).findFirst().orElse(null);
            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }

            System.out.println("Serviços disponíveis:");
            for (int i = 0; i < servicos.size(); i++) {
                Servico s = servicos.get(i);
                System.out.printf("%d. %s (Duração: %d min)\n", i + 1, s.getNome(), s.getDuracaoMinutos());
            }

            System.out.print("Escolha o serviço (número): ");
            int servicoIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (servicoIndex < 0 || servicoIndex >= servicos.size()) {
                System.out.println("Serviço inválido.");
                return;
            }

            Servico servico = servicos.get(servicoIndex);

            System.out.print("Data e hora do agendamento (ex: 2025-05-25T15:30): ");
            LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine());

            
            boolean conflito = agendamentos.stream()
                    .anyMatch(a -> a.getPet().equals(pet) && a.getDataHora().equals(dataHora));
            if (conflito) {
                System.out.println("Conflito de horário: já existe um agendamento para esse pet nesse horário.");
                return;
            }

            Agendamento agendamento = new Agendamento(pet, servico, dataHora);
            agendamentos.add(agendamento);
            System.out.println("Serviço agendado com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro no agendamento: " + e.getMessage());
        }
    }

    private static void registrarSaidaPet() {
        try {
            System.out.print("ID do Pet para registrar saída: ");
            int petId = Integer.parseInt(scanner.nextLine());

            Pet pet = pets.stream().filter(p -> p.getId() == petId).findFirst().orElse(null);
            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }

            pet.registrarSaida();
            System.out.println("Saída registrada para o pet: " + pet.getNome());

        } catch (Exception e) {
            System.out.println("Erro ao registrar saída: " + e.getMessage());
        }
    }

    private static void emitirFatura() {
        try {
            System.out.print("ID do Pet para emitir fatura: ");
            int petId = Integer.parseInt(scanner.nextLine());

            Pet pet = pets.stream().filter(p -> p.getId() == petId).findFirst().orElse(null);
            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }

            Fatura fatura = new Fatura(pet, agendamentos);
            fatura.emitir();

        } catch (Exception e) {
            System.out.println("Erro ao emitir fatura: " + e.getMessage());
        }
    }

    private static void gerarRelatorios() {
        System.out.println("\n--- Relatórios ---");
        System.out.println("1. Listar pets por espécie");
        System.out.println("2. Listar pets por plano");
        System.out.println("3. Listar pets por tempo de permanência (horas)");
        System.out.println("4. Relatório mensal (pets atendidos, serviços, receita)");
        System.out.print("Escolha uma opção: ");
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> listarPetsPorEspecie();
                case 2 -> listarPetsPorPlano();
                case 3 -> listarPetsPorTempo();
                case 4 -> relatorioMensal();
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static void listarPetsPorEspecie() {
        System.out.print("Digite a espécie para filtrar: ");
        String especie = scanner.nextLine();
        pets.stream()
                .filter(p -> p.getEspecie().equalsIgnoreCase(especie))
                .forEach(p -> System.out.println(p.getNome() + " - Tutor: " + p.getTutor().getNome()));
    }

    private static void listarPetsPorPlano() {
        System.out.print("Digite o plano para filtrar (STANDARD, PREMIUM, VIP): ");
        try {
            PlanoHospedagem plano = PlanoHospedagem.valueOf(scanner.nextLine().toUpperCase());
            pets.stream()
                    .filter(p -> p.getPlano() == plano)
                    .forEach(p -> System.out.println(p.getNome() + " - Tutor: " + p.getTutor().getNome()));
        } catch (IllegalArgumentException e) {
            System.out.println("Plano inválido.");
        }
    }

    private static void listarPetsPorTempo() {
        System.out.print("Digite o tempo mínimo de permanência em horas: ");
        try {
            long horas = Long.parseLong(scanner.nextLine());
            pets.stream()
                    .filter(p -> p.getHorasHospedadas() >= horas)
                    .forEach(p -> System.out.printf("%s - %d horas\n", p.getNome(), p.getHorasHospedadas()));
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void relatorioMensal() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioMes = agora.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        long petsAtendidos = agendamentos.stream()
                .filter(a -> a.getDataHora().isAfter(inicioMes))
                .map(Agendamento::getPet)
                .distinct()
                .count();

        Map<String, Long> servicosMaisUsados = new HashMap<>();
        double receitaTotal = 0;

        for (Agendamento ag : agendamentos) {
            if (ag.getDataHora().isAfter(inicioMes)) {
                String nomeServico = ag.getServico().getNome();
                servicosMaisUsados.put(nomeServico, servicosMaisUsados.getOrDefault(nomeServico, 0L) + 1);
                receitaTotal += ag.getServico().calcularPreco(ag.getPet());
            }
        }

        System.out.println("Relatório mensal:");
        System.out.println("Pets atendidos: " + petsAtendidos);

        System.out.println("Serviços mais utilizados:");
        servicosMaisUsados.forEach((servico, qtd) -> System.out.printf("- %s: %d vezes\n", servico, qtd));

        System.out.printf("Receita total: R$ %.2f\n", receitaTotal);
    }
}
