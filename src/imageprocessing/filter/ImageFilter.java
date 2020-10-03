package imageprocessing.filter;

import java.util.function.Consumer;

import imageprocessing.accessor.ImageAccessor;
import imageprocessing.filter.option.ImageFilterSettings;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rappresenta un filtro
 *
 * @param <S> Le impostazioni del filtro
 */
@AllArgsConstructor
public abstract class ImageFilter<S extends ImageFilterSettings> {

	/**
	 * La classe delle impostazioni del filtro
	 */
	@Getter
	protected final Class<S> filterSettings;

	/**
	 * Il nome del filtro
	 */
	@Getter
	protected final String filterName;

	/**
	 * Esegue il filtro sull'immagine
	 *
	 * @param filterSettings	Le impostazioni del filtro
	 * @param filterImage		L'immagine su cui eseguire il filtro
	 * @param filterProgress	Il callback del progresso del filtro (range 0-1)
	 */
	public void filterImageRaw(ImageFilterSettings filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress) {
		filterImage(this.filterSettings.cast(filterSettings), filterImage, filterProgress);
	}

	/**
	 * Esegue il filtro sull'immagine
	 *
	 * @param filterSettings	Le impostazioni del filtro
	 * @param filterImage		L'immagine su cui eseguire il filtro
	 * @param filterProgress	Il callback del progresso del filtro (range 0-1)
	 */
    protected abstract void filterImage(S filterSettings, ImageAccessor filterImage, Consumer<Double> filterProgress);

    /**
     * Ritorna il nome del filtro
     */
    @Override
    public String toString() {
    	return getFilterName();
    }

}
