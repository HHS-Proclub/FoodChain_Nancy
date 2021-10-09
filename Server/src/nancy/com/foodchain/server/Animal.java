package nancy.com.foodchain.server;

import java.util.Random;

public class Animal extends Life {
	public Animal(FoodChain foodchain, String name, int x, int y, String icon) {
		super(foodchain, name, x, y, icon);
		// TODO Auto-generated constructor stub
	}
	public int[] direction = new int[]{1,1};
	public int directionPeriod = 5;
	public void run() {
		super.run();
		System.out.println("Animal.run");
	}
	
	public void move() {
		Random rand = new Random();	
		while (true) {
			if (--directionPeriod==0) {
				direction[0]=(1-rand.nextInt(2));
				direction[1]=(1-rand.nextInt(2));
				directionPeriod = 25+rand.nextInt(20);
			}
			x+=direction[0];
			y+=direction[1];
			
			if (x<0||x>foodchain.field.width) {
				direction[0] *= -1;
				x+=direction[0];
				directionPeriod = 5+rand.nextInt(20);
			}
			if (y<0||y>foodchain.field.height) {
				direction[1] *= -1;
				y+=direction[1];
				directionPeriod = 5+rand.nextInt(20);
			}
			
			//System.err.println(name+":"+x+", "+y);
			
		}
	}
	public Life born(String name) {
		return super.born(name);
	}
	
	
}
