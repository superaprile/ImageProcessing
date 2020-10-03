package imageprocessing.accessor;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import imageprocessing.accessor.structure.ImageChange;
import imageprocessing.accessor.structure.ImageColor;
import lombok.Getter;

/**
 * Implementazione dell'accessor di immagini utilizzando una buffered image
 */
public class ImageAccessorBuffered implements ImageAccessor {

	/**
	 * La buffered image che viene modificata
	 */
	@Getter
	private final BufferedImage imageBuffer;

	/**
	 * La lista di modifiche presenti nei vari batch
	 */
	private final LinkedList<ImageChange> imageChanges;

	/**
	 * Inizializza l'accessor con l'immagine specificata
	 *
	 * @param imageBuffer	La buffered image che viene acceduta
	 */
	public ImageAccessorBuffered(BufferedImage imageBuffer) {
		this.imageChanges = new LinkedList<>();
		this.imageBuffer = imageBuffer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageColor getImagePixel(int imagePixelX, int imagePixelY) {
		// Verifico che le coordinate siano dentro l'immagine
		if (hasImagePixel(imagePixelX, imagePixelY)) {
			return new ImageColor(imageBuffer.getRGB(imagePixelX, imagePixelY));
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setImagePixel(int imagePixelX, int imagePixelY, ImageColor imagePixelColor, String batch) {
		// Verifico che le coordinate siano dentro l'immagine
		if (hasImagePixel(imagePixelX, imagePixelY)) {
			imageChanges.add(new ImageChange(batch, imagePixelX, imagePixelY, imagePixelColor.colorCompact()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commitImagePixels(String batch) {
		// Ottengo l'iteratore a tutte le modifiche dell'immagine
		Iterator<ImageChange> imageChangesIterator = imageChanges.iterator();
		// Iterato l'iteratore delle modifiche
		while (imageChangesIterator.hasNext()) {
			ImageChange imageChange = imageChangesIterator.next();
			// Controllo che la modifica sia nel batch specificat
			if (batch == null || batch.equals(imageChange.getChangeBatch())) {
				// Effettuo la modifica sull'immagine
				imageBuffer.setRGB(
					imageChange.getChangePixelX(),
					imageChange.getChangePixelY(),
					imageChange.getChangePixelColor()
				);
				// Rimuovo la modifica dal batch
				imageChangesIterator.remove();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] getImageDimensions() {
		return new int[] { imageBuffer.getWidth(), imageBuffer.getHeight() };
	}

}
