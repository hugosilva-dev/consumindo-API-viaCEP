package project.hugo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConsumindoViaCEP {
    public static void main(String[] args){
        System.out.println("Bem vindo ao Buscador de Endereços!");
        System.out.println("Informe o CEP do local que quer encontrar:");
        Scanner leitura = new Scanner(System.in);
        String cep = leitura.nextLine();
        ValidandoCEP.validaCep(cep);
        String endereco = "https://viacep.com.br/ws/" + cep + "/json/";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send
                    (request, HttpResponse.BodyHandlers.ofString());

            Cep localPesquisado = gson.fromJson(response.body(), Cep.class);
            System.out.println(localPesquisado);

            FileWriter escrita = new FileWriter(cep + ".json");
            escrita.write(gson.toJson(localPesquisado));
            escrita.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Não consegui obter o endereço a partir desse CEP!");
        }
        leitura.close();
    }
}
