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
public abstract class ImageFilterIndexed<S extends ImageFilterSettings> extends ImageFilter<S> {

	/**
	 * {@inheritDoc}
	 */
	public ImageFilterIndexed(Class<S> filterSettings, String filterName) {
		super(filterSettings, filterName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterImage(S filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		// Itero tutti i pixel presenti nell'immagine in sequenza di indice
		for (int imagePixelIndex = 0; imagePixelIndex < filterImage.getImageDimension(); imagePixelIndex++) {
			// Ottengo il colore del pixel alla posizione
			ImageColor imagePixelColor = filterImage.getImagePixel(imagePixelIndex);
			// Richiamo l'implementazione del filtro
			filterImagePixel(filterSettings, filterImage, imagePixelIndex, imagePixelColor);
			// Imposto il colore del pixel modificato
			filterImage.setImagePixel(imagePixelIndex, imagePixelColor);
			// Segnalo lo stato di progressione del filtro
			filterProgress.accept(imagePixelIndex / (double) filterImage.getImageDimension());
		}
	}

	/**
	 * Filtra il colore del pixel alla posizione
	 *
	 * @param filterSettings	Le impostazioni del filtro
	 * @param filterImage		L'immagine da filtrare
	 * @param imagePixelIndex	L'indice del pixel
	 * @param imagePixelColor	Il colore del pixel
	 */
	public abstract void filterImagePixel(S filterSettings, ImageAccessor filterImage, int imagePixelIndex, ImageColor imagePixelColor);

}
