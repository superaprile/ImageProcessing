package imageprocessing.filter.container;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.container.ImageFilterVignette.ImageFilterVignetteSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIColor.ImageFilterOptionColor;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterVignette extends ImageFilter<ImageFilterVignetteSettings> {

	public ImageFilterVignette() {
		super(ImageFilterVignetteSettings.class, "Vignetta");
	}

	@Override
	protected void filterImage(ImageFilterVignetteSettings filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		double imageCenterX = filterImage.getImageDimensionW() / 2D;
		double imageCenterY = filterImage.getImageDimensionH() / 2D;

		BiFunction<Double, Double, Double> computeDistance = (imagePointX, imagePointY) -> {
			return Math.sqrt(
				Math.pow(Math.abs(imagePointX - imageCenterX), 2) +
				Math.pow(Math.abs(imagePointY - imageCenterY), 2)
			);
		};

		ImageColor vignetteColor = filterSettings.getFilterColor();

		double imagePixelDistanceMax = computeDistance.apply(0D, 0D);

		for (int imagePixelX = 0; imagePixelX < filterImage.getImageDimensionW(); imagePixelX++) {
			for (int imagePixelY = 0; imagePixelY < filterImage.getImageDimensionH(); imagePixelY++) {

				double imagePixelDistance = computeDistance.apply((double) imagePixelX, (double) imagePixelY);
				double imagePixelDistanceNormal = Math.pow(imagePixelDistance / imagePixelDistanceMax, filterSettings.getFilterIntensity());

				vignetteColor.setColorChannelA(imagePixelDistanceNormal);

				ImageColor imagePixelColor = filterImage.getImagePixel(imagePixelX, imagePixelY);
				imagePixelColor.addColor(vignetteColor, true);

				filterImage.setImagePixel(imagePixelX, imagePixelY, imagePixelColor);
			}
		}
	}

	public static class ImageFilterVignetteSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Intensità",
			optionDescription = "Intensità della vignettatura"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 5,
			optionSliderMin = 0,
			optionSliderMax = 10,
			optionSliderStep = 0.5
		)
		@Getter
		private double filterIntensity;

		@ImageFilterOption(
			optionTitle = "Colore",
			optionDescription = "Colore vignettatura"
		)
		@ImageFilterOptionColor(
			optionColorDefault = 0x000000
		)
		@Getter
		private ImageColor filterColor;

	}

}
