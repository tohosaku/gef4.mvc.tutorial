package gef4.mvc.tutorial.handlers;

import java.util.Set;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnTypeHandler;

import gef4.mvc.tutorial.parts.TextNodePart;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//only applicable for NodeContentPart (see #getHost())
public class TextNodeOnTypeHandler extends AbstractHandler implements IOnTypeHandler {

	@Override
	public TextNodePart getHost() {
		return (TextNodePart) super.getHost();
	}

	@Override
	public void type(KeyEvent event, Set<KeyCode> pressedKeys) {
		if (pressedKeys.contains(KeyCode.F2) && !getHost().isEditing()) {
			System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 1");
			getHost().editModeStart();
		} else if (pressedKeys.contains(KeyCode.ENTER)) {
			if (getHost().isEditing()) {
				System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 2");
				event.consume();
				getHost().editModeEnd(true);
			} else {
				System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 3");
				event.consume();
				getHost().editModeStart();
			}
		} else if (pressedKeys.contains(KeyCode.ESCAPE)) {
			event.consume();
			getHost().editModeEnd(false);
		}
	}
}
