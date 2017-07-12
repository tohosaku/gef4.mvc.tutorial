package gef4.mvc.tutorial.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.RoundedRectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import gef4.mvc.tutorial.model.Model;
import javafx.scene.paint.Color;

public class ModelPart extends AbstractContentPart<GeometryNode<RoundedRectangle>> {

	@Override
	public Model getContent() {
		return (Model) super.getContent();
	}

	protected GeometryNode<RoundedRectangle> createVisual() {
		return new GeometryNode<>();
	}

	protected void doRefreshVisual(GeometryNode<RoundedRectangle> visual) {
		Model model = getContent();
		visual.setGeometry(new RoundedRectangle(model.getRect(), 10, 10));
		visual.setFill(model.getColor());
		visual.setStroke(Color.BLACK);
		visual.setStrokeWidth(2);
	}

	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	public List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected GeometryNode<RoundedRectangle> doCreateVisual() {
		GeometryNode<RoundedRectangle> visual = createVisual();
		doRefreshVisual(visual);
		return visual;
	}
}
