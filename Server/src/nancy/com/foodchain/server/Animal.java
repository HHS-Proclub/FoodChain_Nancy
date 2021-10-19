package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Animal extends Life {

	public Animal(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		// TODO Auto-generated constructor stub
	}

	//the circular scan area
	public int scanRange = 20;
	//# of turns (5) before stopping to scan
	public int scanBreak = 5;
	//helps you count turns to make sure you stop to scan
	public int scanCount = 0;
	
	Life target;
	public int approachSpeed = 8;
	
	
	public int[] direction = new int[]{1,1};
	public int directionPeriod = 5;
	public int catchRange = 5;
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
	}
	
	

	public void walk() {
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
	
	public void eat() {
		
	}
	
	public void scan() {
		
	}
	
	public boolean isInScanRange() {
		return true;
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
