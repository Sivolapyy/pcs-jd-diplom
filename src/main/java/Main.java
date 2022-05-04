import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        while (true) {

            try (ServerSocket serverSocket = new ServerSocket(8989);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

                final String searchWord = in.readLine();

                if (searchWord.equals("stop")) {
                    clientSocket.close();
                    out.close();
                    in.close();
                    break;
                }

                List<PageEntry> result = engine.search(searchWord);

                if (!result.isEmpty()) {
                    Type listType = new TypeToken<List<PageEntry>>() {}.getType();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    out.println(gson.toJson(result, listType));
                } else {
                    out.println("Слово " + searchWord + " в документах не найдено...");
                }

            } catch (IOException exception) {
                System.out.println("Ошибка - " + exception.getMessage());
            }

        }

    }

}