package server;
import java.io.*;
import java.net.*;

public class echoserver {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server Started (Using port 8080 )");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected to the Server");

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;

            while ((message = input.readLine()) != null) {
                System.out.println("Message Recieved : " + message);
                output.println("Echo : " + message);
            }
            
            clientSocket.close();
            serverSocket.close();


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
