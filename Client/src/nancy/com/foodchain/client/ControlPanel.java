package nancy.com.foodchain.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ControlPanel extends JDialog implements ActionListener  {
	   private String[] data;
	   private JTextField descBox;
	   private JComboBox<String> colorList;
	   private JButton btnOk;
	   private JButton btnCancel;
	   private Client client;
	   private long lastSliderTime;
	   public boolean isSetValue = false;
	   private int sliderValue;
	   public Map <String, JSlider> sliderMap;
	   public ControlPanel(JFrame parent, Client client) {
	      super(parent,"Control Panel",false);
	      this.client = client;
	      
	     
	      sliderMap = new HashMap<String, JSlider>();
	      Point loc = parent.getLocation();
	      setLocation(loc.x+80,loc.y+80);
	      data = new String[2]; // set to amount of data items
	      JPanel panel = new JPanel();
	      
	      panel.setLayout(new BorderLayout());
	      
	      JPanel sliderPanel = new JPanel(new BorderLayout());
	      sliderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      panel.add(sliderPanel, BorderLayout.NORTH);
	      
	      JPanel btnPanel = new JPanel(new GridBagLayout());
	      btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      panel.add(btnPanel, BorderLayout.SOUTH);
	      
	      JPanel bornPeriodPanel = new JPanel(new FlowLayout());
	      bornPeriodPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      sliderPanel.add(bornPeriodPanel, BorderLayout.NORTH);
	      
	      JPanel bornRatePanel = new JPanel(new FlowLayout());
	      bornRatePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	      sliderPanel.add(bornRatePanel, BorderLayout.SOUTH);
	      
	      
	      bornPeriodPanel.add(addSlider("Weather Condition", "weatherCondition", 1, 10, client.weatherCondition, 1, 1));	
	      bornPeriodPanel.add(addSlider("Birth Period: Wolf", "wolfBornPeriod", 1, nancy.com.foodchain.server.Life.BORN_PERIOD*2, client.wolfBornPeriod, 100, 5));	
	      bornPeriodPanel.add(addSlider("Birth Period: Rabbit", "rabbitBornPeriod", 1, nancy.com.foodchain.server.Life.BORN_PERIOD*2, client.rabbitBornPeriod, 100, 5));	
	      bornRatePanel.add(addSlider("Birth Rate: Wolf", "wolfBornRate", 1, nancy.com.foodchain.server.Life.BORN_RATE*2, client.wolfBornRate, 49, 10));
	      bornRatePanel.add(addSlider("Birth Rate: Rabbit", "rabbitBornRate", 1, nancy.com.foodchain.server.Life.BORN_RATE*2, client.rabbitBornRate, 49, 10));
	      bornRatePanel.add(addSlider("Birth Rate: Dandelion", "dandelionBornRate", 1, nancy.com.foodchain.server.Life.BORN_RATE*2, client.dandelionBornRate, 49, 10));
	      
	      GridBagConstraints gbc = new GridBagConstraints();
	      
	      btnOk = new JButton("Ok");
	      btnOk.addActionListener(this);
	      gbc.gridwidth = 1;
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      btnPanel.add(btnOk,gbc);
	      btnCancel = new JButton("Cancel");
	      btnCancel.addActionListener(this);
	      gbc.gridx = 1;
	      gbc.gridy = 0;
	      btnPanel.add(btnCancel,gbc);
	      getContentPane().add(panel);
	      pack();
	   }
	   public void actionPerformed(ActionEvent ae) {
	      sliderMap = null;
	      dispose();
	   }
	   public String[] run() {
	      this.setVisible(true);
	      return data;
	   }
	   
	   private JPanel addSlider( String label, String key, int FPS_MIN, int FPS_MAX, int FPS_INIT, int mainTicksSpace, int minTicksSpace) {
		  JPanel panel = new JPanel();
		  panel.add(new JLabel(label));
		 // System.err.println("HORIZONTAL="+JSlider.HORIZONTAL+" FPS_MIN="+
          //        FPS_MIN+" FPS_MAX="+FPS_MAX+" FPS INIT="+ FPS_INIT);
	   	  JSlider slider = new JSlider(JSlider.HORIZONTAL,
                  FPS_MIN, FPS_MAX, FPS_INIT);
	   	  sliderMap.put(key, slider);
	   	  slider.setMajorTickSpacing(mainTicksSpace);
	   	  slider.setMinorTickSpacing(minTicksSpace);
	   	  slider.setPaintTicks(true);
	   	  slider.setPaintLabels(true);
	   	  slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        	long curTime = timestamp.getTime();
	        	
	        	//System.err.println("dt="+(curTime - lastSliderTime));
				if (curTime - lastSliderTime<2000 || isSetValue) {
					lastSliderTime = curTime;
	        		isSetValue = false;

	        		return;
	        	}
	        	System.out.println("Slider_"+key+": " + slider.getValue());
		        try {
		        	    PrintWriter out = new PrintWriter(client.socket.getOutputStream(), true);
		        	    String msg = "["+new ClientLife("c", key,""+slider.getValue()).toJson()+"]";
			            out.println(msg);				
				        //System.err.println("Send control msg:"+msg);			
		        } catch (Exception ee) {
		            System.out.println("Initializing error. Make sure that server is alive!\n" + ee);
		        }
	        }
	      });

		
		  //Turn on labels at major tick marks.
	   	slider.setMajorTickSpacing(10);
	   	slider.setMinorTickSpacing(1);
	   	slider.setPaintTicks(true);
	   	slider.setPaintLabels(true);
	   	panel.add(slider);
		return panel;
	   }

	}