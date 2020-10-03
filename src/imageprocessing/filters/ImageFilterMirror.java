package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterMirror.ImageFilterMirrorSettings;

public class ImageFilterMirror extends ImageFilterRaster<ImageFilterMirrorSettings> {

	public ImageFilterMirror() {
		super(ImageFilterMirrorSettings.class, "Specchio");
	}

	@Override
	public void filterImagePixel(ImageFilterMirrorSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
	
		double half = filterImage.getImageDimensionW() / 2D;
		
		if (imagePixelX > half)
			imagePixelColor.setColor(filterImage.getImagePixel((int) (half - (imagePixelX - half)), imagePixelY));
		

	}

	public static class ImageFilterMirrorSettings extends ImageFilterSettings {		

	}

}
