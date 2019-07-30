package uk.co.awesomepens.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ImagePanel extends JPanel{

    private BufferedImage image;
    private JFrame parent;
    
	public ImagePanel() {

	}
    
	public ImagePanel(JFrame parent) {
		this.parent = parent;
	}

    public ImagePanel(JFrame parent, String imagePath) {
       this.parent = parent;
    	try {                
          image = ImageIO.read(new File(imagePath));
			
       } catch (IOException ex) {
            // handle exception...
       }
    }
    
    public void setImage(String imagePath) {
    	try {                
            image = ImageIO.read(new File(imagePath));
  			
         } catch (IOException ex) {
              // handle exception...
         }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

}