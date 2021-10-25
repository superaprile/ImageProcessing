package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterBAW.ImageFilterBAWSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterBAW extends ImageFilterRaster<ImageFilterBAWSettings> {

	public ImageFilterBAW() {
		super(ImageFilterBAWSettings.class, "Black & White");
	}

	@Override
	public void filterImagePixel(ImageFilterBAWSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		int channelR = imagePixelColor.getColorChannelR();
		int channelG = imagePixelColor.getColorChannelG();
		int channelB = imagePixelColor.getColorChannelB();

		double pixelColor = (channelR + channelG + channelB) / 255D;
		int pixelState = pixelColor >= filterSettings.getFilterThreshold() ? 255 : 0;

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, pixelState);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, pixelState);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, pixelState);
	}

	public static class ImageFilterBAWSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Soglia",
			optionDescription = "Intensità del bianco e nero"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.5,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.1
		)
		@Getter
		private double filterThreshold;

	}

}
