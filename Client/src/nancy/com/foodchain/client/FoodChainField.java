package nancy.com.foodchain.client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.*;
import com.google.gson.Gson;



public class FoodChainField extends JFrame  implements MouseListener{
	Canvas canvas;
	Graphics g;
	Canvas panel;
	JLabel tips;
	Client client;
    // constructor
	FoodChainField(Client client)
    {
        super("canvas");
        this.client = client;
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(0, 0, 400, 30);
        controlPanel.setLayout(new FlowLayout());
        addMouseListener(this);
        this.tips = new JLabel("Tips");
        tips.setLocation(0, 0);
        tips.setSize(new Dimension(540,20));
        
        JButton okButton = new JButton("Control Panel");
        JFrame self = this;
        okButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
        	   ControlPanel dlg = new ControlPanel(self, client);
        	   dlg.setVisible(true);
           }
        });
        controlPanel.add(okButton);
        controlPanel.add(tips);
        add(controlPanel);
        
        this.canvas = new Canvas(true);
        add(canvas);
        this.pack();
        this.setVisible(true);
        setSize(1000, 1000);
    }
 
	public List <Life> lifeList = new ArrayList <Life>();
	public Map <String, Life> lifeMap = new HashMap<String, Life>();

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
    	canvas.revalidate();
    	this.repaint();
    }
    
    class Canvas extends JPanel {
		  public Canvas() {
			super();
			
		}

		public Canvas(boolean isDoubleBuffered) {
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

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		for (int i=0; i<lifeList.size();i++) {
			Life life = lifeList.get(i);
			if (life==null) {
	    		continue;
	    	}			
			int lx = life.x+life.width/2;
			int ly = life.y+life.height/2;
			System.err.println("lx="+lx+" ly="+ly+" x="+p.x+" y="+p.y);
			if (Math.abs(p.x-lx)<50 && Math.abs(p.y-ly)<50) {
				tips.setText("Name:"+life.name+" State:"+life.state);
				this.repaint();
				break;
			}
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class DlgButton extends JButton {

	    DlgButton() {
	        setText("Control Panel");
	        addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
	        });

	    }

	}
}
