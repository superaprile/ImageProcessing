package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.ui.options.ImageFilterOptionUIModes.ImageFilterOptionModes;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ImageFilterOptionUIModes extends ImageFilterOptionUI<ImageFilterOptionModes, Integer> {

	public ImageFilterOptionUIModes() {
		super(ImageFilterOptionModes.class, Integer.class);
	}

	@Override
	protected Node build(ImageFilterOptionModes annotation, Consumer<Integer> listener) {
		VBox modesContainer = new VBox();
		modesContainer.setSpacing(5);

		String[] modeNames = annotation.optionModesNames();
		int modeDefault = annotation.optionModeDefault();

		final ToggleGroup modeGroup = new ToggleGroup();

		for (int modeIndex = 0; modeIndex < modeNames.length; modeIndex++) {

			RadioButton modeButton = new RadioButton();
			modeButton.setSelected(modeIndex == modeDefault);
			modeButton.setText(modeNames[modeIndex]);
			modeButton.setToggleGroup(modeGroup);

			final int modeIndexFinal = modeIndex;
			modeButton.selectedProperty().addListener((valueProperty, valueOld, valueNew) -> {
				if (valueNew) listener.accept(modeIndexFinal);
			});

			modesContainer.getChildren().add(modeButton);
		}

		return modesContainer;
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ImageFilterOptionModes {

		public String[] optionModesNames();

		public int optionModeDefault() default 0;

	}

}
