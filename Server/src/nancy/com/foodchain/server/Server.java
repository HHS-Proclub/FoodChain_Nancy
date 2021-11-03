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

import nancy.com.foodchain.server.Life.State;
 
public class Server {
	FoodChain foodChain;

	
    public static void main(String[] args) throws IOException {
    	new Server().doIt();
    }
    
    void doIt() {
        System.out.println("Server is starting...");
        foodChain = new FoodChain();
        foodChain.doIt();
        
        new Thread(new UpdateServer(this)).start();
        new Thread(new ControlServer(this)).start();
    }
    
    public class UpdateServer implements Runnable{
    	Server server;

    	public UpdateServer(Server server) {
			super();
			this.server = server;
		}


		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("Update Server is ready at port 8081");
                Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String controlMsg = in.readLine();
                
                while (true) {
                	StringBuilder sb = new StringBuilder();
                	List <Life>list = server.foodChain.getLifeList();
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
                		d = Integer.parseInt(foodChain.total.get(life.type));
                		foodChain.total.put(life.type, ""+(d+1));
                		d = Integer.parseInt(foodChain.total.get("Total"));
                		foodChain.total.put("Total", ""+(d+1));
                		
                	}
                	String lifeListString = "["+sb.toString()+"]";
                	out.println(new StringBuilder(lifeListString));
                }
                
            } catch (Exception e) {
                System.out.println("Initializing error. Try changing port number!" + e);
            }
		}
    }
    
    public class ControlServer implements Runnable{
    	Server server;

    	public ControlServer(Server server) {
			super();
			this.server = server;
		}


		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(8082);
                System.out.println("Control Server is ready at port 8082");
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String controlMsg = in.readLine();
                while (true) {
                	ControlMessage msgs = new ControlMessage(foodChain, controlMsg);
                	msgs.apply();
                	StringBuilder sb = new StringBuilder();
                	for (int i=0; i<msgs.fields.size(); i++) {
            			KeyValue field = msgs.fields.get(i);
            			if (field.value==null) {
            				foodChain.setFieldValue(field);
            			}
            			if (i>0) {
            				sb.append(","+field.toJson());
            			} else {
            				sb.append(field.toJson());
            			}
            			
            			//keyValue toJson()
            			
            		}
                	
                	String resString = "["+sb.toString()+"]";
                	out.println(resString);
                	//reply^^
                	controlMsg = in.readLine();
                	//block^^
                }
                
            } catch (Exception e) {
                System.out.println("Initializing error. Try changing port number!" + e);
            }			
		}
    }
}