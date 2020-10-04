package imageprocessing.filter.container;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.container.ImageFilterBorder.ImageFilterBorderSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import lombok.Getter;

public class ImageFilterBorder extends ImageFilter<ImageFilterBorderSettings> {

	public ImageFilterBorder() {
		super(ImageFilterBorderSettings.class, "Border");
	}

	@Override
	protected void filterImage(ImageFilterBorderSettings filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		
		int borderChannelR = (int) filterSettings.getFilterBordWidthRedSlider();
		int borderChannelG = (int) filterSettings.getFilterBordWidthGreenSlider();
		int borderChannelB = (int) filterSettings.getFilterBordWidthBlueSlider();
		
		int borderWidthX =  (int) ((filterSettings.getFilterBordWidthXSlider() * filterImage.getImageDimensionW()) / 100);
		int borderWidthY =  (filterSettings.filterBordWidthModes == 0) ? borderWidthX :  (int) (filterSettings.getFilterBordWidthYSlider() * filterImage.getImageDimensionH()) / 100;
		
		ImageColor borderColor = new ImageColor(borderChannelR, borderChannelG, borderChannelB);
		
		//Horizontal Border
		for (int x = 0; x < filterImage.getImageDimensionW(); x++) {
			for (int offset = 0; offset < borderWidthX; offset++) {
				filterImage.setImagePixel(x, filterImage.getImageDimensionH() - offset, borderColor);
				filterImage.setImagePixel(x, offset, borderColor);
			}
		}
		
		//Vertical Border
		for (int y = 0; y < filterImage.getImageDimensionH(); y++) {
			for (int offset = 0; offset < borderWidthY; offset++) {
				filterImage.setImagePixel(filterImage.getImageDimensionW() - offset, y, borderColor);
				filterImage.setImagePixel(offset, y, borderColor);
			}
		}
		
	}

	public static class ImageFilterBorderSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Dimensione del bordo in proporzione alla X",
			optionDescription = "Dimensione del bordo in proporzione alla larghezza dell`immagine"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 1,
			optionSliderMin = 0,
			optionSliderMax = 100,
			optionSliderStep = 1
		)
		@Getter
		private double filterBordWidthXSlider;
		
		@ImageFilterOption(
			optionTitle = "Dimensione del bordo in proporzione alla Y",
			optionDescription = "Dimensione del bordo in proporzione all`altezza dell`immagine"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 1,
			optionSliderMin = 0,
			optionSliderMax = 100,
			optionSliderStep = 1
		)
		@Getter
		private double filterBordWidthYSlider;
		
		@ImageFilterOption(
			optionTitle = "Strategia dimensione bordo",
			optionDescription = "Quale strategia per la dimensione del bordo vuoi adottare?"
		)
		@ImageFilterOptionModes(
			optionModeDefault = 0,
			optionModesNames = { "Tutti i bordi con la stessa dimensione (scelta dalla X)", "Bordi con dimensioni diverse" }
		)
		@Getter
		private int filterBordWidthModes;
		
		@ImageFilterOption(
			optionTitle = "Canale Rosso",
			optionDescription = ""
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 255,
			optionSliderMin = 0,
			optionSliderMax = 255
			//optionSliderStep = 1
		)
		@Getter
		private double filterBordWidthRedSlider;
		
		@ImageFilterOption(
			optionTitle = "Canale Verde",
			optionDescription = ""
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 255,
			optionSliderMin = 0,
			optionSliderMax = 255
		)
		@Getter
		private double filterBordWidthGreenSlider;
		
		@ImageFilterOption(
			optionTitle = "Canale Blu",
			optionDescription = ""
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 255,
			optionSliderMin = 0,
			optionSliderMax = 255
			//optionSliderStep = 1
		)
		@Getter
		private double filterBordWidthBlueSlider;

	}

}
