package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class to display score information in a panel.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel
{
  /**
   * The margin of space that the score is drawn from the left of panel.
   */
  private static final int SCORE_X_MARGIN = 10;
  /**
   * Proportional constant to maintain line spacing.
   */
  private static final int SPACING_FACTOR_BETWEEN_LINES = 4;
  /**
   * For setting the numbers with relation to text in labels.
   */
  private static final String TEXT_FORMATTER = ": ";
  /**
   * String to display in the level label.
   */
  private static final String LEVEL = "Level";
  /**
   * String to display in the line label.
   */
  private static final String LINES = "Lines";
  /**
   * String to display in the score label.
   */
  private static final String SCORE = "Score";
  /**
   * Factor that determines how many lines to next level.
   */
  private static final int LEVEL_FACTOR = 10;
  /**
   * Multiplier for setting the score.
   */
  private static final int MULTIPLIER = 100;
  /**
   * Label that will display the score.
   */
  private final JLabel my_score_label;
  /**
   * Label to display the lines cleared.
   */
  private final JLabel my_line_label;
  /**
   * Label to display the level.
   */
  private final JLabel my_level_label;
  /**
   * Number of lines cleared.
   */
  private int my_lines;
  /**
   * Current score.
   */
  private int my_score;
  /**
   * Current level.
   */
  private int my_level;
  /**
   * Level that each game starts at.
   */
  private int my_starting_level;
  /**
   * Background image that is displayed behind the scores.
   */
  private BufferedImage my_background;
  /**
   * Basic no arg constructor to initialize fields.
   */
  public ScorePanel()
  {
    super();
    my_level_label = new JLabel();
    my_line_label = new JLabel();
    my_score_label = new JLabel();
    my_starting_level = 1;
    updateLabels();
    setColors();
    reset();
    try
    {
      my_background = ImageIO.read(new File("East.png"));
    }
    catch (final IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**{@inheritDoc}*/
  public void paintComponent(final Graphics the_graphics)
  {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.drawImage(my_background, 0, 0, this.getWidth(), this.getHeight(), null);
    
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("ComicSans", Font.BOLD, this.getWidth() / SCORE_X_MARGIN));
    
    //Lots of ratios here to maintain line spacing as the font and panel resizes.
    g2d.drawString(my_level_label.getText(), SCORE_X_MARGIN, 
                   (SPACING_FACTOR_BETWEEN_LINES * SCORE_X_MARGIN) + SCORE_X_MARGIN);
    g2d.drawString(my_line_label.getText(), SCORE_X_MARGIN,
                   ((2 * SPACING_FACTOR_BETWEEN_LINES * SCORE_X_MARGIN) + SCORE_X_MARGIN) 
                   + this.getHeight() / (SCORE_X_MARGIN * 2));
    g2d.drawString(my_score_label.getText(), SCORE_X_MARGIN, 
                   ((2 * SPACING_FACTOR_BETWEEN_LINES * SCORE_X_MARGIN) + SCORE_X_MARGIN) 
                   + SCORE_X_MARGIN * SPACING_FACTOR_BETWEEN_LINES 
                   + this.getHeight() / SCORE_X_MARGIN);
    g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
  }
  
  /**
   * Private method to initialize colors for panel elements.
   */
  private void setColors()
  {
    my_level_label.setForeground(Color.WHITE);
    my_line_label.setForeground(Color.WHITE);
    my_score_label.setForeground(Color.WHITE);
    this.setBackground(Color.BLACK);
  }
  /**
   * Resets the values for the game.
   */
  public final void reset()
  {
    my_lines = 0;
    my_score = 0;
    my_level = 1;
    updateLabels();
  }

  /**
   * Setter to set the current level.
   * @param the_level The level being set.
   */
  public void setLevel(final int the_level)
  {
    my_level = the_level;
  }
  /**
   * Setter to store what the starting level is.
   * @param the_level The new starting level as an integer.
   */
  public void setStartingLevel(final int the_level)
  {
    my_starting_level = the_level;
  }
  
  /**
   * Update the number of lines that have been cleared
   * add the number of new lines being added.
   * @param the_lines The number lines to add to current lines.
   */
  public void updateLines(final int the_lines)
  {
    my_lines += the_lines;
  }

  /**
   * Updates the current level based on lines cleared.
   */
  public void updateLevel()
  {
    if (my_starting_level > 1)
    {
      my_level = my_starting_level + my_lines / LEVEL_FACTOR;
    }
    else
    {
      my_level = 1 + my_lines / LEVEL_FACTOR;
    }
  }
  
  /**
   * Adds to the score when a piece has been placed.
   */
  public void pieceLanded()
  {
    my_score += LEVEL_FACTOR + (my_level + 1);
    updateLabels();
  }
  

  /**
   * Add to the score and update with the number of lines
   * that are being cleared.
   * @param the_lines The number of lines being cleared to add to score.
   */
  public void updateScore(final int the_lines)
  {
    my_score += MULTIPLIER * the_lines * (my_level + 1);
  }
  
  /**
   * Getter to return current level.
   * @return Returns the level.
   */
  public int getLevel()
  {
    return my_level;
  }
  
  /**
   * Getter for the number of lines cleared.
   * @return Returns number of lines cleared.
   */
  public int getLines()
  {
    return my_lines;
  }
  
  /**
   * Getter for the current score.
   * @return Returns the score for the game.
   */
  public int getScore()
  {
    return my_score;
  }

  /**
   * Updates all relevant values based on number of new lines
   * that are being cleared. One shot method when lines are cleared.
   * @param the_lines The number of lines that were cleared.
   */
  public void updateValues(final int the_lines)
  {
    updateScore(the_lines);
    updateLines(the_lines);
    updateLevel();
    updateLabels();
  }
  

  /**
   * Update the labels with the current class fields.
   */
  private void updateLabels()
  {
    my_level_label.setText(LEVEL + TEXT_FORMATTER + my_level);
    my_line_label.setText(LINES + TEXT_FORMATTER + my_lines);
    my_score_label.setText(SCORE + TEXT_FORMATTER + my_score);  
  }

  
}
