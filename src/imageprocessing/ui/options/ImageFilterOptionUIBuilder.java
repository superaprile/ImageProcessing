package imageprocessing.ui.options;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import imageprocessing.filter.option.ImageFilterOption;
import imageprocessing.filter.option.ImageFilterSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ImageFilterOptionUIBuilder {

	private static final ImageFilterOptionUI<?, ?>[] imageFilterOptionUIS = {
		new ImageFilterOptionUISlider(),
		new ImageFilterOptionUIFlags(),
		new ImageFilterOptionUIModes(),
		new ImageFilterOptionUIColor(),
		new ImageFilterOptionUIText(),
	};

	public static VBox buildFor(ImageFilterSettings filterSettings) {

		VBox optionsPanel = new VBox();
		optionsPanel.setSpacing(20);

		Class<?> filterSettingsClass = filterSettings.getClass();
		for (Field filterSettingField : filterSettingsClass.getDeclaredFields()) {
			filterSettingField.setAccessible(true);

			if (filterSettingField.isAnnotationPresent(ImageFilterOption.class)) {

				ImageFilterOption filterSettingOption = filterSettingField.getAnnotation(ImageFilterOption.class);

				VBox optionPanel = new VBox();
				optionPanel.getStyleClass().add("ip-option-panel");
				optionPanel.setPadding(new Insets(10));
				optionPanel.setSpacing(15);

				VBox optionPanelHeader = new VBox();

				Label optionPanelTitle = new Label();
				optionPanelTitle.setText(filterSettingOption.optionTitle());
				optionPanelTitle.getStyleClass().add("ip-option-title");
				optionPanelHeader.getChildren().add(optionPanelTitle);

				Label optionPanelDescription = new Label();
				optionPanelDescription.setText(filterSettingOption.optionDescription());
				optionPanelDescription.getStyleClass().add("ip-option-description");
				optionPanelHeader.getChildren().add(optionPanelDescription);

				optionPanel.getChildren().add(optionPanelHeader);

				for (ImageFilterOptionUI<?, ?> imageFilterOptionUI : imageFilterOptionUIS) {
					if (filterSettingField.isAnnotationPresent(imageFilterOptionUI.getClassAnnotation())) {
						Node imageFilterOptionNode = imageFilterOptionUI.buildFor(
							filterSettingField.getAnnotation(imageFilterOptionUI.getClassAnnotation()),
							(filterOptionValue) -> {
								try {
									Class<?> filterOptionClassObtained = filterOptionValue.getClass();
									Class<?> filterOptionClassExpected = filterSettingField.getType();
									if (filterOptionClassObtained.isArray() && !filterOptionClassExpected.isArray()) {
										filterSettingField.set(filterSettings, Array.get(filterOptionValue, 0));
									} else {
										filterSettingField.set(filterSettings, filterOptionValue);
									}
								} catch (Exception exception) {
									exception.printStackTrace();
								}
							}
						);
						optionPanel.getChildren().add(imageFilterOptionNode);
						break;
					}
				}

				optionsPanel.getChildren().add(optionPanel);
			}
		}

		return optionsPanel;
	}

}
