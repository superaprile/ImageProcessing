package imageprocessing.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe di utility
 */
public class ImageUtils {

	/**
	 * Clona una buffered image.
	 *
	 * @param imageBuffer	L'immagine da clonare
	 *
	 * @return L'immagine clonata
	 */
	public static BufferedImage cloneImage(BufferedImage imageBuffer) {
		BufferedImage imageBufferClone = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics imageBufferGraphics = imageBufferClone.getGraphics();
		imageBufferGraphics.drawImage(imageBuffer, 0, 0, null);
		imageBufferGraphics.dispose();

		return imageBufferClone;
	}

	/**
	 * Carica un immagine da file
	 *
	 * @param imageFile		Il file dell'immagine
	 *
	 * @return L'immagine caricata
	 *
	 * @throws Exception	In caso di errore nel caricamento
	 */
	public static BufferedImage parseImage(File imageFile) throws Exception {
		return cloneImage(ImageIO.read(imageFile));
	}

	public static void saveImage(File imageFile, BufferedImage image) {
		try {
			ImageIO.write(image, "PNG", imageFile);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static double map(double x, double minX, double maxX, double minY, double maxY) {
	     return (x - minX) * (maxY - minY) / (maxX - minX) + minY;
	}

}
