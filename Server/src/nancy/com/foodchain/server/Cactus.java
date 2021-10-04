package nancy.com.foodchain.server;

public class Cactus extends Plant {

	public Cactus(FoodChain foodchain, String name, int x, int y) {
		super(foodchain, name, x, y);
		growSpeed = 2;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		System.out.println("Cactus.run");
	}
}
