package homework1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExampleGeoSegments eg = new ExampleGeoSegments();
        JFrame frame = new JFrame("Route Formatter GUI");
        Container contentPane = frame.getContentPane();
        contentPane.add(new RouteFormatterGUI(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        ExampleGeoSegments eg = new ExampleGeoSegments();
//        GeoPoint p1 = new GeoPoint(0,0);
//        GeoPoint p2 = new GeoPoint(1,1);
//        GeoPoint p3 = new GeoPoint(2,2);
//        GeoSegment gs = new GeoSegment("1st", p1, p2);
//        GeoSegment gs2 = new GeoSegment("1st", p2,p3);
//        Route r = new Route(gs);
//        r = r.addSegment(gs2);
//        System.out.println(r.toString());
    }
}
