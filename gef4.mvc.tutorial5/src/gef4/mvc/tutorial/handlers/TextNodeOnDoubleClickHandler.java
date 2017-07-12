package gef4.mvc.tutorial.handlers;

import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;

import gef4.mvc.tutorial.parts.TextNodePart;
import javafx.scene.input.MouseEvent;

// only applicable for NodeContentPart (see #getHost())
public class TextNodeOnDoubleClickHandler extends AbstractHandler implements IOnClickHandler {

	@Override
	public TextNodePart getHost() {
		return (TextNodePart) super.getHost();
	}
	
	@Override
	public void click(MouseEvent e) {
		if (e.getClickCount() == 2 && e.isPrimaryButtonDown()) {
			getHost().editModeStart();
		}
	}
}
