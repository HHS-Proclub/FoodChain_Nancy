package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.List;

public class Life implements  Serializable, Runnable{
	FoodChain foodChain;
	public enum State {
	    NORMAL,
	    SLEEP,
	    SCANNING,
	    APPROACHING,
	    EATING, 
	    DEAD
	}
	public String name;
	public int x;
	public int y;
	public int age = 0;
	public int volume = 1;
	public int width = 20;
	public int height = 20;
	public String icon;
	public String[] edibleList;
	public int health = 100;
	public State state = State.NORMAL;
	public int deltaHealth = 1;
	public Thread thread;
	public Life(FoodChain foodchain, String name, int x, int y, String icon) {
		this.foodChain = foodchain;
		this.name = name;
		this.x = x;
		this.y = y;
		this.icon = icon;
	}
	public void run() {
		live();
		age++;
		System.out.println("Life.run:"+ age);
		
	}
	
	public void live() {		
		while (state!=State.DEAD) {			
			try{Thread.sleep(100);}catch(InterruptedException e){System.out.println(e);}
			doLive();
		}
	}
	
	public void dead() {
		List <Life> lifeList = foodChain.getLifeList();
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life == this) {
				lifeList.remove(i);
				break;
			}
		}
		
	}
	void doLive() {
		// TODO Auto-generated method stub
		
	}
	public void update() {
		// TODO Auto-generated method stub
		
	}
	public Life born(String name) {
		return new Life(this.foodChain, name, this.x+1, this.y+1, icon);
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
