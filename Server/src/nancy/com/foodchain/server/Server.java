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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nancy.com.foodchain.server.Life.State;
 
public class Server {
	FoodChain foodChain;
	int clientCount = 0;
	UpdateWorker updateServer;
	List <ClientThread> clients = new ArrayList<ClientThread>();
	boolean isBroadcasting = false;
	
    public static void main(String[] args) throws IOException {
    	new Server().doIt(args);
    }
    
    void doIt(String[] args) {
        System.out.println("Server is starting...");
        foodChain = new FoodChain(args);
        foodChain.doIt();
        startStopwatch();
        
        try {
        	ServerSocket serverSocket = new ServerSocket(8081);
			System.out.println("Server is ready at port 8081");
			
			while (true) {
				Socket socket = serverSocket.accept();
				if (updateServer==null) {
					updateServer = new UpdateWorker(this);
					new Thread(updateServer).start();
				}
				ClientThread client = new ClientThread(this, socket);
				clients.add(client);
				new Thread(client).start();
			}
			
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    private void startStopwatch() {
		// TODO Auto-generated method stub
		
		
	}

	void broadcast(String resString, String type) {
    	while (isBroadcasting) {			
			try{Thread.sleep(100);}catch(InterruptedException e){System.out.println(e);}
		}
    	isBroadcasting = true;
		try {
			for (int i=0; i<clients.size(); i++) {
			    ClientThread client = clients.get(i);
			    
			    PrintWriter out = new PrintWriter(client.socket.getOutputStream(), true);
			    out.println(resString);
			    //if (type=="c") {
			    //	System.err.println("Broadcasting to "+client.id+": "+resString);
			    //}
			    
			}
		
		} catch (Exception e) {
            System.out.println("Initializing error. Try changing port number!" + e);
        }
		isBroadcasting = false;
	}
    public class UpdateWorker implements Runnable{
    	Server server;

    	public UpdateWorker(Server server) {
			super();
			this.server = server;
		}


		@Override
		public void run() {
			try {
				
				
				while (true) {					                
	                
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
	            		if (life==null || life.state==State.DEAD) {
	            			foodChain.nullList.add(""+i);
	            			continue;
	            		}
	            		if (i==0) {
	            			sb.append(life.toJson());
	            		} else {
	            			sb.append(","+life.toJson());
	            		}
	            		//update number of a certain life type 
	            		d = Integer.parseInt(foodChain.total.get(life.lifeType));
	            		foodChain.total.put(life.lifeType, ""+(d+1));
	            		
	            		//update number of a total life 
	            		d = Integer.parseInt(foodChain.total.get("Total"));
	            		foodChain.total.put("Total", ""+(d+1));
	            		
	            	}
	            	if (sb.isEmpty()) {
	            		sb.append(new Wolf(foodChain, -1, -1, -1, -1, null, -1).toJson());
	            	}
	            	String lifeListString = "["+sb.toString()+"]";
	            	server.broadcast(lifeListString, "u");
	            	//System.err.println("Server broadcast:"+lifeListString);
				}
                
            } catch (Exception e) {
                System.out.println("Initializing error. Try changing port number!" + e);
            }
			System.err.println("Server exit:");
		}
    }

}