package nancy.com.foodchain.server;
import com.google.gson.Gson; 
import java.util.Arrays;
import java.util.List;

import nancy.com.foodchain.client.Life;

public class ControlMessage {
	FoodChain foodChain;
	List<KeyValue> fields;
	public ControlMessage(FoodChain foodChain, String msg) {
		super();
		this.msg = msg;
		this.foodChain = foodChain;
	}

	String msg;
	
	public void apply() {
		Gson gson = new Gson();
		fields = Arrays.asList(gson.fromJson(msg, KeyValue[].class));
		for (int i=0; i<fields.size(); i++) {
			KeyValue field = fields.get(i);
			foodChain.setField(field);
			
		}
	}
	
	
	
}
