/*******************************************************************************
 * Copyright (c) 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package gef4.mvc.tutorial.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.policies.CreationPolicy;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;

import gef4.mvc.tutorial.model.TextNode;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

// TODO: only applicable for FXRootPart and FXViewer
public class CreationMenuOnClickHandler extends AbstractHandler implements IOnClickHandler {

	/**
	 * The adapter role for the
	 * <code>Provider&lt;List&lt;IFXCreationMenuItem&gt;&gt;</code>.
	 */
	public static final String MENU_ITEM_PROVIDER_ROLE = "Provider<List<IFXCreationMenuItem>>";

	private Point2D initialMousePositionInScene;

	private Point2D initialMousePositionInScreen;

	private Popup popup;

	@Override
	public void click(MouseEvent e) {
		// open menu on right click
		if (MouseButton.SECONDARY.equals(e.getButton())) {

			initialMousePositionInScreen = new Point2D(e.getScreenX(), e.getScreenY());

			// use the viewer to transform into local coordinates
			// this works even if the viewer is scrolled and/or zoomed.
			InfiniteCanvas infiniteCanvas = (InfiniteCanvas)getViewer().getCanvas();
			initialMousePositionInScene = infiniteCanvas.getContentGroup().screenToLocal(initialMousePositionInScreen);

			// only open if the even was on the visible canvas
			if (infiniteCanvas.getBoundsInLocal().contains(initialMousePositionInScene)) {
				openMenu(e);
			}
		}
	}

	private IViewer getViewer() {
		return (IViewer) getHost().getRoot().getViewer();
	}

	private void openMenu(final MouseEvent me) {
		popup = new Popup();

		popup.autoFixProperty().set(true);
		popup.autoHideProperty().set(true);
		popup.setX(initialMousePositionInScreen.getX());
		popup.setY(initialMousePositionInScreen.getY());

		HBox hb = new HBox();
		hb.setStyle("-fx-border-width: 1px; -fx-border-color: DIMGRAY; -fx-background-color: lightgray");
		Button first = new Button();
		first.setOnAction(e -> this.addTextNode());
		ImageView iv = new ImageView(new Image(CreationMenuOnClickHandler.class.getResourceAsStream("AddTextNode.png")));
		iv.autosize();
		first.setGraphic(iv);
		hb.getChildren().add(first);
		hb.setEffect(new DropShadow(4, 4, 2, Color.GRAY));
		hb.setSpacing(4);
		hb.setPadding(new Insets(4, 4, 4, 4));
		popup.getContent().addAll(hb);
		popup.show(((Node) getViewer()).getScene().getWindow());

	}

	private void addTextNode() {

		IRootPart<? extends Node> root = getHost().getRoot();
		IViewer viewer = root.getViewer();

		TextNode textNode = new TextNode(initialMousePositionInScene.getX(), initialMousePositionInScene.getY(), "A");
		IContentPart<? extends Node> contentPartModel = getHost().getRoot().getContentPartChildren().get(0);

		// build create operation
		CreationPolicy creationPolicy = root.getAdapter(new TypeToken<CreationPolicy>(){
			private static final long serialVersionUID = 1L;});
		creationPolicy.init();
		creationPolicy.create(textNode, contentPartModel, HashMultimap.create());

		// execute on stack
		try {
			viewer.getDomain().execute(creationPolicy.commit(),null);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		popup.hide();
	}

}
