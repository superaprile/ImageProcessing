package imageprocessing.accessor.structure;

import javafx.scene.paint.Color;

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
	 * @param colorChannelA	Il canale a (0 - 255)
	 * @param colorChannelR Il canale r (0 - 255)
	 * @param colorChannelG Il canale g (0 - 255)
	 * @param colorChannelB Il canale b (0 - 255)
	 */
	public ImageColor(int colorChannelA, int colorChannelR, int colorChannelG, int colorChannelB) {
		colorChannels = new int[] {
			colorChannelHook(colorChannelA),
			colorChannelHook(colorChannelR),
			colorChannelHook(colorChannelG),
			colorChannelHook(colorChannelB)
		};
	}

	/**
	 * Inizializza il colore dai canali
	 *
	 * @param colorChannelR Il canale r (0 - 255)
	 * @param colorChannelG Il canale g (0 - 255)
	 * @param colorChannelB Il canale b (0 - 255)
	 *
	 */
	public ImageColor(int colorChannelR, int colorChannelG, int colorChannelB) {
		this(0xFF, colorChannelR, colorChannelG, colorChannelB);
	}

	public void mulColorChannelSpin(int colorChannel, double colorChannelFactor) {
		setColorChannel(colorChannel, colorChannelHook((int) (getColorChannel(colorChannel) * colorChannelFactor)));
	}

	public void mulColorChannelSpinA(double colorChannelFactor) { mulColorChannelSpin(COLOR_CHANNEL_A, colorChannelFactor); }
	public void mulColorChannelSpinR(double colorChannelFactor) { mulColorChannelSpin(COLOR_CHANNEL_R, colorChannelFactor); }
	public void mulColorChannelSpinG(double colorChannelFactor) { mulColorChannelSpin(COLOR_CHANNEL_G, colorChannelFactor); }
	public void mulColorChannelSpinB(double colorChannelFactor) { mulColorChannelSpin(COLOR_CHANNEL_B, colorChannelFactor); }

	public void mulColorChannelHook(int colorChannel, double colorChannelFactor) {
		setColorChannel(colorChannel, colorChannelSpin((int) (getColorChannel(colorChannel) * colorChannelFactor)));
	}

	public void mulColorChannelHookA(double colorChannelFactor) { mulColorChannelHook(COLOR_CHANNEL_A, colorChannelFactor); }
	public void mulColorChannelHookR(double colorChannelFactor) { mulColorChannelHook(COLOR_CHANNEL_R, colorChannelFactor); }
	public void mulColorChannelHookG(double colorChannelFactor) { mulColorChannelHook(COLOR_CHANNEL_G, colorChannelFactor); }
	public void mulColorChannelHookB(double colorChannelFactor) { mulColorChannelHook(COLOR_CHANNEL_B, colorChannelFactor); }

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
		setColorChannel(colorChannel, colorChannelSpin(colorChannels[colorChannel] + colorChannelValue));
	}

	public void addColorChannelSpinA(int colorChannelValue) { addColorChannelSpin(COLOR_CHANNEL_A, colorChannelValue); }
	public void addColorChannelSpinR(int colorChannelValue) { addColorChannelSpin(COLOR_CHANNEL_R, colorChannelValue); }
	public void addColorChannelSpinG(int colorChannelValue) { addColorChannelSpin(COLOR_CHANNEL_G, colorChannelValue); }
	public void addColorChannelSpinB(int colorChannelValue) { addColorChannelSpin(COLOR_CHANNEL_B, colorChannelValue); }

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
		setColorChannel(colorChannel, colorChannelHook(colorChannels[colorChannel] + colorChannelValue));
	}

	public void addColorChannelHookA(int colorChannelValue) { addColorChannelHook(COLOR_CHANNEL_A, colorChannelValue); }
	public void addColorChannelHookR(int colorChannelValue) { addColorChannelHook(COLOR_CHANNEL_R, colorChannelValue); }
	public void addColorChannelHookG(int colorChannelValue) { addColorChannelHook(COLOR_CHANNEL_G, colorChannelValue); }
	public void addColorChannelHookB(int colorChannelValue) { addColorChannelHook(COLOR_CHANNEL_B, colorChannelValue); }

	/**
	 * Aggiunge a questo colore un colore
	 *
	 * @param color			Il colore d'aggiungere
	 * @param colorAlpha	Se l'intesità deve essere regolata dal canale alfa
	 */
	public void addColor(ImageColor color, boolean colorAlpha) {
		double colorIntensity = colorAlpha ? color.getColorChannel(COLOR_CHANNEL_A) / 255D : 1;
		addColorChannelHook(COLOR_CHANNEL_R, (int) (color.getColorChannel(COLOR_CHANNEL_R) * colorIntensity));
		addColorChannelHook(COLOR_CHANNEL_G, (int) (color.getColorChannel(COLOR_CHANNEL_G) * colorIntensity));
		addColorChannelHook(COLOR_CHANNEL_B, (int) (color.getColorChannel(COLOR_CHANNEL_B) * colorIntensity));
	}

	/**
	 * Imposta un valore al canale specificato
	 * Il metodo utilizza la normalizzazione con {@link #colorChannelHook(int)}
	 *
	 * @param colorChannel			L'indice del canale da modificare
	 * @param colorChannelValue		Il valore del canale (range 0-255)
	 */
	public void setColorChannel(int colorChannel, int colorChannelValue) {
		colorChannels[colorChannel] = colorChannelHook(colorChannelValue);
	}

	public void setColorChannelA(int colorChannelValue) { setColorChannel(COLOR_CHANNEL_A, colorChannelValue); }
	public void setColorChannelR(int colorChannelValue) { setColorChannel(COLOR_CHANNEL_R, colorChannelValue); }
	public void setColorChannelG(int colorChannelValue) { setColorChannel(COLOR_CHANNEL_G, colorChannelValue); }
	public void setColorChannelB(int colorChannelValue) { setColorChannel(COLOR_CHANNEL_B, colorChannelValue); }

	/**
	 * Imposta un valore al canale specificato
	 * Il metodo utilizza la normalizzazione con {@link #colorChannelHook(int)}
	 *
	 * @param colorChannel			L'indice del canale da modificare
	 * @param colorChannelValue		Il valore del canale (range 0-1)
	 */
	public void setColorChannel(int colorChannel, double colorChannelValue) {
		colorChannels[colorChannel] = colorChannelHook((int) (colorChannelValue * 255D));
	}

	public void setColorChannelA(double colorChannelValue) { setColorChannel(COLOR_CHANNEL_A, colorChannelValue); }
	public void setColorChannelR(double colorChannelValue) { setColorChannel(COLOR_CHANNEL_R, colorChannelValue); }
	public void setColorChannelG(double colorChannelValue) { setColorChannel(COLOR_CHANNEL_G, colorChannelValue); }
	public void setColorChannelB(double colorChannelValue) { setColorChannel(COLOR_CHANNEL_B, colorChannelValue); }

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
		System.arraycopy(colorCompactChannels, 0, colorChannels, 0, 4);
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

	public int getColorChannelA() {	return getColorChannel(COLOR_CHANNEL_A); }
	public int getColorChannelR() { return getColorChannel(COLOR_CHANNEL_R); }
	public int getColorChannelG() { return getColorChannel(COLOR_CHANNEL_G); }
	public int getColorChannelB() {	return getColorChannel(COLOR_CHANNEL_B); }

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

	public double getColorChannelNormalA() { return getColorChannelNormal(COLOR_CHANNEL_A); }
	public double getColorChannelNormalR() { return getColorChannelNormal(COLOR_CHANNEL_R); }
	public double getColorChannelNormalG() { return getColorChannelNormal(COLOR_CHANNEL_G); }
	public double getColorChannelNormalB() { return getColorChannelNormal(COLOR_CHANNEL_B); }

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
	 * Converte il colore in un colore di JavaFX
	 *
	 * @return Il colore convertito
	 */
	public Color toColor() {
		return new Color(
			colorChannels[COLOR_CHANNEL_R] / 255D,
			colorChannels[COLOR_CHANNEL_G] / 255D,
			colorChannels[COLOR_CHANNEL_B] / 255D,
			colorChannels[COLOR_CHANNEL_A] / 255D
		);
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
