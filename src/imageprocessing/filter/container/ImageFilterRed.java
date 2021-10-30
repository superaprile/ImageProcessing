package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRed.ImageFilterRedSettings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterRed extends ImageFilterRaster<ImageFilterRedSettings> {

    public ImageFilterRed() {
        super(ImageFilterRedSettings.class, "Filtro Rosso");
    }

    @Override
    public void filterImagePixel(ImageFilterRedSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, 0);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, 0);
    }

    public static class ImageFilterRedSettings extends ImageFilterSettings {

    }
}


