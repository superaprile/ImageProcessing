package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.ui.options.ImageFilterOptionUIText.ImageFilterOptionText;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class ImageFilterOptionUIText extends ImageFilterOptionUI<ImageFilterOptionText, String> {

	public ImageFilterOptionUIText() {
		super(ImageFilterOptionText.class, String.class);
	}

	@Override
	protected Node build(ImageFilterOptionText annotation, Consumer<String> listener) {
		TextField textfield = new TextField();
		textfield.setMaxWidth(Double.MAX_VALUE);
		textfield.setPrefHeight(20);

		textfield.textProperty().addListener(($1, $2, $3) -> {
			String textfieldValue = textfield.getText();
			if (textfieldValue.length() > annotation.optionTextMaxlen()) {
				textfield.setText(textfieldValue.substring(0, annotation.optionTextMaxlen()));
			}
		});

		textfield.textProperty().addListener(($1, $2, $3) -> {
			listener.accept(textfield.getText());
		});

		textfield.setText(annotation.optionTextDefault());

		return textfield;
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ImageFilterOptionText {

		public String optionTextDefault() default "";

		public int optionTextMaxlen();

	}

}
