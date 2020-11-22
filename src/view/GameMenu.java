/*
 * Jeremiah Stowe
 * Spring 2013
 */
package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 * JMenuBar that is attached to the Tetris GUI.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
@SuppressWarnings("serial")
public class GameMenu extends JMenuBar
{
  /**
   * Difficulty levels to choose from..
   */
  private static final int[] DIFFICULTIES = {1, 2, 3, 4, 5, 6};
  /**
   * Menu that holds the difficulties.
   */
  private JMenu my_difficulty_menu;
  /**
   * New game item.
   */
  private JMenuItem my_new_game;
  /**
   * Pause menu item.
   */
  private JMenuItem my_pause;
  /**
   * End game item.
   */
  private JMenuItem my_end_game;
  /**
   * Exit menu item.
   */
  private JMenuItem my_exit;
  /**
   * Control menu item.
   */
  private JMenuItem my_controls;
  /**
   * List of the difficulties menu items.
   */
  private List<JMenuItem> my_difficulties;
  /**
   * Menu item for the rock music.
   */
  private JMenuItem my_rock;
  /**
   * Menu item for the alternative music.
   */
  private JMenuItem my_alternative;
  /**
   * Menu item for the techno music.
   */
  private JMenuItem my_techno;
  /**
   * Menu item for the traditional music.
   */
  private JMenuItem my_traditional;
  /**
   * Menu item for the boss music.
   */
  private JMenuItem my_ff7;
  /**
   * Menu item for the pop music.
   */
  private JMenuItem my_pop;
  
  /**
   * Basic no argument constructor initializes the menu bar.
   */
  public GameMenu()
  {
    super();
    initializeComponents();
  }

  /**
   * Does all the work to attach and initialize the menu items.
   */
  private void initializeComponents()
  {
    final JMenu file_menu = new JMenu("Game");
    my_new_game = new JMenuItem("New Game");
    my_pause = new JMenuItem("Pause");
    my_end_game = new JMenuItem("End Game");
    my_exit = new JMenuItem("Exit");
    file_menu.add(my_new_game);
    file_menu.add(my_pause);
    file_menu.add(my_end_game);
    file_menu.addSeparator();
    file_menu.add(my_exit);
    this.add(file_menu);

    final JMenu music_menu = new JMenu("Music");
    my_traditional = new JMenuItem("Traditional");
    my_rock = new JMenuItem("Rock");
    my_alternative = new JMenuItem("Alternative");
    my_techno = new JMenuItem("Techno");
    my_ff7 = new JMenuItem("Boss Fight");
    my_pop = new JMenuItem("Pop");
    music_menu.add(my_traditional);
    music_menu.add(my_rock);
    music_menu.add(my_alternative);
    music_menu.add(my_techno);
    music_menu.add(my_ff7);
    music_menu.add(my_pop);
    
    this.add(music_menu);
    my_difficulty_menu = new JMenu("Difficutly");
    this.add(my_difficulty_menu);
    
    final ButtonGroup group_difficulties = new ButtonGroup();
    my_difficulties = new ArrayList<>();
    

    for (final Integer s : DIFFICULTIES)
    {
      my_difficulties.add(new JRadioButtonMenuItem(s.toString()));
      my_difficulties.get(s - 1).setMnemonic(KeyEvent.VK_1 + s - 1);
      if (s == 1)
      {
        my_difficulties.get(s - 1).setSelected(true);
      }
      
      group_difficulties.add(my_difficulties.get(s - 1));
      my_difficulty_menu.add(my_difficulties.get(s - 1));
    }
    
    
    
    
    final JMenu help_menu = new JMenu("Help");
    my_controls = new JMenuItem("Controls");
    help_menu.add(my_controls);
    this.add(help_menu);
    
  }
  /**
   * Method to get the difficulty level menu items.
   * @return Returns difficulty level items.
   */
  public List<JMenuItem> getDifficulties()
  {
    return my_difficulties;
  }
  
  /**
   * Attaches a listener to the pause item.
   * @param the_listener Listener to attach.
   */
  public void addPauseListener(final ActionListener the_listener)
  {
    my_pause.addActionListener(the_listener);
    my_pause.setAccelerator(KeyStroke.getKeyStroke('p'));
  }
  /**
   * Attaches a listener to the end game item.
   * @param the_listener Listener to attach.
   */
  public void addEndGameListener(final ActionListener the_listener)
  {
    my_end_game.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the new game item.
   * @param the_listener Listener to attach.
   */
  public void addNewGameListener(final ActionListener the_listener)
  {
    my_new_game.addActionListener(the_listener);
    my_new_game.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
  }
  /**
   * Attaches a listener to the exit game item.
   * @param the_listener Listener to attach.
   */
  public void addExitListener(final ActionListener the_listener)
  {
    my_exit.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the control display item.
   * @param the_listener Listener to attach.
   */
  public void addControlListener(final ActionListener the_listener)
  {
    my_controls.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the traditional item.
   * @param the_listener Listener to attach.
   */
  public void addTraditionalListener(final ActionListener the_listener)
  {
    my_traditional.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the rock item.
   * @param the_listener Listener to attach.
   */
  public void addRockListener(final ActionListener the_listener)
  {
    my_rock.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the alternative item.
   * @param the_listener Listener to attach.
   */
  public void addAlternativeListener(final ActionListener the_listener)
  {
    my_alternative.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the Techno item.
   * @param the_listener Listener to attach.
   */
  public void addTechnoListener(final ActionListener the_listener)
  {
    my_techno.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the boss item.
   * @param the_listener Listener to attach.
   */
  public void addBossListener(final ActionListener the_listener)
  {
    my_ff7.addActionListener(the_listener);
  }
  /**
   * Attaches a listener to the wow item.
   * @param the_listener Listener to attach.
   */
  public void addWowListener(final ActionListener the_listener)
  {
    my_pop.addActionListener(the_listener);
  }
}
