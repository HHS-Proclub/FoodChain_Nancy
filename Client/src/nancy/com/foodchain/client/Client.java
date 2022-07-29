package nancy.com.foodchain.client;

import java.io.BufferedReader;

import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JSlider;



 
public class Client {
	FoodChainField field;
	public Socket socket;
	public int weatherCondition = 1;
	public int wolfBornPeriod = 1;
	public int rabbitBornPeriod = 1;
	public int wolfBornRate = 1;
	public int rabbitBornRate = 1;
	public int dandelionBornRate = 1;
	public String name;
	public int totalPopulation;
	public int countWolf;
	public int countRabbit;
	public int countDandelion;
	public long elapsedTime;
    public Client(String name) {
		// TODO Auto-generated constructor stub
    	this.name = name;
	}

	public static void main(String[] args) throws IOException {
    	new Client(args[0]).doIt();
    }
    
    void doIt() throws IOException {
    	
    	Thread worker = new Thread(new Worker(this));   	  	
    	field = new FoodChainField(this);
    	worker.start();
    	
    	
    }
    
    private void init() {
		// TODO Auto-generated method stub
		try {
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
			StringBuilder sb = new StringBuilder();
			sb.append(new ClientLife("c", "weatherCondition", null).toJson());
			sb.append(","+new ClientLife("c", "wolfBornPeriod", null).toJson());
			sb.append(","+new ClientLife("c", "rabbitBornPeriod", null).toJson());
			sb.append(","+new ClientLife("c", "wolfBornRate", null).toJson());
			sb.append(","+new ClientLife("c", "rabbitBornRate", null).toJson());
			sb.append(","+new ClientLife("c", "dandelionBornRate", null).toJson());
			sb.append(","+new ClientLife("c", "name", null).toJson());
			out.println("["+ sb.toString()  +"]");		
	        //String respond = controlClient.in.readLine();
	        //processServerResponse(respond);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        		
	}

	class Worker implements Runnable{
    	public Worker(Client client) {
			super();
			this.client = client;
		}

    	Client client;

		@Override
		public void run() {		
			System.err.println("Enter run in UpdateClient");
	        try {
	            //localhost because server running on my machine otherwise IP address of Server machine
	        	client.socket = new Socket("localhost", 8081);
	        	client.init();
	            PrintWriter out = new PrintWriter(client.socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
		        Gson gson = new Gson();
		        
		        //loop to listen to server broadcase: u for field update and c for client state update (control msg)
		        while (true) {
		    		String jsonString = in.readLine();
		    		//System.err.println("Client receive msg from server updateThread:"+jsonString);
		    		try {
			            List<ClientLife> lifeList = Arrays.asList(gson.fromJson(jsonString, ClientLife[].class));
			            if (lifeList.size()<1) {
			            	continue;
			            }
			            
			            //Loop to find the type of response
			            int i=0;
			            int foundIndex = -1;
			            while (i<lifeList.size()) {
			            	if (lifeList.get(i)==null) {
			            		i++;
			            	} else {
			            		foundIndex = i;
			            		break;
			            	}
			            	
			            }
			            if (foundIndex<0) {
			            	continue;
			            }
			            String type = lifeList.get(foundIndex).type;
			            ///////////////////////
			            
			            if (type.equals("u")) {
			            	field.update(lifeList);
			            	//System.err.println("Client receive msg from server updateThread:"+jsonString);
			            } else if (type.equals("c")) {
			            	processServerResponseForControlPanel(lifeList);
			            	//System.err.println("Client receive msg from server ClientThread:"+jsonString);
				    		
			            } else if (type.equals("d")) {
			            	//System.err.println("Client receive msg from server ClientThread:"+jsonString);
				    		
			            	processServerResponseForDashboard(lifeList);
			            } 
			        }catch (com.google.gson.JsonSyntaxException e) {
						System.err.println(e);
					}
		        }
	        } catch (Exception e) {  
	            System.out.println("Initializing error. Make sure that server is alive!\n" + e);
	        }
			
		}

		
    	
    }
	private void processServerResponseForDashboard(List<ClientLife> fields) {
		for (int i=0; i<fields.size(); i++) {
			ClientLife field = fields.get(i);
			setFieldForDashboard(field);
			
		}
		setFieldForDashboard(new ClientLife("d", "totalPopulation", null));
		
	}
	private void setFieldForDashboard(ClientLife clientField) {
		//System.err.println("clientField.key==="+clientField.key.equals("totalPopulation"));
		if (clientField.value==null && !(clientField.key.equals("totalPopulation"))) {
			return;
		}
			
		if (clientField.key.equals("countWolf")) {
			countWolf = Integer.parseInt(clientField.value);
			//System.err.println("countWolf="+countWolf);
		}
		
		if (clientField.key.equals("countRabbit")) {
			countRabbit = Integer.parseInt(clientField.value);
			//System.err.println("countRabbit="+countRabbit);
		}
		
		if (clientField.key.equals("countDandelion")) {
			countDandelion = Integer.parseInt(clientField.value);
			//System.err.println("countDandelion="+countDandelion);
		}
		
		if (clientField.key.equals("elapsedTime")) {
			//System.err.println("elapsedTime="+elapsedTime);
			elapsedTime = Long.parseLong(clientField.value);
		}
		
		if (clientField.key.equals("totalPopulation")) {
			totalPopulation = countWolf+countRabbit+countDandelion;
			//System.err.println("totalPopulation="+totalPopulation);
			clientField.value =  ""+totalPopulation;
		}
		
		if (field.dashBoardDlg!=null && field.dashBoardDlg.fieldMap!=null) {
			JLabel label = field.dashBoardDlg.fieldMap.get(clientField.key);
			//System.err.println("clientField.KEY: "+ clientField.key); 
			//System.err.println("clientField.value: "+ clientField.value);
			if (clientField.key.equals("elapsedTime")) {
				label.setText(getDuration());
			} else {
				label.setText(""+clientField.value);
			}
			
		}
		
	}

	public void processServerResponseForControlPanel(List<ClientLife> fields) {
		for (int i=0; i<fields.size(); i++) {
			ClientLife field = fields.get(i);
			setFieldForControlPanel(field);
			
		}
	}

	public void setFieldForControlPanel(ClientLife clientField) {
		if (clientField.value==null) {
			return;
		}
			
		if (clientField.key.equals("weatherCondition")) {
			weatherCondition = Integer.parseInt(clientField.value);
			//System.err.println("weatherCondition="+weatherCondition);
		} else if (clientField.key.equals("wolfBornPeriod")) {
			wolfBornPeriod = Integer.parseInt(clientField.value);
			//System.err.println("wolfBornPeriod="+wolfBornPeriod);
		} else if (clientField.key.equals("rabbitBornPeriod")) {
			rabbitBornPeriod = Integer.parseInt(clientField.value);
			//System.err.println("rabbitBornPeriod="+rabbitBornPeriod);
			
		} else if (clientField.key.equals("wolfBornRate")) {
			wolfBornRate = Integer.parseInt(clientField.value);
			//System.err.println("name="+name);
			
		} else if (clientField.key.equals("rabbitBornRate")) {
			rabbitBornRate = Integer.parseInt(clientField.value);
			//System.err.println("name="+name);
			
		} else if (clientField.key.equals("dandelionBornRate")) {
			dandelionBornRate = Integer.parseInt(clientField.value);
			//System.err.println("name="+name);
			
		} else if (clientField.key.equals("name")) {
			name = clientField.value;
			//System.err.println("name="+name);
		}
		if (field.controlDlg!=null && field.controlDlg.sliderMap!=null) {
			JSlider slider = field.controlDlg.sliderMap.get(clientField.key);
			field.controlDlg.isSetValue = true;
			slider.setValueIsAdjusting(true);
			slider.setValue(Integer.parseInt(clientField.value));
		}
	}
    
	String getDuration() {
		  Duration duration = Duration.ofMillis(this.elapsedTime);
	      long HH = duration.toHours();
	      long MM = duration.toMinutesPart();
	      long SS = duration.toSecondsPart();
	      String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
	      //System.err.println("#############"+timeInHHMMSS);
	      return timeInHHMMSS;
	   }
 
}
