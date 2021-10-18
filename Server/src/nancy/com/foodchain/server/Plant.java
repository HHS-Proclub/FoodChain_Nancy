package nancy.com.foodchain.server;

public class Plant extends Life {

	public Plant(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
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
	public Life born(String name) {
		return super.born(name);
	}
	
	void doLive() {
		
	}
}
