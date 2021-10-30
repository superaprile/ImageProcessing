package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRandom4.ImageFilterRandom4Settings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterRandom4 extends ImageFilterRaster<ImageFilterRandom4Settings> {

    public ImageFilterRandom4() {
        super(ImageFilterRandom4Settings.class, "Random");
    }

    @Override
    public void filterImagePixel(ImageFilterRandom4Settings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, System.currentTimeMillis() % 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, LocalDateTime.now().getMinute() * 2);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, Math.floor(Math.random() * 255));
    }

    public static class ImageFilterRandom4Settings extends ImageFilterSettings {

    }
}


