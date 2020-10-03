package imageprocessing.filters;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterExample.ImageFilterExampleSettings;
import imageprocessing.ui.options.ImageFilterOptionUIFlags.ImageFilterOptionFlags;
import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
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
			optionDescription = "Descrizione sempio"
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
			optionDescription = "Descrizione sempio 2"
		)
		@ImageFilterOptionFlags(
			//optionFlagDefaults = { false, true, false },
			optionFlagNames = { "Flag1", "Flag2", "Flag3" }
		)
		@Getter
		private boolean[] filterCheckboxes;

		@ImageFilterOption(
			optionTitle = "Titolo Esempio 3",
			optionDescription = "Descrizione sempio 3"
		)
		@ImageFilterOptionModes(
			//optionModeDefault = 0,
			optionModesNames = { "Flag1", "Flag2", "Flag3" }
		)
		@Getter
		private int filterModes;

	}

}
