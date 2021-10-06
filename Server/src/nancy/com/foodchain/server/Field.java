package nancy.com.foodchain.server;

import java.io.Serializable;

public class Field implements  Serializable{
	public Field(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	public int width;
	public int height;
	

}
