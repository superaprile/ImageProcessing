package imageprocessing.filters;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterFlip.ImageFilterFlipSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import lombok.Getter;

public class ImageFilterFlip extends ImageFilterRaster<ImageFilterFlipSettings> {

	public ImageFilterFlip() {
		super(ImageFilterFlipSettings.class, "Flip");
	}

	@Override
	public void filterImagePixel(ImageFilterFlipSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		if (filterSettings.getFilterFlip() == 0) {
			imagePixelColor.setColor(filterImage.getImagePixel((filterImage.getImageDimensionW() - 1) - imagePixelX, imagePixelY));
		}
		if (filterSettings.getFilterFlip() == 1) {
			imagePixelColor.setColor(filterImage.getImagePixel(imagePixelX, (filterImage.getImageDimensionH() - 1) - imagePixelY));
		}
	}

	public static class ImageFilterFlipSettings extends ImageFilterSettings {

			@ImageFilterOption(
				optionTitle = "Direzione",
				optionDescription = "Direzione in cui effettuare il flip"
			)
			@ImageFilterOptionModes(
				optionModeDefault = 0,
				optionModesNames = { "Orizzontale", "Verticale" }
			)
			@Getter
			private int filterFlip;

	}

}
