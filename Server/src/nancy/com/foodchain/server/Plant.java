package nancy.com.foodchain.server;

public abstract class Plant extends Life {


	public Plant(FoodChain foodchain, int x, int y, int width, int height, String icon) {
		super(foodchain, x, y, width, height, icon);
		// TODO Auto-generated constructor stub
	}

	public int growSpeed = 1;
	public void run() {
		super.run();
		System.out.println("Plant.run");
		while (true) {
			//volume+=growSpeed;
		}
	}

	
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
}
