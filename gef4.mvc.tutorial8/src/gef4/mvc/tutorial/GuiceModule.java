package gef4.mvc.tutorial;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
//import org.eclipse.gef.mvc.fx.tools.ClickDragTool;
//import org.eclipse.gef.mvc.fx.tools.HoverTool;
//import org.eclipse.gef.mvc.fx.tools.TypeTool;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import gef4.mvc.tutorial.handlers.GlobalOnTypeHandler;
import gef4.mvc.tutorial.parts.ContentPartFactory;
//import gef4.mvc.tutorial.parts.SideAnchorProvider;
import gef4.mvc.tutorial.parts.TextNodePart;
import gef4.mvc.tutorial.parts.TextNodeRelationPart;
import javafx.scene.Node;

@SuppressWarnings("serial")
public final class GuiceModule extends MvcFxModule {

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
	}

	protected void bindTextNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// geometry provider for selection handles
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		adapterMapBinder
				.addBinding(AdapterKey
						.role(DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// interaction policies to relocate on drag (including anchored
		// elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

//		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(SideAnchorProvider.class);

	}

	private void bindTextNodePartRelationAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
	}

	@Override
	protected void bindRootPartAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindRootPartAsContentViewerAdapter(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypeHandler.class);

	}

	@Override
	protected void configure() {
		super.configure();

		binder().bind(GlobalOnTypeHandler.class).in(Scopes.SINGLETON);

		binder().bind(new TypeLiteral<IContentPartFactory>() {
		}).toInstance(new ContentPartFactory());

		bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

		bindTextNodePartRelationAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodeRelationPart.class));

	}

}