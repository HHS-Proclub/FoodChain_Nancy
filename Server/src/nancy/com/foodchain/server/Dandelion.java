package nancy.com.foodchain.server;

import java.util.Random;

import nancy.com.foodchain.server.Life.State;

public class Dandelion extends Plant{

	public Dandelion(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		growSpeed = 2;
		size = 10;
		this.bornPeriod = 250;
		Random rand = new Random();	
		this.bornCount = 20+rand.nextInt(bornPeriod);
		maxW = 40;
		maxH = 40;
		maxAge = 9999999;
		matureSize = 35;
		minSize = 5;
		size = ((int)(maxW/2));
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		//System.out.println("Cactus.run");
	}

	@Override
	public void born(){
		if (size<matureSize) {
			return;
		}
		Random rand = new Random();	
		Life life = new Dandelion(this.foodChain, this.x+(rand.nextInt(3)>1?-1:1)*(20+rand.nextInt(100)), this.y+(rand.nextInt(3)>1?-1:1)*(20+rand.nextInt(100)), maxW/5, maxH/5, icon);
		if (foodChain.nullList.size()>0) {
			try {
				foodChain.lifeList.set(Integer.parseInt(foodChain.nullList.get(0)), life);
			} catch (java.lang.IndexOutOfBoundsException e) {
				foodChain.lifeList.add(life);
			}
		} else {;
			foodChain.lifeList.add(life);
		}
		
		life.thread = new Thread(life);
		life.thread.start();
		foodChain.threadCount++;
		state = State.NORMAL;
	}
}
