package imageprocessing.filter.container;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.container.ImageFilterExample.ImageFilterExampleSettings;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIColor.ImageFilterOptionColor;
import imageprocessing.ui.options.ImageFilterOptionUIFlags.ImageFilterOptionFlags;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import imageprocessing.ui.options.ImageFilterOptionUIText.ImageFilterOptionText;
import lombok.Getter;

public class ImageFilterExample extends ImageFilter<ImageFilterExampleSettings> {

	public ImageFilterExample() {
		super(ImageFilterExampleSettings.class, "Example");
	}

	@Override
	protected void filterImage(ImageFilterExampleSettings filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		// Implementazione del filtro qui
	}

	public static class ImageFilterExampleSettings extends ImageFilterSettings {

		@ImageFilterOption(
			optionTitle = "Titolo Esempio",
			optionDescription = "Descrizione esempio"
		)
		@ImageFilterOptionSlider(
			optionSliderDefault = 50,
			optionSliderMin = 0x00,
			optionSliderMax = 0xFF
			//optionSliderStep = 1
		)
		@Getter
		private double filterSlider;

		@ImageFilterOption(
			optionTitle = "Titolo Esempio 2",
			optionDescription = "Descrizione esempio 2"
		)
		@ImageFilterOptionFlags(
			//optionFlagDefaults = { false, true, false },
			optionFlagNames = { "Flag1", "Flag2", "Flag3" }
		)
		@Getter
		private boolean[] filterCheckboxes;

		@ImageFilterOption(
			optionTitle = "Titolo Esempio 3",
			optionDescription = "Descrizione esempio 3"
		)
		@ImageFilterOptionModes(
			//optionModeDefault = 0,
			optionModesNames = { "Flag1", "Flag2", "Flag3" }
		)
		@Getter
		private int filterModes;

		@ImageFilterOption(
			optionTitle = "Titolo Esempio 4",
			optionDescription = "Descrizione esempio 4"
		)
		@ImageFilterOptionColor(
			optionColorDefault = 0xFF00FF
		)
		@Getter
		private ImageColor filterColor;

		@ImageFilterOption(
			optionTitle = "Titolo Esempio 5",
			optionDescription = "Descrizione esempio 5"
		)
		@ImageFilterOptionText(
			optionTextMaxlen = 20
		)
		@Getter
		private String filterText;

	}

}
