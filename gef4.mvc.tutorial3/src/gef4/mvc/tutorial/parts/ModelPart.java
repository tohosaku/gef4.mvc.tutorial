package gef4.mvc.tutorial.parts;

import java.util.List;

import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import gef4.mvc.tutorial.model.Model;
import gef4.mvc.tutorial.model.TextNode;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

public class ModelPart extends AbstractContentPart<Group> {

	@Override
	public Model getContent() {
		return (Model) super.getContent();
	}

	@Override
	protected void doRefreshVisual(Group visual) {
	}

	@Override
	public List<? extends TextNode> doGetContentChildren() {
		Model model = getContent();
		return model.getNodes();
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		ObservableList<Node> children = getVisual().getChildren();
		Node visual = child.getVisual();
		children.add(visual);
	}

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected Group doCreateVisual() {
		return new Group();
	}

}
