package nancy.com.foodchain.client;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.imageio.ImageIO;
import javax.swing.*;
import nancy.com.foodchain.server.*;



public class FoodChainField extends JFrame {
	Canvas canvas;
	Graphics g;
    // constructor
	FoodChainField()
    {
        super("canvas");
        // create a empty canvas
        this.canvas = new Canvas() {
 
            // paint the canvas
            public void paint(Graphics g)
            {
                // set color to red
                g.setColor(Color.red);
 
                // set Font
                g.setFont(new Font("Bold", 1, 20));
 
                // draw a string
                g.drawString("ccccc", 100, 100);
                
                //circle
                setSize(400, 400);
                setVisible(true);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawOval(150, 150, 100, 100);
                
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
 
        add(this.canvas);
        setSize(1000, 1000);
        show();
    }
 
	public List <Life> lifeList = new ArrayList <Life>();
	public Map <String, Life> lifeMap = new HashMap<String, Life>();
    // Main Method
    public static void main(String args[])
    {
    	FoodChainField c = new FoodChainField();
    }
    public void update(List <Life> lifeList) {
    	
    	//for (int i=0; i<lifeList.size();i++) {
		//	Life life = lifeList.get(i);
		//	System.err.println("name=" + life.name+" x="+life.x + ", y=" + life.y);
		//}
    	//Map to hold new lifes
    	Map <String, Life> newLifeMap = new HashMap<String, Life>();
    	
    	//Loop through new life list to add new life or update existing life
    	for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			
			if (lifeMap.get(life.name)==null) {				
			} else {
				life.update();
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
    	this.canvas.repaint();
    }
}
