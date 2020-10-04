package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterFading.ImageFilterSepiaSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterFading extends ImageFilterRaster<ImageFilterSepiaSettings> {

	public ImageFilterFading() {
		super(ImageFilterSepiaSettings.class, "Fading");
	}

	@Override
	public void filterImagePixel(ImageFilterSepiaSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_A, (int) (filterSettings.getFilterFading()));
	}

	public static class ImageFilterSepiaSettings extends ImageFilterSettings {
			@ImageFilterOption(
				optionTitle = "Trasparenza",
				optionDescription = "Intensità livello di trasparenza"
			)
			@ImageFilterOptionSlider(
				optionSliderDefault = 50,
				optionSliderMin = 0x00,
				optionSliderMax = 0xFF
			)
			@Getter
			private double filterFading;
	}

}
