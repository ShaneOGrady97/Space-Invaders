import java.awt.*;
import javax.swing.*;

public class Sprite2D
{
	protected double x,y;
	protected double xSpeed;
	protected Image myImage;
	protected Image myImage2;
	protected int windowWidth;
	protected int framesDrawn=0;
	
	public Sprite2D(Image i,Image i2,int windowWidth)
	{
		this.windowWidth=windowWidth;
		myImage=i;
		myImage2=i2;
		
	}
	
	public void setPosition(double xx, double yy) {
		xx = x;
		yy = y;
	}
	
	public void setXSpeed(double dx) {
		xSpeed=dx;
	}
	
	public void paint(Graphics g)
	{
		framesDrawn++;
		if ( framesDrawn%100<50 )
		g.drawImage(myImage, (int)x, (int)y, null);
		else
		g.drawImage(myImage2, (int)x, (int)y, null);
	}

	
}
