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
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.AbstractPiece;
import model.Block;
import model.MutablePiece;

/**
 * This is the panel that the board is drawn on using Graphics2D. Setters
 * and helper methods in place to find blocks to draw.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
@SuppressWarnings("serial")
public class BoardDisplay extends JPanel
{
  /**
   * String to point to the CYAN image file.
   */
  private static final String CYAN_BLOCK = "CYAN_BLOCK.png";
  /**
   * String to point to the GREEN image file.
   */
  private static final String GREEN_BLOCK = "GREEN_BLOCK.png";
  /**
   * String to point to the ORANGE image file.
   */
  private static final String ORANGE_BLOCK = "ORANGE_BLOCK.png";
  /**
   * String to point to the YELLOW image file.
   */
  private static final String YELLOW_BLOCK = "YELLOW_BLOCK.png";
  /**
   * String to point to the PINK image file.
   */
  private static final String PINK_BLOCK = "PINK_BLOCK.png";
  /**
   * String to point to the RED image file.
   */
  private static final String RED_BLOCK = "RED_BLOCK.png";
  /**
   * String to point to the BLUE image file.
   */
  private static final String BLUE_BLOCK = "BLUE_BLOCK.png";
  /**
   * String to point to the Background image file.
   */
  private static final String BACKGROUND = "BackGround.png";
  /**
   * File name for the pause screen.
   */
  private static final String PAUSE_SCREEN = "PAUSE_SCREEN.jpg";
  /**
   * String that directs to the game over image.
   */
  private static final String GAME_OVER = "Game_Over_op1.png";
  /**
   * Thickness of the border for the preview next piece box.
   */
  private static final float BORDER_THICKNESS = 2f;
  /**
   * Row correction factor to put the piece 
   * coordinates in place with the board.
   */
  private static final int ROW_CORRECTION = 19;
  /**
   * Constant for iterating through the block layout when drawing
   * to make sure all 4 are drawn.
   */
  private static final int BLOCKS = 3;
  /**
   * Size in pixels to scale each drawn block by.
   */
  private static final int LENGTH_FACTOR = 20;
  /**
   *Aspect ratio for resizing game board.
   */
  private static final double ASPECT_RATIO = 0.5;
  /**
   * Keep track of game over status.
   */
  private boolean my_game_over;
  /**
   * Keep track of paused or not.
   */
  private boolean my_pause_status;
  /**
   * Image to display the game over overlay.
   */
  private BufferedImage my_game_over_image;
  /**
   * Image to display on board when game is paused.
   */
  private BufferedImage my_pause_image;
  /**
   * Background image drawn behind the blocks.
   */
  private BufferedImage my_background;
  /**
   * Map to convert the Color class scheme of each block to images.
   */
  private Map<Color, BufferedImage> my_color_to_image;
  /**
   * Square length to draw each block determined by panel size.
   */
  private double my_piece_size;
  /**
   * Current active piece being drawn on the board.
   */
  private MutablePiece my_current_piece;
  /**
   * List of all the frozen blocks on the board.
   */
  private List<Block[]> my_frozen_blocks;
  
  /**
   * Basic no argument constructor to initialize fields and set preferred size.
   */
  public BoardDisplay()
  {
    super();
    my_piece_size = LENGTH_FACTOR;
    this.setPreferredSize(new Dimension((int) (LENGTH_FACTOR * LENGTH_FACTOR * ASPECT_RATIO),
                                       LENGTH_FACTOR * LENGTH_FACTOR));
    this.setBackground(Color.BLACK);
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
      my_background = ImageIO.read(new File(BACKGROUND));
      my_game_over_image = ImageIO.read(new File(GAME_OVER));
      my_pause_image = ImageIO.read(new File(PAUSE_SCREEN));
      green_block = ImageIO.read(new File(GREEN_BLOCK));
      red_block = ImageIO.read(new File(RED_BLOCK));
      cyan_block = ImageIO.read(new File(CYAN_BLOCK));
      yellow_block = ImageIO.read(new File(YELLOW_BLOCK));
      orange_block = ImageIO.read(new File(ORANGE_BLOCK));
      pink_block = ImageIO.read(new File(PINK_BLOCK));
      blue_block = ImageIO.read(new File(BLUE_BLOCK));
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
    this.setSize((int) (this.getHeight() * ASPECT_RATIO), this.getHeight());
    my_piece_size = (double) this.getHeight() / LENGTH_FACTOR;
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawImage(my_background, 0, 0, this.getWidth(), this.getHeight(), null);
    drawCurrentPiece(g2d);
    drawFrozen(g2d);
    g2d.setStroke(new BasicStroke(BORDER_THICKNESS));
    g2d.setColor(Color.WHITE);
    g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
    
    if (my_game_over)
    {
      g2d.drawImage(my_game_over_image, 0, 0, this.getWidth(), this.getHeight(), null);
    }
    if (my_pause_status)
    {
      g2d.drawImage(my_pause_image, 0, 0, this.getWidth(), this.getHeight(), null);
    }
  }
  
  /**
   * Method to draw all the frozen blocks in place.
   * @param the_graphics The graphics that are being drawn to.
   */
  public void drawFrozen(final Graphics2D the_graphics)
  {

    final Graphics2D g2d = the_graphics;
    if (my_frozen_blocks != null && !my_frozen_blocks.isEmpty())
    {
      final Rectangle2D.Double piece = new Rectangle2D.Double();
      for (int i = 0; i < my_frozen_blocks.size(); i++)
      {
        final Block[] row = my_frozen_blocks.get(i);
        for (int j = 0; j < row.length; j++)
        {
          if (row[j] != Block.EMPTY)
          {
            piece.setRect(j * my_piece_size, ROW_CORRECTION * my_piece_size - i 
                          * my_piece_size, my_piece_size, my_piece_size);
            g2d.setColor(row[j].getColor());
            g2d.setColor(Color.BLACK);
            g2d.drawImage(my_color_to_image.get(row[j].getColor()), (int) (j * my_piece_size), 
                          (int) (ROW_CORRECTION * my_piece_size - i 
                          * my_piece_size), (int) my_piece_size, (int) my_piece_size, null);
          }
        }
      }
    }
  }
  
  /**
   * Draws the squares for the current piece moving on the board.
   * @param the_graphics The graphics object that is being drawn to.
   */
  public void drawCurrentPiece(final Graphics2D the_graphics)
  {
    final Graphics2D g2d = the_graphics;
    if (my_current_piece != null)
    {
      final Rectangle2D.Double piece = new Rectangle2D.Double();
      for (int i = 0; i <= BLOCKS; i++)
      {
        
        for (int j = 0; j <= BLOCKS; j++)
        {
          if (pieceAt(my_current_piece.getX() + i, my_current_piece.getY() + j))
          {
            g2d.setColor(((AbstractPiece) my_current_piece).getBlock().getColor());
            piece.setRect(my_current_piece.getX() * my_piece_size + i * my_piece_size,
                          ROW_CORRECTION * my_piece_size - my_current_piece.getY()
                          * my_piece_size - j * my_piece_size, my_piece_size, my_piece_size);
            //g2d.fill(piece);
            g2d.setColor(Color.BLACK);
            //g2d.draw(piece);
            g2d.drawImage(my_color_to_image.get(((AbstractPiece)
                my_current_piece).getBlock().getColor()),
                (int) (my_current_piece.getX() * my_piece_size + i * my_piece_size), 
                (int) (ROW_CORRECTION * my_piece_size - my_current_piece.getY()
                * my_piece_size - j * my_piece_size),
                (int) my_piece_size, (int) my_piece_size, null);
            
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
    final int[][] blocks = ((AbstractPiece) my_current_piece).getBoardCoordinates();

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
   * Getter to find what the current block size is being drawn at.
   * @return Returns the size of the blocks WIDTH/HEIGHT.
   */
  public Double getPieceSize()
  {
    return my_piece_size; 
  }
  
  /**
   * Setter to update the current piece being drawn.
   * @param the_piece The new piece that is active on the board.
   */
  public void setCurrentPiece(final MutablePiece the_piece)
  {
    my_current_piece = the_piece;
  }
  
  /**
   * Setter to access the frozen blocks and iterate through for drawing.
   * @param the_blocks The list of frozen blocks from the board.
   */
  public void setFrozenBlocks(final List<Block[]> the_blocks)
  {
    my_frozen_blocks = the_blocks;
  }
  /**
   * Setter to change the paused status to determine what to draw.
   * @param the_pause_status Boolean is true when paused.
   */
  public void setPaused(final boolean the_pause_status)
  {
    my_pause_status = the_pause_status;
  }
  /**
   * Sets game over status to draw text overlay on board.
   * @param the_game_is_over Boolean true if game is over.
   */
  public void setGameOver(final boolean the_game_is_over)
  {
    my_game_over = the_game_is_over;
  }
}
