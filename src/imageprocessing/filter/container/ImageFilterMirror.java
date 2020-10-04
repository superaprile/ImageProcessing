package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterMirror.ImageFilterMirrorSettings;
import imageprocessing.filter.option.ImageFilterSettings;

public class ImageFilterMirror extends ImageFilterRaster<ImageFilterMirrorSettings> {

	public ImageFilterMirror() {
		super(ImageFilterMirrorSettings.class, "Specchio");
	}

	@Override
	public void filterImagePixel(ImageFilterMirrorSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

		double imageHalf = filterImage.getImageDimensionW() / 2D;

		if (imagePixelX > imageHalf) {
			imagePixelColor.setColor(filterImage.getImagePixel((int) (imageHalf - (imagePixelX - imageHalf)), imagePixelY));
		}

	}

	public static class ImageFilterMirrorSettings extends ImageFilterSettings {

	}

}
