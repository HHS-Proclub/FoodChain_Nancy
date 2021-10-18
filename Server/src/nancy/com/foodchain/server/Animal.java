package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal extends Life {

	//the circular scan area
	public int scanRange = 20;
	//# of turns (5) before stopping to scan
	public int scanBreak = 5;
	//helps you count turns to make sure you stop to scan
	public int scanCount = 0;
	
	Life target;
	private int approachSpeed = 2;
	public Animal(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		// TODO Auto-generated constructor stub
	}

	public int[] direction = new int[]{1,1};
	public int directionPeriod = 5;
	private int catchRange = 5;
	public void run() {
		super.run();
		System.out.println("Animal.run");
	}
	
	void doLive() {
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
			case DEAD:
				dead();
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
		
		System.err.println(name+":"+x+", "+y);
		
	}
	
	public void eat() {
		health+= deltaHealth;
		target.state = State.DEAD;
	}
	
	public void scan() {
		if (edibleList==null) {
			return;
		}
		List <Life> lifeList = foodChain.getLifeList();
		List <Life>foundList = new ArrayList();
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life==this) {
				continue;
			}
			String name = life.getClass().getName();
			int index = name.lastIndexOf(".");
			if (!Arrays.asList(edibleList).contains(name.substring(index+1, name.length())) ||!isInScanRange()) {
				//currently only approach first found
				continue;
				
			} 
			foundList.add(life);
			
			
		}
		
		int minDistance = 99999999;
		target = null;
		for (int i=0; i<foundList.size();i++) {
			Life life = foundList.get(i);
			if (life==this) {
				continue;
			}
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
		System.err.println(target.name);
		setApprochingDirection();
		walk();
		if (isCaught()) {
			this.state = State.EATING;
		}
	}
	
	private boolean isCaught() {
		if (Math.abs(x-target.x)<catchRange&&Math.abs(y-target.y)<catchRange) {
			return true;
		}
		return false;
	}



	private void setApprochingDirection() {
		// TODO Auto-generated method stub
		float rate =  (target.x - x)==0?0:((float)(target.y-y )/(float)(x - target.x));
		direction[0] = approachSpeed;
		direction[1] = Math.min((int)(approachSpeed*rate), approachSpeed);
	}

	public void sleep() {
		
	}
}
