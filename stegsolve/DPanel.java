/*
 * DPanel.java
 */

package stegsolve;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

/**
 * A JPanel with an image attached to it
 * @author Caesum
 */
public class DPanel extends JPanel
{
    /**
     * The image attached to this panel
     */
    private BufferedImage bi = null;

    /**
     * Constructor
     */
    DPanel()
    {
    }

    /**
     * Overridden paint method for the panel which
     * paints the image on the panel
     * @param g graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(bi!=null)
          g.drawImage(bi, 0, 0, this);
    }

    /**
     * Sets the image for the panel, and calls
     * repaint
     * @param bix Image to show on the panel
     */
    public void setImage(BufferedImage bix)
    {
        bi = bix;
        setSize(bi.getWidth(), bi.getHeight());
        repaint();
    }

}
