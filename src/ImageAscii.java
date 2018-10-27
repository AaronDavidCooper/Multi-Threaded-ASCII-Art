

import java.util.List;

public class ImageAscii {
	
	List<String> hexColor;
	List<Character> pixelAscii;
	int width;
	
	public ImageAscii() {
		
	}
	
	

	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public List<String> getHexColor() {
		return hexColor;
	}



	public void setHexColor(List<String> hexColor) {
		this.hexColor = hexColor;
	}



	public List<Character> getPixelAscii() {
		return pixelAscii;
	}

	public void setPixelAscii(List<Character> pixelAscii) {
		this.pixelAscii = pixelAscii;
	}



	@Override
	public String toString() {
		return "ImageAscii [hexColor=" + hexColor + ", pixelAscii=" + pixelAscii + ", width=" + width + "]";
	}
	
	
	
}
