/*******************************************************************************
 * Copyright (c) 2014, 2015 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API & implementation
 *
 *******************************************************************************/
package gef4.mvc.tutorial.handlers;

import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DeleteOnClickHandler extends AbstractHandler implements IOnClickHandler {

	@Override
	public void click(MouseEvent e) {
		System.out.println("DeleteOnClickPolicy.click()");
		IVisualPart<? extends Node> targetPart = getTargetPart();
		if (targetPart instanceof IContentPart) {
			DeletionPolicy policy = getHost().getRoot().getAdapter(new TypeToken<DeletionPolicy>(){
				private static final long serialVersionUID = -8619641770288884329L;});
			if (policy != null) {
				init(policy);
				// un establish anchor relations
				policy.delete((IContentPart<? extends Node>) targetPart);
				commit(policy);
			}
		}
	}

	/**
	 * Returns the target {@link IVisualPart} for this policy. Per default the
	 * first anchorage is returned.
	 *
	 * @return The target {@link IVisualPart} for this policy.
	 */
	protected IVisualPart<? extends Node> getTargetPart() {
		return getHost().getAnchoragesUnmodifiable().keySet().iterator().next();
	}

}
