package imageprocessing.accessor;

import imageprocessing.accessor.structure.ImageColor;

/**
 * Questa interfaccia rappsenta il superset di funzioni
 * di lettura e scrittura di un immagine
 */
public interface ImageAccessor {

	/**
	 * Ritorna il colore del pixel dell'immagine alla posizione specificata
	 *
	 * @param imagePixelX	La coordinata X del pixel
	 * @param imagePixelY	La coordinata Y del pixel
	 *
	 * @return Il colore del pixel (o null se le coordinate sono errate)
	 */
	public ImageColor getImagePixel(int imagePixelX, int imagePixelY);

	/**
	 * Ritorna il colore del pixel dell'immagine alla posizione specificata
	 *
	 * @param imagePixelIndex	L'indice del pixel
	 *
	 * @return Il colore del pixel (o null se le coordinate sono errate)
	 */
	public default ImageColor getImagePixel(int imagePixelIndex) {
		int[] imagePixelCoordinate = getPixelCoordinate(imagePixelIndex);
		return getImagePixel(imagePixelCoordinate[0], imagePixelCoordinate[1]);
	}

	/**
	 * Imposta il colore del pixel dell'immagine alla posizione specificata,
	 * Questo metodo non effettua la modifica sull'immagine finchè non viene
	 * eseguito il commit del batch su cui è salvata.
	 *
	 * @param imagePixelX		La coordinata X del pixel
	 * @param imagePixelY		La coordinata Y del pixel
	 * @param imagePixelColor	Il nuovo colore del pixel
	 * @param batch				Il batch su cui salvare questa modifica
	 *
	 * @see #commitImagePixels(String)
	 */
	public void setImagePixel(int imagePixelX, int imagePixelY, ImageColor imagePixelColor, String batch);

	/**
	 * Imposta il colore del pixel dell'immagine alla posizione specificata,
	 * Questo metodo non effettua la modifica sull'immagine finchè non viene
	 * eseguito il commit del batch su cui è salvata.
	 *
	 * @param imagePixelIndex	L'indice del pixel
	 * @param imagePixelColor	Il nuovo colore del pixel
	 * @param batch				Il batch su cui salvare questa modifica
	 *
	 * @see #commitImagePixels(String)
	 */
	public default void setImagePixel(int imagePixelIndex, ImageColor imagePixelColor, String batch) {
		int[] imagePixelCoordinate = getPixelCoordinate(imagePixelIndex);
		setImagePixel(imagePixelCoordinate[0], imagePixelCoordinate[1], imagePixelColor, batch);
	}

	/**
	 * Imposta il colore del pixel dell'immagine alla posizione specificata,
	 * Questo metodo non effettua la modifica sull'immagine finchè non viene
	 * eseguito il commit del batch su cui è salvata.
	 *
	 * Questo metodo utilizza il batch di default
	 *
	 * @param imagePixelX		La coordinata X del pixel
	 * @param imagePixelY		La coordinata Y del pixel
	 * @param imagePixelColor	Il nuovo colore del pixel
	 *
	 * @see #commitImagePixels(String)
	 */
	public default void setImagePixel(int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {
		setImagePixel(imagePixelX, imagePixelY, imagePixelColor, "default");
	}

	/**
	 * Imposta il colore del pixel dell'immagine alla posizione specificata,
	 * Questo metodo non effettua la modifica sull'immagine finchè non viene
	 * eseguito il commit del batch su cui è salvata.
	 *
	 * Questo metodo utilizza il batch di default
	 *
	 * @param imagePixelIndex	L'indice del pixel
	 * @param imagePixelColor	Il nuovo colore del pixel
	 *
	 * @see #commitImagePixels(String)
	 */
	public default void setImagePixel(int imagePixelIndex, ImageColor imagePixelColor) {
		int[] imagePixelCoordinate = getPixelCoordinate(imagePixelIndex);
		setImagePixel(imagePixelCoordinate[0], imagePixelCoordinate[1], imagePixelColor);
	}

	/**
	 * Effettua tutte le modifiche salvate nel batch specificato
	 *
	 * @param batch		Il batch da eseguire (o null per eseguirli tutti)
	 */
	public void commitImagePixels(String batch);

	/**
	 * Effettua tutte le modifiche salvate nel batch di default
	 *
	 * @see #commitImagePixels(String)
	 */
	public default void commitImagePixels() {
		commitImagePixels("default");
	}

	/**
	 * Verifica se le coordinate sono all'interno dell'immagine
	 *
	 * @param imagePixelX	La coordinata X del pixel
	 * @param imagePixelY	La coordinata Y del pixel
	 *
	 * @return Lo stato della verifica
	 */
	public default boolean hasImagePixel(int imagePixelX, int imagePixelY) {
		return imagePixelX >= 0 && imagePixelX < getImageDimensionW()
		    && imagePixelY >= 0 && imagePixelY < getImageDimensionH();
	}

	/**
	 * Verifica se l'indice del pixel è all'interno dell'immagine
	 *
	 * @param imagePixelIndex	L'indice del pixel
	 *
	 * @return Lo stato della verifica
	 */
	public default boolean hasImagePixel(int imagePixelIndex) {
		return imagePixelIndex < getImageDimension();
	}

	/**
	 * Ritorna l'indice del pixel
	 *
	 * @param imagePixelX	La coordinata X del pixel
	 * @param imagePixelY	La coordinata Y del pixel
	 *
	 * @return L'indice del pixel
	 */
	public default int getPixelIndex(int imagePixelX, int imagePixelY) {
		return (imagePixelY * getImageDimensionW()) + imagePixelX;
	}

	/**
	 * Ritorna le coordinate del pixel in base al suo indice
	 *
	 * @param  imagePixelIndex	L'indice del pixel
	 *
	 * @return Le coordinate del pixel [x, y]
	 */
	public default int[] getPixelCoordinate(int imagePixelIndex) {
		return new int[] {
			imagePixelIndex % getImageDimensionW(),
			imagePixelIndex / getImageDimensionW()
		};
	}

	/**
	 * Ritorna la dimensione dell'immagine
	 *
	 * @return la dimensione dell'immagine [width, height]
	 */
	public int[] getImageDimensions();

	/**
	 * Ritorna il numero totale di pixel dell'immagine
	 *
	 * @return Il numero totale di pixel dell'immagine
	 */
	public default int getImageDimension() {
		return getImageDimensionW() *
			   getImageDimensionH();
	}

	/**
	 * Ritorna la larghezza dell'immagine
	 *
	 * @return La larghezza dell'immagine
	 */
	public default int getImageDimensionW() {
		return getImageDimensions()[0];
	}

	/**
	 * Ritorna l'altezza dell'immagine
	 *
	 * @return L'altezza dell'immagine
	 */
	public default int getImageDimensionH() {
		return getImageDimensions()[1];
	}

}
