package gef4.mvc.tutorial.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gef4.mvc.tutorial.model.TextNode;
import gef4.mvc.tutorial.model.TextNodeRelation;
import javafx.scene.Node;

public class ContentPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {

		if (content instanceof TextNode) {
			return injector.getInstance(TextNodePart.class);
		} else if (content instanceof TextNodeRelation) {
			return injector.getInstance(TextNodeRelationPart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	};

}
