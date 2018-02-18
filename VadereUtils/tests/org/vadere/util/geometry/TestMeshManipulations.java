package org.vadere.util.geometry;

import org.junit.Before;
import org.junit.Test;
import org.vadere.util.geometry.mesh.gen.PFace;
import org.vadere.util.geometry.mesh.gen.PHalfEdge;
import org.vadere.util.geometry.mesh.gen.PMesh;
import org.vadere.util.geometry.mesh.gen.PVertex;
import org.vadere.util.geometry.mesh.impl.VPTriangulation;
import org.vadere.util.geometry.mesh.inter.ITriangulation;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.geometry.shapes.VRectangle;
import org.vadere.util.triangulation.adaptive.PSMeshingPanel;

import javax.swing.*;

/**
 * @author Benedikt Zoennchen
 */
public class TestMeshManipulations {

	/**
	 * Building a geometry containing 2 triangles
	 * xyz and wyx
	 */
	private VPTriangulation triangulation;
	private VRectangle bound;

	@Before
	public void setUp() throws Exception {
		bound = new VRectangle(0, 0, 10, 10);
		triangulation = ITriangulation.createVPTriangulation(bound);
		triangulation.insert(new VPoint(0,0));
		triangulation.insert(new VPoint(2,0));
		triangulation.insert(new VPoint(1, 1.5));
		triangulation.insert(new VPoint(4,2.5));
		triangulation.insert(new VPoint(2, 2.6));
		triangulation.insert(new VPoint(10, 10));
		triangulation.insert(new VPoint(10, 0));
		triangulation.insert(new VPoint(0, 10));
		triangulation.insert(new VPoint(6, 8));
		triangulation.finish();
	}

	@Test
	public void testRemoveFace() {

	}

	public static void main(String... args) {
		TestMeshManipulations test = new TestMeshManipulations();
		try {
			test.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}

		test.triangulation.removeFace(test.triangulation.locateFace(3, 5).get(), true);
		test.triangulation.removeFace(test.triangulation.locateFace(8, 3).get(), true);
		test.triangulation.removeFace(test.triangulation.locateFace(4, 5).get(), true);
		test.triangulation.removeFace(test.triangulation.locateFace(4, 9).get(), true);
		//PFace<VPoint> face = ;
		PSMeshingPanel<VPoint, PVertex<VPoint>, PHalfEdge<VPoint>, PFace<VPoint>> panel = new PSMeshingPanel<>(test.triangulation.getMesh(),
				f -> test.triangulation.getMesh().isHole(f), 800, 800, test.bound);
		JFrame frame = panel.display();
		frame.setVisible(true);
		panel.repaint();
	}
}
