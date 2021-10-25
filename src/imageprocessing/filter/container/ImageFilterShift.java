package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterShift.ImageFilterShiftSettings;
import imageprocessing.filter.option.ImageFilterSettings;

public class ImageFilterShift extends ImageFilterRaster<ImageFilterShiftSettings> {

	public ImageFilterShift() {
		super(ImageFilterShiftSettings.class, "Shift");
	}

	@Override
	public void filterImagePixel(ImageFilterShiftSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		int channelB = imagePixelColor.getColorChannelR();
		int channelR = imagePixelColor.getColorChannelG();
		int channelG = imagePixelColor.getColorChannelB();

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, channelR);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, channelG);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, channelB);
	}

	public static class ImageFilterShiftSettings extends ImageFilterSettings {}

}
