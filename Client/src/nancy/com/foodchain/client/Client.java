package nancy.com.foodchain.client;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import nancy.com.foodchain.server.KeyValue;
import nancy.com.foodchain.server.Life;
 
public class Client {
	FoodChainField field;
	public Socket socket;
	public int weatherCondition;
	public int wolfBornPeriod;
    public static void main(String[] args) throws IOException {
    	new Client().doIt();
    }
    
    void doIt() throws IOException {
    	
    	Thread updateClient = new Thread(new UpdateClient(this));   	  	
    	field = new FoodChainField(this);
    	updateClient.start();
    	socket = new Socket("localhost", 8082);
    	init();
    	
    }
    
    private void init() {
		// TODO Auto-generated method stub
    	PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			StringBuilder sb = new StringBuilder();
			sb.append(new KeyValue("weatherCondition", null).toJson());
			sb.append(","+new KeyValue("wolfBornPeriod", null).toJson());
			out.println("["+ sb.toString()  +"]");	
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
	        String respond = in.readLine();
	        processServerResponse(respond);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        		
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

	public void processServerResponse(String msg) {
		Gson gson = new Gson();
		List<KeyValue> fields = Arrays.asList(gson.fromJson(msg, KeyValue[].class));
		for (int i=0; i<fields.size(); i++) {
			KeyValue field = fields.get(i);
			setField(field);
			
		}
	}

	public void setField(KeyValue field) {
		if (field.value==null) {
			return;
		}
			
		if (field.key.equals("weatherCondition")) {
			weatherCondition = Integer.parseInt(field.value);
			System.err.println("weatherCondition="+weatherCondition);
		}
		if (field.key.equals("wolfBornPeriod")) {
			wolfBornPeriod = Integer.parseInt(field.value);
			System.err.println("wolfBornPeriod="+wolfBornPeriod);
		}
	}
    
   
 
}
