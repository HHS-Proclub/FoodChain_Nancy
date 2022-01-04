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
	      
	      JPanel populationLabel = new JPanel(new GridBagLayout());
	      populationLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      grid2.fill = GridBagConstraints.HORIZONTAL;
	      grid2.gridx = 0;
	      grid2.gridy = 0;
	      textPanel.add(populationLabel, grid2);
	      
	      JPanel environmentPanel = new JPanel(new GridBagLayout());
	      environmentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      grid2.fill = GridBagConstraints.HORIZONTAL;
	      grid2.gridx = 1;
	      grid2.gridy = 0;
	      textPanel.add(environmentPanel, grid2);
	      
	      GridBagConstraints grid = new GridBagConstraints();

	      //POPULATION PANEL STARTS
	      
	      JLabel populationTitleLabel = new JLabel("Population");
	      populationTitleLabel.setSize(150, 100);
	      populationTitleLabel.setFont(new Font("Consolas", Font.BOLD, 15));
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 0;
	      populationLabel.add(populationTitleLabel, grid); 
	      populationTitleLabel.setHorizontalAlignment(JLabel.CENTER);
	      
	      //COUNTS LABELS
	      
	      JLabel totalPopulation = new JLabel("                    Total: ");
	      totalPopulation.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 1;
	      populationLabel.add(totalPopulation, grid); 
	      
	      JLabel totalPopulationLabel = new JLabel(""+this.client.totalPopulation);
	      totalPopulationLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 1;
	      populationLabel.add(totalPopulationLabel, grid); 
	      fieldMap.put("totalPopulation", totalPopulationLabel);
	      
	      
	      JLabel wolfLabel = new JLabel("         Wolf count: ");
	      wolfLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 2;
	      populationLabel.add(wolfLabel, grid); 
	      
	      JLabel wolfCountLabel = new JLabel(""+this.client.countWolf);
	      wolfCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 2;
	      populationLabel.add(wolfCountLabel, grid); 
	      fieldMap.put("countWolf", wolfCountLabel);
	      
	      JLabel rabbitLabel = new JLabel("      Rabbit count: ");
	      rabbitLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 3;
	      populationLabel.add(rabbitLabel, grid);
	      
	      JLabel rabbitCountLabel = new JLabel(""+this.client.countRabbit);
	      rabbitCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 3;
	      populationLabel.add(rabbitCountLabel, grid); 
	      fieldMap.put("countRabbit", rabbitCountLabel);
	      
	      JLabel dandelionLabel = new JLabel("Dandelion count: ");
	      dandelionLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 4;
	      populationLabel.add(dandelionLabel, grid);
	      
	      JLabel dandelionCountLabel = new JLabel(""+this.client.countDandelion);
	      dandelionCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 4;
	      populationLabel.add(dandelionCountLabel, grid); 
	      fieldMap.put("countDandelion", dandelionCountLabel);
	      
	      //*TIME LABEL  
	      
	      JLabel elapsedTimeLabel = new JLabel("      Elapsed time: ");
	      elapsedTimeLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 5;
	      populationLabel.add(elapsedTimeLabel, grid);
	      
	     
	      JLabel elapsedTimeCountLabel = new JLabel(client.getDuration());
	      elapsedTimeCountLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 5;
	      populationLabel.add(elapsedTimeCountLabel, grid); 
	      fieldMap.put("elapsedTime", elapsedTimeCountLabel);
	      
	      
	      //ENVIRONMENT PANEL STARTS
	      
	      
	      JLabel environmentLabel = new JLabel("Environment");
	      environmentLabel.setSize(150, 100);
	      environmentLabel.setFont(new Font("Consolas", Font.BOLD, 15));
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 6;
	      environmentPanel.add(environmentLabel, grid);
	      environmentLabel.setHorizontalAlignment(JLabel.CENTER);
	      
	      //*WEATHER LABEL
	      
	      JLabel weatherLabel = new JLabel("Weather Condition:   ");
	      weatherLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 7;
	      environmentPanel.add(weatherLabel, grid);
	      
	      JLabel weatherConditionLabel = new JLabel(""+this.client.weatherCondition);
	      weatherConditionLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 7;
	      environmentPanel.add(weatherConditionLabel, grid); 
	      fieldMap.put("weatherCondition", weatherConditionLabel);
	      
	      //born PERIOD labels	  
	      
	      JLabel wolfBPLabel = new JLabel("   Wolf Born Period:");
	      wolfBPLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 8;
	      environmentPanel.add(wolfBPLabel, grid);
	      
	      JLabel wolfBPNumLabel = new JLabel(""+this.client.wolfBornPeriod);
	      wolfBPNumLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 8;
	      environmentPanel.add(wolfBPNumLabel, grid); 
	      fieldMap.put("wolfBornPeriod", wolfBPNumLabel);
	      
	      JLabel rabbitBPLabel = new JLabel("Rabbit Born Period:");
	      rabbitBPLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 9;
	      environmentPanel.add(rabbitBPLabel, grid);
	      
	      JLabel rabbitBPNumLabel = new JLabel(""+this.client.rabbitBornPeriod);
	      rabbitBPNumLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 9;
	      environmentPanel.add(rabbitBPNumLabel, grid); 
	      fieldMap.put("rabbitBornPeriod", rabbitBPNumLabel);
	      
	      
	      //born RATE labels	      
	      
	      JLabel wolfBRLabel = new JLabel("Wolf Born Rate:");
	      wolfBRLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 0;
	      grid.gridy = 10;
	      environmentPanel.add(wolfBRLabel, grid); 
	      
	      JLabel wolfBRNumLabel = new JLabel(""+this.client.wolfBornRate);
	      wolfBRNumLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 1;
	      grid.gridy = 10;
	      environmentPanel.add(wolfBRNumLabel, grid);
	      fieldMap.put("wolfBornRate", wolfBRNumLabel);
	      
	      JLabel rabbitBRLabel = new JLabel("Rabbit Born Rate:");
	      rabbitBRLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 11;
	      environmentPanel.add(rabbitBRLabel, grid);
	      
	      JLabel rabbitBRNumLabel = new JLabel(""+this.client.rabbitBornRate);
	      rabbitBRNumLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 11;
	      environmentPanel.add(rabbitBRNumLabel, grid); 
	      fieldMap.put("rabbitBornRate", rabbitBRNumLabel);
	      
	      JLabel dandelionBRLabel = new JLabel("Dandelion Born Rate:");
	      dandelionBRLabel.setSize(150, 100);
	      grid.fill = GridBagConstraints.HORIZONTAL;
	      grid.gridx = 0;
	      grid.gridy = 12;
	      environmentPanel.add(dandelionBRLabel, grid);
	      
	      JLabel dandelionBRNumLabel = new JLabel(""+this.client.dandelionBornRate);
	      dandelionBRNumLabel.setSize(150, 100);
	      grid.weighty = 1.0;
	      grid.gridx = 1;
	      grid.gridy = 12;
	      environmentPanel.add(dandelionBRNumLabel, grid); 
	      fieldMap.put("dandelionBornRate", dandelionBRNumLabel);
	      
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
