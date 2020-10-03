package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.ui.options.ImageFilterOptionUIFlags.ImageFilterOptionFlags;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class ImageFilterOptionUIFlags extends ImageFilterOptionUI<ImageFilterOptionFlags, boolean[]> {

	public ImageFilterOptionUIFlags() {
		super(ImageFilterOptionFlags.class, boolean[].class);
	}

	@Override
	protected Node build(ImageFilterOptionFlags annotation, Consumer<boolean[]> listener) {
		VBox flagsContainer = new VBox();
		flagsContainer.setSpacing(5);

		boolean[] flagDefaults = annotation.optionFlagDefaults();
		String[] flagNames = annotation.optionFlagNames();

		final boolean[] flagValues = new boolean[flagNames.length];

		for (int flagIndex = 0; flagIndex < flagNames.length; flagIndex++) {
			CheckBox flagCheckbox = new CheckBox();
			flagCheckbox.setText(flagNames[flagIndex]);
			flagsContainer.getChildren().add(flagCheckbox);

			final int flagIndexFinal = flagIndex;
			flagCheckbox.selectedProperty().addListener((valueProperty, valueOld, valueNew) -> {
				flagValues[flagIndexFinal] = valueNew;
				listener.accept(flagValues);
			});

			if (flagIndex < flagDefaults.length) {
				flagCheckbox.setSelected(flagDefaults[flagIndex]);
			}
		}

		return flagsContainer;
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ImageFilterOptionFlags {

		public String[] optionFlagNames();

		public boolean[] optionFlagDefaults() default {};

	}

}
