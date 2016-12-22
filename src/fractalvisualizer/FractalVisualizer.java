/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalvisualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author Ben Rhuman
 * 12/24/16
 */
public class FractalVisualizer {

    public static void main(String[] args) {
        FractalFrame fv = new FractalFrame();

        //Opens up frame in Full Screen mode
//        fv.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        fv.setUndecorated(true);
//        fv.setVisible(true);
//        
        //Lamda expression to close window when ESC keystroke occurs
        KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        int w = JComponent.WHEN_IN_FOCUSED_WINDOW;
        fv.getRootPane().registerKeyboardAction(e -> System.exit(0), esc, w);  //Might make ESC just go to windowed mode

    }

}

class FractalFrame extends JFrame {

    public FractalFrame() {
        super("Fractal Visualizer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add a new FractalPanel to the JFrame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new FractalPanel(), BorderLayout.CENTER);
        fullScreen();
    }

    //Allows the user to switch to fullscreen
    private void fullScreen() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }
}

    //Possibly will be used to make frame size changes
//    private void downSize(){
//        //setVisible(false);
//        setExtendedState(NORMAL);
//        //setUndecorated(false);
//        setVisible(true);
//        fullscreen = false;
//    }
//    
//    //Toggles the size of the screen
//    public void changeSize(){
//        if(fullscreen){
//            downSize();
//        } else {
//            fullScreen();
//        }
//    }
//}
