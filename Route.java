package homework1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {
	private final GeoPoint start;
	private GeoPoint end;
	private double start_heading, end_heading;
	private final List<GeoFeature> geo_features;
	private final List<GeoSegment> geo_segments;
	private double length;
	private GeoSegment ending_geo_segment;
	
 	// TODO Write abstraction function and representation invariant
	// Abs. Function for every Route r:
	// r represents a geographical route between r.start and r.end that goes through
	// r.geoFeatures, routes length is r.length and r.startHeading represents the starting movement angle

	// Rep. Invariant for every Route r:
	// r.start != null && r.end != null && r.ending_geo_segment != null && r.length >= 0 &&
	// geoFeatures != null && geoFeatures contains at least one GeoFeature &&
	// geoSegments != null && geoSegments contains at least one GeoSegment &&
	// for all i: gf.geoSegments.get(i).getP2().equals(gf.geoSegments.get(i+1).getP1())
	// NOTE: i in [0,geoSegments.size()-1]
	// i.e., all geo segments come aligned one after the other
	// Do we need to check that for every feature in geoFeatures, feature segments fit the feature (same name)?

	/**
	 * Checks if representation invariant holds for every GeoSegment gs
	 * @throws AssertionError if representation invariant is violated
	 * */
	private void checkRep(){
		assert (start != null && end != null) : "invalid geo points";
		assert ending_geo_segment != null : "invalid endingGeoSegment";
		assert length >= 0 : "invalid length";
		assert (geo_features != null && geo_features.size() >= 1) : "invalid geoFeatures";
		for(GeoFeature gf: geo_features){
			Iterator<GeoSegment> it = gf.getGeoSegments();
			while(it.hasNext()){
				GeoSegment gs = it.next();
				assert gf.getName().equals(gs.getName()) : "invalid segment in feature";
			}
		}
		assert (geo_segments != null && geo_segments.size() >= 1) : "invalid geoSegments";
		for(int i=0; i<geo_segments.size()-1; ++i){
			assert geo_segments.get(i).getP2().equals(geo_segments.get(i+1).getP1()) : "invalid geo feature points";
		}
	}

  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {
		start_heading = gs.getHeading();
		end_heading = start_heading;
		start = gs.getP1();
		end = gs.getP2();
		geo_features = new ArrayList<>();
		geo_segments = new ArrayList<>();
		geo_features.add(new GeoFeature(new GeoSegment(gs)));
		geo_segments.add(new GeoSegment(gs));
		length = 0;
		ending_geo_segment = new GeoSegment(gs);
		checkRep();
  	}

	/**
	 * Constructs a new copy of Route route
	 * @requires route != null
	 * @effects constructs a new copy of gf s.t.,
	 *		startHeading = route.startHeading && endHeading == route.endHeading
	 *	&& start = route.start && end = route.end && geoSegments = route.geoSegments && geoFeatures = route.geoFeatures
	 *  && endingGeoSegment = route.endingGeoSegment
	 * **/
	public Route(Route route){
		start_heading = route.start_heading;
		end_heading = route.end_heading;
		start = new GeoPoint(route.start);
		end = new GeoPoint(route.end);
		geo_features = new ArrayList<>();
		geo_segments = new ArrayList<>();
		for(GeoFeature gf: route.geo_features){
			geo_features.add(new GeoFeature(gf));
		}
		for(GeoSegment gs: route.geo_segments){
			geo_segments.add(new GeoSegment(gs));
		}
		length = route.length;
		ending_geo_segment = new GeoSegment(route.ending_geo_segment);
		checkRep();
	}

    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		checkRep();
		return new GeoPoint(start);
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		checkRep();
		return new GeoPoint(end);
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees.
   	 **/
  	public double getStartHeading() {
  		checkRep();
		return start_heading;
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees.
     **/
  	public double getEndHeading() {
  		checkRep();
		return end_heading;
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		checkRep();
		return length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		checkRep();
		Route r = new Route(geo_segments.get(0));
		r.geo_segments.remove(0);
		r.geo_features.remove(0);
		r.length = length + gs.getLength();
		r.end = new GeoPoint(gs.getP2());
		r.end_heading = gs.getHeading();
		for (GeoSegment segment: geo_segments) {
			r.geo_segments.add(new GeoSegment(segment));
		}
		r.geo_segments.add(new GeoSegment(gs));
		GeoFeature updated;
		for(GeoFeature gf: geo_features){
			if(gf.getName().equals(gs.getName())){
				updated = gf.addSegment(new GeoSegment(gs));
				r.geo_features.add(updated);
			}else{
				updated = new GeoFeature(gf);
				r.geo_features.add(updated);
			}
		}
		checkRep();
		return r;
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		checkRep();
		List<GeoFeature> geo_feature_copy = new ArrayList<>();
		for(GeoFeature gf: geo_features){
			geo_feature_copy.add(new GeoFeature(gf));
		}
		checkRep();
		return geo_feature_copy.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
		List<GeoSegment> geo_seg_copy = new ArrayList<>();
		for(GeoSegment gs: geo_segments){
			geo_seg_copy.add(new GeoSegment(gs));
		}
		checkRep();
		return geo_seg_copy.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
		if((o == null) || !(o instanceof Route))
			return false;
		checkRep();
		return geo_features.equals(((Route) o).geo_features);
  	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.
    	return 1;
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
		return new String("Printing Route:\n" + geo_features.toString());
  	}

}
