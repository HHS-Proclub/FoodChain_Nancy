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
		this.bornCount = 20+rand.nextInt(Math.max(bornPeriod, 2));
		maxW = 40;
		maxH = 40;
		maxAge = 300 +rand.nextInt(50);
		matureSize = 30;
		minSize = 5;
		size = ((int)(maxW/2));
		growPeriodOrigin = 20;
		bornRate = foodchain.weatherCondition*100/10;
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
		int x, y;
		int maxW = foodChain.field.width-200;
		int maxH = foodChain.field.height-200;
		
		Random rand = new Random();	
		if (rand.nextInt(100)>50) {
			x = this.x+(1-rand.nextInt(2))*(7+rand.nextInt(3));
			y = this.y+(1-rand.nextInt(2))*(7+rand.nextInt(3));
		} else {
			x = (50+rand.nextInt(maxW));
			y = (50+rand.nextInt(maxH));
		}
		Life life = new Dandelion(this.foodChain, x, y, this.maxW/5, this.maxH/5, icon);
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
