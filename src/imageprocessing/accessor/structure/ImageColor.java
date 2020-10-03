package imageprocessing.accessor.structure;

/**
 * Classe di rappresentazione di un colore.
 *
 * Un colore è composto da 4 canali ARGB (Alfa, Rosso, Verde, Blu)
 * Ogni canale è rappresentato da un byte che ha un range da 0 a 255 (0x00 - 0xFF)
 * Il colore può essere compattato in un intero (int = 4 byte RGBA)
 *
 * Il colore rappresentato da questa classe è diviso nei suoi canali,
 * ovvero ogni canale del colore è indirizzabile singolarmente.
 */
public class ImageColor {

	/** Posizione del canale alfa */
	public static final int COLOR_CHANNEL_A = 0;

	/** Posizione del canale rosso */
	public static final int COLOR_CHANNEL_R = 1;

	/** Posizione del canale verde */
	public static final int COLOR_CHANNEL_G = 2;

	/** Posizione del canale blu */
	public static final int COLOR_CHANNEL_B = 3;

	/**
	 * I canali del colore in formato RGBA
	 * Ogni canale ha un range massimo da 0 a 255 (0xFF)
	 */
	private final int[] colorChannels;

	/**
	 * Inizializza il colore
	 *
	 * @param colorCompact	Il colore compatto
	 */
	public ImageColor(int colorCompact) {
		colorChannels = colorExplode(colorCompact);
	}

	/**
	 * Inizializza il colore dai canali
	 *
	 * @param colorChannelA	Il canale alfa (0 - 255)
	 * @param colorChannelR Il canale rosso (0 - 255)
	 * @param colorChannelG Il canale verde (0 - 255)
	 * @param colorChannelB Il canale blu (0 - 255)
	 */
	public ImageColor(int colorChannelA, int colorChannelR, int colorChannelG, int colorChannelB) {
		colorChannels = new int[] { colorChannelA, colorChannelR, colorChannelG, colorChannelB };
	}

	/**
	 * Inizializza il colore dai canali
	 *
	 * @param colorChannelR Il canale rosso (0 - 255)
	 * @param colorChannelG Il canale verde (0 - 255)
	 * @param colorChannelB Il canale blu (0 - 255)
	 *
	 */
	public ImageColor(int colorChannelR, int colorChannelG, int colorChannelB) {
		this(0xFF, colorChannelR, colorChannelG, colorChannelB);
	}


	/**
	 * Aggiunge un valore al canale specificato.
	 * Il metodo utilizza la normalizzazione con {@link #colorChannelSpin(int)}
	 *
	 * @param colorChannel			L'indice del canale da modificare
	 * @param colorChannelValue		Il valore da aggiungere al canale
	 *
	 * @see #colorChannelSpin(int)
	 */
	public void addColorChannelSpin(int colorChannel, int colorChannelValue) {
		colorChannels[colorChannel] = colorChannelSpin(colorChannels[colorChannel] + colorChannelValue);
	}

	/**
	 * Aggiunge un valore al canale specificato.
	 * Il metodo utilizza la normalizzazione con {@link #colorChannelHook(int)}
	 *
	 * @param colorChannel			L'indice del canale da modificare
	 * @param colorChannelValue		Il valore da aggiungere al canale
	 *
	 * @see #colorChannelHook(int)
	 */
	public void addColorChannelHook(int colorChannel, int colorChannelValue) {
		colorChannels[colorChannel] = colorChannelHook(colorChannels[colorChannel] + colorChannelValue);
	}

	/**
	 * Imposta un valore al canale specificato
	 * Il metodo utilizza la normalizzazione con {@link #colorChannelHook(int)}
	 *
	 * @param colorChannel			L'indice del canale da modificare
	 * @param colorChannelValue		Il valore del canale
	 */
	public void setColorChannel(int colorChannel, int colorChannelValue) {
		colorChannels[colorChannel] = colorChannelHook(colorChannelValue);
	}

	/**
	 * Imposta il colore copiandolo da un altro
	 *
	 * @param color Il colore da copiare
	 */
	public void setColor(ImageColor color) {
		System.arraycopy(color.colorChannels, 0, colorChannels, 0, 4);
	}

	/**
	 * Imposta il valore di tutti i canali partendo da un colore compatto
	 *
	 * @param colorCompact	Il colore compatto
	 */
	public void setColor(int colorCompact) {
		int[] colorCompactChannels = colorExplode(colorCompact);
		for (int colorChannelIndex = 0; colorChannelIndex < colorChannels.length; colorChannelIndex++) {
			colorChannels[colorChannelIndex] = colorCompactChannels[colorChannelIndex];
		}
	}

