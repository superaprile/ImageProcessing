package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterSepia.ImageFilterSepiaSettings;
import imageprocessing.filter.option.ImageFilterSettings;

public class ImageFilterSepia extends ImageFilterRaster<ImageFilterSepiaSettings> {

	public ImageFilterSepia() {
		super(ImageFilterSepiaSettings.class, "Seppia");
	}

	@Override
	public void filterImagePixel(ImageFilterSepiaSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

		int imagePixelR = imagePixelColor.getColorChannelR();
		int imagePixelG = imagePixelColor.getColorChannelG();
		int imagePixelB = imagePixelColor.getColorChannelB();

		// https://stackoverflow.com/questions/1061093/how-is-a-sepia-tone-created
		int outputChannelR = (int) ((imagePixelR * .393) + (imagePixelG * .769) + (imagePixelB * .189));
		int outputChannelG = (int) ((imagePixelR * .349) + (imagePixelG * .686) + (imagePixelB * .168));
		int outputChannelB = (int) ((imagePixelR * .272) + (imagePixelG * .534) + (imagePixelB * .131));

		imagePixelColor.setColorChannelR(outputChannelR);
		imagePixelColor.setColorChannelG(outputChannelG);
		imagePixelColor.setColorChannelB(outputChannelB);
	}

	public static class ImageFilterSepiaSettings extends ImageFilterSettings {

	}

}
