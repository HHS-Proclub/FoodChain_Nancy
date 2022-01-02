package nancy.com.foodchain.client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.*;




public class FoodChainField extends JFrame  implements MouseListener{
	Canvas canvas;
	Graphics g;
	Canvas panel;
	JLabel tips;
	Client client;
	public ControlPanel controlDlg;
	public DashBoard dashBoardDlg;
    // constructor
	FoodChainField(Client client)
    {
        super("canvas");
        this.client = client;
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(0, 0, 600, 40);
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addMouseListener(this);
        
        
        this.tips = new JLabel("Tips");
        tips.setLocation(0, 0);
        tips.setSize(new Dimension(540,20));
        
        JButton controlButton = new JButton("Control Panel");
        JFrame self = this;
        controlButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
        	   controlDlg = new ControlPanel(self, client);
        	   controlDlg.setLocation(630,5);
        	   controlDlg.setVisible(true);
           }
        });
      
        
        JButton dashButton = new JButton("DashBoard");
        dashButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
        	   dashBoardDlg = new DashBoard(self, client);
        	   dashBoardDlg.setLocation(1200,130);
        	   dashBoardDlg.setVisible(true);
           }
        });
        controlPanel.add(controlButton);
        controlPanel.add(dashButton);
        controlPanel.add(tips);
        
        add(controlPanel);
        
        this.canvas = new Canvas(true);
        add(canvas);
        this.pack();
        this.setVisible(true);
        setSize(1700, 1000);
    }
 
	public List <ClientLife> lifeList = new ArrayList <ClientLife>();
	public Map <String, ClientLife> lifeMap = new HashMap<String, ClientLife>();

    public void update(List<ClientLife> lifeList) {
    	
    	//for (int i=0; i<lifeList.size();i++) {
		//	Life life = lifeList.get(i);
		//	System.err.println("name=" + life.name+" x="+life.x + ", y=" + life.y);
		//}
    	//Map to hold new lifes
    	Map <String, ClientLife> newLifeMap = new HashMap<String, ClientLife>();
    	
    	//Loop through new life list to add new life or update existing life
    	for (int i=0; i<lifeList.size();i++) {
			ClientLife life = lifeList.get(i);
			if (life==null || life.icon==null) {
	    		continue;
	    	}			
			newLifeMap.put(life.name, life);
		}
    	
    	//Loop through existing life map to remove no longer exist life
    	for (Map.Entry<String,ClientLife> entry : lifeMap.entrySet()) {
    		ClientLife life = entry.getValue();
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
			for (Map.Entry<String,ClientLife> entry : lifeMap.entrySet()) {
        		ClientLife life = entry.getValue();
        		
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
			ClientLife life = lifeList.get(i);
			if (life==null) {
	    		continue;
	    	}			
			int lx = life.x+life.width/2;
			int ly = life.y+life.height/2;
			//System.err.println("lx="+lx+" ly="+ly+" x="+p.x+" y="+p.y);
			if (Math.abs(p.x-lx)<50 && Math.abs(p.y-ly)<50) {
				tips.setText("Name: "+life.name+" State: "+life.state+" Age: "+life.age+" Health: "+life.health);
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
