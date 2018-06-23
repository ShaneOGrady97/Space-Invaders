import java.awt.Image;

public class PlayerBullet extends Sprite2D {
	
	public PlayerBullet(Image i, int windowWidth) {
		super(i,i, windowWidth);
	}
	
	public boolean move() {
		y-=20;
		return(y<0);
	}


}


