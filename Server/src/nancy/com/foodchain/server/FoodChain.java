package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nancy.com.foodchain.server.Animal;

public class FoodChain implements  Serializable{
	public List <Life> lifeList = new ArrayList <Life>();
	public Field field = new Field(1500,800);
	public int maxThread = 200;
	public int threadCount= 0;
	public Map <String, String>total = new HashMap();
	public List<String>nullList = new ArrayList();
	public int weatherConditionMax = 10;
	public int weatherCondition = 1;
	public int wolfBornPeriod = Life.BORN_PERIOD;
	public boolean test = false;
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
		
		for (int i=0; i<2; i++) {
			Life life = new Wolf(this, (30+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxW)), (100+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "wolf.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
			threadCount++;
		}
		
		for (int i=0; i<10; i++) {
			Life life = new Rabbit(this, (20+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxW)), (100+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "rabbit.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
			threadCount++;
		}
		
		for (int i=0; i<20; i++) {
			Life life = new Dandelion(this, (10+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxW)), (100+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "dandelion.png");
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
			threadCount++;
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
	
	public void setField(KeyValue field) {
		if (field.value==null) {
			return;
		}
			
		if (field.key.equals("weatherCondition")) {
			weatherCondition = Integer.parseInt(field.value);
			System.err.println("weatherCondition="+weatherCondition);
		} else if (field.key.equals("wolfBornPeriod")) {
			for (int i=0; i<lifeList.size();i++) {
				Life life = lifeList.get(i);
				if (life==null || !(life instanceof Wolf)) {
					continue;
				}

				life.bornPeriod = Integer.parseInt(field.value);
					
			}
			
		}
	}

	public void setFieldValue(KeyValue field) {
		if (field.key.equals("weatherCondition")) {
			field.value = ""+ weatherCondition;
			System.err.println("weatherCondition="+weatherCondition);
		} else if (field.key.equals("wolfBornPeriod")) {
			field.value = ""+ wolfBornPeriod;
			
		}
		
	}
}
