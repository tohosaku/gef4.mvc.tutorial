package gef4.mvc.tutorial.handlers;

import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.IOnTypeHandler;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GlobalOnTypeHandler extends AbstractHandler implements IOnTypeHandler {

	@Override
	public void type(KeyEvent keyEvent, Set<KeyCode> pressedKeys) {
		HistoricizingDomain domain = (HistoricizingDomain)getDomain();
		if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.Z)) {
			try {
				domain.getOperationHistory().undo(domain.getUndoContext(), null, null);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.Y)) {
			try {
				domain.getOperationHistory().redo(domain.getUndoContext(), null, null);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private IDomain getDomain() {
		return getHost().getRoot().getViewer().getDomain();
	}

}
