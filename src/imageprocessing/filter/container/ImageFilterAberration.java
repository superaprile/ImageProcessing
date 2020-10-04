package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterAberration.ImageFilterAberrationSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterAberration extends ImageFilterRaster<ImageFilterAberrationSettings> {

	public ImageFilterAberration() {
		super(ImageFilterAberrationSettings.class, "Aberrazione Cromatica");
	}

	@Override
	public void filterImagePixel(ImageFilterAberrationSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

		ImageColor imagePixelR = filterImage.getImagePixel((int) (imagePixelX + filterSettings.getFilterOffset()), imagePixelY);
		ImageColor imagePixelL = filterImage.getImagePixel((int) (imagePixelX - filterSettings.getFilterOffset()), imagePixelY);

		if (imagePixelR != null) imagePixelColor.mulColorChannelHookR(filterSettings.getFilterIntensity());
		if (imagePixelL != null) imagePixelColor.mulColorChannelHookB(filterSettings.getFilterIntensity());

	}

	public static class ImageFilterAberrationSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Intesità",
			optionDescription = "Intensità dell'aberrazione cromatica"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.5,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.1
		)
		@Getter
		private double filterIntensity;

		@ImageFilterOption(
			optionTitle = "Scostamento",
			optionDescription = "Scosamento dell'aberrazione"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 50,
			optionSliderMin = 1,
			optionSliderMax = 1000
		)
		@Getter
		private double filterOffset;

	}

}
