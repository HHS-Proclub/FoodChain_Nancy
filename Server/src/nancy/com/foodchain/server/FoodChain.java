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
		int maxW = field.width-100;
		int maxH = field.height-100;
		
		for (int i=0; i<1; i++) {
			lifeList.add(new Wolf(this, "Wolf"+i, (30+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 30, 30, "wolf.png"));			
		}
		
		for (int i=0; i<20; i++) {
			lifeList.add(new Rabbit(this, "Rabbit"+i, (20+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 20, 20, "rabbit.png"));			
		}
		
		for (int i=0; i<10; i++) {
			lifeList.add(new Dandelion(this, "Dandelion"+i, (10+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 40, 40, "dandelion.png"));			
		}

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
