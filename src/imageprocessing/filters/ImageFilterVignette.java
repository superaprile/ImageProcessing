package imageprocessing.filters;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterVignette.ImageFilterVignetteSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterVignette extends ImageFilter<ImageFilterVignetteSettings> {

	public ImageFilterVignette() {
		super(ImageFilterVignetteSettings.class, "Vignetta");
	}

	@Override
	protected void filterImage(ImageFilterVignetteSettings filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		int cx = filterImage.getImageDimensionW() / 2;
		int cy = filterImage.getImageDimensionH() / 2;

		double maxdistance = Math.sqrt(Math.pow(Math.abs(0 - cx), 2) + Math.pow(Math.abs(0 - cy), 2));

		for (int imagePixelX = 0; imagePixelX < filterImage.getImageDimensionW(); imagePixelX++) {
			for (int imagePixelY = 0; imagePixelY < filterImage.getImageDimensionH(); imagePixelY++) {

				double distance = Math.sqrt(Math.pow(Math.abs(imagePixelX - cx), 2) + Math.pow(Math.abs(imagePixelY - cy), 2));

				double distanceNormal =  Math.pow(distance / maxdistance, filterSettings.getFilterSlider() / 10D);

				ImageColor color = filterImage.getImagePixel(imagePixelX, imagePixelY);

				for (int i = 1; i <= 3; i++)
					color.addColorChannelHook(i, (int) -(200D * distanceNormal));

				filterImage.setImagePixel(imagePixelX, imagePixelY, color);
			}
		}


	}

	public static class ImageFilterVignetteSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Titolo Esempio",
			optionDescription = "Descrizione sempio"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 50,
			optionSliderMin = 0,
			optionSliderMax = 100
			//optionSliderStep = 1
		)
		@Getter
		private double filterSlider;



	}

}
