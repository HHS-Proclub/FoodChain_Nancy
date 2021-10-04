package nancy.com.foodchain.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Server {
 
    public static void main(String[] args) throws IOException {
    	FoodChain foodChain;
        System.out.println("Server is starting...");
        foodChain = new FoodChain();
        foodChain.doIt();
        
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
 
        try {
            serverSocket = new ServerSocket(8081);
            System.out.println("Server is ready!");
        } catch (Exception e) {
            System.out.println("Initializing error. Try changing port number!" + e);
        }
 
        clientSocket = serverSocket.accept();
 
        //use sending message to client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //use receiving message from client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
        String message = null;
 
        while (true) {
            //send message
            out.println("Write a word to see some magic: ");
 
            //receive message
            message = in.readLine();
            System.out.println("<New message from client> " + message);
 
            //reverse and send it back
           // out.println(new StringBuilder(message).reverse().toString());
            out.println(new StringBuilder(foodChain.getLifeList().toString()));
 
            //close socket when receive "exit"
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Session closed!");
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
                break;
            }
        }
 
    }
}