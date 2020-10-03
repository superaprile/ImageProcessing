package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterInverter.ImageFilterGreyscaleSettings;


public class ImageFilterInverter extends ImageFilterRaster<ImageFilterGreyscaleSettings> {

	public ImageFilterInverter() {
		super(ImageFilterGreyscaleSettings.class, "Inverter");
	}
	
	@Override
	public void filterImagePixel(ImageFilterGreyscaleSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		
		int channelR = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_R);
		int channelG = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_G);
		int channelB = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_B);

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, 255 - channelR);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, 255 - channelG);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, 255 - channelB);

		
	}

	public static class ImageFilterGreyscaleSettings extends ImageFilterSettings {

	}


}
