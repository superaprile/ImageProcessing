package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterFading.ImageFilterSepiaSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterFading extends ImageFilterRaster<ImageFilterSepiaSettings> {

	public ImageFilterFading() {
		super(ImageFilterSepiaSettings.class, "Fading");
	}

	@Override
	public void filterImagePixel(ImageFilterSepiaSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		imagePixelColor.setColorChannelA(filterSettings.getFilterFading());
	}

	public static class ImageFilterSepiaSettings extends ImageFilterSettings {
		@ImageFilterOption(
			optionTitle = "Trasparenza",
			optionDescription = "Intensità livello di trasparenza"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.5,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.05
		)
		@Getter
		private double filterFading;
	}

}
