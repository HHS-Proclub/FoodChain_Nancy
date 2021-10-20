package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nancy.com.foodchain.server.Life.State;

public class Rabbit extends Animal{

	
	
	public Rabbit(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		this.edibleList = new String[]{"Dandelion"};
		this.bornPeriod = 80;
		Random rand = new Random();	
		this.bornCount = 200+rand.nextInt(bornPeriod);
		maxW = 20;
		maxH = 20;
		maxAge = 1000;
		matureSize = 18;
	}

	public void run() {
		super.run();
		walk();
	}
	public void eat() {
		//Eater gain more health
		health+= deltaHealth;
		
		//Set eatee to dead
		//target.state = State.DEAD;
		target.size = Math.max(target.size-this.biteSize, target.minSize);
		if (target.size<=target.minSize) {
			state = State.NORMAL;
		}
		
		//Eater return to nomal state
		state = State.NORMAL;
		System.err.println(this.name + " is eating "+target.name);
	}
	
	public boolean isInScanRange() {
		return true;
	}
	
	public void approach() {
		//System.err.println(target.name);
		setApprochingDirection();
		walk();
		if (isCaught()) {
			this.state = State.EATING;
		}
	}
	
	public void walk() {
		super.walk();
		if (state==State.NORMAL) {
			if (++scanCount>=scanBreak) {
				//reset turns before scan
				scanCount = 0;
				state = State.SCANNING;
			}
		}
		
	}
	
	public boolean isCaught() {
		if (Math.abs(x-target.x)<catchRange&&Math.abs(y-target.y)<catchRange) {
			return true;
		}
		return false;
	}
	
	public void setApprochingDirection() {
		if (x>target.x) {
			direction[0] = -approachSpeed;
		} else {
			direction[0] = approachSpeed;
		}
		if (y>target.y) {
			direction[1] = -approachSpeed;
		} else {
			direction[1] = approachSpeed;
		}
		
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
			if (life==null) {
				continue;
			}
			
			if (!Arrays.asList(edibleList).contains(life.type) ||!isInScanRange()) {
				
				continue;
				
			} 
			foundList.add(life);
			
			
		}
		
		int minDistance = 99999999;
		target = null;
		for (int i=0; i<foundList.size();i++) {
			Life life = foundList.get(i);
			if (life==this||life.size<life.edibleSize) {
				continue;
			}
			int distance = getDistance();
			//currently only approach first found
			if (distance<minDistance && life.approacher==null) {
				minDistance = distance;
				target = life;
				target.approacher = this;
			}
		}
		
		if (target != null) {
			state = State.APPROACHING;
			System.err.println(this.name+" approach "+target.name);
			
		}
		
	}

	@Override
	public void born(){
		Life life = new Rabbit(this.foodChain, this.x+1, this.y+1, maxW/2, maxH/2, icon);
		if (foodChain.nullList.size()>0) {
			foodChain.lifeList.set(Integer.parseInt(foodChain.nullList.get(0)), life);
		} else {;
			foodChain.lifeList.add(life);
		}
		
		life.thread = new Thread(life);
		life.thread.start();
		state = State.NORMAL;
	}

}
