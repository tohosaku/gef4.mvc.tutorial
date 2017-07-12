package gef4.mvc.tutorial.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gef4.mvc.tutorial.model.Model;
import javafx.scene.Node;

public class ModelPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {
		if (content instanceof Model) {
			return injector.getInstance(ModelPart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	};
}
