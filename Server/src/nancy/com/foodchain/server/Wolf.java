package nancy.com.foodchain.server;

public class Wolf extends Animal {
	public Wolf(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		volume+=2;
		System.out.println("Dog.run:" +volume+ "," +age);
		move();
	}
}
