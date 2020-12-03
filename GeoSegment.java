package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {
	private final String name;
	private final GeoPoint p1,p2;
	private final double length, heading;

  	// TODO Write abstraction function and representation invariant
	// Abs. Function for every GroSegment gs:
	// gs represents a geographical segment between two GeoPoints gs.p1 and gs.p2
	// where gs.length is the segment length and gs.heading is the movement angle between p1 and p2

	// Rep. Invariant for every GeoSegment gs:
	// gs.name != null && gs.name is not empty
	// gs.p1 != null && gs.p2 != null
	// gs.length >= 0

	/**
	 * Checks if representation invariant holds for every GeoSegment gs
	 * @throws AssertionError if representation invariant is violated
	 * */
	private void checkRep(){
		assert (name != null && !name.isEmpty()) : "invalid segment name";
		assert (p1 != null && p2 != null) : "invalid geo points";
		assert (length >= 0) : "invalid length";
	}

  	/**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
  	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
		this.name = name;
		this.p1 = new GeoPoint(p1);
		this.p2 = new GeoPoint(p2);
		length = p1.distanceTo(p2);
		heading = p1.headingTo(p2);
		checkRep();
  	}

	/**
	 * Constructs GeoSegment from  gs.latitude and gs.longitude
	 * @requires gs != null
	 * @returns a new copied geo segment from gs **/
	public GeoSegment(GeoSegment gs){
		name = gs.getName();
		p1 = gs.getP1();
		p2 = gs.getP2();
		length = gs.length;
		heading = gs.heading;
		checkRep();
	}

  	/**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
  	public GeoSegment reverse() {
  		checkRep();
		return new GeoSegment(this.name, this.p2, this.p1);
  	}


  	/**
  	 * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
  	public String getName() {
  		checkRep();
		return new String(this.name);
  	}


  	/**
  	 * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
  	public GeoPoint getP1() {
  		checkRep();
		return new GeoPoint(p1);
  	}


  	/**
  	 * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
  	public GeoPoint getP2() {
  		checkRep();
		return new GeoPoint(p2);
  	}


  	/**
  	 * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
  	public double getLength() {
  		checkRep();
		return length;
  	}


  	/**
  	 * Returns the compass heading from p1 to p2.
     * @requires this.length != 0
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation.
     **/
  	public double getHeading() {
  		checkRep();
		return heading;
  	}


  	/**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
   	 **/
  	public boolean equals(Object gs) {
		if((gs == null) || !(gs instanceof GeoSegment))
			return false;
		checkRep();
		return (name.equals(((GeoSegment) gs).getName()) && p1.equals(((GeoSegment) gs).p1) && p2.equals(((GeoSegment) gs).p2));
  	}


  	/**
  	 * Returns a hash code value for this.
     * @return a hash code value for this.
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
		String str = "(\"" + name + "\"," + p1.toString() + "," + p2.toString() + ")";
		return new String(str);
  	}

}

