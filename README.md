<img align="right" src="https://i.imgur.com/vrVxlSe.png" height="200" width="200">

## Introduzione

Image Processing è un proof of concept applicativo che consente la creazione e prototipazione rapida di filtri immagine

## Creazione di un filtro

Per creare un filtro è necessario estendere la clasee ImageFilter.
Sono presenti due classi di utility: ImageFilterRaster e ImageFilterIndexed

**Esempio**:

```java
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
		private boolean[] filterModes;

	}
}
```

**Aggiunta filtro al registro**:

> Guarda [Registro]()

```java
	public static final ImageFilter<?>[] IMAGE_FILTERS = {
		new ImageFilterEdge(),
		new ImageFilterMatrix(),
		new ImageFilterAberration(),
		new ImageFilterFlip(),
        // ... //
	};
```

[Sorgenti]()
- [Java-FX]()
- [Lombok]()
