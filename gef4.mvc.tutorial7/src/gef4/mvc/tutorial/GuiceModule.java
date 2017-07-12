package gef4.mvc.tutorial;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.HoverBehavior;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.handlers.DeleteSelectedOnTypeHandler;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import gef4.mvc.tutorial.handlers.CreationMenuOnClickHandler;
import gef4.mvc.tutorial.handlers.DeleteOnClickHandler;
import gef4.mvc.tutorial.handlers.GlobalOnTypeHandler;
import gef4.mvc.tutorial.handlers.TextNodeOnDoubleClickHandler;
import gef4.mvc.tutorial.handlers.TextNodeOnTypeHandler;
import gef4.mvc.tutorial.parts.ContentPartFactory;
import gef4.mvc.tutorial.parts.DeleteHoverHandlePart;
import gef4.mvc.tutorial.parts.HandlePartFactory;
import gef4.mvc.tutorial.parts.TextNodePart;
import gef4.mvc.tutorial.policies.TextNodeTransformPolicy;


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

		// register resize/transform policies (writing changes also to model)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeTransformPolicy.class);

		// interaction policies to relocate on drag (including anchored
		// elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// edit node label policies
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnDoubleClickHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnTypeHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypeHandler.class);

		// interaction policy to delete on key type
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DeleteSelectedOnTypeHandler.class);
	}

	@Override
	protected void bindRootPartAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindRootPartAsContentViewerAdapter(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypeHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreationMenuOnClickHandler.class);
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(HandlePartFactory.class);
	}

	protected void bindDeleteHoverHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role("0")).to(DeleteOnClickHandler.class);
	}

	@Override
	protected void configure() {
		super.configure();

		binder().bind(GlobalOnTypeHandler.class).in(Scopes.SINGLETON);

		binder().bind(new TypeLiteral<IContentPartFactory>() {
		}).toInstance(new ContentPartFactory());

		bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

		bindDeleteHoverHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DeleteHoverHandlePart.class));

	}
}