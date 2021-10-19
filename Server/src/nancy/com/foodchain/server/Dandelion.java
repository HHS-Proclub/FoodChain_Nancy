package nancy.com.foodchain.server;

import java.util.Random;

import nancy.com.foodchain.server.Life.State;

public class Dandelion extends Plant{

	public Dandelion(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		growSpeed = 2;
		Random rand = new Random();	
		this.bornCount = 850+rand.nextInt(bornPeroid);
		maxW = 40;
		maxH = 40;
		maxAge = 1000;
		matureSize = 35;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		//System.out.println("Cactus.run");
	}

	@Override
	public void born(){
		Random rand = new Random();
		Life life = new Dandelion(this.foodChain, this.x+20+rand.nextInt(100), this.y+20+rand.nextInt(100), maxW/2, maxH/2, icon);
		foodChain.lifeList.add(life);
		life.thread = new Thread(life);
		life.thread.start();
		state = State.NORMAL;
	}
}
