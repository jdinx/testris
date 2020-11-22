/*
 * Jeremiah Stowe
 * Spring 2013
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.AbstractPiece;
import model.MutablePiece;

/**
 * Class for the next piece that will come into play.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
@SuppressWarnings("serial")
public class NextPiecePanel extends JPanel
{
  /**
   * Constant file name for the CYAN block.
   */
  private static final String CYAN_BLOCK = "CYAN_BLOCK.png";
  /**
   * Constant file name for the GREEN block.
   */
  private static final String GREEN_BLOCK = "GREEN_BLOCK.png";
  /**
   * Constant file name for the ORANGE block.
   */
  private static final String ORANGE_BLOCK = "ORANGE_BLOCK.png";
  /**
   * Constant file name for the YELLOW block.
   */
  private static final String YELLOW_BLOCK = "YELLOW_BLOCK.png";
  /**
   * Constant file name for the PINK block.
   */
  private static final String PINK_BLOCK = "PINK_BLOCK.png";
  /**
   * Constant file name for the RED block.
   */
  private static final String RED_BLOCK = "RED_BLOCK.png";
  /**
   * Constant file name for the BLUE block.
   */
  private static final String BLUE_BLOCK = "BLUE_BLOCK.png";
  /**
   * Constant file name for the background image.
   */
  private static final String BACKGROUND = "East.png";
  /**
   * Constant for iterating through the block layout when drawing
   * to make sure all 4 are drawn.
   */
  private static final int BLOCKS = 3;
  
  /**
   * Thickness of the border for the preview next piece box.
   */
  private static final float BORDER_THICKNESS = 2f;
  /**
   *Pixel width to scale the pieces with. 
   */
  private static final int LENGTH_FACTOR = 20;
  /**
   * Number of panel squares to use for the preview window.
   */
  private static final int PANEL_SIZE = 6;
  /**
   * Next piece that will be in play to be drawn in preview window.
   */
  private MutablePiece my_piece;
  /**
   * Size for drawing the individual blocks within the piece.
   */
  private Double my_piece_size;
  /**
   * Field to store the buffered background image.
   */
  private BufferedImage my_background;
  /**
   * Keep track of paused or not.
   */
  private boolean my_pause_status;
  /**
   * Map to relate block Color class type to the corresponding image file.
   */
  private Map<Color, BufferedImage> my_color_to_image;
  
  /**
   * Basic no arg constructor to initialize fields and set preferred size.
   */
  public NextPiecePanel()
  {
    super();
    this.setPreferredSize(new Dimension(LENGTH_FACTOR 
                                        * PANEL_SIZE, LENGTH_FACTOR * PANEL_SIZE));
    my_piece_size = (double) LENGTH_FACTOR;
    initiateFiles();
      
  }

  /**
   * Constructor helper method to open the image files.
   */
  private void initiateFiles()
  {
    BufferedImage pink_block = null;
    BufferedImage blue_block = null;
    BufferedImage green_block = null;
    BufferedImage red_block = null;
    BufferedImage cyan_block = null;
    BufferedImage yellow_block = null;
    BufferedImage orange_block = null;
    
    try
    {
      
      green_block = ImageIO.read(new File(GREEN_BLOCK));
      red_block = ImageIO.read(new File(RED_BLOCK));
      cyan_block = ImageIO.read(new File(CYAN_BLOCK));
      yellow_block = ImageIO.read(new File(YELLOW_BLOCK));
      orange_block = ImageIO.read(new File(ORANGE_BLOCK));
      pink_block = ImageIO.read(new File(PINK_BLOCK));
      blue_block = ImageIO.read(new File(BLUE_BLOCK));
      my_background = ImageIO.read(new File(BACKGROUND));
    }
    catch (final IOException exception)
    {
      exception.printStackTrace();
    }
    
    my_color_to_image = new HashMap<Color, BufferedImage>();
    my_color_to_image.put(Color.CYAN, cyan_block);
    my_color_to_image.put(Color.RED, red_block);
    my_color_to_image.put(Color.MAGENTA, pink_block);
    my_color_to_image.put(Color.ORANGE, orange_block);
    my_color_to_image.put(Color.YELLOW, yellow_block);
    my_color_to_image.put(Color.GREEN, green_block);
    my_color_to_image.put(Color.BLUE, blue_block);

  }
  
  /**
   * {@inheritDoc}
   */
  public void paintComponent(final Graphics the_graphics)
  {
    super.paintComponent(the_graphics);
    this.setSize((int) (my_piece_size * PANEL_SIZE), 
                 (int) (my_piece_size * (PANEL_SIZE - 2)));
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.drawImage(my_background, 0, 0, this.getWidth(), this.getHeight(), null);
    
    if (!my_pause_status)
    {
      drawNextPiece(g2d);
    }
    g2d.setStroke(new BasicStroke(BORDER_THICKNESS));
    g2d.setColor(Color.WHITE);
    g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
  }
  
  /**
   * Goes through the blocks and draws the blocks for the next piece.
   * @param the_graphics The graphics object being drawn to.
   */
  private void drawNextPiece(final Graphics2D the_graphics)
  {
    final Graphics2D g2d = the_graphics;
    if (my_piece != null)
    {
      final Rectangle2D.Double piece = new Rectangle2D.Double();
      
      for (int i = 0; i <= BLOCKS; i++)
      {
        
        for (int j = 0; j <= BLOCKS; j++)
        {
          if (pieceAt(my_piece.getX() + i, my_piece.getY() + j))
          {
            final Color piece_color = ((AbstractPiece) my_piece).getBlock().getColor();
            g2d.setColor(piece_color);
            if (piece_color == Color.YELLOW)
            {
              piece.setRect(this.getWidth() / BLOCKS  + i * my_piece_size,
                            this.getWidth() / 2 + my_piece_size - j * my_piece_size, 
                            my_piece_size, my_piece_size);
            
              g2d.drawImage(my_color_to_image.get(((AbstractPiece)
                  my_piece).getBlock().getColor()),
                  (int) (this.getWidth() / BLOCKS  + i * my_piece_size), 
                  (int) (this.getWidth() / 2 + my_piece_size - j * my_piece_size),
                  my_piece_size.intValue(), my_piece_size.intValue(), null);
            
            }
            else if (piece_color == Color.CYAN)
            {
              piece.setRect(this.getWidth() / (BLOCKS * 2)  + i * my_piece_size,
                            this.getHeight() / 2 + my_piece_size / 2 - j * my_piece_size / 2, 
                            my_piece_size, my_piece_size);
              
              g2d.drawImage(my_color_to_image.get(((AbstractPiece)
                  my_piece).getBlock().getColor()),
                  (int) (this.getWidth() / (BLOCKS * 2)  + i * my_piece_size), 
                  (int) (this.getHeight() / 2 + my_piece_size / 2 - j * my_piece_size / 2),
                  my_piece_size.intValue(), my_piece_size.intValue(), null);
            
            }
            else
            {
              g2d.drawImage(my_color_to_image.get(((AbstractPiece)
                  my_piece).getBlock().getColor()),
                  (int) (this.getWidth() / (2 * 2)  + i * my_piece_size), 
                  (int) (this.getWidth() / 2 + my_piece_size - j * my_piece_size),
                  my_piece_size.intValue(), my_piece_size.intValue(), null);
            }
            //g2d.fill(piece);
            //g2d.setColor(Color.BLACK);
            //g2d.draw(piece);
          }
        }
      }
    }
    
  }


  /**
   * Checks to see where the blocks are at on the grid returns true when found.
   * @param the_x The X coordinate being checked.
   * @param the_y The Y coordinate being checked.
   * @return Returns true if block is found false else.
   */
  public boolean pieceAt(final int the_x, final int the_y)
  {
    boolean result = false;
    final int[][] blocks = ((AbstractPiece) my_piece).getBoardCoordinates();

    for (int block = 0; block < blocks.length; block++)
    {
      if (blocks[block][1] == the_y && blocks[block][0] == the_x)
      {
        result = true;
      }
    }
    return result;
  }
  
  /**
   * Setter to access the current piece for drawing on this panel.
   * @param the_piece The piece that is being set to draw.
   */
  public void setNextPiece(final MutablePiece the_piece)
  {
    my_piece = the_piece;
  }

  /**
   * Sets the size of the individual block for the pieces being drawn.
   * @param the_size The size of the blocks.
   */
  public void setPieceSize(final Double the_size)
  {
    my_piece_size = the_size;
  }
  /**
   * Setter to change the paused status to determine what to draw.
   * @param the_pause_status Boolean is true when paused.
   */
  public void setPaused(final boolean the_pause_status)
  {
    my_pause_status = the_pause_status;
  }
  
}
