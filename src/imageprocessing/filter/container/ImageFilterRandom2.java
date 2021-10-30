package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRandom2.ImageFilterRandom2Settings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterRandom2 extends ImageFilterRaster<ImageFilterRandom2Settings> {

    public ImageFilterRandom2() {
        super(ImageFilterRandom2Settings.class, "Random");
    }

    @Override
    public void filterImagePixel(ImageFilterRandom2Settings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, LocalDateTime.now().getHour() * 10);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, LocalDateTime.now().getMinute() * 2);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, LocalDateTime.now().getSecond() * 2);
    }

    public static class ImageFilterRandom2Settings extends ImageFilterSettings {

    }
}


