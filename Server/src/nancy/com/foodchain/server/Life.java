package nancy.com.foodchain.server;

import java.io.Serializable;

public class Life implements  Serializable, Runnable{
	FoodChain foodchain;
	public String name;
	public int x;
	public int y;
	public int age = 0;
	public int volume = 1;
	public int width = 20;
	public int height = 20;
	public String icon;
	public Life(FoodChain foodchain, String name, int x, int y, String icon) {
		this.foodchain = foodchain;
		this.name = name;
		this.x = x;
		this.y = y;
		this.icon = icon;
	}
	public void run() {
		age++;
		System.out.println("Life.run:"+ age);
		
	}
	public void update() {
		// TODO Auto-generated method stub
		
	}
	public Life born(String name) {
		return new Life(this.foodchain, name, this.x+1, this.y+1, icon);
	}
	
	public String toJson( ) {
		//StringBuilder sb = new StringBuilder();
		return "{"+
				"\"name\":\""+name+"\","+
				"\"x\":"+x+","+
				"\"y\":"+y+","+
				"\"age\":"+age+","+
				"\"volume\":"+volume+","+
				"\"width\":"+width+","+
				"\"height\":"+height+","+
				"\"icon\":\""+icon+"\""+				
				"}";
	}

}
