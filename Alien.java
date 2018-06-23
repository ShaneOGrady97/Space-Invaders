import java.awt.Image;

public class Alien extends Sprite2D {
	
	private static double xSpeed=0;
	
	private boolean isAlive=true;
	
	private boolean crashed=false;
	int direction=1;

	public Alien(Image i,Image i2, int windowWidth) {
		super(i,i2, windowWidth);
	}
	

	public void setCrashed(boolean newVal){
		crashed = newVal;
	}
	
	
	public boolean getCrashed(){
		return crashed;
	}
	
	public boolean move(){
		if(isAlive) {
		x += ((direction) * xSpeed);
	
		if (x >= 800 - myImage.getWidth(null) || x <= 0) {
		return crashed=true;
		}
		else {
			return false;
		}
		}
		return false;
		
	}
	
	public static void reverseDirection() {
		xSpeed=-xSpeed;
	}
	
	public void jumpDownwards() {
		y+=20;
	}
}

