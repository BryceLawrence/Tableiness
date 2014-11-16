/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

import java.io.*;
import java.awt.Container;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

import javafx.scene.image.Image;
import be.ugent.caagt.jmathtex.*;
import java.awt.Color;

/**
 * Gets TeX formula images.
 *
 * @author Brother Neff
 */
public class ImageGetter
{
   /**
    * Gets an Image using the JMathTeX library, parsing a TeX formula string.
    * @param The LaTeX formula to parse.
    * @return An Image of the formula, using the default size (18).
    */
   public static Image getTeXImage(String pFormula)
   {
      return getTeXImage(pFormula, 18.0f);
   }

   /**
    * Gets an Image using the JMathTeX library, parsing a TeX formula string.
    * @param The TeX formula to parse.
    * @param size the approximate point size of the formula.
    * @return An Image of the formula.
    */
   public static Image getTeXImage(String pFormula, float size)
   {
      TeXFormula formula = new TeXFormula(pFormula);
      formula.setColor(Color.black);
      formula.centerOnAxis();
      // int width = 190; // TODO: figure this more intelligently
      // int height = ((size > 18) ? 70 : 40); // TODO: ditto
      Icon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);
      // TODO: perhaps base width and height on
      //       icon.getIconWidth() and icon.getIconHeight()
      int width = icon.getIconWidth();
      int height = icon.getIconHeight();
      BufferedImage image = 
         new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Container container = new Container();
      icon.paintIcon(container, image.getGraphics(), 0, 0);
      Image fxImage = null;
      try
      {
         fxImage = FXImaging.createImage(image);
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
      return fxImage;
   }
}
