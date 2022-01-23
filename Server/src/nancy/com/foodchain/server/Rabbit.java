package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nancy.com.foodchain.server.Life.State;

public class Rabbit extends Animal{

	
	
	public Rabbit(FoodChain foodchain, int x, int y, int width, int height, String icon, int _bornPeriod) {
		super(foodchain, x, y, width, height, icon);
		this.edibleList = new String[]{"Dandelion"};
		Random rand = new Random();	
		this.bornPeriod = _bornPeriod>0?(10+rand.nextInt(Math.max(_bornPeriod/2, 2))):(20+rand.nextInt(Math.max(bornPeriod, 2)));
		this.bornCount = bornPeriod;
		maxW = 20;
		maxH = 20;
		maxAge = 5500 +rand.nextInt(2000);
		matureSize = 15;
		bitSize = 1;
		hungryPeriod = 10;
		size = ((int)(maxW/2));
		scanRange = 1500;
		healthPeriod = 4 +(1-rand.nextInt(2));
		bornRate = 30;
		
	}

	public void run() {
		super.run();
		walk();
	}
	public boolean eat() {
		if (super.eat()==false) {
			return false;
		}
		//Eater gain more health
		health+= deltaHealth;
		//only eat one bit
		eatCount = 0;
		

		target.size = Math.max(target.size-this.biteSize, target.minSize);
		if (target.size<=target.minSize) {
			state = State.NORMAL;
			target.approacher = null;
		}
		
		//Eater return to normal state
		if (target.size<1) {
			state = State.NORMAL;
		}
		if (foodChain.test) {
			System.err.println(this.name + " is eating "+target.name);
		}
		
		
		//Grow only when eating
		size = Math.min(size+ 1, maxW);
		width = height = size;
		
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
			hungryCount = Math.max(0, hungryCount-1);
			if (--scanCount<1 && hungryCount<1) {
				//reset turns before scan
				scanCount = scanPeriod;
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
		int minDistance = 99999999;
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life==this) {
				continue;
			}
			if (life==null) {
				continue;
			}
			if (life==this||life.size<life.edibleSize) {
				continue;
			}
			if (!Arrays.asList(edibleList).contains(life.lifeType) ||!isInScanRange(life)) {
				
				continue;
				
			} 
			int distance = getDistance();
			//currently only approach first found
			if (distance>=minDistance || (life.approacher!=null)) {
				continue;
			}
			foundList.add(life);
			
			
		}
		if (foundList.size()>0) {
			Random rand = new Random();	
			int targetIndex = rand.nextInt(foundList.size());
			target = foundList.get(targetIndex);
			state = State.APPROACHING;
			target.approacher = this;
			if (foodChain.test) {
				System.err.println(this.name+" approach "+target.name);
			}
		} else {
			state = State.NORMAL;
			scanCount = scanPeriod;
		}
		
		/*for (int i=0; i<foundList.size();i++) {
			Life life = foundList.get(i);
			
			int distance = getDistance();
			//currently only approach first found
			if (distance<minDistance && (life.approacher==null)) {
				minDistance = distance;
				target = life;
				target.approacher = this;
			}
		}
		*/
		//if (target != null) {
			
			
		//}
		
	}

	@Override
	public void born(){
		if (health<bornHealthMin) {
			return;
		}
		Life life = new Rabbit(this.foodChain, this.x+1, this.y+1, maxW/2, maxH/2, icon, bornPeriod);
		if (foodChain.nullList.size()>0) {
			try {
				foodChain.setLife(Integer.parseInt(foodChain.nullList.get(0)), life);
			} catch (java.lang.IndexOutOfBoundsException e) {
				foodChain.addLife(life);
			}
		} else {;
			foodChain.addLife(life);
		}
		
		life.thread = new Thread(life);
		life.thread.start();
		foodChain.threadCount++;
		state = State.NORMAL;
	}

}
