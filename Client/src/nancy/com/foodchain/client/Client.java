package nancy.com.foodchain.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
 
public class Client {
 
    public static void main(String[] args) throws IOException {
 
        System.out.println("Client is starting...");
 
        Socket socket = null;
 
        try {
            //localhost because server running on my machine otherwise IP address of Server machine
            socket = new Socket("localhost", 8081);
        } catch (Exception e) {
            System.out.println("Initializing error. Make sure that server is alive!\n" + e);
        }
        //FoodChainField c = new FoodChainField();
        //use sending message to server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
 
        //use receiving message from server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
        while (true) {
            //receive message
            System.out.println("<New message from server> " + in.readLine());
 
            //get text from user
            BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
            String message = data.readLine();
 
            //send message
            out.println(message);
 
            //receive response
            System.out.println("<Response from server> " + in.readLine());
 
            //close socket when receive exit
            if (message.equalsIgnoreCase("exit")) {
                out.close();
                in.close();
                data.close();
                socket.close();
                break;
            }
 
        }
 
    }
 
}
