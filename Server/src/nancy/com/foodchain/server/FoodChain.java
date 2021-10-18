package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodChain implements  Serializable{
	public List <Life> lifeList = new ArrayList <Life>();
	public Field field = new Field(1000,1000);
	public static void main(String[] args) {
		new FoodChain().doIt();

	}

	void doIt() {
		Random rand = new Random(99);	
		int maxW = field.width-300;
		int maxH = field.height-500;
		lifeList.add(new Wolf(this, "Wolf1", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "wolf.png"));
		//lifeList.add(new Wolf(this, "Wolf2", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "wolf.png"));
		lifeList.add(new Rabbit(this, "Rabbit1", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "rabbit.png"));
		//lifeList.add(new Rabbit(this, "Rabbit2", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "rabbit.png"));
		//lifeList.add(new Rabbit(this, "Rabbit3", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "rabbit.png"));
		lifeList.add(new Dandelion(this, "Dandelion1", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "dandelion.png"));
		//lifeList.add(new Dandelion(this, "Dandelion2", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "dandelion.png"));
		//lifeList.add(new Dandelion(this, "Dandelion3", (100+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), "dandelion.png"));
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			life.thread = new Thread(life);
			life.thread.start();
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
