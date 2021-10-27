package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Life implements  Runnable{
	FoodChain foodChain;
	public enum State {
	    NORMAL,
	    SLEEP,
	    SCANNING,
	    APPROACHING,
	    EATING, 
	    DEAD, 
	    BORN, 
	    GROW
	}
	public String name;
	public int x;
	public int y;
	public int age = 0;
	
	public int width = 20;
	public int height = 20;
	public String type;
	public String icon;
	public int growCount;
	public int growPeriod = 10;
	public int bornPeriod = 300;
	public int bornCount = 300;
	public int matureSize = 18;
	public int maxW = 20;
	public int maxH = 20;
	public int size = 1;
	public String[] edibleList;
	
	public State state = State.NORMAL;
	public int deltaHealth = 1;
	public static int idCount;
	public Life approacher;
	public Thread thread;
	public int maxAge = 500;
	public int minSize = 0;
	public int edibleSize = 10;
	public double weathFactor = 1;
	public Life(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		this.type = getType(this);
		this.foodChain = foodchain;
		this.name = type+(++idCount);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.icon = icon;
		
		
	}
	public void run() {
		live();
		
		//System.out.println("Life.run:"+ age);
		
	}
	
	public void live() {		
		while (state!=State.DEAD) {			
			try{Thread.sleep(100);}catch(InterruptedException e){System.out.println(e);}
			handleAge();
			handleLive();			
			handleBorn();
		}
		foodChain.threadCount--;
	}
	
	public void handleAge() {
		
		if (++age>maxAge || size<1) {
			state = State.DEAD;
			return;
		};
		if (--growCount<1) {
			Random rand = new Random();	
			growCount = 10+rand.nextInt(growPeriod);
			size = Math.min(size+((int)(foodChain.weatherCondition*weathFactor)) , maxW);
		}
		
		width = height =size;
		
		
	}
	public void handleBorn() {
		if (--bornCount<1 && width>=matureSize && foodChain.threadCount<foodChain.maxThread) {
			born();
			Random rand = new Random();	
			bornCount = 50+rand.nextInt(bornPeriod);
		}
	}
	public void dead() {
		List <Life> lifeList = foodChain.getLifeList();

		for (int i=0; i<lifeList.size(); i++) {
	    	Life life = lifeList.get(i);
	    	if (life==null) {
	    		continue;
	    	}
	    	if (life.state==State.DEAD) {
	    		lifeList.set(i, null);
	    		break;
	    	}
	    }

		if (foodChain.test) {
			System.err.println(this.name+" dead.                        total:"+foodChain.total.get("Total")+" wolf:"+foodChain.total.get("Wolf")+" Rabbits:"+foodChain.total.get("Rabbit"));
		}
	}
	void handleLive() {
		// TODO Auto-generated method stub
		
	}
	public abstract void born();
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public String getType(Life life) {
		String name = life.getClass().getName();
		int index = name.lastIndexOf(".");
		return name.substring(index+1, name.length());
	}
	
	public int getDistance(Life life) {
		return (int) Math.sqrt(Math.pow(Math.abs(y-life.y), 2)+Math.pow(Math.abs(x-life.x), 2));
	}

	public String toJson( ) {
		//StringBuilder sb = new StringBuilder();
		return "{"+
				"\"name\":\""+name+"\","+
				"\"x\":"+x+","+
				"\"y\":"+y+","+
				"\"width\":"+width+","+
				"\"height\":"+height+","+
				"\"age\":"+age+","+
				"\"volume\":"+size+","+
				"\"width\":"+width+","+
				"\"height\":"+height+","+
				"\"icon\":\""+icon+"\","+
				"\"state\":\""+state.toString()+"\""+
				"}";
	}

}
