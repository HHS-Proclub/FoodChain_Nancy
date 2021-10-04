package nancy.com.foodchain.server;

public class Life implements Runnable{
	FoodChain foodchain;
	public String name;
	public int x;
	public int y;
	public int age = 0;
	public int volume = 1;
	public Life(FoodChain foodchain, String name, int x, int y) {
		this.foodchain = foodchain;
		this.name = name;
		this.x = x;
		this.y = y;
	}
	public void run() {
		age++;
		System.out.println("Life.run:"+ age);
		
	}

}
