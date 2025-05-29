package util;
import model.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static void exportarPets(List<Pet> pets, String caminho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Pet pet : pets) {
                writer.write(String.format("%d,%s,%s,%s,%d,%.2f,%s,%s,%s,%s",
                        pet.getId(),
                        pet.getNome(),
                        pet.getEspecie(),
                        pet.getRaca(),
                        pet.getIdade(),
                        pet.getPeso(),
                        pet.getTutor().getNome(),
                        pet.getTutor().getContato(),
                        pet.getPlano(),
                        pet.getHoraEntrada()
                ));
                writer.newLine();
            }
        }
    }
    public static List<Pet> importarPets(String caminho) throws IOException {
        List<Pet> pets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int id = 0;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 10) {
                    Tutor tutor = new Tutor(id++, dados[6], dados[7]);
                    PlanoHospedagem plano = PlanoHospedagem.valueOf(dados[8]);
                    LocalDateTime entrada = LocalDateTime.parse(dados[9]);
                    Pet pet = new Pet(Integer.parseInt(dados[0]), dados[1], dados[2], dados[3],
                            Integer.parseInt(dados[4]), Double.parseDouble(dados[5]), tutor, plano, entrada);
                    pets.add(pet);
                }
            }
        }
        return pets;
    }
}

