/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalvisualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.Math.log;
import javax.imageio.ImageIO;

/**
 *
 * @author Ben
 */
public class FractalImage extends JPanel {

    private BufferedImage canvas;
    
    //Screen capture
    int captureNum = 0;

    //Window information
    private double X_LOW = -2.5;
    private double X_HIGH = 1.0;
    private double Y_LOW = -1.0;
    private double Y_HIGH = 1.0;
    private int windowX = 1920;
    private int windowY = 1080;

    public FractalImage() {

        Dimension dims = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(dims);
        canvas = mandelbrotSet(); //1080p
        //canvas = mandelbrotSet(3840,2160); //4K
        //canvas = mandelbrotSet(7680,4320); //8K
        //screenCapture();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), this);
    }

    private BufferedImage mandelbrotSet() {
//        For each pixel (Px, Py) on the screen, do:
//{
//  x0 = scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale (-2.5, 1))
//  y0 = scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale (-1, 1))
//  x = 0.0
//  y = 0.0
//  iteration = 0
//  max_iteration = 1000
//  while (x*x + y*y < 2*2  AND  iteration < max_iteration) {
//    xtemp = x*x - y*y + x0
//    y = 2*x*y + y0
//    x = xtemp
//    iteration = iteration + 1
//  }
//  color = palette[iteration]
//  plot(Px, Py, color)
//}
        Color[] palette = createPalette();
//        new Color[6];
//        palette[1] = Color.BLUE;
//        palette[2] = Color.CYAN;
//        palette[5] = Color.ORANGE;
//        palette[0] = Color.YELLOW;
//        palette[3] = Color.MAGENTA;
//        palette[4] = Color.RED;
        BufferedImage image = new BufferedImage(this.windowX, this.windowY, BufferedImage.TYPE_INT_ARGB);

        double x0;
        double y0;
        double x;
        double y;
        double deltaX;
        double deltaY;
        double iteration;

        //Constants
        int MAX_ITERATION = 1000;

        for (int i = 0; i < windowX; i++) {
            for (int j = 0; j < windowY; j++) {
                x = 0.0;
                y = 0.0;
                deltaX = (X_HIGH - X_LOW) / windowX;
                deltaY = (Y_HIGH - Y_LOW) / windowY;
                x0 = X_LOW + deltaX * i;
                y0 = Y_LOW + deltaY * j;
                iteration = 0;

                while (x * x + y * y < (1 << 16) && iteration < MAX_ITERATION) {
                    double xtemp = x * x - y * y + x0;
                    y = 2 * x * y + y0;
                    x = xtemp;
                    iteration++;
                }
                if (iteration == MAX_ITERATION) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    double log_z = log(x * x + y * y) / 2;
                    double nu = log(log_z / log(2)) / log(2);
                    iteration = iteration + 1 - nu;
                    Color color1 = palette[(int) iteration % palette.length];
                    Color color2 = palette[(int) (iteration + 1) % palette.length];
                    image.setRGB(i, j, linearInterpolation(color1, color2, iteration % 1).getRGB());
                }
            }
        }
        return image;
    }

    private Color linearInterpolation(Color color1, Color color2, double interpolation) {
        double red = color2.getRed() * interpolation + color1.getRed() * (1 - interpolation);
        double green = color2.getGreen() * interpolation + color1.getGreen() * (1 - interpolation);
        double blue = color2.getBlue() * interpolation + color1.getBlue() * (1 - interpolation);

        return new Color((int) red, (int) green, (int) blue);
    }

    private BufferedImage mandelbrotSetRoughShading() {
        Color[] palette = createPalette();//new Color[6];
//        palette[1] = Color.BLUE;
//        palette[2] = Color.CYAN;
//        palette[5] = Color.ORANGE;
//        palette[4] = Color.YELLOW;
//        palette[3] = Color.MAGENTA;
//        palette[0] = Color.RED;

        BufferedImage image = new BufferedImage(windowX, windowY, BufferedImage.TYPE_INT_ARGB);

        double x0;
        double y0;
        double x;
        double y;
        double deltaX;
        double deltaY;
        int iteration;

        //Constants
        int MAX_ITERATION = 1000;


        for (int i = 0; i < windowX; i++) {
            for (int j = 0; j < windowY; j++) {
                x = 0.0;
                y = 0.0;
                deltaX = (X_HIGH - X_LOW) / windowX;
                deltaY = (Y_HIGH - Y_LOW) / windowY;
                x0 = X_LOW + deltaX * i;
                y0 = Y_LOW + deltaY * j;
                iteration = 0;

                while (x * x + y * y < 2 * 2 && iteration < MAX_ITERATION) {
                    double xtemp = x * x - y * y + x0;
                    y = 2 * x * y + y0;
                    x = xtemp;
                    iteration++;
                }
                if (iteration == MAX_ITERATION) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, palette[(iteration % palette.length)].getRGB());
                }
            }
        }
        return image;
    }

    private Color[] createPalette() {
        Color[] palette = new Color[27];
        palette[0] = new Color(0, 0, 255);
        palette[1] = new Color(0, 0, 238);
        palette[2] = new Color(0, 0, 205);
        palette[3] = new Color(72, 118, 255);
        palette[4] = new Color(30, 144, 255);
        palette[5] = new Color(135, 206, 250);
        palette[6] = new Color(64, 224, 208);
        palette[7] = new Color(127, 255, 212);
        palette[8] = new Color(102, 205, 170);
        palette[9] = new Color(0, 250, 154);
        palette[10] = new Color(0, 255, 250);
        palette[11] = new Color(127, 255, 0);
        palette[12] = new Color(192, 255, 62);
        palette[13] = new Color(255, 255, 0);
        palette[14] = new Color(255, 246, 143);
        palette[15] = new Color(255, 127, 36);
        palette[16] = new Color(255, 69, 0);
        palette[17] = new Color(238, 59, 59);
        palette[18] = new Color(255, 48, 48);
        palette[19] = new Color(255, 0, 0);
        palette[20] = new Color(255, 131, 250);
        palette[21] = new Color(255, 0, 255);
        palette[22] = new Color(224, 102, 255);
        palette[23] = new Color(148, 0, 211);
        palette[24] = new Color(104, 34, 139);
        palette[25] = new Color(171, 130, 255);
        palette[26] = new Color(106, 90, 205);
        return palette;
    }

    public void screenCapture() {
        captureNum++;
        try {
            File f = new File("mandelbrot_"+ captureNum +".png");
            ImageIO.write(canvas, "PNG", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zoomIn(int x, int y, double magnification) {
        //Translates to the current window coordinates
        double deltaX = (X_HIGH - X_LOW) / windowX;
        double deltaY = (Y_HIGH - Y_LOW) / windowY;
        double x0 = X_LOW + deltaX * x;
        double y0 = Y_LOW + deltaY * y;

        //Sizes the new window based on maginification
        double xShift = 0.5 * Math.sqrt(((X_HIGH - X_LOW) * (X_HIGH - X_LOW)) / magnification);
        double yShift = 0.5 * Math.sqrt(((Y_HIGH - Y_LOW) * (Y_HIGH - Y_LOW)) / magnification);
        
        System.out.println("xShift: " + xShift);
        System.out.println("yShift: " + yShift);
        
        //Reset boundaries to reflect the zoom
        X_LOW = x0 - xShift;
        X_HIGH = x0 + xShift;
        Y_LOW = y0 - yShift;
        Y_HIGH = y0 + yShift;
        
        canvas = mandelbrotSet();
        repaint();
    }
    
    public void zoomOut(int x, int y, double magnification) {
        //Translates to the current window coordinates
        double deltaX = (X_HIGH - X_LOW) / windowX;
        double deltaY = (Y_HIGH - Y_LOW) / windowY;
        double x0 = X_LOW + deltaX * x;
        double y0 = Y_LOW + deltaY * y;

        //Sizes the new window based on maginification
        double xShift = 0.5 * (X_HIGH - X_LOW) * Math.sqrt(magnification);
        double yShift = 0.5 * (Y_HIGH - Y_LOW) * Math.sqrt(magnification);
        
        System.out.println("xShift: " + xShift);
        System.out.println("yShift: " + yShift);
        
        //Reset boundaries to reflect the zoom
        X_LOW = x0 - xShift;
        X_HIGH = x0 + xShift;
        Y_LOW = y0 - yShift;
        Y_HIGH = y0 + yShift;
        
        canvas = mandelbrotSet();
        repaint();
    }
    
}
