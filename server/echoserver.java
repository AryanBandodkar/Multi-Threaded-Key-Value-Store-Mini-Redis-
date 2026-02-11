package server;
import java.io.*;
import java.net.*;
import java.util.*;

public class echoserver {
    public static void main(String[] args) {

        HashMap<String, String> store = new HashMap<>();

        try {
            
            //Server Connection to port 8080
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server Started (Using port 8080 )");

            //Waiting (Blocked) till client connects to the server
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected to the Server");

            // I/o readers for taking the client message and sending it back
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;

            //loop till client closes
            while ((message = input.readLine()) != null) {
                
                //checks if the message is empty or just spaces are sent
                if (message.trim().isEmpty()) {
                    output.println("Empty command given");
                    continue;
                }

                //Assume that spaces are there between each word/command 
                String trimmed = message.trim();
                String[] parts = trimmed.split("\\s+",3);
                //Gets the command and makes it to upper case for easier processing
                String command = parts[0].toUpperCase();

                switch (command) {
                    case "SET":
                        //checks if the set command isnt used properly
                        if (parts.length != 3) {
                            output.println("Incomplete command. Format Set <key> <value>");
                        }
                        else {

                            //extracts the key value pair nd puts it in the hashmap
                            String key = parts[1];
                            String value = parts[2];
                            store.put(key, value);
                            output.println("Data Stored Successfully");
                        }

                        break;

                    case "GET":
                        
                        if (parts.length != 2) {
                            output.println("Incomplete Command . Format Get <key>");

                        }
                        else {
                            String key = parts[1];
                            if (store.containsKey(key)) {
                                output.println("Value is : " + store.get(key));
                            }
                            else {
                                output.println("Key Doesnt Exist");
                            }
                        }

                        break;

                    case "DEL":

                        if (parts.length != 2){
                            output.println("Incomplete Command. Format Del <key>");
                        }
                        else {
                            String key = parts[1];
                            if (store.containsKey(key)) {
                                store.remove(key);
                                output.println("Key Removed Successfully");
                            } else {
                                output.println("Key not Found");
                            }
                        }
                        break;
                        
                    default:
                        output.println("Unknown command (set,get,del)");

                }


            }
            
            //closes for mem leak
            clientSocket.close();
            serverSocket.close();


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
