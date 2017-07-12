package gef4.mvc.tutorial;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import gef4.mvc.tutorial.handlers.TextNodeOnDoubleClickHandler;
import gef4.mvc.tutorial.handlers.TextNodeOnTypeHandler;
import gef4.mvc.tutorial.parts.ModelPartFactory;
import gef4.mvc.tutorial.parts.TextNodePart;
import gef4.mvc.tutorial.policies.TextNodeTransformPolicy;

public final class GuiceModule extends MvcFxModule {
	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);
		// register (default) interaction policies (which are based on viewer
		// models and do not depend on transaction policies)

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
		// register resize/transform policies (writing changes also to model)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeTransformPolicy.class);
		// .to(FXTransformPolicy.class);

		// interaction policies to relocate on drag (including anchored
		// elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// edit node label policies
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnDoubleClickHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnTypeHandler.class);

	}

	@Override
	protected void configure() {
		super.configure();

		binder().bind(new TypeLiteral<IContentPartFactory>() {}).toInstance(new ModelPartFactory());

		bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

	}
}