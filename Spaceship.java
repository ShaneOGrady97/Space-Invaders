import java.awt.Image;

public class Spaceship extends Sprite2D {
	
	private double xSpeed=0;

	public Spaceship(Image i, int windowWidth) {
		super(i,i, windowWidth);
	} 
	
	public void setXSpeed(double dx) {
		xSpeed=dx;
	}
	
	public void move() {
		x+=xSpeed;
		
		if ( x >= windowWidth - myImage.getWidth(null)) x = windowWidth - myImage.getWidth(null);
		else if (x <= 0) x = 0;
	}

}

