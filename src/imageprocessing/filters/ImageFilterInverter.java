package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterInverter.ImageFilterInverterSettings;


public class ImageFilterInverter extends ImageFilterRaster<ImageFilterInverterSettings> {

	public ImageFilterInverter() {
		super(ImageFilterInverterSettings.class, "Inverter");
	}

	@Override
	public void filterImagePixel(ImageFilterInverterSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		imagePixelColor.colorInvert();
	}

	public static class ImageFilterInverterSettings extends ImageFilterSettings {

	}


}
