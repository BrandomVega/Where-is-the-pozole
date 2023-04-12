
package agentes;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class BackGroundPanel extends JPanel
{
    
    ImageIcon backGround;
    
    public BackGroundPanel(ImageIcon backGround)
    {
        super();
        this.backGround = backGround;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Dimension dim = this.getSize();
        int ancho = (int) dim.getWidth();
        int alto  = (int) dim.getHeight();
        g.drawImage(backGround.getImage(), 0, 0, ancho, alto, null);
        setOpaque(false);
        super.paintComponent(g);
    }
    
}
