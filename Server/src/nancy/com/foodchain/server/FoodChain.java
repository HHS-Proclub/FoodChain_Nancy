package nancy.com.foodchain.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nancy.com.foodchain.client.ClientLife;
import nancy.com.foodchain.server.Animal;
import nancy.com.foodchain.server.Life.State;

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
	public int rabbitBornPeriod = Life.BORN_PERIOD;
	public boolean test = false;
	private int countWolf;
	private int countRabbit;
	private int countDandelion;
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
		
		for (int i=0; i<10; i++) {
			Life life = new Wolf(this, (30+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxW)), (100+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "wolf.png", -1);
			lifeList.add(life);		
			life.thread = new Thread(life);
			life.thread.start();
			threadCount++;
		}
		
		for (int i=0; i<40; i++) {
			Life life = new Rabbit(this, (20+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxW)), (100+(rand.nextInt(3)>1?-1:1)*rand.nextInt(maxH)), 2*maxW/3, 2*maxH/3, "rabbit.png", -1);
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
	
	public void setField(ClientLife field) {
		if (field.value==null) {
			return;
		}
			
		if (field.key.equals("weatherCondition")) {
			weatherCondition = Integer.parseInt(field.value);
			//System.err.println("weatherCondition="+weatherCondition);
			
		} else if (field.key.equals("wolfBornPeriod")) {
			int newPeriod = Integer.parseInt(field.value);
			for (int i=0; i<lifeList.size();i++) {
				Life life = lifeList.get(i);
				if (life==null || !(life instanceof Wolf)) {
					continue;
				}
				//int oldPeriod = life.bornPeriod;
				life.bornPeriod = newPeriod;
				//if (newPeriod<oldPeriod) {
					life.bornCount = 0;//Math.min(life.bornCount, life.bornPeriod);	
				//} else {
				//	life.bornCount = Math.max(life.bornCount, life.bornPeriod);	
				//}
			}
			wolfBornPeriod = newPeriod;
			
		} else if (field.key.equals("rabbitBornPeriod")) {
			int newPeriod = Integer.parseInt(field.value);
			for (int i=0; i<lifeList.size();i++) {
				Life life = lifeList.get(i);
				if (life==null || !(life instanceof Rabbit)) {
					continue;
				}
				int oldPeriod = life.bornPeriod;
				life.bornPeriod = newPeriod;
				if (newPeriod<oldPeriod) {
					life.bornCount = Math.min(life.bornCount, life.bornPeriod);	
				} else {
					life.bornCount = Math.max(life.bornCount, life.bornPeriod);	
				}
			}
			rabbitBornPeriod = newPeriod;
		} else if (field.key.equals("countWolf")) {
			countWolf = Integer.parseInt(field.value);
			//System.err.println("weatherCondition="+weatherCondition);
			
		} else if (field.key.equals("countRabbit")) {
			countRabbit = Integer.parseInt(field.value);
			//System.err.println("weatherCondition="+weatherCondition);
			
		} else if (field.key.equals("countDandelion")) {
			countDandelion = Integer.parseInt(field.value);
			//System.err.println("weatherCondition="+weatherCondition);
			
		} 
	}

	public void setFieldValue(ClientLife field) {
		if (field.key.equals("weatherCondition")) {
			field.value = ""+ weatherCondition;
			//System.err.println("weatherCondition="+weatherCondition);
		} else if (field.key.equals("wolfBornPeriod")) {
			field.value = ""+ wolfBornPeriod;
		} else if (field.key.equals("rabbitBornPeriod")) {
			field.value = ""+ rabbitBornPeriod;
		}
		
	}
	
	public String getFieldValue(String key) {
		if (key.equals("weatherCondition")) {
			return ""+weatherCondition;
			//System.err.println("weatherCondition="+weatherCondition);
		} else if (key.equals("wolfBornPeriod")) {
			return ""+ wolfBornPeriod;
		} else if (key.equals("rabbitBornPeriod")) {
			return  ""+ rabbitBornPeriod;
		} else if (key.equals("countWolf")) {			
			return  ""+ countWolf;
		} else if (key.equals("countRabbit")) {			
			return  ""+ countRabbit;
		} else if (key.equals("countDandelion")) {			
			return  ""+ countDandelion;
		}
		return null;
	}

	public void updateFields() {
		// TODO Auto-generated method stub
		Map<String, String> counts = new HashMap();
		for (int i=0; i<lifeList.size(); i++) {
			Life life = lifeList.get(i);
			if (life==null || life.state==State.DEAD) {
				continue;
			}
			String nn = counts.get("count"+life.lifeType);
			int n = nn==null?1:(Integer.parseInt(nn)+1);
			counts.put("count"+life.lifeType, ""+n);
			
		}
		for (String key : counts.keySet()) {
			setField(new ClientLife("d", key, counts.get(key)));
		}
	}
}
