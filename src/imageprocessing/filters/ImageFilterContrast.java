package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterContrast.ImageFilterContrastSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterContrast extends ImageFilterRaster<ImageFilterContrastSettings> {

	public ImageFilterContrast() {
		super(ImageFilterContrastSettings.class, "Contrast");
	}
	
	@Override
	public void filterImagePixel(ImageFilterContrastSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		
		int contrast = (int) (filterSettings.getFilterContrast());
		int contrastFactor = (259 * (contrast + 255)) / (255 * (259 - contrast));

		int channelR = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_R);
		int channelG = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_G);
		int channelB = imagePixelColor.getColorChannel(ImageColor.COLOR_CHANNEL_B);

		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, (contrastFactor * (channelR - 128)) + 128);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, (contrastFactor * (channelG - 128)) + 128);
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, (contrastFactor * (channelB - 128)) + 128);
	}

	public static class ImageFilterContrastSettings extends ImageFilterSettings {
			@ImageFilterOption(
				optionTitle = "Contrast Level",
				optionDescription = "Higher is more luminous"
			)
			
			@ImageFilterOptionSlider(
				optionSliderDefault = 128,
				optionSliderMin = 0,
				optionSliderMax = 128,
				optionSliderStep = 1
			)
			
			@Getter
			private double filterContrast;
	}


}
