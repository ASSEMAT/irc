package com.cfranc.irc.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class JImagePanel extends JPanel {
	
	private Image image;

	public JImagePanel() {
		super();
	}

	public JImagePanel(LayoutManager layout) {
		super(layout);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		this.setOpaque(false);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (this.image != null ){
			g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

}
