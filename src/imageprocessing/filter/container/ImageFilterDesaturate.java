package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterDesaturate.ImageFilterDesaturationSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterDesaturate extends ImageFilterRaster<ImageFilterDesaturationSettings> {

	public ImageFilterDesaturate() {
		super(ImageFilterDesaturationSettings.class, "Desaturate");
	}

	@Override
	public void filterImagePixel(ImageFilterDesaturationSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		int channelR = imagePixelColor.getColorChannelR() - (int) (filterSettings.filterDesaturation * 255D);
		int channelG = imagePixelColor.getColorChannelG() - (int) (filterSettings.filterDesaturation * 255D);
		int channelB = imagePixelColor.getColorChannelB() - (int) (filterSettings.filterDesaturation * 255D);

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, channelR);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, channelG);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, channelB);
	}

	public static class ImageFilterDesaturationSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Desaturazione",
			optionDescription = "Intensità della desaturazione"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.5,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.1
		)
		@Getter
		private double filterDesaturation;

	}


}
