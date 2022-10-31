package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterGreen.ImageFilterGreenSettings;
import imageprocessing.filter.option.ImageFilterSettings;


public class ImageFilterGreen extends ImageFilterRaster<ImageFilterGreenSettings> {

    public ImageFilterGreen() {
        super(ImageFilterGreenSettings.class, "Filtro Verde");
    }

    @Override
    public void filterImagePixel(ImageFilterGreenSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, 0);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, 255);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, 0);
    }

    public static class ImageFilterGreenSettings extends ImageFilterSettings {

    }
}


