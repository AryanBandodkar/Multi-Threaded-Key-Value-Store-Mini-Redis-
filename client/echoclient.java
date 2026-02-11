package client;

import java.io.*;
import java.net.*;
public class echoclient {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 8080);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);

            String message;
            System.out.println("Connected to server , Input Messages : ");

            while ((message = userInput.readLine()) != null) {
                serverOutput.println(message);

                String responce = serverInput.readLine();
                System.out.println("Server : " + responce);
            }
            
            socket.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
