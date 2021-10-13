package nancy.com.foodchain.server;

public class Wolf extends Animal {
	public Wolf(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		this.edibleList = new String[]{"Rabbit"};
		// TODO Auto-generated constructor stub
	}

	public void run() {
		super.run();
		volume+=2;
		System.out.println("Wolf.run:" +volume+ "," +age);
		walk();
	}
}
