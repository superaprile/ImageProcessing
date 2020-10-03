package imageprocessing.ui.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

import imageprocessing.ui.options.ImageFilterOptionUISlider.ImageFilterOptionSlider;
import imageprocessing.utils.ImageUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ImageFilterOptionUISlider extends ImageFilterOptionUI<ImageFilterOptionSlider, Double> {

	public ImageFilterOptionUISlider() {
		super(ImageFilterOptionSlider.class, Double.class);
	}

	@Override
	protected Node build(ImageFilterOptionSlider annotation, Consumer<Double> listener) {
		HBox sliderContainer = new HBox();
		sliderContainer.setAlignment(Pos.CENTER);
		sliderContainer.setSpacing(10);

		Label sliderDisplay = new Label();

		Slider slider = new Slider();
		slider.setMin(annotation.optionSliderMin());
		slider.setMax(annotation.optionSliderMax());


		slider.valueProperty().addListener((valueProperty, valueOld, valueNew) -> {
			sliderDisplay.setText(String.format("%.2f%%", ImageUtils.map(valueNew.doubleValue(), slider.getMin(), slider.getMax(), 0, 100)));
			listener.accept(valueNew.doubleValue());
		});

		slider.setValue(annotation.optionSliderDefault());


		slider.setMajorTickUnit(annotation.optionSliderStep());
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);

		HBox.setHgrow(slider, Priority.ALWAYS);

		sliderContainer.getChildren()
			.addAll(sliderDisplay, slider);

		return sliderContainer;
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
