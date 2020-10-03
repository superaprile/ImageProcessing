package imageprocessing.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import imageprocessing.engine.ImageEngine;
import imageprocessing.filter.ImageFilter;
import imageprocessing.utils.ImageUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageFrame {

    @FXML
    private BorderPane ipRoot;

    @FXML
    private ComboBox<String> ipFilters;

    @FXML
    private ScrollPane ipFilterOptions;

    @FXML
    private ProgressBar ipProgressBar;

    @FXML
    private Label ipProgressLabel;

    @FXML
    private Button ipActionUndo;

    @FXML
    private Button ipActionRedo;

    @FXML
    private Button ipActionSave;

    @FXML
    private Button ipActionFilter;

    @FXML
    private StackPane ipImageWrapper;

    @FXML
    private Canvas ipImageView;

    @FXML
    private Label ipImageHint;

	private Stage ipFrameStage;

	private Scene ipFrameScene;

	private ImageEngine imageEngine;

	public ImageFrame() throws Exception {
		ImageUtils.newInstance(JFXPanel.class);

		imageEngine = new ImageEngine();

		URL frameXML = ImageFrame.class.getResource("/frame.fxml");
		URL frameCSS = ImageFrame.class.getResource("/frame.css");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(frameXML);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		Platform.runLater(() -> {

			// Aggiungo lo stile al frame
			List<String> ipRootStylesheet = ipRoot.getStylesheets();
			ipRootStylesheet.add(frameCSS.toExternalForm());

			ipFrameStage = new Stage();
			ipFrameStage.getIcons().add(new Image(
				ImageFrame.class.getResourceAsStream("/icon.png")
			));
			ipFrameStage.setTitle("Image Processing v1.0");

			ipFrameStage.setMinWidth(1000);
			ipFrameStage.setMinHeight(650);

			ipFrameScene = new Scene(ipRoot);
			ipFrameStage.setScene(ipFrameScene);

			/* ========================================================================== */
			/* Pulsante Salva */

			ipActionSave.setDisable(true);
			ipActionSave.setOnAction(($) -> {
				FileChooser ipFileChooser = new FileChooser();
				ipFileChooser.setInitialFileName("snapshot.png");
				ipFileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("PNG file (*.png)", "*.png")
				);

				File ipFileSave = ipFileChooser.showSaveDialog(ipFrameStage);

				if (Objects.nonNull(ipFileSave)) {
					BufferedImage ipImage = imageEngine.currentImage();
					ImageUtils.saveImage(ipFileSave, ipImage);
				}
			});

			/* ========================================================================== */
			/* Pulsanti Azione */

			// Task per controllare lo stato dei pulsanti azione
			Runnable ipActionCheck = () -> {
				ipActionUndo.setDisable(!imageEngine.hasPrevious());
				ipActionRedo.setDisable(!imageEngine.hasNext());
			};
			ipActionCheck.run();

			ipActionUndo.setOnAction(($) -> {
				imageEngine.prev();
				ipActionCheck.run();
				renderImage();
			});

			ipActionRedo.setOnAction(($) -> {
				imageEngine.next();
				ipActionCheck.run();
				renderImage();
			});

			/* ========================================================================== */
			/* Comboxbox */

			// Aggiungo il nome dei filtri alla combobox
			ObservableList<String> ipFiltersItems = ipFilters.getItems();
			Arrays.stream(ImageEngine.IMAGE_FILTERS)
				.map(ImageFilter::getFilterName)
				.forEach(ipFiltersItems::add);

			// Ottengo il modello della selezione della combobox
			SingleSelectionModel<String> ipFiltersSelection = ipFilters.getSelectionModel();

			// Ascolto l'evento del cambio dei valori della combobox
			ipFilters.valueProperty().addListener(($1, $2, $3) -> {
				Node ipFilterSettings = imageEngine.setFilter(
					ipFiltersSelection.getSelectedIndex()
				);
				ipFilterOptions.setContent(ipFilterSettings);
			});

			// Seleziono il primo elemento della combobox
			ipFiltersSelection.selectFirst();


			/* ========================================================================== */
			/* Azione Filtro */

			ipActionFilter.setDisable(true);
			ipActionFilter.setOnAction(($) -> {
				ipActionFilter.setDisable(true);

				imageEngine.applyFilter(
					(filterProgress) -> {
						if (ipProgressBar.getProgress() - filterProgress > 0.05) {
							Platform.runLater(() -> {
								ipProgressBar.setProgress(filterProgress);
								ipProgressLabel.setText(String.format("%.2f", filterProgress * 100D));
							});
						}
					},
					(filterTime) -> {
						Platform.runLater(() -> {
							ipProgressLabel.setText("Tempo impiegato: " + filterTime + "ms");
							ipProgressBar.setProgress(1.0);
							ipActionFilter.setDisable(false);

							renderImage();
							ipActionCheck.run();
						});
					}
				);

			});


			/* ========================================================================== */
			/* Listener Ridimensione */

			ChangeListener<? super Number> listenerResize = ($1, $2, $3) -> {
				ipImageView.setWidth(ipRoot.getWidth() - 300);
				ipImageView.setHeight(ipRoot.getHeight());
				renderImage();
			};
			ipRoot.heightProperty().addListener(listenerResize);
			ipRoot.widthProperty().addListener(listenerResize);
			listenerResize.changed(null, null, null);

			/* ========================================================================== */
			/* Listener Drag & Drop */

			ipImageWrapper.setOnDragOver((event) -> {
				Dragboard eventDragboard = event.getDragboard();
				if (event.getGestureSource() != ipImageWrapper && eventDragboard.hasFiles()) {
					event.acceptTransferModes(TransferMode.LINK);
				}
				event.consume();
			});

			ipImageWrapper.setOnDragDropped((event) -> {
				Dragboard eventDragboard = event.getDragboard();
				boolean eventDragboardSuccess = false;
				if (eventDragboard.hasFiles()) {
					List<File> eventDragboardFiles = eventDragboard.getFiles();
					if (eventDragboardFiles.size() == 1) {
						try {
							File eventDragboardImageFile = eventDragboardFiles.get(0);
							BufferedImage ipImageBuffer = ImageUtils.parseImage(eventDragboardImageFile);
							imageEngine.initializeImage(ipImageBuffer);

							renderImage();

							eventDragboardSuccess = true;
							ipActionSave.setDisable(false);
							ipActionFilter.setDisable(false);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}
				event.setDropCompleted(eventDragboardSuccess);
				event.consume();
			});

			ipImageWrapper.setOnDragEntered(($) -> {
				ipImageHint.getStyleClass().add("active");
			});

			ipImageWrapper.setOnDragExited(($) -> {
				ipImageHint.getStyleClass().remove("active");
			});

		});

	}

	private void renderImage() {
		GraphicsContext context = ipImageView.getGraphicsContext2D();

		BufferedImage ipImageBuffer = imageEngine.currentImage();
		if (ipImageBuffer != null) {

			WritableImage ipImageConverted = SwingFXUtils.toFXImage(ipImageBuffer, null);

			double imageHeight = ipImageBuffer.getHeight();
			double imageWidth = ipImageBuffer.getWidth();

			if (ipImageView.getWidth() < imageWidth && ipImageView.getWidth() < ipImageView.getHeight()) {
				imageWidth = ipImageView.getWidth();
				imageHeight = (ipImageBuffer.getHeight() * imageWidth) / ipImageBuffer.getWidth();
			} else if (ipImageView.getHeight() < imageHeight && ipImageView.getHeight() < ipImageView.getWidth()) {
				imageHeight = ipImageView.getHeight();
				imageWidth = (ipImageBuffer.getWidth() * imageHeight) / ipImageBuffer.getHeight();
			}

			if (ipImageView.getWidth() < imageWidth) {
				imageWidth = ipImageView.getWidth();
				imageHeight = (ipImageBuffer.getHeight() * imageWidth) / ipImageBuffer.getWidth();
			} else if (ipImageView.getHeight() < imageHeight) {
				imageHeight = ipImageView.getHeight();
				imageWidth = (ipImageBuffer.getWidth() * imageHeight) / ipImageBuffer.getHeight();
			}

			double y = (ipImageView.getHeight() - imageHeight) / 2;
			double x = (ipImageView.getWidth() - imageWidth) / 2;

			context.clearRect(0, 0, ipImageView.getWidth(), ipImageView.getHeight());
			context.drawImage(ipImageConverted, x, y, imageWidth, imageHeight);
		}

	}

	public void frameShow() {
		Platform.runLater(() -> {
			ipFrameStage.show();
		});
	}

}
