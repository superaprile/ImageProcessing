package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.ui.options.ImageFilterOptionUIColor.ImageFilterOptionColor;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ImageFilterOptionUIColor extends ImageFilterOptionUI<ImageFilterOptionColor, ImageColor> {

	public ImageFilterOptionUIColor() {
		super(ImageFilterOptionColor.class, ImageColor.class);
	}

	@Override
	protected Node build(ImageFilterOptionColor annotation, Consumer<ImageColor> listener) {

		ColorPicker colorpicker = new ColorPicker();
		colorpicker.setMaxWidth(Double.MAX_VALUE);
		colorpicker.setPrefHeight(25);
		colorpicker.setMinHeight(25);

		colorpicker.valueProperty().addListener(($1, $2, $3) -> {
			Color color = colorpicker.getValue();

			ImageColor colorImage = new ImageColor(
				(int) (color.getOpacity() 	* 0xFF),
				(int) (color.getRed() 		* 0xFF),
				(int) (color.getGreen() 	* 0xFF),
				(int) (color.getBlue() 		* 0xFF)
			);

			if (!annotation.optionColorAlpha()) {
				colorImage.setColorChannel(
					ImageColor.COLOR_CHANNEL_A, 0xFF
				);
			}

			listener.accept(colorImage);
		});

		if (annotation.optionColorDefault() >= 0) {
			ImageColor colorDefault = new ImageColor(
				annotation.optionColorDefault()
			);
			colorpicker.setValue(colorDefault.toColor());
		} else {
			colorpicker.setValue(Color.BLACK);
		}

		return colorpicker;
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ImageFilterOptionColor {

		public boolean optionColorAlpha() default false;

		public int optionColorDefault();

	}

}
