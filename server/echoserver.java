package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class echoserver {
    private static ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(8080);){

           
            System.out.println("Server Started (Using port 8080 )");

            //Having a thread for each client that connects
            while (true) {

                Socket client = serverSocket.accept();
                new Thread(() -> handleClient(client)).start();
            }

            
            //closes for mem leak
            
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void handleClient(Socket client) {
        try {
            //Done for each client
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);

            String message;

            while ((message = input.readLine()) != null) {
                String response = handleCommand(message);
                output.println(response);
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static String handleCommand(String message) {
        
        //Assume that spaces are there between each word/command 
        String trimmed = message.trim();
        String[] parts = trimmed.split("\\s+", 3);
        //Gets the command and makes it to upper case for easier processing
        String command = parts[0].toUpperCase();
        
        switch (command) {
            case "SET":
                //checks if the set command isnt used properly
                if (parts.length != 3) {
                    return "Incomplete command. Format Set <key> <value>";
                } else {
        
                    //extracts the key value pair nd puts it in the hashmap
                    String key = parts[1];
                    String value = parts[2];
                    store.put(key, value);
                   return "Data Stored Successfully";
                }
        
                
        
            case "GET":
        
                if (parts.length != 2) {
                    return "Incomplete Command . Format Get <key>";
        
                } else {
                    String key = parts[1];
                    if (store.containsKey(key)) {
                        return store.get(key);
                    } else {
                        return "Key Doesnt Exist";
                    }
                }
        
                
        
            case "DEL":
        
                if (parts.length != 2) {
                    return "Incomplete Command. Format Del <key>";
                } else {
                    String key = parts[1];
                    if (store.containsKey(key)) {
                        store.remove(key);
                        return "Key Removed Successfully";
                    } else {
                        return "Key not Found";
                    }
                }
                
        
            default:
                return "Unknown command (set,get,del)";
        
        }
    }

}
