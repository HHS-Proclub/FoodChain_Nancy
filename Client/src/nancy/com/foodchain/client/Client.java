package nancy.com.foodchain.client;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import nancy.com.foodchain.server.Life;
 
public class Client {
	FoodChainField field;
	public Socket socket;
	public int weatherCondition = 5;
    public static void main(String[] args) throws IOException {
    	new Client().doIt();
    }
    
    void doIt() throws IOException {
    	
    	Thread updateClient = new Thread(new UpdateClient(this));   	  	
    	field = new FoodChainField(this);
    	updateClient.start();
    	socket = new Socket("localhost", 8082);
    	
    }
    
    class UpdateClient implements Runnable{
    	public UpdateClient(Client client) {
			super();
			this.client = client;
		}

    	Client client;

		@Override
		public void run() {		
			System.err.println("Enter run in UpdateClient");
	        try {
	            //localhost because server running on my machine otherwise IP address of Server machine
	        	Socket socket = new Socket("localhost", 8081);
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		        out.println("[]");
		        while (true) {
		    		String jsonString = in.readLine();
					field.update(jsonString);
		        }
	        } catch (Exception e) {
	            System.out.println("Initializing error. Make sure that server is alive!\n" + e);
	        }
			
		}
    	
    }
    
   
 
}
