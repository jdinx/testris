/*
 * Jeremiah Stowe
 * Spring 2013
 */
package view;

/**
 * Main entry point for the tetris program.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
public final class TetrisMain
{

  /**
   * Private constructor to prevent instantiation.
   */
  private TetrisMain()
  {
    
  }

  /**
   * Main entry point for the program.
   * @param the_args Arguments passed from the system.
   */
  public static void main(final String[] the_args)
  {
    final TetrisGUI gui = new TetrisGUI();
    gui.start();

  }

}
