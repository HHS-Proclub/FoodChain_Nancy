package nancy.com.foodchain.server;

import java.util.Random;

public abstract class Plant extends Life {


	public Plant(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		// TODO Auto-generated constructor stub
		edibleSize = 5;
		growPeriod = 20;
	}

	public int growSpeed = 1;

	void handleLive() {
		switch (state) {		
		case GROW:
			grow();
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
	private void grow() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleGrow() {
		if (--growCount<1) {
			Random rand = new Random();	
			growPeriod = growPeriodOrigin*5/foodChain.weatherCondition;
			
			growCount = 3+rand.nextInt(growPeriod);
			size = Math.min(size+ 1, maxW);
		}
		
		width = height =size;
		
	}
}
