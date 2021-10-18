package nancy.com.foodchain.server;

public class Rabbit extends Animal {

	public Rabbit(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		this.edibleList = new String[]{"Plant"};
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		super.run();
		volume+=2;
		System.out.println("Rabbit.run:" +volume+ "," +age);
		walk();
	}

}
