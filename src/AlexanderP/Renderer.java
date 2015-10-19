package AlexanderP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Renderer {
	public Renderer() {
	}

	public void drawAndHighlightAtoms(Graphics2D g, ArrayList<Atom> atomList,
			QuadTree root, int mouseX, int mouseY, boolean pressed) {
		for (int n = 0; n < atomList.size(); n++) {
			atomList.get(n).setColor(atomList.get(n).getMaterial().getColor());
		}
		root.highlightAtom(g, mouseX, mouseY, pressed, atomList);
		g.setColor(new Color(0, 0, 0));
		this.drawAtoms(g, atomList);
	}

	public void drawAtoms(Graphics2D g, ArrayList<Atom> atomList) {
		for (int p = 0; p < atomList.size(); p++) {
			atomList.get(p).draw(g);
		}
	}

	public void drawBonds(Graphics2D g, ArrayList<Bond> bondList,
			ArrayList<Atom> atomList) {
		for (int u = 0; u < bondList.size(); u++) {
			if (bondList.get(u).length <= bondList.get(u).getMaxDist()) {
				bondList.get(u).draw(g, atomList);
			}
		}
	}

	public void drawQuadTrees(Graphics2D g, QuadTree root) {
		g.setColor(new Color(0, 255, 255));
		root.draw(g, new Color(0, 255, 255));
	}

	public void drawMessage() {
	}
}
