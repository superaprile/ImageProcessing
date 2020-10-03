package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import javafx.scene.Node;
import javafx.scene.control.Slider;

public class ImageFilterOptionUISlider extends ImageFilterOptionUI<ImageFilterOptionSlider, Double> {

	public ImageFilterOptionUISlider() {
		super(ImageFilterOptionSlider.class, Double.class);
	}

	@Override
	protected Node build(ImageFilterOptionSlider annotation, Consumer<Double> listener) {
		Slider slider = new Slider();

		slider.valueProperty().addListener((valueProperty, valueOld, valueNew) -> {
			listener.accept(valueNew.doubleValue());
		});

		slider.setValue(annotation.optionSliderDefault());
		slider.setMin(annotation.optionSliderMin());
		slider.setMax(annotation.optionSliderMax());

		slider.setMajorTickUnit(annotation.optionSliderStep());
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);

		return slider;
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ImageFilterOptionSlider {

		public double optionSliderMin();

		public double optionSliderMax();

		public double optionSliderStep() default 1;

		public double optionSliderDefault();

	}




}
