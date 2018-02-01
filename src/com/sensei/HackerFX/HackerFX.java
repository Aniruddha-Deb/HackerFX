package com.sensei.HackerFX;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.docopt.Docopt;

public class HackerFX {
	
	private Map<String, Object> parsedArgs = null;
	private BufferedImage inImg = null;
	private int fontSize = 0;
	
	public static final String FONT_SIZE  = "-s";
	public static final String INPUT_IMG  = "-i";
	public static final String OUTPUT_IMG = "-o";
	public static final String BG_COLOUR  = "-b";
	public static final String FG_COLOUR  = "-f";
	
	public HackerFX( String[] args ) {
		parsedArgs = getArguments( args );
	}
	
	public void process() throws IOException {
		BufferedImage outImg = processImage();
		saveImage( outImg );
	}
	
	private Map<String, Object> getArguments( String[] args ) {
		Docopt docopt = new Docopt( HackerFX.class.getResourceAsStream( 
				"/docopt_help_string.txt" ) );
		return docopt.parse( args );
	}
	
	private BufferedImage processImage() throws NumberFormatException, IOException {
		fontSize = getIntegerParameter( FONT_SIZE );		
		inImg = ImageIO.read( 
				              new File( getStringParameter( INPUT_IMG ) ) );
		return generateImage();
	}
	
	private BufferedImage generateImage() {
		
		int charHeight = fontSize;
		int charWidth = (fontSize*2)/3;
		int xWidth = charWidth  * inImg.getWidth();
		int yWidth = charHeight * inImg.getHeight();
		
		BufferedImage generatedImage = new BufferedImage( xWidth, yWidth, 
				                                  BufferedImage.TYPE_INT_RGB );		
		Graphics2D g2d = (Graphics2D)generatedImage.getGraphics();
		g2d.setColor( getColorParameter( BG_COLOUR ) );
		g2d.fillRect( 0, 0, xWidth, yWidth );
		g2d.setColor( getColorParameter( FG_COLOUR ) );
		g2d.setFont( new Font( "Menlo", Font.PLAIN, fontSize ) );

		Point currentPixel = new Point();

		for( int y=0; y<yWidth; y+=charHeight ) {
			currentPixel.x = 0;
			for( int x=0; x<xWidth; x+=charWidth ) {
				if( isBlack( currentPixel ) ) {
					g2d.drawString( generateRandomBit(), x, y );					
				}
				currentPixel.x++;
			}
			currentPixel.y++;
		}
		
		return generatedImage;
	}

	// Util methods
	
	private String getStringParameter( String key ) {
		return parsedArgs.get( key ).toString();
	}
	
	private Color getColorParameter( String key ) throws NumberFormatException {
		return Color.decode( getStringParameter( key ) );
	}
	
	private int getIntegerParameter( String key ) throws NumberFormatException {
		return Integer.parseInt( getStringParameter( key ) );
	}
	
	private void saveImage( BufferedImage i ) throws IOException {
		ImageIO.write( i, "png", new File( getStringParameter( OUTPUT_IMG ) ) );		
	}
	
	private String generateRandomBit() {
		return String.valueOf( (int)Math.round( Math.random() ) );
	}
	
	private boolean isBlack( Point pixel ) {
		return inImg.getRGB( pixel.x, pixel.y ) < -5;
	}
	
	public static void main( String[] args ) throws IOException {
		HackerFX hackerFX = new HackerFX( args );
		hackerFX.process();
		
 	}
}
