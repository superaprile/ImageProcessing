package imageprocessing.filter.container;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.accessor.structure.ImageColor;
import imageprocessing.filter.ImageFilterRaster;
import imageprocessing.filter.container.ImageFilterBlue.ImageFilterBlueSettings;
import imageprocessing.filter.option.ImageFilterSettings;

import java.time.LocalDateTime;


public class ImageFilterBlue extends ImageFilterRaster<ImageFilterBlueSettings> {

    public ImageFilterBlue() {
        super(ImageFilterBlueSettings.class, "Filtro Blu");
    }

    @Override
    public void filterImagePixel(ImageFilterBlueSettings filterSettings, ImageAccessor filterImage, int imagePixelX, int imagePixelY, ImageColor imagePixelColor) {

        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_R, 0);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_G, 0);
        imagePixelColor.setColorChannel(ImageColor.COLOR_CHANNEL_B, 255);
    }

    public static class ImageFilterBlueSettings extends ImageFilterSettings {

    }
}


