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
		imagePixelColor.colorInvert();
	}

	public static class ImageFilterGreyscaleSettings extends ImageFilterSettings {

	}


}
