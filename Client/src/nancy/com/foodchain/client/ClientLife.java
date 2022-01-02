package nancy.com.foodchain.client;


public class ClientLife {

	public ClientLife(String type, String key, String value) {
		super();
		this.type = type;
		this.name = "";
		this.x = -1;
		this.y = -1;
		this.age = -1;
		this.health = -1;
		this.volume = -1;
		this.width = -1;
		this.height = -1;
		this.icon = "";
		this.state = "";
		this.key = key;
		this.value = value;
	}
	
	public String type;
	public String name;
	public int x;
	public int y;
	public int age = 0;
	public int health = 100;
	public int volume = 1;
	public int width = 20;
	public int height = 20;
	public String icon;
	public String state;
	public String key;
	public String value;
	
	public String toJson( ) {
		//StringBuilder sb = new StringBuilder();
		return "{"+
				"\"type\":\""+type+"\","+
				"\"name\":\""+name+"\","+
				"\"x\":"+x+","+
				"\"y\":"+y+","+
				"\"width\":"+width+","+
				"\"height\":"+height+","+
				"\"age\":"+age+","+
				"\"health\":"+health+","+
				"\"volume\":"+volume+","+
				"\"width\":"+width+","+
				"\"height\":"+height+","+
				"\"icon\":\""+icon+"\","+
				"\"state\":\""+state.toString()+"\","+
				"\"key\":\""+key+"\","+
				"\"value\":"+(value==null?null:("\""+value+"\""))+
				"}";
	}
}
