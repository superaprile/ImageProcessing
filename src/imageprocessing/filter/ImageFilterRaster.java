package imageprocessing.filter;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.option.ImageFilterSettings;

/**
 * Rappresenta un filtro che viene eseguito su ogni pixel
 *
 * @param <S> Le impostazioni del filtro
 */
public abstract class ImageFilterRaster<S extends ImageFilterSettings> extends ImageFilter<S> {

	/**
	 * {@inheritDoc}
	 */
	public ImageFilterRaster(Class<S> filterSettings, String filterName) {
		super(filterSettings, filterName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterImage(S filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		// Il totale di pixel processati
		long imagePixelCount = 0;
		// Itero tutti i pixel presenti nell'immagine (x < width, y < height)
		for (int imagePixelX = 0; imagePixelX < filterImage.getImageDimensionW(); imagePixelX++) {
			for (int imagePixelY = 0; imagePixelY < filterImage.getImageDimensionH(); imagePixelY++) {
				// Ottengo il colore del pixel alla posizione
				ImageColor imagePixelColor = filterImage.getImagePixel(imagePixelX, imagePixelY);
				// Richiamo l'implementazione del filtro
				filterImagePixel(filterSettings, filterImage, imagePixelX, imagePixelY, imagePixelColor);
				// Imposto il colore del pixel modificato
				filterImage.setImagePixel(imagePixelX, imagePixelY, imagePixelColor);
				// Segnalo lo stato di progressione del filtro
				filterProgress.accept(++imagePixelCount / (double) filterImage.getImageDimension());
			}
		}
	}

	/**
	 * Filtra il colore del pixel alla posizione
	 *
	 * @param filterSettings	Le impostazioni del filtro
	 * @param filterImage		L'immagine da filtrare
	 *
	 * @param imagePixelX		La posizione X del pixel
	 * @param imagePixelY		La posizione Y del pixel
	 *
	 * @param imagePixelColor	Il colore del pixel
	 */
	public abstract void filterImagePixel(S filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor);

}
