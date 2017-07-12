package gef4.mvc.tutorial;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import gef4.mvc.tutorial.model.Model;
import gef4.mvc.tutorial.model.TextNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gef4MvcTutorial extends Application {

	private Model model;
	private JAXBContext jaxbContext;

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		jaxbContext = JAXBContext.newInstance(Model.class, TextNode.class);

		Injector injector = Guice.createInjector(createGuiceModule());

		IDomain domain = injector.getInstance(IDomain.class);

		IViewer viewer = domain.getAdapter(IViewer.class);

		AnchorPane paneCtrl = new AnchorPane();
		AnchorPane paneDraw = new AnchorPane();
		VBox vbox = new VBox(paneCtrl, paneDraw);
		vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		Button btnUpdateModel = new Button("update model");
		btnUpdateModel.setOnAction(e -> model.doChanges());
		btnUpdateModel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		paneCtrl.getChildren().add(btnUpdateModel);
		AnchorPane.setTopAnchor(btnUpdateModel, 10d);
		AnchorPane.setLeftAnchor(btnUpdateModel, 10d);
		AnchorPane.setRightAnchor(btnUpdateModel, 10d);

		InfiniteCanvas drawingPane = (InfiniteCanvas)viewer.getCanvas();
		paneDraw.getChildren().add(drawingPane);
		paneDraw.setPrefHeight(2000);
		AnchorPane.setTopAnchor(drawingPane, 10d);
		AnchorPane.setLeftAnchor(drawingPane, 10d);
		AnchorPane.setRightAnchor(drawingPane, 10d);
		AnchorPane.setBottomAnchor(drawingPane, 10d);

		primaryStage.setScene(new Scene(vbox));

		primaryStage.setResizable(true);
		primaryStage.setWidth(640);
		primaryStage.setHeight(480);
		primaryStage.setTitle("GEF4 MVC Tutorial 5 - Editable Text");

		primaryStage.show();

		domain.activate();

		viewer.getContents().setAll(createContents());
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(model, new File("model.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List<? extends Object> createContents() {
		if (Files.isReadable(Paths.get("model.xml"))) {
			try {
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				model = (Model) jaxbUnmarshaller.unmarshal(new File("model.xml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (model == null) {
			model = new Model();
			model.addNode(new TextNode(20, 20, "First"));
			model.addNode(new TextNode(20, 120, "Second"));

		}

		return Collections.singletonList(model);
	}

	protected Module createGuiceModule() {
		return new GuiceModule();
	}
}
