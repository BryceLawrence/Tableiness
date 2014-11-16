/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Saves a Node to an Image, either to the Clipboard or to a file.
 * @author Garrett Roberts
 * @author with help from Drew Mecham
 */
public class FXImaging
{
   /**
    * For lining up the capturing; shove it a bit to the right
    */
   private final int X_OFFSET = 13;
   
   /**
    * It looks better if we don't capture the WHOLE node, so we'll
    * shave a bit off
    */
   private final int X_SIZE = -10;
   private final int Y_SIZE = -5;
   
   private JFXPanel mFxPanel;
   private JFrame mFrame;
   private Stage mStage;
   private Scene mScene;
   private Node mNode;
   private BoundingBox mBound;
   
   /**
    *  
    * @param pNode The Node to be saved.
    * @param pWidth Width of image to be saved.
    * @param pHeight Height of image to be saved.
    */
   public FXImaging(final Node pNode, double pNodeWidth, double pNodeHeight)
   {
      mNode = pNode;
      mStage = (Stage) pNode.getScene().getWindow();
      mScene = pNode.getScene();
      Point2D nodePos = mNode.localToScene(0, 0);
      mBound = new BoundingBox(nodePos.getX(), nodePos.getY(), 
         pNodeWidth, pNodeHeight);
      mFrame = new JFrame();
      mFxPanel = new JFXPanel();
   }

   /**
    * Copies an image to the system clipboard without using
    * an awt thread.
    */
   public void copy()
   {
      Image image = captureFXImage();
      ClipboardContent cc = new ClipboardContent();
      cc.putImage(image);
      Clipboard.getSystemClipboard().setContent(cc);
   }
   
   /**
    * Saves an image to a file.
    * @param pImage The image to save.
    * @param pFilename The image filename.
    */
   public void save(BufferedImage pImage, String pFilename)
   {
      try
      {
         // File needs to be in .png format to save properly.
         if (!(pFilename.endsWith(".png")))
         {
            pFilename += ".png";
         }
         ImageIO.write(pImage, "png", new File(pFilename));
      }
      catch (IOException e)
      {
      }
   }

   /**
    * Captures an awt image from the screen without using an awt thread.
    * @return The BufferedImage of the graph.
    */
   public BufferedImage captureAwtImage()
   {
      Point2D nodePos = mNode.localToScene(0, 0);
      Bounds b = mNode.getBoundsInParent();
      int x = (int)Math.round(
         mStage.getX() + nodePos.getX() + X_OFFSET + mScene.getX());
      int y = (int)Math.round(
         mStage.getY() + nodePos.getY() + mScene.getY());
      int w = (int)Math.round(b.getWidth() + X_SIZE);
      int h = (int)Math.round(b.getHeight() + Y_SIZE);
      BufferedImage bi = null;
      try
      {
         java.awt.Robot robot = new java.awt.Robot();
         bi = robot.createScreenCapture(
            new java.awt.Rectangle(x, y, w, h));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return bi;
   }
   
   /**
    * Captures an image from the screen and converts it to a JavaFX image.
    * @return The Image of the graph.
    */
   private Image captureFXImage()
   {
      BufferedImage bi = captureAwtImage();
      java.io.ByteArrayOutputStream stream =
         new java.io.ByteArrayOutputStream();
      Image image = null;
      try
      {
         ImageIO.write(bi, "png", stream);
         image = new Image(new java.io.ByteArrayInputStream(
            stream.toByteArray()), bi.getWidth(), bi.getHeight(), true, true);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return image;
   }
   
   /**
    * A utility method to convert an awt Image into a Javafx image.
    * @param image A java.awt.Image.
    * @return A javafx.scene.image.Image.
    */
   public static javafx.scene.image.Image createImage(java.awt.Image image)
      throws IOException 
   {
      // Just in case it's not a BufferedImage
      if (!(image instanceof RenderedImage)) 
      {
         BufferedImage bufferedImage = new BufferedImage(
            image.getWidth(null), 
            image.getHeight(null), 
            BufferedImage.TYPE_INT_ARGB);
         Graphics g = bufferedImage.createGraphics();
         g.drawImage(image, 0, 0, null);
         g.dispose();
    
         image = bufferedImage;
      }
      return SwingFXUtils.toFXImage((BufferedImage) image, null);
   }
}

