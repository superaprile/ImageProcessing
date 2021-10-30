package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterSaturate.ImageFilterSaturationSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterSaturate extends ImageFilterRaster<ImageFilterSaturationSettings> {

	public ImageFilterSaturate() {
		super(ImageFilterSaturationSettings.class, "Saturate");
	}

	@Override
	public void filterImagePixel(ImageFilterSaturationSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		int channelR = imagePixelColor.getColorChannelR() + (int) (filterSettings.filterSaturation * 255D);
		int channelG = imagePixelColor.getColorChannelG() + (int) (filterSettings.filterSaturation * 255D);
		int channelB = imagePixelColor.getColorChannelB() + (int) (filterSettings.filterSaturation * 255D);

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, channelR);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, channelG);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, channelB);
	}

	public static class ImageFilterSaturationSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Saturazione",
			optionDescription = "Intensità della saturazione"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.5,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.1
		)
		@Getter
		private double filterSaturation;

	}


}
