import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class EchoServer {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]))) {
            System.out.println("EchoServer started on port " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new EchoHandler(clientSocket, UUID.randomUUID().toString())).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

class EchoHandler implements Runnable {
    Socket clientSocket;
    String uuid;
    public EchoHandler(Socket clientSocket, String uuid) {
        this.clientSocket = clientSocket;
        this.uuid = uuid;
        System.out.println("Starting EchoHandler for " + uuid);
    }

    @Override
    public void run() {
        try(PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }
            clientSocket.close();
            System.out.println("Stopped EchoHandler for " + uuid);
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + clientSocket.getPort() + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}