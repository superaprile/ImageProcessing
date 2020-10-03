package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterSepia.ImageFilterSepiaSettings;

public class ImageFilterSepia extends ImageFilterRaster<ImageFilterSepiaSettings> {

	public ImageFilterSepia() {
		super(ImageFilterSepiaSettings.class, "Seppia");
	}

	@Override
	public void filterImagePixel(ImageFilterSepiaSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

		int imagePixelR = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_R);
		int imagePixelG = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_G);
		int imagePixelB = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_B);
	
		// https://stackoverflow.com/questions/1061093/how-is-a-sepia-tone-created
		int outputChannelR = (int) ((imagePixelR * .393) + (imagePixelG * .769) + (imagePixelB * .189));
		int outputChannelG = (int) ((imagePixelR * .349) + (imagePixelG * .686) + (imagePixelB * .168));
		int outputChannelB = (int) ((imagePixelR * .272) + (imagePixelG * .534) + (imagePixelB * .131));
		
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R,  outputChannelR);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, outputChannelG);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, outputChannelB);
	}

	public static class ImageFilterSepiaSettings extends ImageFilterSettings {

	}

}
