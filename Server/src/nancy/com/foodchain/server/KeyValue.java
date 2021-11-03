package nancy.com.foodchain.server;

public class KeyValue {
	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String key;
	public String value;
	public Object toJson() {
		return "{"+
				"\"key\":\""+key+"\","+
				"\"value\":"+((value instanceof String)?("\""+value+"\""):value)+
				"}";
	}
}


