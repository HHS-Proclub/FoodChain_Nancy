package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FoodChain implements  Serializable{
	public List <Life> lifeList = new ArrayList <Life>();
	public Field field = new Field(1000,1000);
	public int maxThread = 200;
	public Map <String, String>total = new HashMap();
	public List<String>nullList = new ArrayList();
	public static void main(String[] args) {
		new FoodChain().doIt();

	}

	void doIt() {
		Random rand = new Random(99);	
		int maxW = field.width-100;
		int maxH = field.height-100;
		total.put("Wolf", "0");
		total.put("Rabbit", "0");
		total.put("Dandelion", "0");
		total.put("Null", "0");
		total.put("Total", "0");
		
		for (int i=0; i<0; i++) {
			Life life = new Wolf(this, (30+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "wolf.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
		}
		
		for (int i=0; i<1; i++) {
			Life life = new Rabbit(this, (20+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "rabbit.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
		}
		
		for (int i=0; i<1; i++) {
			Life life = new Dandelion(this, (10+rand.nextInt(maxW)), (100+rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "dandelion.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
		}


	}
	
	public List getLifeList() {
		//printList();
		return lifeList;
	}
	public void setLifeList(List list) {
		//printList();
		lifeList = list;
	}
	public void printList() {
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life==null) {
				continue;
			}
			System.err.println("name=" + life.name+"x="+life.x + ", y=" + life.y);
		}
	}
}
