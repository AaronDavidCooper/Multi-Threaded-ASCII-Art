import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiProcessor {
	
	private BufferedImage originalImage;
	private List<Object> pixelBrightness = new ArrayList<Object>();        
	private List<Object> hexColor = new ArrayList<Object>(); 
	private List<Object> asciiPixels = new ArrayList<Object>();
	
    public MultiProcessor (BufferedImage originalImage){
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
        
        // Character values used to replace brightness value
        String asciiSwap = "`^\\\",:;Il!i~+_-?][}{1)(|\\\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
        float convertValue = (float)255 / (asciiSwap.length()-1); // 255/66 = 3.8636363636 brightness per character   
        
        // Multithreading support
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Worker[] workers = new Worker[10];
        int workloadPerThread = imageData.length / workers.length;        
   
        for (int k = 0; k < workers.length; k++) {
            int startAt = k * workloadPerThread;
            int endAt = startAt + workloadPerThread;
            if(k != workers.length -1) {
            	workers[k] = new Worker(k, startAt, endAt, imageData, asciiSwap, convertValue);
            } else {
            	workers[k] = new Worker(k, startAt, imageData.length, imageData, asciiSwap, convertValue); // last worker picks up workload + any remainder
            }
        }
        
        // Populating lists with workers workload
        try {
			List<Future<Map>> allResults = threadPool.invokeAll(Arrays.asList(workers));
			for (int m = 0; m < allResults.size(); m++) {
				Map<String, List<Object>> workerResult = allResults.get(m).get();
				for(Map.Entry<String, List<Object>> entry : workerResult.entrySet()){
					if(entry.getKey().equals("brightness")) {
						pixelBrightness.addAll(entry.getValue());
					} else if (entry.getKey().equals("hexcolor")) {
						hexColor.addAll(entry.getValue());
					} else if (entry.getKey().equals("ascii")) {
						asciiPixels.addAll(entry.getValue());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e2) {
			e2.printStackTrace();
		}
        threadPool.shutdown();        
        
        System.out.println("ASCII SIZE = " +asciiPixels.size());
        System.out.println("HEXCOLOR SIZE = " + hexColor.size());        
    }

	public List<Object> getHexColor() {
		return hexColor;
	}


	public List<Object> getAsciiPixels() {
		return asciiPixels;
	}
}
