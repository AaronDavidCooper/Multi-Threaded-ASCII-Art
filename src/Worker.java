import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class Worker implements Callable<Map>  {	
	private int id;
	private int startAt;
	private int endAt;
	private int[] imageData;
	private String asciiSwap;
	private float convertValue;
		
	public Worker(int k, int startAt, int endAt, int[] imageData, String asciiSwap, float convertValue) {
		this.id = k;
		this.startAt = startAt;
		this.endAt = endAt;
		this.imageData = imageData;
		this.asciiSwap = asciiSwap;
		this.convertValue = convertValue;
	}

	@Override
	public Map call() throws Exception {
        List<Object> pixelBrightness = new ArrayList<Object>();        
        List<Object> hexColor = new ArrayList<Object>(); 
        List<Object> asciiPixels = new ArrayList<Object>();
        
        for(int i = startAt; i < endAt; i++) {
        	Color pixel = new Color(imageData[i]);
        	String hex = String.format("#%02X%02X%02X", pixel.getRed(), pixel.getGreen(), pixel.getBlue());  
        	int brightness = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;  
        	int ascii = (int)(brightness / convertValue);  
    
        	pixelBrightness.add(brightness);
        	hexColor.add(hex);
        	asciiPixels.add(asciiSwap.charAt(ascii));
        }
        
        Map<String, List<Object>> workComplete = new HashMap<String, List<Object>>();        
        workComplete.put("brightness", pixelBrightness);
        workComplete.put("hexcolor", hexColor);
        workComplete.put("ascii", asciiPixels);
        
        return workComplete;
	}
}
