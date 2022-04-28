import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("localhost", 8989);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Введите слово для поиска?");
            String word = sc.nextLine();

            out.println(word);

            String input;
            while ((input = in.readLine()) != null) {
                System.out.println(input);
            }

        } catch (IOException exc) {
            System.out.println("Ошибка - " + exc.getMessage());
        }

    }

}
