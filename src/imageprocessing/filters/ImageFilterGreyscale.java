package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import lombok.Getter;
import imageprocessing.filters.ImageFilterGreyscale.ImageFilterGreyscaleSettings;


public class ImageFilterGreyscale extends ImageFilterRaster<ImageFilterGreyscaleSettings> {

	public ImageFilterGreyscale() {
		super(ImageFilterGreyscaleSettings.class, "Greyscale");
	}

	@Override
	public void filterImagePixel(ImageFilterGreyscaleSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		int channelR = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_R);
		int channelG = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_G);
		int channelB = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_B);

		//Formula found on https://www.rapidtables.com/convert/image/rgb-to-grayscale.html
		int channelValue = 0;
		switch (filterSettings.getFilterModes()) {
			case 0: channelValue = (int) (0.2126 * channelR + 0.7152 * channelG + 0.0722 * channelB); break;
			case 1: channelValue = (int) (0.2126 * channelR + 0.7152 * channelG + 0.0722 * channelB); break;
			case 2: channelValue = (int) (0.2126 * channelR + 0.7152 * channelG + 0.0722 * channelB); break;
		}

		//On greyscale each channel has the same value because R = G = B
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, channelValue);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, channelValue);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, channelValue);
	}

	public static class ImageFilterGreyscaleSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Tipologia",
			optionDescription = "Tipologia della scala di grigi"
		)
		@ImageFilterOptionModes(
			optionModeDefault = 1,
			optionModesNames = { "(R+G+B) / 3", "0.2126R + 0.7152G + 0.0722B", " 0.299R + 0.587G + 0.114B" }
		)
		@Getter
		private int filterModes;

	}


}
