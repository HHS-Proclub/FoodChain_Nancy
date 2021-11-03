package nancy.com.foodchain.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nancy.com.foodchain.server.KeyValue;
import nancy.com.foodchain.server.Life;

public class ControlPanel extends JDialog implements ActionListener  {
	   private String[] data;
	   private JTextField descBox;
	   private JComboBox<String> colorList;
	   private JButton btnOk;
	   private JButton btnCancel;
	   private Client client;
	   public ControlPanel(JFrame parent, Client client) {
	      super(parent,"Control Panel",true);
	      this.client = client;
	      Point loc = parent.getLocation();
	      setLocation(loc.x+80,loc.y+80);
	      data = new String[2]; // set to amount of data items
	      JPanel panel = new JPanel();
	      panel.setLayout(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.insets = new Insets(2,2,2,2);
	      
	      panel.add(addSlider("Weather Condition", "weatherCondition", 1, 10, client.weatherCondition));	
	      panel.add(addSlider("Birth Period: Wolf", "bornPeriod", 1, Life.BORN_PERIOD, client.wolfBornPeriod));	
	      
	      btnOk = new JButton("Ok");
	      btnOk.addActionListener(this);
	      gbc.gridwidth = 1;
	      gbc.gridx = 0;
	      gbc.gridy = 3;
	      panel.add(btnOk,gbc);
	      btnCancel = new JButton("Cancel");
	      btnCancel.addActionListener(this);
	      gbc.gridx = 1;
	      gbc.gridy = 3;
	      panel.add(btnCancel,gbc);
	      getContentPane().add(panel);
	      pack();
	   }
	   public void actionPerformed(ActionEvent ae) {
	      /*Object source = ae.getSource();
	      if (source == btnOk) {
	         data[0] = descBox.getText();
	         data[1] = (String)colorList.getSelectedItem();
	      }
	      else {
	         data[0] = null;
	      }*/
	      dispose();
	   }
	   public String[] run() {
	      this.setVisible(true);
	      return data;
	   }
	   
	   private JPanel addSlider( String label, String key, int FPS_MIN, int FPS_MAX, int FPS_INIT) {
		  JPanel panel = new JPanel();
		  panel.add(new JLabel(label));
	   	  JSlider slider = new JSlider(JSlider.HORIZONTAL,
                  FPS_MIN, FPS_MAX, FPS_INIT);
	   	  slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
		        System.out.println("Slider1: " + slider.getValue());
		        try {
			            PrintWriter out = new PrintWriter(client.socket.getOutputStream(), true);	
			            BufferedReader in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
				        out.println("["+new KeyValue(key,""+slider.getValue()).toJson()+"]");				
				        String respond = in.readLine();
				        client.processServerResponse(respond);
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