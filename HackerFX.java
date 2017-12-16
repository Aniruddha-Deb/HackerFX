import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HackerFX {

	public static void main( String[] args ) throws IOException {
		
		int xWidth = 2560;
		int yWidth = 1440;
		
		BufferedImage img = new BufferedImage( xWidth, yWidth, BufferedImage.TYPE_INT_RGB );
		
		BufferedImage inImg = ImageIO.read( HackerFX.class.getResource( "AD_bg.png" ) );
		Graphics g2d = img.getGraphics();
		
		g2d.setColor( Color.BLACK );
		g2d.drawRect( 0, 0, xWidth, yWidth );
		g2d.setColor( Color.GREEN );
		int fontSize = 24;
		g2d.setFont( new Font( "Menlo", Font.PLAIN, fontSize ) );

		int i=0;
		int numPxY = 0;
		int numPxX = 0;
		while( i < yWidth ) {
			int j=0;
			numPxX = 0;
			while( j < xWidth ) {
				if( inImg.getRGB( numPxX, numPxY ) < -5 ) {
					g2d.drawString( (int)Math.round( Math.random() )+"" , j, i );					
				}
				j += (fontSize*2/3);
				numPxX++;
			}
			i += fontSize;
			numPxY++;
		}
		
		System.out.println( numPxX + " by " + numPxY );
		
		ImageIO.write( img, "png", new File( "hackerfx_img.png" ) );
 	}
}
