package homework1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;
		
		// TODO Write the body of this method
		//GUI implementation, "stolen" from RouteFormatterGUI class
		lstSegments = new JList<>(new DefaultListModel<GeoSegment>());
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrlSegments = new JScrollPane(lstSegments);
		scrlSegments.setPreferredSize(new Dimension(450, 100));
		JLabel lblSegments = new JLabel("GeoSegments:");
		lblSegments.setLabelFor(lstSegments);

		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //i.e., pressing cancel button will close the window
			}
		});
		JButton btn_add = new JButton("Add");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pressing add will add the selected (if a valid selection) segment to the RouteFormatter list
				GeoSegment gs = lstSegments.getSelectedValue();
				Route route = parent.getRoute();
				if(route == null) {
					parent.addSegment(gs);
				}else{
					if(!route.getEnd().equals(gs.getP1())){
						dispose();
						return;
					}
					parent.addSegment(gs);
				}
				dispose();
			}
		});
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(lblSegments, c);
		this.add(lblSegments);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 5;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(scrlSegments, c);
		this.add(scrlSegments);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
//		c.gridheight = 1;
//		c.insets = new Insets(0,20,0,0);
//		c.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(btn_add, c);
		this.add(btn_add);

		c.gridx=1;
		c.gridwidth = 1;
		c.insets = new Insets(0,250,0,0);
		c.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(btn_cancel, c);
		this.add(btn_cancel);

		//Adding example segments to the list
		ExampleGeoSegments eg = new ExampleGeoSegments();
		DefaultListModel<GeoSegment> model =
				(DefaultListModel<GeoSegment>)(this.lstSegments.getModel());
		for(GeoSegment gs : eg.segments){
			model.addElement(new GeoSegment(gs));
		}
	}
}
