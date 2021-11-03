package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Animal extends Life {

	public Animal(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
	}

	//the circular scan area
	public int scanRange = 200;
	//# of turns (5) before stopping to scan
	public int scanPeriod = 20;
	//helps you count turns to make sure you stop to scan
	public int scanCount = scanPeriod;
	public int biteSize = 1;
	Life target;
	public int approachSpeed = 8;
	public int health = 100;
	public int healthPeriod = 5;
	public int bornHealthMin = 50;
	public int healthCount = healthPeriod;
	public int []healthLevel = new int[] {80, 60, 40, 20, 5}; 
	public int[] direction = new int[]{1,1};
	public int directionPeriod = 5;
	public int catchRange = 20;
	public int eatPeriod = 10;
	public int bitSize = 1;
	public int eatCount = eatPeriod;
	public int hungryPeriod = 50;
	public int hungryCount = hungryPeriod;
	public void run() {
		super.run();
		//System.out.println("Animal.run");
	}
	
	void handleLive() {
		
		switch (state) {
			case NORMAL:
				walk();
				
				
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
			case DEAD:
				dead();
				break;
			case BORN:
				born();
				break;
			default:
				break;
	
		}
		handleHealth();
	}
	

	private void handleHealth() {
		if (health<1) {
			state = State.DEAD;
			return;
		}
		
		if (--healthCount<1) {
			healthCount = healthPeriod;
			health--;
		}
		
		boolean found = false;
		for (int i=4; i>-1;i--) {
			if (health<healthLevel[4]) {
				icon = (type+4+".png").toLowerCase();
				found = true;
			}
		}
		if (!found) {
			icon = (type+".png").toLowerCase();
		}
		
	}

	public void walk() {
		if (approacher!=null && approacher.state==State.EATING) {
			return;
		}
		Random rand = new Random();	
		
		
		if (--directionPeriod==0 && state != State.APPROACHING) {
			direction[0]=(1-rand.nextInt(3));
			direction[1]=(1-rand.nextInt(3));
			if (direction[0]==0 && direction[1]==0) {
				if (rand.nextInt(3)>0) {
					direction[0] = (1-rand.nextInt(3));
				} else {
					direction[1] = (1-rand.nextInt(3));
				}
				
			}
			directionPeriod = 5+rand.nextInt(20);
		}
		x+=direction[0];
		y+=direction[1];
		
		if (x<0||x>foodChain.field.width) {
			direction[0] *= -1;
			x+=direction[0];
			directionPeriod = 5+rand.nextInt(20);
		}
		if (y<0||y>foodChain.field.height) {
			direction[1] *= -1;
			y+=direction[1];
			directionPeriod = 5+rand.nextInt(20);
		}
		
		//System.err.println(name+":"+x+", "+y);
		
	}
	
	public boolean eat() {
		if (--eatCount<1) {
			eatCount = eatPeriod;
			hungryCount = hungryPeriod;
			return true;
		}
		return false;
	}
	
	public void scan() {
		
	}
	
	public boolean isInScanRange(Life life) {
		if (getDistance(life) <= scanRange) {
			return true;
		}
		return false;
	}

	public int getDistance() {
		// TODO Auto-generated method stub
		return 99999990;
	}

	public void approach() {

	}
	
	public boolean isCaught() {
		return false;
	}



	public void setApprochingDirection() {

	}

	public void sleep() {
		
	}
}
