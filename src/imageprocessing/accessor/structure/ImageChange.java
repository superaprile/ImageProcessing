package imageprocessing.accessor.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe di dati che rappresnta una modifica all'immagine
 */
@AllArgsConstructor
public class ImageChange {

	/**
	 * Il batch della modifica
	 */
	@Getter
	private final String changeBatch;

	/**
	 * Le coordinate del pixel da modificare
	 */
	@Getter
	private final int changePixelX, changePixelY;

	/**
	 * Il colore da modificare
	 */
	@Getter
	private final int changePixelColor;

}
