package imageprocessing.engine;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessorBuffered;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.container.ImageFilterAberration;
import imageprocessing.filter.container.ImageFilterBorder;
import imageprocessing.filter.container.ImageFilterContrast;
import imageprocessing.filter.container.ImageFilterEdge;
import imageprocessing.filter.container.ImageFilterExample;
import imageprocessing.filter.container.ImageFilterFading;
import imageprocessing.filter.container.ImageFilterFlag;
import imageprocessing.filter.container.ImageFilterFlip;
import imageprocessing.filter.container.ImageFilterGreyscale;
import imageprocessing.filter.container.ImageFilterInverter;
import imageprocessing.filter.container.ImageFilterMatrix;
import imageprocessing.filter.container.ImageFilterMirror;
import imageprocessing.filter.container.ImageFilterSepia;
import imageprocessing.filter.container.ImageFilterVignette;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.ui.options.ImageFilterOptionUIBuilder;
import imageprocessing.utils.ImageSnapshots;
import imageprocessing.utils.ImageUtils;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ImageEngine {

	public static final ImageFilter<?>[] IMAGE_FILTERS = {
		new ImageFilterAberration(),
		new ImageFilterContrast(),
		new ImageFilterEdge(),
		new ImageFilterExample(),
		new ImageFilterFlag(),
		new ImageFilterFlip(),
		new ImageFilterGreyscale(),
		new ImageFilterVignette(),
		new ImageFilterInverter(),
		new ImageFilterMatrix(),
		new ImageFilterMirror(),
		new ImageFilterFading(),
		new ImageFilterSepia(),
		new ImageFilterBorder()
	};

	private ImageFilter<?> imageFilter;

	private ImageSnapshots imageSnapshots;

	private ImageAccessorBuffered imageCurrent;

	private ImageFilterSettings imageFilterSettings;

	private Thread imageFilterThread;

	public ImageEngine() {
		this.imageSnapshots = new ImageSnapshots();
		this.imageFilter = IMAGE_FILTERS[0];
	}

	public void initializeImage(BufferedImage image) {
		imageCurrent = new ImageAccessorBuffered(image);
		imageSnapshots.snapshotClear();
		imageSnapshots.snapshotMake(image);
	}

	public BufferedImage currentImage() {
		if (Objects.nonNull(imageCurrent)) {
			return imageCurrent.getImageBuffer();
		}
		return null;
	}

	public Node setFilter(int imageFilterIndex) {
			this.imageFilter = IMAGE_FILTERS[imageFilterIndex];

			imageFilterSettings = ImageUtils.newInstance(imageFilter.getFilterSettings());
			VBox imageFilterSettingsNode = ImageFilterOptionUIBuilder.buildFor(imageFilterSettings);

			return imageFilterSettingsNode;
	}

	public void applyFilter(Consumer<Double> imageFilterProgress, Consumer<Long> imageFilterTime) {
		if (imageFilterThread == null) {
			imageFilterThread = new Thread(() -> {
				long imageFilterTimer = System.currentTimeMillis();
				imageFilter.filterImageRaw(imageFilterSettings, imageCurrent, imageFilterProgress);
				imageCurrent.commitImagePixels();
				imageSnapshots.snapshotMake(imageCurrent.getImageBuffer());
				imageFilterThread = null;
				imageFilterTimer = System.currentTimeMillis() - imageFilterTimer;
				imageFilterTime.accept(imageFilterTimer);
			});
			imageFilterThread.setDaemon(true);
			imageFilterThread.start();
		}
	}

	public boolean hasPrevious() {
		return imageSnapshots.snapshotHasPositionPrevious();
	}

	public boolean hasNext() {
		return imageSnapshots.snapshotHasPositionNext();
	}

	public void next() {
		imageSnapshots.snapshotPositionNext();
		imageCurrent = new ImageAccessorBuffered(imageSnapshots.snapshotGet());
	}

	public void prev() {
		imageSnapshots.snapshotPositionPrevious();
		imageCurrent = new ImageAccessorBuffered(imageSnapshots.snapshotGet());
	}

	public int index() {
		return imageSnapshots.getSnapshotIndex();
	}

}
