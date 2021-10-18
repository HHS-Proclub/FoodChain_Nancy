package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nancy.com.foodchain.server.Life.State;

public class Wolf extends Animal {


	public Wolf(FoodChain foodchain, String name, int x, int y, int width, int height, String icon) {
		super(foodchain, name, x, y, width, height, icon);
		this.edibleList = new String[]{"Rabbit"};
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		volume+=2;
		System.out.println("Wolf.run:" +volume+ "," +age);
		walk();
	}
	public void walk() {
		super.walk();
		if (++scanCount>=scanBreak) {
			//reset turns before scan
			scanCount = 0;
			scan();
		}
	}
	public void eat() {
		//Eater gain more health
		health+= deltaHealth;
		
		//Set eatee to dead
		target.state = State.DEAD;
		
		//Eater return to nomal state
		state = State.NORMAL;
	}
	
	public boolean isInScanRange() {
		return true;
	}
	
	public void approach() {
		System.err.println(target.name);
		setApprochingDirection();
		walk();
		if (isCaught()) {
			this.state = State.EATING;
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
	
}
