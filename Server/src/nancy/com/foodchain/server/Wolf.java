package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nancy.com.foodchain.server.Life.State;

public class Wolf extends Animal{


	public Wolf(FoodChain foodchain, int x, int y, int width, int height, String icon, int _bornPeriod) {
		super(foodchain, x, y, width, height, icon);
		this.edibleList = new String[]{"Rabbit"};
		Random rand = new Random();	
		this.bornPeriod = _bornPeriod>0?(20+rand.nextInt(Math.max(_bornPeriod, 2))):(Life.BORN_PERIOD/2+rand.nextInt(Math.max(bornPeriod, 2)));
		this.bornCount = this.bornPeriod;
		maxW = 30;
		maxH = 30;
		maxAge = 6500 +rand.nextInt(3000);
		matureSize = 20;
		bitSize = 5;
		hungryPeriod = 150;
		size = ((int)(maxW/2));
		scanRange = 500;
		healthPeriod = 4 +(1-rand.nextInt(2));
		bornRate = 80;
	}

	public void run() {
		super.run();
		walk();
	}
	public void walk() {
		super.walk();
		if (state==State.NORMAL) {
			hungryCount = Math.max(0, hungryCount-1);
			if (--scanCount<1 && hungryCount<1) {
				scanCount = scanPeriod;
				state = State.SCANNING;
			}
		}
		
	}
	public boolean eat() {
		if (super.eat()==false) {
			return false;
		}
		//Eater gain more health
		health+= deltaHealth;
		target.size -= bitSize;

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
	
	public boolean isCaught() {
		if (target==null) {
			state = State.NORMAL;
			return false;
		}
		if (Math.abs(x-target.x)<catchRange&&Math.abs(y-target.y)<catchRange) {
			return true;
		}
		return false;
	}
	public void born(){	
		Life life = new Wolf(this.foodChain, this.x+1, this.y+1, maxW/2, maxH/2, icon, bornPeriod);
		foodChain.lifeList.add(life);
		life.thread = new Thread(life);
		life.thread.start();
		foodChain.threadCount++;
		state = State.NORMAL;
		if (foodChain.test) {
			System.err.println("born "+life.name+" from "+this.name);
		}
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

			if (!Arrays.asList(edibleList).contains(life.lifeType) ||!isInScanRange(life)) {
				//currently only approach first found
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
		}else {
			state = State.NORMAL;
			
		}
		
		/*

		int minDistance = 99999999;
		target = null;
		for (int i=0; i<foundList.size();i++) {
			Life life = foundList.get(i);
			if (life==this) {
				continue;
			}
			int distance = getDistance();
			if (distance<minDistance && life.approacher==null) {
				//minDistance = distance;
				target = life;
				target.approacher = this;
				break;
			} else if (life.approacher!=null) {
				if (life.approacher.state==State.NORMAL) {
					life.approacher = null;
				}
			}
		}
		
		if (target != null) {
			state = State.APPROACHING;
			System.err.println(this.name+" approach "+target.name);
		}
		*/
	}
}
