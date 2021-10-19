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
		this.bornPeroid = 80;
		Random rand = new Random();	
		this.bornCount = 200+rand.nextInt(bornPeroid);
		maxW = 20;
		maxH = 20;
		maxAge = 700;
		matureSize = 18;
	}

	public void run() {
		super.run();
		volume+=2;
		//System.out.println("Rabbit.run:" +volume+ "," +age);
		walk();
	}
	public void eat() {
		
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
			if (life==this) {
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
