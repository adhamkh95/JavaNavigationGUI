package homework1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	private final GeoPoint start;
	private GeoPoint end;
	private double start_heading, end_heading;
	private final List<GeoSegment> geo_segments;
	private final String name;
	private double length;

	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
	
  	// TODO Write abstraction function and representation invariant
	// Abs. Function for every GeoFeature gf:
	// gf.geoSegments represents a geographical feature path between gf.start and gf.end of gf.length with gf.startHeading

	// Rep. Invariant for every GeoFeature gf:
	// gf.name != null && gs.name isn't empty
	// gf.start != null && gf.end != null && gf.geoSegments != null and contains at least one GeoSegment
	// gf.length >= 0
	// for all i: gf.geoSegments.get(i).getP2().equals(gf.geoSegments.get(i+1).getP1())
	// NOTE: i in (0,geoSegments.size()-1)
	// i.e., all geo segments come aligned one after the other in a feature
	// for all i: gf.geoSegments.get(i).getName().equals(gf.name)
	// i.e., all feature segments fit the feature name

	/**
	 * Checks if representation invariant holds for every GeoSegment gs
	 * @throws AssertionError if representation invariant is violated
	 * */
	private void checkRep(){
		assert (name != null && !name.isEmpty()) : "invalid segment name";
		assert (start != null && end != null) : "invalid geo points";
		assert (geo_segments != null && geo_segments.size() >= 1) : "invalid geoSegment";
		for(int i=0; i<geo_segments.size()-1; ++i){
			assert geo_segments.get(i).getP2().equals(geo_segments.get(i+1).getP1()) : "invalid geo feature points";
		}
		for(GeoSegment gs : geo_segments){
			assert gs.getName().equals(name) : "invalid segment name";
		}
	}

	/**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
		name = gs.getName();
		start_heading = gs.getHeading();
		end_heading = start_heading;
		start = gs.getP1();
		end = gs.getP2();
		length = gs.getLength();
		geo_segments = new ArrayList<>();
		geo_segments.add(gs);
		checkRep();
  	}
  	/**
	 * Constructs a new copy of Geo feature
	 * @requires gf != null
	 * @effects constructs a new copy of gf s.t.,
	 *		name = gf.name && startHeading = gf.startHeading && endHeading == gf.endHeading
	 *	&& start = gf.start && end = gf.end && geoSegments = gf.geoSegments
	 * **/
  	public GeoFeature(GeoFeature gf){
  		name = gf.name;
  		start_heading = gf.start_heading;
  		end_heading = gf.end_heading;
  		start = new GeoPoint(gf.start);
  		end = new GeoPoint(gf.end);
  		length = gf.length;
  		geo_segments = new ArrayList<>();
  		for(GeoSegment gs: gf.geo_segments){
  			geo_segments.add(new GeoSegment(gs));
		}
  		checkRep();
	}

 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		checkRep();
		return new String(name);
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		checkRep();
		return new GeoPoint(start);
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		checkRep();
		return new GeoPoint(end);
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     */
  	public double getStartHeading() {
  		checkRep();
		return start_heading;
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     */
  	public double getEndHeading() {
  		checkRep();
		return end_heading;
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		checkRep();
		return length;
  	}


  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		checkRep();
		GeoFeature gf = new GeoFeature(new GeoSegment(geo_segments.get(0)));
		for(int i=1; i<geo_segments.size(); ++i){
			gf.geo_segments.add(new GeoSegment(geo_segments.get(i)));
		}
		gf.geo_segments.add(new GeoSegment(gs));
		gf.length = length + gs.getLength();
		gf.end = new GeoPoint(gs.getP2());
		gf.end_heading = gs.getHeading();
		checkRep();
  		return gf;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
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
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
		if((o == null) || !(o instanceof GeoFeature))
			return false;
		checkRep();
		return geo_segments.equals(((GeoFeature) o).geo_segments);
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// improved performance.
    	
    	return 1;
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
  		String str = geo_segments.toString();
  		return str;
  	}
}
