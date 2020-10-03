package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterMatrix.ImageFilterMatrixSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterMatrix extends ImageFilterRaster<ImageFilterMatrixSettings> {

	public ImageFilterMatrix() {
		super(ImageFilterMatrixSettings.class, "Matrix");
	}

	@Override
	public void filterImagePixel(ImageFilterMatrixSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		if (filterSettings.getFilterChannel() == 0) imagePixelColor.addColorChannelHook(ImageColor.COLOR_CHANNEL_R, (int) filterSettings.getFilterIntensity());
		if (filterSettings.getFilterChannel() == 1) imagePixelColor.addColorChannelHook(ImageColor.COLOR_CHANNEL_G, (int) filterSettings.getFilterIntensity());
		if (filterSettings.getFilterChannel() == 2) imagePixelColor.addColorChannelHook(ImageColor.COLOR_CHANNEL_B, (int) filterSettings.getFilterIntensity());
	}

	public static class ImageFilterMatrixSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Canale",
			optionDescription = "Canale su cui applicare il filtro"
		)
		@ImageFilterOptionModes(
			optionModeDefault = 0,
			optionModesNames = { "Rosso", "Verde", "Blu" }
		)
		@Getter
		private int filterChannel;

		@ImageFilterOption(
			optionTitle = "Canale",
			optionDescription = "Canale su cui applicare il filtro"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 20,
			optionSliderMax = 0xFF,
			optionSliderMin = 0x00
		)
		@Getter
		private double filterIntensity;

	}


}
