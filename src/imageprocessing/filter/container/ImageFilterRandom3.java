package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRandom3.ImageFilterRandom3Settings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterRandom3 extends ImageFilterRaster<ImageFilterRandom3Settings> {

    public ImageFilterRandom3() {
        super(ImageFilterRandom3Settings.class, "Random");
    }

    @Override
    public void filterImagePixel(ImageFilterRandom3Settings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, System.currentTimeMillis() % 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, System.currentTimeMillis() % 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, System.currentTimeMillis() % 255);
    }

    public static class ImageFilterRandom3Settings extends ImageFilterSettings {

    }
}


