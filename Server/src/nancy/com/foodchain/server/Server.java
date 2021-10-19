package nancy.com.foodchain.server;
//import com.google.gson.Gson; 
//import com.google.gson.GsonBuilder;  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
 
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
        
     // get the output stream from the socket.
        OutputStream outputStream = clientSocket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        
        
        
        //use receiving message from client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
        String message = "";
 
        while (true) {
        	//foodChain.getLifeList()
        	StringBuilder sb = new StringBuilder();
        	List <Life>list = foodChain.getLifeList();
        	foodChain.total.put("Wolf", "0");
        	foodChain.total.put("Rabbit", "0");
        	foodChain.total.put("Dandelion", "0");
        	foodChain.total.put("Total", "0");
        	foodChain.nullList = new ArrayList();
        	int d;
        	for (int i=0; i<list.size();i++) {
        		Life life = list.get(i);
        		if (life==null) {
        			foodChain.nullList.add(""+i);
        			continue;
        		}
        		if (i==0) {
        			sb.append(life.toJson());
        		} else {
        			sb.append(","+life.toJson());
        		}
        		d = Integer.parseInt(foodChain.total.get(life.type));
        		foodChain.total.put(life.type, ""+(d+1));
        		d = Integer.parseInt(foodChain.total.get("Total"));
        		foodChain.total.put("Total", ""+(d+1));
        	}
        	String lifeListString = "["+sb.toString()+"]";
        		
        	//objectOutputStream.writeObject();
        	///foodChain.printList();
            out.println(new StringBuilder(lifeListString));
        	try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}  
            //close socket when receive "exit"
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Session closed!");
                objectOutputStream.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
                break;
            }
        }
 
    }
}