	/**
	 * Ritorna il valore del canale specificato
	 *
	 * @param colorChannel	Il canale del colore
	 *
	 * @return Il valore del canale
	 */
	public int getColorChannel(int colorChannel) {
		return colorChannels[colorChannel];
	}

	/**
	 * Ritorna il valore del canale specificato
	 *
	 * @param colorChannel	Il canale del colore
	 *
	 * @return Il valore del canale normalizzato (0 - 1)
	 */
	public double getColorChannelNormal(int colorChannel) {
		return colorChannels[colorChannel] / 255D;
	}


	/**
	 * Normalizza il valore del canale.
	 * Utilizza la foruma {@code min < x < max}
	 *
	 * @param 	colorChannel	Il valore del canale da normalizzare
	 *
	 * @return	Il valore normalizzato
	 */
	public static int colorChannelHook(int colorChannel) {
		return colorChannel > 0xFF ? 0xFF
			 : colorChannel < 0x00 ? 0x00
			 : colorChannel;
	}

	/**
	 * Normalizza il valore del canale.
	 * Utilizza la foruma {@code x % max}
	 *
	 * @param 	colorChannel	Il valore del canale da normalizzare
	 *
	 * @return	Il valore normalizzato
	 */
	public static int colorChannelSpin(int colorChannel) {
		return colorChannel % 0xFF;
	}

	/**
	 * Compatta il colore
	 *
	 * @return Il colore compattato
	 *
	 * @see #colorCompact(int[])
	 */
	public int colorCompact() {
		return colorCompact(colorChannels);
	}

	/**
	 * Calcola la distanza fra questo colore e un colore di riferimento.
	 * La distanza fra due colori è dato dalla media dei delta dei canali RGB
	 *
	 * @param  color	Il colore di riferimento
	 *
	 * @return La distanza del colore
	 */
	public double colorDistance(ImageColor color) {
		double colorDistance = 0.0;
		colorDistance += Math.abs(getColorChannel(COLOR_CHANNEL_R) - color.getColorChannel(COLOR_CHANNEL_R));
		colorDistance += Math.abs(getColorChannel(COLOR_CHANNEL_G) - color.getColorChannel(COLOR_CHANNEL_G));
		colorDistance += Math.abs(getColorChannel(COLOR_CHANNEL_B) - color.getColorChannel(COLOR_CHANNEL_B));
		return colorDistance / 3D;
	}

	/**
	 * Inverte il colore
	 */
	public void colorInvert() {
		colorChannels[COLOR_CHANNEL_R] = 0xFF - colorChannels[COLOR_CHANNEL_R];
		colorChannels[COLOR_CHANNEL_G] = 0xFF - colorChannels[COLOR_CHANNEL_G];
		colorChannels[COLOR_CHANNEL_B] = 0xFF - colorChannels[COLOR_CHANNEL_B];
	}

	/**
	 * Esplode il colore nei suoi canali
	 *
	 * @param colorCompact	Il colore compatto
	 *
	 * @return I canali del colore
	 */
	public static int[] colorExplode(int colorCompact) {
		return new int[] {
			(colorCompact >> 24) & 0xFF, // A
			(colorCompact >> 16) & 0xFF, // R
			(colorCompact >>  8) & 0xFF, // G
			(colorCompact >>  0) & 0xFF, // B
		};
	}

	/**
	 * Compatta i canali del colore
	 *
	 * @param colorChannels		I canali del colore
	 *
	 * @return	Il colore compatto
	 */
	public static int colorCompact(int[] colorChannels) {
		return ((colorChannels[0] & 0xFF) << 24) // A
	         | ((colorChannels[1] & 0xFF) << 16) // R
	         | ((colorChannels[2] & 0xFF) << 8)  // G
	         | ((colorChannels[3] & 0xFF) << 0); // B
	}

	/**
	 * Visualizza il colore nel suo formato esadecimale 0xAARRGGBB
	 */
	@Override
	public String toString() {
		return String.format("0x%02X%02X%02X%02X - %s",
			getColorChannel(COLOR_CHANNEL_A),
			getColorChannel(COLOR_CHANNEL_R),
			getColorChannel(COLOR_CHANNEL_G),
			getColorChannel(COLOR_CHANNEL_B),
			colorCompact()
		);
	}

}
