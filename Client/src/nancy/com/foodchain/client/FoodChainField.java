package nancy.com.foodchain.client;
import java.awt.*;
import javax.swing.*;

public class FoodChainField extends JFrame {
    // constructor
	FoodChainField()
    {
        super("canvas");
 
        // create a empty canvas
        Canvas c = new Canvas() {
 
            // paint the canvas
            public void paint(Graphics g)
            {
                // set color to red
                g.setColor(Color.red);
 
                // set Font
                g.setFont(new Font("Bold", 1, 20));
 
                // draw a string
                g.drawString("ccccc", 100, 100);
            }
        };
 
        // set background
        c.setBackground(Color.white);
 
        add(c);
        setSize(1000, 1000);
        show();
    }
 
    // Main Method
    public static void main(String args[])
    {
    	FoodChainField c = new FoodChainField();
    }
}
