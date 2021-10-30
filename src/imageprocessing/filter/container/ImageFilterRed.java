package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRed.ImageFilterRandom4Settings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterRed extends ImageFilterRaster<ImageFilterRandom4Settings> {

    public ImageFilterRed() {
        super(ImageFilterRandom4Settings.class, "Random");
    }

    @Override
    public void filterImagePixel(ImageFilterRandom4Settings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, 0);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, 0);
    }

    public static class ImageFilterRandom4Settings extends ImageFilterSettings {

    }
}


