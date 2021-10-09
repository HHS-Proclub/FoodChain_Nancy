package nancy.com.foodchain.server;

public class Dandelion extends Plant {

	public Dandelion(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		growSpeed = 2;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		System.out.println("Cactus.run");
	}
}
