package imageprocessing.filter.option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione da apporre sopra un campo di opzione
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFilterOption {

	/**
	 * @return Il nome dell'opzione
	 */
	public String optionTitle();

	/**
	 * @return La descrizione dell'opzione
	 */
	public String optionDescription();

}
