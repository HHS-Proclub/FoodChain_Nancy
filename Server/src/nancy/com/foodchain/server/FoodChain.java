package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodChain implements  Serializable{
	public List <Life> lifeList = new ArrayList <Life>();
	public Field field = new Field(100,100);
	public static void main(String[] args) {
		new FoodChain().doIt();

	}

	void doIt() {
		lifeList.add(new Wolf(this, "Wolf1", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "wolf.png"));
		lifeList.add(new Wolf(this, "Wolf2", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "wolf.png"));
		lifeList.add(new Rabbit(this, "Rabbit1", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "rabbit.png"));
		lifeList.add(new Rabbit(this, "Rabbit2", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "rabbit.png"));
		lifeList.add(new Rabbit(this, "Rabbit3", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "rabbit.png"));
		lifeList.add(new Dandelion(this, "Dandelion1", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "dandelion.png"));
		lifeList.add(new Dandelion(this, "Dandelion2", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "dandelion.png"));
		lifeList.add(new Dandelion(this, "Dandelion3", ((int)Math.random()*field.width), ((int)Math.random()*field.height), "dandelion.png"));
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			new Thread(life).start();
		}
	}
	
	public List getLifeList() {
		//printList();
		return lifeList;
	}

	public void printList() {
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			System.err.println("name=" + life.name+"x="+life.x + ", y=" + life.y);
		}
	}
}
