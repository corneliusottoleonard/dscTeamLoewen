package org.vadere.util.tex;

import org.jetbrains.annotations.NotNull;
import org.vadere.util.geometry.mesh.inter.IFace;
import org.vadere.util.geometry.mesh.inter.IHalfEdge;
import org.vadere.util.geometry.mesh.inter.IMesh;
import org.vadere.util.geometry.mesh.inter.IVertex;
import org.vadere.util.geometry.shapes.IPoint;
import org.vadere.util.geometry.shapes.VLine;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.geometry.shapes.VTriangle;

import java.util.List;
import java.util.stream.Collectors;

public class TexGraphGenerator {

	public static <P extends IPoint, V extends IVertex<P>, E extends IHalfEdge<P>, F extends IFace<P>> String toTikz(
			@NotNull final IMesh<P, V, E, F> mesh){
		StringBuilder builder = new StringBuilder();
		builder.append("\\begin{tikzpicture}[scale=1.0]\n");

		for(VPoint point : mesh.getUniquePoints()) {
			//builder.append("\\draw[fill=black] ("+point.getX()+","+point.getY()+") circle (3pt); \n");
		}

		builder.append("\\draw ");

		for(VLine line : mesh.getLines()) {
			builder.append("("+line.getX1()+","+line.getY1()+") -- ("+line.getX2()+","+line.getY2()+")\n");
		}

		builder.append(";\n");

		builder.append("\\end{tikzpicture}");

		return builder.toString();
	}

	public static <P extends IPoint, V extends IVertex<P>, E extends IHalfEdge<P>, F extends IFace<P>> String toTikz(
			@NotNull final IMesh<P, V, E, F> mesh,
			@NotNull final List<F> faces) {
		StringBuilder builder = new StringBuilder();
		builder.append("\\begin{tikzpicture}[scale=1.0]\n");

		builder.append("\\draw[gray, thick] ");

		for(F face : faces) {
			List<VLine> lines = mesh.streamEdges(face).map(e -> mesh.toLine(e)).collect(Collectors.toList());

			for(VLine line : lines) {
				builder.append("("+line.getX1()+","+line.getY1()+") -- ("+line.getX2()+","+line.getY2()+")\n");
			}
		}

		builder.append(";\n");
		builder.append("\n");

		builder.append("\\draw[black, thick] ");
		VPoint prefIncenter = null;
		VLine firstLine = null;
		VLine lastLine = null;
		for(F face : faces) {
			List<E> edges = mesh.getEdges(face);

			// is triangle
			if(edges.size() == 3) {
				VTriangle triangle = mesh.toTriangle(face);
				VPoint incenter = triangle.getIncenter();

				if(prefIncenter != null) {
					builder.append("("+prefIncenter.getX()+","+prefIncenter.getY()+") -- ("+incenter.getX()+","+incenter.getY()+")\n");
					if(firstLine == null) {
						firstLine = new VLine(prefIncenter, incenter);
					}

					lastLine = new VLine(prefIncenter, incenter);
				}

				prefIncenter = incenter;
			}
		}

		builder.append(";\n");
		if(firstLine != null && lastLine != null) {
			builder.append("\\draw[-{Latex[length=3mm]}]("+firstLine.getX1()+","+firstLine.getY1()+") -- ("+firstLine.getX2()+","+firstLine.getY2()+");\n");
			builder.append("\\draw[-{Latex[length=3mm]}]("+lastLine.getX1()+","+lastLine.getY1()+") -- ("+lastLine.getX2()+","+lastLine.getY2()+");\n");
		}
		builder.append("\\end{tikzpicture}");
		return builder.toString();
	}

}

/*

%% vertices
\draw[fill=black] (0,0) circle (3pt);
\draw[fill=black] (4,0) circle (3pt);
\draw[fill=black] (2,1) circle (3pt);
\draw[fill=black] (2,3) circle (3pt);
%% vertex labels
\node at (-0.5,0) {1};
\node at (4.5,0) {2};
\node at (2.5,1.2) {3};
\node at (2,3.3) {4};

\begin{tikzpicture}[thick,scale=0.8]
    % The following path utilizes several useful tricks and features:
    % 1) The foreach statement is put inside a path, so all the edges
    %    will in fact be a the same path.
    % 2) The node construct is used to draw the nodes. Nodes are special
    %    in the way that they are drawn *after* the path is drawn. This
    %    is very useful in this case because the nodes will be drawn on
    %    top of the path and therefore hide all edge joins.
    % 3) Simple arithmetics can be used when specifying coordinates.
    \draw \foreach \x in {0,36,...,324}
    {
        (\x:2) node {}  -- (\x+108:2)
        (\x-10:3) node {} -- (\x+5:4)
        (\x-10:3) -- (\x+36:2)
        (\x-10:3) --(\x+170:3)
        (\x+5:4) node {} -- (\x+41:4)
    };
\end{tikzpicture}
 */