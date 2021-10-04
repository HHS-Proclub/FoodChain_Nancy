package nancy.com.foodchain.server;

import java.util.ArrayList;
import java.util.List;

public class FoodChain {
	public List <Life> lifeList = new ArrayList <Life>();
	public Field field = new Field(100,100);
	public static void main(String[] args) {
		new FoodChain().doIt();

	}

	void doIt() {
		lifeList.add(new Dog(this, "Dog1", ((int)Math.random()*field.width), ((int)Math.random()*field.height)));
		lifeList.add(new Dog(this, "Dog2", ((int)Math.random()*field.width), ((int)Math.random()*field.height)));
		lifeList.add(new Cactus(this, "Cactus1", ((int)Math.random()*field.width), ((int)Math.random()*field.height)));
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			new Thread(life).start();
		}
	}
	
	public List getLifeList() {
		return lifeList;
	}

}
