package imageprocessing.engine;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessorBuffered;
import imageprocessing.filter.ImageFilter;
import imageprocessing.filter.option.ImageFilterSettings;
import imageprocessing.filters.ImageFilterAberration;
import imageprocessing.filters.ImageFilterBorder;
import imageprocessing.filters.ImageFilterContrast;
import imageprocessing.filters.ImageFilterEdge;
import imageprocessing.filters.ImageFilterExample;
import imageprocessing.filters.ImageFilterFlag;
import imageprocessing.filters.ImageFilterFlip;
import imageprocessing.filters.ImageFilterGreyscale;
import imageprocessing.filters.ImageFilterInverter;
import imageprocessing.filters.ImageFilterMatrix;
import imageprocessing.filters.ImageFilterMirror;
import imageprocessing.filters.ImageFilterSepia;
import imageprocessing.filters.ImageFilterVignette;
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
		new ImageFilterInverter(),
		new ImageFilterMatrix(),
		new ImageFilterMirror(),
		new ImageFilterSepia(),
		new ImageFilterVignette(),
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
