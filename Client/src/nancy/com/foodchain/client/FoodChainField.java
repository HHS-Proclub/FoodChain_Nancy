package nancy.com.foodchain.client;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.imageio.ImageIO;
import javax.swing.*;
import nancy.com.foodchain.server.*;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder; 


public class FoodChainField extends JFrame {
	Canvas canvas;
	Graphics g;
	MyPanel panel;
	
    // constructor
	FoodChainField()
    {
        super("canvas");
        this.panel = new MyPanel(true);
        add(panel);
        this.setVisible(true);
        setSize(1000, 1000);
        // create a empty canvas
        /*this.canvas = new Canvas() {
 
            // paint the canvas
            public void paint(Graphics g)
            {

                for (Map.Entry<String,Life> entry : lifeMap.entrySet()) {
            		Life life = entry.getValue();
            		
            		ImageIcon icon = new ImageIcon(life.icon);
                    Image newImg = icon.getImage().getScaledInstance(life.width, life.height, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newImg);
                    icon.paintIcon(this, g, life.x, life.y);
                    System.err.println("x="+life.x+" y="+life.y);
            	}
                
                
               
                
            }
        };
     
        // set background
        this.canvas.setBackground(Color.white);
        this.canvas.setBounds(0, 0, 1000, 1000);
        add(this.canvas);
        setSize(1000, 1000);
        show();*/
    }
 
	public List <Life> lifeList = new ArrayList <Life>();
	public Map <String, Life> lifeMap = new HashMap<String, Life>();
    // Main Method
    public static void main(String args[])
    {
    	FoodChainField c = new FoodChainField();
    }
    public void update(String jsonString) {
    	
    	Gson gson = new Gson();
        List<Life> lifeList = Arrays.asList(gson.fromJson(jsonString, Life[].class));
        
    	//for (int i=0; i<lifeList.size();i++) {
		//	Life life = lifeList.get(i);
		//	System.err.println("name=" + life.name+" x="+life.x + ", y=" + life.y);
		//}
    	//Map to hold new lifes
    	Map <String, Life> newLifeMap = new HashMap<String, Life>();
    	
    	//Loop through new life list to add new life or update existing life
    	for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life==null) {
	    		continue;
	    	}			
			newLifeMap.put(life.name, life);
		}
    	
    	//Loop through existing life map to remove no longer exist life
    	for (Map.Entry<String,Life> entry : lifeMap.entrySet()) {
    		Life life = entry.getValue();
    	}
    	
    	//Update life list and map
    	this.lifeList = lifeList;
    	this.lifeMap = newLifeMap;
    	panel.revalidate();
    	this.repaint();
    }
    
    class MyPanel extends JPanel {
		  public MyPanel() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MyPanel(boolean isDoubleBuffered) {
			super(isDoubleBuffered);
			// TODO Auto-generated constructor stub
		}


		public void paintComponent(Graphics g) {
			for (Map.Entry<String,Life> entry : lifeMap.entrySet()) {
        		Life life = entry.getValue();
        		
        		ImageIcon icon = new ImageIcon(life.icon);
                Image newImg = icon.getImage().getScaledInstance(life.width, life.height, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                icon.paintIcon(this, g, life.x, life.y);
                //System.err.println("x="+life.x+" y="+life.y);
        	}
		  }
		}
}
