package nancy.com.foodchain.server;
import com.google.gson.Gson; 
import java.util.Arrays;
import java.util.List;

import nancy.com.foodchain.client.ClientLife;

public class ClientMessage {
	FoodChain foodChain;
	public List<ClientLife> fields;
	public ClientMessage(FoodChain foodChain, String msg) {
		super();
		
		this.foodChain = foodChain;
		Gson gson = new Gson();
		try {
			fields = Arrays.asList(gson.fromJson(msg, ClientLife[].class));
		}catch (com.google.gson.JsonSyntaxException e) {
			System.err.println(e);
		}
	}

	String msg;
	
	public void apply() {
		
		for (int i=0; i<fields.size(); i++) {
			ClientLife field = fields.get(i);
			foodChain.setField(field);
			
		}
	}
	
	
	
}
