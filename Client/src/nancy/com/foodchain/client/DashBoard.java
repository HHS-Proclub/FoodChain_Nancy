package nancy.com.foodchain.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class DashBoard extends JDialog implements ActionListener, Runnable  {
	   private String[] data;
	   private JTextField descBox;
	   private JComboBox<String> colorList;
	   private JButton btnOk;
	   private JButton btnCancel;
	   private Client client;
	   public boolean isSetValue = false;
	   public Map<String, JLabel> fieldMap;
	   public DashBoard(JFrame parent, Client client) {
	      super(parent,"DashBoard",false);
	      this.client = client;
	      
	     
	      fieldMap = new HashMap<String, JLabel>();
	      Point loc = parent.getLocation();
	      setLocation(loc.x+80,loc.y+80);
	      data = new String[2]; // set to amount of data items
	      
	      JPanel panel = new JPanel();
	      panel.setLayout(new BorderLayout());
	      panel.setPreferredSize(new Dimension(700, 700));
	      panel.setLocation(650, 650);
	      
	      JPanel textPanel = new JPanel(new GridBagLayout());
	      textPanel.setBackground(Color.green);
	      
	      GridBagConstraints grid2 = new GridBagConstraints();
	      grid2.insets= new Insets(3,3,3,3);
	      
	      JPanel textPanel1 = new JPanel(new GridBagLayout());
	      textPanel1.setBorder(new EmptyBorder(10, 10, 10, 10));
	      grid2.fill = GridBagConstraints.HORIZONTAL;
	      grid2.gridx = 0;
	      grid2.gridy = 0;
	      textPanel.add(textPanel1, grid2);
	      
	      JPanel textPanel2 = new JPanel(new GridBagLayout());
	      textPanel2.setBorder(new EmptyBorder(10, 10, 10, 10));
	      grid2.fill = GridBagConstraints.HORIZONTAL;
	      grid2.gridx = 1;
	      grid2.gridy = 0;
	      textPanel.add(textPanel2, grid2);
	      
	      GridBagConstraints grid = new GridBagConstraints();

	      JLabel populationLabel = new JLabel("Population");
	      populationLabel.setSize(150, 100);
	      populationLabel.setFont(new Font("Consolas", Font.BOLD, 15));
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 0;
	      textPanel1.add(populationLabel, grid); 
	      populationLabel.setHorizontalAlignment(JLabel.CENTER);
	      
	      JLabel totalPopulation = new JLabel("                    Total: ");
	      totalPopulation.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 1;
	      textPanel1.add(totalPopulation, grid); 
	      
	      JLabel totalPopulationLabel = new JLabel(""+this.client.totalPopulation);
	      totalPopulationLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 1;
	      textPanel1.add(totalPopulationLabel, grid); 
	      fieldMap.put("totalPopulation", totalPopulationLabel);
	      
	      JLabel wolfLabel = new JLabel("         Wolf count: ");
	      wolfLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 2;
	      textPanel1.add(wolfLabel, grid); 
	      
	      JLabel wolfCountLabel = new JLabel(""+this.client.countWolf);
	      wolfCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 2;
	      textPanel1.add(wolfCountLabel, grid); 
	      fieldMap.put("countWolf", wolfCountLabel);
	      
	      JLabel rabbitLabel = new JLabel("      Rabbit count: ");
	      rabbitLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 3;
	      textPanel1.add(rabbitLabel, grid);
	      
	      JLabel rabbitCountLabel = new JLabel(""+this.client.countRabbit);
	      rabbitCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 3;
	      textPanel1.add(rabbitCountLabel, grid); 
	      fieldMap.put("countRabbit", rabbitCountLabel);
	      
	      JLabel dandelionLabel = new JLabel("Dandelion count: ");
	      dandelionLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 4;
	      textPanel1.add(dandelionLabel, grid);
	      
	      JLabel dandelionCountLabel = new JLabel(""+this.client.countDandelion);
	      dandelionCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 4;
	      textPanel1.add(dandelionCountLabel, grid); 
	      fieldMap.put("countDandelion", dandelionCountLabel);
	      
	      JLabel elapsedTimeLabel = new JLabel("      Elapsed time: ");
	      elapsedTimeLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 5;
	      textPanel1.add(elapsedTimeLabel, grid);
	      
	     
	      JLabel elapsedTimeCountLabel = new JLabel(client.getDuration());
	      elapsedTimeCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 5;
	      textPanel1.add(elapsedTimeCountLabel, grid); 
	      fieldMap.put("elapsedTime", elapsedTimeCountLabel);
	      
	      JLabel environmentLabel = new JLabel("Environment");
	      environmentLabel.setSize(150, 100);
	      environmentLabel.setFont(new Font("Consolas", Font.BOLD, 15));
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 6;
	      textPanel2.add(environmentLabel, grid);
	      environmentLabel.setHorizontalAlignment(JLabel.CENTER);
	      
	      JLabel weatherLabel = new JLabel("Weather Condition:   ");
	      weatherLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 7;
	      textPanel2.add(weatherLabel, grid);
	      
	      JLabel weatherConditionLabel = new JLabel(""+this.client.weatherCondition);
	      weatherConditionLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 7;
	      textPanel2.add(weatherConditionLabel, grid); 
	      fieldMap.put("weatherCondition", weatherConditionLabel);
	      
	      JLabel wolfBornLabel = new JLabel("   Wolf Born Period:");
	      wolfBornLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 8;
	      textPanel2.add(wolfBornLabel, grid);
	      
	      JLabel wolfBornPeriodLabel = new JLabel(""+this.client.wolfBornPeriod);
	      wolfBornPeriodLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 8;
	      textPanel2.add(wolfBornPeriodLabel, grid); 
	      fieldMap.put("wolfBornPeriod", wolfBornPeriodLabel);
	      
	      JLabel rabbitBornLabel = new JLabel("Rabbit Born Period:");
	      rabbitBornLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 9;
	      textPanel2.add(rabbitBornLabel, grid);
	      
	      JLabel rabbitBornPeriodLabel = new JLabel(""+this.client.rabbitBornPeriod);
	      rabbitBornPeriodLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 9;
	      textPanel2.add(rabbitBornPeriodLabel, grid); 
	      fieldMap.put("rabbitBornPeriod", rabbitBornPeriodLabel);
	      
	      panel.add(textPanel, BorderLayout.NORTH);
	      
	      
	      JPanel btnPanel = new JPanel();
	      panel.setPreferredSize(new Dimension(400, 400));
	      btnPanel.setBackground(Color.red); 
	      
	      btnOk = new JButton("Ok");
	      btnOk.addActionListener(this);
	      btnPanel.add(btnOk);
	      
	      btnCancel = new JButton("Cancel");
	      btnCancel.addActionListener(this);
	      btnPanel.add(btnCancel);
	      
	      panel.add(btnPanel, BorderLayout.SOUTH);
	      
	      getContentPane().add(panel);
	      
	      pack();
	      
	      new Thread(this).start();
	      
	   }
	
	   
			
		private void sendDashboardUpdateRequest() {
			try {
        	    PrintWriter out = new PrintWriter(client.socket.getOutputStream(), true);
        	    String dashboardMsg = "["+
        	    		(new ClientLife("d", "weatherCondition",""+fieldMap.get("weatherCondition").getText()).toJson())+","+
        	    		(new ClientLife("d", "wolfBornPeriod",""+fieldMap.get("wolfBornPeriod").getText()).toJson())+","+
        	    		(new ClientLife("d", "rabbitBornPeriod",""+fieldMap.get("rabbitBornPeriod").getText()).toJson())+","+
        	    		(new ClientLife("d", "countWolf",""+fieldMap.get("countWolf").getText()).toJson())+","+
        	    		(new ClientLife("d", "countRabbit",""+fieldMap.get("countRabbit").getText()).toJson())+","+
        	    		(new ClientLife("d", "countDandelion",""+fieldMap.get("countDandelion").getText()).toJson())+","+
        	    		(new ClientLife("d", "elapsedTime",""+fieldMap.get("elapsedTime").getText()).toJson())+
        	    		"]";
	            out.println(dashboardMsg);				
		        //System.err.println("Send dashboard msg:"+dashboardMsg);			
        } catch (Exception ee) {
            System.out.println("Initializing error. Make sure that server is alive!\n" + ee);
        }
			
		}




		public void actionPerformed(ActionEvent ae) {
	      fieldMap = null;
	      dispose();
	   }




		@Override
		public void run() {
			while (fieldMap != null) {
				sendDashboardUpdateRequest();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			System.err.println("Dashboard exit");
		}

	   
	   
}
