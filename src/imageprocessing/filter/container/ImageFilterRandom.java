package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterRandom.ImageFilterRandomSettings;
import imageprocessing.filter.option.ImageFilterSettings;


public class ImageFilterRandom extends ImageFilterRaster<ImageFilterRandomSettings> {

    public ImageFilterRandom() {
        super(ImageFilterRandomSettings.class, "Random");
    }

    @Override
    public void filterImagePixel(ImageFilterRandomSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, Math.floor(Math.random() * 255));
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, Math.floor(Math.random() * 255));
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, Math.floor(Math.random() * 255));
    }

    public static class ImageFilterRandomSettings extends ImageFilterSettings {

    }
}


