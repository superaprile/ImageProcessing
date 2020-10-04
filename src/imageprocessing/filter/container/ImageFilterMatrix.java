package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterMatrix.ImageFilterMatrixSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterMatrix extends ImageFilterRaster<ImageFilterMatrixSettings> {

	public ImageFilterMatrix() {
		super(ImageFilterMatrixSettings.class, "Matrix");
	}

	@Override
	public void filterImagePixel(ImageFilterMatrixSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		if (filterSettings.getFilterChannel() == 0) imagePixelColor.mulColorChannelHookR(filterSettings.getFilterIntensity());
		if (filterSettings.getFilterChannel() == 1) imagePixelColor.mulColorChannelHookG(filterSettings.getFilterIntensity());
		if (filterSettings.getFilterChannel() == 2) imagePixelColor.mulColorChannelHookB(filterSettings.getFilterIntensity());
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
			optionSliderDefault = 0.5,
			optionSliderMax = 1,
			optionSliderMin = 0,
			optionSliderStep = 0.05
		)
		@Getter
		private double filterIntensity;

	}


}
