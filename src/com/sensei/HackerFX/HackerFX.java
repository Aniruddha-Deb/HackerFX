package com.sensei.HackerFX;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.docopt.Docopt;

public class HackerFX {
	
	public static void main( String[] args ) throws IOException {
		
		Docopt docopt = new Docopt( HackerFX.class.getResourceAsStream( 
				"/docopt_help_string.txt" ) );
		
		Map<String, Object> parsedArgs = docopt.parse( args );
		
		int fontSize = Integer.parseInt( parsedArgs.get( "-s" ).toString() );
		System.out.println( fontSize );
		
		BufferedImage inImg = ImageIO.read( 
				new File( parsedArgs.get( "-i" ).toString() ) );
		
		int charHeight = fontSize;
		int charWidth = (fontSize*2)/3;
		
		int xWidth = charWidth * inImg.getWidth();
		int yWidth = charHeight * inImg.getHeight();
		
		BufferedImage outImg = new BufferedImage( xWidth, yWidth, 
				                                  BufferedImage.TYPE_INT_RGB );
		
		Graphics g2d = outImg.getGraphics();
		
		g2d.setColor( Color.decode( parsedArgs.get( "-b" ).toString() ) );
		g2d.drawRect( 0, 0, xWidth, yWidth );
		g2d.setColor( Color.decode( parsedArgs.get( "-f" ).toString() ) );
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
		
		ImageIO.write( outImg, "png", new File( parsedArgs.get( "-o" ).toString() ) );
 	}
}
