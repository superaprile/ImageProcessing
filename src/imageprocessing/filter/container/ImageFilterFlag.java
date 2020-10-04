package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterFlag.ImageFilterFlagSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterFlag extends ImageFilterRaster<ImageFilterFlagSettings> {

	public ImageFilterFlag() {
		super(ImageFilterFlagSettings.class, "Bandiera");
	}

	@Override
	public void filterImagePixel(ImageFilterFlagSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

		switch (filterSettings.getFilterFlag()) {
			case 0: {
				int imagePortionW = (int) Math.ceil(filterImage.getImageDimensionW() / 3D);

				if (imagePixelX >= imagePortionW * 0 && imagePixelX < imagePortionW * 1) {
					imagePixelColor.addColorChannelHookG((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelX >= imagePortionW * 1 && imagePixelX < imagePortionW * 2) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookG((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookB((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelX >= imagePortionW * 2 && imagePixelX < imagePortionW * 3) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
				}

				break;
			}
			case 1: {
				int imagePortionW = (int) Math.ceil(filterImage.getImageDimensionW() / 3D);

				if (imagePixelX >= imagePortionW * 0 && imagePixelX < imagePortionW * 1) {
					imagePixelColor.addColorChannelHookB((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelX >= imagePortionW * 1 && imagePixelX < imagePortionW * 2) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookG((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookB((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelX >= imagePortionW * 2 && imagePixelX < imagePortionW * 3) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
				}

				break;
			}
			case 2: {

				int imagePortionH = (int) Math.ceil(filterImage.getImageDimensionW() / 6D);

				if (imagePixelY >= imagePortionH * 0 && imagePixelY < imagePortionH * 1) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelY >= imagePortionH * 1 && imagePixelY < imagePortionH * 2) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookG((int) (0x80 * filterSettings.getFilterIntensity()));
				}
				if (imagePixelY >= imagePortionH * 2 && imagePixelY < imagePortionH * 3) {
					imagePixelColor.addColorChannelHookR((int) (0xFF * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookG((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelY >= imagePortionH * 3 && imagePixelY < imagePortionH * 4) {
					imagePixelColor.addColorChannelHookG((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelY >= imagePortionH * 4 && imagePixelY < imagePortionH * 5) {
					imagePixelColor.addColorChannelHookB((int) (0xFF * filterSettings.getFilterIntensity()));
				}
				if (imagePixelY >= imagePortionH * 5 && imagePixelY < imagePortionH * 6) {
					imagePixelColor.addColorChannelHookR((int) (0xCC * filterSettings.getFilterIntensity()));
					imagePixelColor.addColorChannelHookB((int) (0xCC * filterSettings.getFilterIntensity()));
				}

				break;
			}
		}
	}

	public static class ImageFilterFlagSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Tipologia",
			optionDescription = "Tipo di bandiera da utilizzare"
		)
		@ImageFilterOptionModes(
			//optionModeDefault = 0,
			optionModesNames = { "Italia", "Francia", "LGBTQ+" }
		)
		@Getter
		private int filterFlag;

		@ImageFilterOption(
			optionTitle = "Intensità",
			optionDescription = "Intensità dei colori della bandiera"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 0.2,
			optionSliderMin = 0,
			optionSliderMax = 1,
			optionSliderStep = 0.05
		)
		@Getter
		private double filterIntensity;
	}
}
