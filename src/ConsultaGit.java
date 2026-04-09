import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ConsultaGit {
    public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);

      System.out.println("Digite o nome de usuario para a consulta: ");
      String usuario = scan.nextLine();

      String endereco = "https://api.github.com/users/" + usuario;

      try {
          HttpClient client = HttpClient.newHttpClient();
          HttpRequest request = HttpRequest.newBuilder()
                  .uri(URI.create(endereco))
                  .header ("accept", "application/vnd.github.v3+json")
                  .build();

          HttpResponse<String> response = client
                  .send(request, HttpResponse.BodyHandlers.ofString());
          if (response.statusCode() == 404) {
              throw  new  ErroConsultaGitHubException("Usuario nao encontrado");
          }
          String json = response.body();
          System.out.println(json);
      }
      catch (IOException | InterruptedException e )
          {
              System.out.println("Opss… Houve um erro durante a consulta à API do GitHub.");
              e.printStackTrace();

          } catch (ErroConsultaGitHubException e)
        {
          System.out.println(e.getMessage());
        }

    }
}