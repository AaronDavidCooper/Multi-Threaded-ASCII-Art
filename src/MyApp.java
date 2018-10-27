import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyApp {

	public static void main(String[] args) throws IOException {
		
		long start = System.currentTimeMillis();
		System.out.println("Starting...");
    	BufferedImage originalImage = null;
    	originalImage = ImageIO.read(new File("src/highres.jpg"));
    	
    	
    	MultiProcessor processor = new MultiProcessor(originalImage);
    	//OriginalProcessor processor2 = new OriginalProcessor(originalImage);		
		processor.process();
		//processor2.process();
		
		long end = System.currentTimeMillis();		
		System.out.println("Time taken = " + (end - start));
		
	}
}
