package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal extends Life {
	public enum State {
	    NORMAL,
	    SLEEP,
	    SCANNING,
	    APPROACHING,
	    EATING
	}
	//the circular scan area
	public int scanRange = 20;
	//# of turns (5) before stopping to scan
	public int scanBreak = 5;
	//helps you count turns to make sure you stop to scan
	public int scanCount = 0;
	public State state;
	public int approachSpeed = 1;
	Life target;
	public Animal(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		// TODO Auto-generated constructor stub
	}

	public int[] direction = new int[]{1,1};
	public int directionPeriod = 5;
	public void run() {
		super.run();
		System.out.println("Animal.run");
	}
	
	public void live() {
		switch (state) {
			case NORMAL:
				walk();
				
				if (++scanCount>=scanBreak) {
					//reset turns before scan
					scanCount = 0;
					scan();
				}
				break;
			case SLEEP:
				sleep();
				break;
			case SCANNING:
				scan();
				break;
			case APPROACHING:
				approach();
				break;
			case EATING:
				eat();
				break;
			default:
				break;
		
		}
		
	}
	
	public void walk() {
		Random rand = new Random();	
		while (true) {
		if (--directionPeriod==0) {
			direction[0]=(1-rand.nextInt(2));
			direction[1]=(1-rand.nextInt(2));
			directionPeriod = 25+rand.nextInt(20);
		}
		x+=direction[0];
		y+=direction[1];
		
		if (x<0||x>foodchain.field.width) {
			direction[0] *= -1;
			x+=direction[0];
			directionPeriod = 5+rand.nextInt(20);
		}
		if (y<0||y>foodchain.field.height) {
			direction[1] *= -1;
			y+=direction[1];
			directionPeriod = 5+rand.nextInt(20);
		}
		
		System.err.println(name+":"+x+", "+y);
		}
	}
	
	public void eat() {
		health+= deltaHealth;
	}
	
	public void scan() {
		List <Life> lifeList = foodchain.getLifeList();
		List <Life>foundList = new ArrayList();
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			String name = life.getClass().getName();
			if (!Arrays.asList(edibleList).contains(name) ||!isInScanRange()) {
				//currently only approach first found
				continue;
				
			} 
			foundList.add(life);
			
			
		}
		
		int minDistance = 99999999;
		target = null;
		for (int i=0; i<foundList.size();i++) {
			Life life = lifeList.get(i);
			int distance = getDistance();
			if (distance<minDistance) {
				minDistance = distance;
				target = life;
			}
		}
		
		if (target != null) {
			state = State.APPROACHING;
		}
		
	}
	
	private boolean isInScanRange() {
		return true;
	}

	private int getDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void approach() {
		
	}
	
	public void sleep() {
		
	}
}
