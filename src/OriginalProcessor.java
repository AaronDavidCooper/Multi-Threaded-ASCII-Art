import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OriginalProcessor {
	
	private BufferedImage originalImage;
	private List<Integer> pixelBrightness = new ArrayList<Integer>();        
    private List<String> hexColor = new ArrayList<String>(); 
    private List<Character> asciiPixels = new ArrayList<Character>();
	
    public OriginalProcessor (BufferedImage originalImage){
    	this.originalImage = originalImage;
    }
    
    public void process() {    	
    	// Downscale resolution by a quarter
    	int w = originalImage.getWidth() / 1;
        int h = originalImage.getHeight() / 1;
    	BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    	Graphics g = image.createGraphics();
    	g.drawImage(originalImage, 0, 0, w, h, null);
    	g.dispose();
		
    	// Create array of pixel data
    	int w2 = image.getWidth();
        int h2 = image.getHeight();        
        int[] imageData = image.getRGB(0, 0, w2, h2, null, 0, w2);
        
        // Populates 2 arraylists, one for image brightness (ascii chars) & the other their hex color
        for(int i = 0; i < imageData.length; i++) {
        	Color pixel = new Color(imageData[i]);
        	String hex = String.format("#%02X%02X%02X", pixel.getRed(), pixel.getGreen(), pixel.getBlue());  
        	int brightness = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;  
        	hexColor.add(hex);
        	pixelBrightness.add(brightness);
        }
        
        // Character values used to replace brightness value
        String asciiSwap = "`^\\\",:;Il!i~+_-?][}{1)(|\\\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
        float convertValue = (float)255 / (asciiSwap.length()-1); // 255/66 = 3.8636363636 brightness per character        
        
        // populating with above char strings based on brightness value
        for(int j = 0; j < pixelBrightness.size(); j++) {
        	int ascii = (int)(pixelBrightness.get(j) / convertValue);  
        	asciiPixels.add(asciiSwap.charAt(ascii));
        }    		
		
        // Creates pojo and loads it with the data
        ImageAscii output = new ImageAscii();        
        output.setHexColor(hexColor);
        output.setPixelAscii(asciiPixels);
        output.setWidth(w2);
        
        System.out.println("ASCII SIZE = " +asciiPixels.size());
        System.out.println("HEXCOLOR SIZE = " + hexColor.size());
    }

	public List<String> getHexColor() {
		return hexColor;
	}

	public List<Character> getAsciiPixels() {
		return asciiPixels;
	}
}
