package project.exemple.alura;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaDeFilmes {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> titulos = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        /* Gson: percorre o JSON automaticamente para viabilizar a utilização dos dados.
           GsonBuilder(): define a política de nomenclatura a ser encontrada nos atributos do JSON,
           utilizado nesse caso para ler as letras maiúsculas e não precisar alterar no Record TituloOmdb.
         */

        //while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();

            //if (busca.equalsIgnoreCase("sair")) break;

            String chave = "6ed5bc22";
            String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=" + chave;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                //System.out.println(json); //imprime o JSON requisitado no site.

                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                //System.out.println(meuTituloOmdb); Titulo utilizando o Record para capturar os dados.

            /* Para esse projeto, é mais interessante utilizar um objeto da classe Titulo por está mais
            completa e funcional. */

                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                //System.out.println("Titulo já convertido:");
                //System.out.println(meuTitulo); // Titulo utilizando a Classe Titulo.

                titulos.add(meuTitulo);

            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereço");
            } catch (ErroDeConversaoDeAnoException e) { //Exeption criada.
                System.out.println(e.getMessage());
            }
        //}
        System.out.println(titulos);
        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();

        System.out.println("O programa finalizou corretamente!");
    }
}
