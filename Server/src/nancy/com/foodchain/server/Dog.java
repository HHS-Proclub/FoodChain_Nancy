package nancy.com.foodchain.server;

public class Dog extends Animal {
	
	
	public Dog(FoodChain foodchain, String name, int x, int y) {
		super(foodchain, name, x, y);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		volume+=2;
		System.out.println("Dog.run:" +volume+ "," +age);
		move();
	}
}
