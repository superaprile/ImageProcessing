package imageprocessing.utils;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import lombok.Getter;

public class ImageSnapshots {

	private final LinkedList<BufferedImage> snapshots;

	@Getter
	private final int snapshotMax;

	@Getter
	private int snapshotIndex;

	public ImageSnapshots() {
		this.snapshots = new LinkedList<>();
		this.snapshotIndex = -1;
		this.snapshotMax = 100;
	}

	public void snapshotMake(BufferedImage image) {

		snapshots.subList(snapshotIndex + 1, snapshots.size()).clear();

		snapshots.addLast(ImageUtils.cloneImage(image));
		snapshotIndex++;

		if (snapshots.size() > snapshotMax) {
			snapshots.removeFirst();
		}
	}

	public boolean snapshotHasPositionPrevious() {
		return snapshotIndex - 1 >= 0;
	}

	public boolean snapshotHasPositionNext() {
		return snapshotIndex + 1 < snapshots.size();
	}

	public void snapshotPositionNext() {
		if (snapshotHasPositionNext()) {
			snapshotIndex++;
		}
	}

	public void snapshotPositionPrevious() {
		if (snapshotHasPositionPrevious()) {
			snapshotIndex--;
		}
	}

	public void snapshotPositionReset() {
		snapshotIndex = snapshots.size() - 1;
	}

	public void snapshotClear() {
		snapshotIndex = -1;
		snapshots.clear();
	}

	public BufferedImage snapshotGet() {
		return ImageUtils.cloneImage(
			snapshots.get(snapshotIndex)
		);
	}


}
