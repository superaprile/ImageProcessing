package imageprocessing.ui.options;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ImageFilterOptionUI<A extends Annotation, T> {

	@Getter
	protected final Class<A> classAnnotation;

	@Getter
	protected final Class<T> classType;

	public Node buildFor(Annotation annotation, Consumer<T> listener) {
		return build(classAnnotation.cast(annotation), listener);
	}

	protected abstract Node build(A annotation, Consumer<T> listener);

}
