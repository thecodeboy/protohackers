import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileEchoClient {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {

            BufferedReader fileReader = new BufferedReader(new FileReader("orig.txt"));

            PrintWriter fileWriter = new PrintWriter(new FileWriter("printed.txt"));

            String userInput;
            while ((userInput = fileReader.readLine()) != null) {
                out.println(userInput);
                String temp = in.readLine();
                System.out.println("echo: " + temp);
                fileWriter.println(temp);
                fileWriter.flush();
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}