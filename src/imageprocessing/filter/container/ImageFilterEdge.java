package imageprocessing.filter.container;

import java.util.Objects;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterEdge.ImageFilterEdgeSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterEdge extends ImageFilterRaster<ImageFilterEdgeSettings> {

	public ImageFilterEdge() {
		super(ImageFilterEdgeSettings.class, "Edge");
	}

	@Override
	public void filterImagePixel(ImageFilterEdgeSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		ImageColor[] imagePixelsNear = new ImageColor[] {
			filterImage.getImagePixel(imagePixelX + 1, imagePixelY + 1),
			filterImage.getImagePixel(imagePixelX - 1, imagePixelY - 1),
			filterImage.getImagePixel(imagePixelX + 1, imagePixelY - 1),
			filterImage.getImagePixel(imagePixelX - 1, imagePixelY + 1)
		};

		double imagePixelColorDistance = 0;
		for (ImageColor imagePixelNear : imagePixelsNear) {
			if (Objects.nonNull(imagePixelNear)) {
				imagePixelColorDistance = Math.max(imagePixelColorDistance, imagePixelColor.colorDistance(imagePixelNear));
			}
		}

		if (imagePixelColorDistance > filterSettings.getFilterThreshold()) {
			imagePixelColor.colorInvert();
		}
	}

	public static class ImageFilterEdgeSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Soglia",
			optionDescription = "Soglia distanza del colore"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 50,
			optionSliderMin = 0x00,
			optionSliderMax = 0xFF
		)
		@Getter
		private double filterThreshold;

	}

}
