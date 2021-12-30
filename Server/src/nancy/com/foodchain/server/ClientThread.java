package nancy.com.foodchain.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import nancy.com.foodchain.client.ClientLife;

public class ClientThread implements Runnable {
	public ClientThread(Server server, Socket socket) {
		super();
		this.socket = socket;
		this.server = server;
		this.id = "Client"+(++server.clientCount);
	}
	Server server;
	Socket socket;
	String id;
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
	        while (true) {
	        	
	        	String msg = in.readLine();
	        	System.err.println("Server receive msg from Client in ClientThread:"+msg);
	        	ClientMessage clientMessage = new ClientMessage(server.foodChain, msg);
	        	if (clientMessage.fields.get(0).type.equals("c")) {
	        		clientMessage.apply();
		        	StringBuilder sb = new StringBuilder();
		        	for (int i=0; i<clientMessage.fields.size(); i++) {
		    			ClientLife field = clientMessage.fields.get(i);
		    			if (field.value==null || field.value.equals("-1") || field.value.equals("") ) {
		    				server.foodChain.setFieldValue(field);
		    			}
		    			if (field.key.equals("name")) {
		    				field.value = id;
		    			}
		    			if (i>0) {
		    				sb.append(","+field.toJson());
		    			} else {
		    				sb.append(field.toJson());
		    			}
		    			
		    			//keyValue toJson()
		    			
		    		}
		        	
		        	String resString = "["+sb.toString()+"]";
		        	//out.println(resString);
		        	server.broadcast(resString, "c");
	        	} else if (clientMessage.fields.get(0).type.equals("d")) {
	        		//Only send a response to the client instead of broadcasting to all clients
	        		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        		String serverResponseToDashBoard = createServerResponseToDashboard(clientMessage);
	        		out.println(serverResponseToDashBoard);
	        	}
	        }
        	
        	} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	

	}
	private String createServerResponseToDashboard(ClientMessage clientMessage) {
		// TODO Auto-generated method stub
		server.foodChain.updateFields();
		StringBuffer st = new StringBuffer();
		for (int i=0; i<clientMessage.fields.size(); i++) {
			ClientLife field = clientMessage.fields.get(i);
			if (i>0) {
				st.append(",");
			}
			st.append(new ClientLife("d", field.key,""+server.foodChain.getFieldValue(field.key)).toJson());
		}
		return "["+st.toString()+"]";
		
	}
	
	

}
