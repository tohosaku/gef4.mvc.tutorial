package gef4.mvc.tutorial.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.fx.nodes.OrthogonalRouter;
import org.eclipse.gef.fx.nodes.IConnectionRouter;
import org.eclipse.gef.fx.nodes.PolyBezierInterpolator;
import org.eclipse.gef.geometry.planar.ICurve;
import org.eclipse.gef.geometry.planar.IMultiShape;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Path;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import gef4.mvc.tutorial.model.TextNodeRelation;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class TextNodeRelationPart extends AbstractContentPart<Connection> {

	private static final String ROLE_START = "START";
	private static final String ROLE_END   = "END";
	protected static final double VSPACE = 10;

	public TextNodeRelationPart(){
	}
	@Override
	public TextNodeRelation getContent() {
		return (TextNodeRelation)super.getContent();
	}

	@Override
	protected Connection doCreateVisual() {
		
		Connection visual = new Connection();
		visual.setRouter(new OrthogonalRouter());
		/*
		visual.setRouter(new IConnectionRouter();
		{
			@Override
			public ICurve routeConnection(Point[] points) {
				if (points == null || points.length < 2) {
					return new Line(0, 0, 0, 0);
				}
				if( points.length > 2 ) throw new RuntimeException("len: "+points.length);
				Point start = ( points[0].x < points[1].x ) ? points[0] : points[1];
				Point end   = ( points[0].x > points[1].x ) ? points[0] : points[1];
				Point p1 = new Point( start.x + VSPACE, start.y );
				Point p2 = new Point( start.x + VSPACE, end.y );
				Polyline poly = new Polyline(start, p1, p2, end );
				return poly;
			}

			@Override
			public void route(Connection connection) {
			}

			@Override
			public boolean wasInserted(IAnchor anchor) {
				return false;
			}
		});
			*/
		visual.getCurve().setStyle("-fx-stroke: black;-fx-stroke-width:1.5");
		//setStroke(Color.BLACK);
		//visual.getCurve()curve().setStrokeWidth(1.5);
		return visual;
	}

	@Override
	protected void doRefreshVisual(Connection visual) {
	}
	
	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		HashMultimap<Object, String> res = HashMultimap.create();
		TextNodeRelation nr = getContent();
		res.put( nr.getParent(), ROLE_START );
		res.put( nr.getChild(), ROLE_END );
		return res;
	}

	@Override
	public List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@SuppressWarnings("serial")
	@Override
	protected void doAttachToAnchorageVisual( IVisualPart<? extends Node> anchorage, String role) {
		Provider<? extends IAnchor> provider = anchorage.getAdapter(new TypeToken<Provider<? extends IAnchor>>() {});
		
		if (provider == null) return;
		IAnchor anchor = provider.get();
		if (role.equals(ROLE_START)) {
			getVisual().setStartAnchor(anchor);
		} else if (role.equals(ROLE_END)) {
			getVisual().setEndAnchor(anchor);
		} else {
			throw new IllegalStateException( "Cannot attach to anchor with role <" + role + ">.");
		}
	}

}
