/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalvisualizer;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Ben
 */
public class FractalPanel extends JPanel {

    private FractalImage image;

    int magnification = 10;

    public FractalPanel() {
        setLayout(new BorderLayout());

        //Generates the fractal image
        image = new FractalImage();

        //Displays it on the panel
        add(image, BorderLayout.CENTER);
        addMouseListener(new TTTMouseListener());

        //Screen capture keystroke
        KeyStroke p = KeyStroke.getKeyStroke(KeyEvent.VK_P, 0);
        int w = JComponent.WHEN_IN_FOCUSED_WINDOW;
        registerKeyboardAction(e -> image.screenCapture(), p, w);
    }

    private class TTTMouseListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press");
        }

        public void mouseReleased(MouseEvent e) {
            //System.out.println("release");
        }

        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse entered");
        }

        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse exited");
        }

        public void mouseClicked(MouseEvent e) {
            //System.out.println("mouse clicked");

            //get click data from the GUI and convert to spot reference
            int x = e.getX();
            int y = e.getY();

            System.out.println("spot " + x + ", " + y);
            if (e.getButton() == MouseEvent.BUTTON1) {
                image.zoomIn(x, y, magnification);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                image.zoomOut(x, y, magnification);
            }
        }
    }
    
    public FractalImage getImage(){
        return image;
    }
}
