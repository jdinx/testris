/*
 * Jeremiah Stowe
 * Spring 2013
 */
package view;

import audio.Music;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.AbstractPiece;
import model.Board;


/**
 * Main display class for the testris GUI to create frame container.
 * @author Jeremiah Stowe
 * @version Spring 2013
 */
public class TetrisGUI implements Observer
{
  /**
   * Constant multiplier to increase the music by.
   */
  private static final float MUSIC_INCREASE = (float) 0.1;
  /**
   * Constant for minimum frame width.
   */
  private static final int MIN_WIDTH = 280;
  /**
   * Constant for minimum frame height.
   */
  private static final int MIN_HEIGHT = 400;
  /**
   * Timer delay between steps.
   */
  private static final int DELAY = 600;
  /**
   * Frame component.
   */
  private final JFrame my_frame;
  /**
   * Board that has the current game.
   */
  private Board my_board;
  /**
   * Menu bar to be added to GUI.
   */
  private final GameMenu my_menu;
  /**
   * Board Panel that displays active pieces.
   */
  private final JPanel my_board_display;
  /**
   * Next piece display panel to show the next piece to be in play.
   */
  private final JPanel my_next_display;
  /**
   * Panel to display the score information on.
   */
  private final ScorePanel my_score_display;
  /**
   * Timer for game.
   */
  private final Timer my_timer;
  /**
   * Music object for playing music.
   */
  private final Music my_music;
  /**
   * String that stores the current song file being played.
   */
  private String my_current_song;
  /**
   * Panel to hold the next display piece and score board.
   */
  private final JPanel my_east_panel;
  /**
   * Difficulty to start at on new game.
   */
  private int my_starting_difficulty;
  /**
   * Basic no arg constructor for the GUI. 
   */
  public TetrisGUI()
  {
    my_frame = new JFrame("Tetris");
    my_menu = new GameMenu();
    my_board = new Board();
    my_board.addObserver(this);
    my_board_display = new BoardDisplay();
    my_next_display = new NextPiecePanel();
    my_score_display = new ScorePanel();
    my_east_panel = new JPanel();
    my_timer = new Timer(DELAY, new BoardListener());
    my_music = new Music();
    my_current_song = Music.TRADITIONAL;
  }

  /**
   *Public entry point from main for the GUI. 
   */
  public void start()
  {
    my_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    my_frame.setLocationByPlatform(true);
    my_frame.setJMenuBar(my_menu);
    my_frame.add(my_board_display, BorderLayout.CENTER);
    my_east_panel.setLayout(new BoxLayout(my_east_panel, BoxLayout.PAGE_AXIS));
    my_east_panel.setBackground(Color.BLACK);
    my_frame.add(my_east_panel, BorderLayout.EAST);
    my_east_panel.add(my_next_display);
    my_east_panel.add(my_score_display);
    my_score_display.setSize(my_next_display.getWidth(), my_frame.getHeight()
                             - my_next_display.getHeight());
    my_score_display.setLocation(0, my_next_display.getHeight());
    my_frame.addKeyListener(new ControlListener());
    my_frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    my_frame.setVisible(true);
    my_music.newGame();
    my_timer.start();
    addGameControlListeners();
    addMusicListeners();
    my_starting_difficulty = 1;
  //This whole mess is for centering the panels as the screen resizes
    my_board_display.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(final ComponentEvent the_event)
      { 
        ((NextPiecePanel) my_next_display).setPieceSize(((BoardDisplay)
            my_board_display).getPieceSize());
        my_board_display.setLocation((my_frame.getWidth() / 2) 
                    - (my_board_display.getWidth() / 2) - my_next_display.getWidth() / 2, 0); 
        my_east_panel.setLocation((my_frame.getWidth() / 2) 
                                    + (my_board_display.getWidth() / 2) 
                                    - my_next_display.getWidth() / 2, 0);
        my_score_display.setSize(my_next_display.getWidth(), my_frame.getHeight()
                                 - my_next_display.getHeight());
        my_score_display.setLocation(0, my_next_display.getHeight());
        my_east_panel.setSize(my_next_display.getWidth(), my_frame.getHeight());
        my_frame.setMinimumSize(new Dimension((my_board_display.getHeight() / 2) 
                                              + my_next_display.getWidth(), MIN_HEIGHT));
      }
    });
  }
  /**
   * Method to add interactive listeners to the game.
   */
  private void addGameControlListeners()
  {
    my_menu.addPauseListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (my_timer.isRunning())
        {
          ((BoardDisplay) my_board_display).setPaused(true);
          ((NextPiecePanel) my_next_display).setPaused(true);
          ((BoardDisplay) my_board_display).repaint();
          ((NextPiecePanel) my_next_display).repaint();
          my_timer.stop();
          my_music.stop();
          my_frame.removeKeyListener(my_frame.getKeyListeners()[0]);
        }
        else if (!my_board.gameIsOver())
        {
          ((BoardDisplay) my_board_display).setPaused(false);
          ((NextPiecePanel) my_next_display).setPaused(false);
          ((NextPiecePanel) my_next_display).repaint();
          ((BoardDisplay) my_board_display).repaint();
          my_timer.start();
          my_music.start();
          my_frame.addKeyListener(new ControlListener());
        } 
      }
    });
    my_menu.addEndGameListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        
        if (my_timer.isRunning())
        {
          my_timer.stop();
          my_music.stop();
          my_frame.removeKeyListener(my_frame.getKeyListeners()[0]);
          my_board.endGame();
        }
        else if (!my_board.gameIsOver())
        {
          my_board.endGame();
        }
        ((BoardDisplay) my_board_display).setPaused(false);
        ((BoardDisplay) my_board_display).setGameOver(true);
        ((BoardDisplay) my_board_display).repaint();
      }
    });
    my_menu.addExitListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        my_timer.stop();
        my_music.stopMusic();
        my_frame.dispose(); 
      }
    });
    my_menu.addNewGameListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_e)
      {
        newGame(); 
        ((BoardDisplay) my_board_display).setPaused(false);
        if (my_frame.getKeyListeners().length == 0)
        {
          my_frame.addKeyListener(new ControlListener());
        }
      }
    });  
    my_menu.addControlListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_e)
      {
        JOptionPane.showMessageDialog(null, "Left and Right arrow keys to move." + '\n' 
            + "Up arrow to Rotate." + '\n' + "Space bar to drop current piece.");
          
      }
    });
    
    for (final JMenuItem s : my_menu.getDifficulties())
    {
      s.addActionListener(new ActionListener() 
      {
        public void actionPerformed(final ActionEvent the_event)
        {
          my_starting_difficulty = s.getMnemonic() - 48;
        }
      });
    }
    
  }
  /**
   * Helper method that adds the listeners for the music menu.
   */
  private void addMusicListeners()
  {
    my_menu.addTraditionalListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.TRADITIONAL; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.TRADITIONAL; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    my_menu.addRockListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.ROCK; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.ROCK; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    my_menu.addAlternativeListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.MUSE; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.MUSE; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    my_menu.addTechnoListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.TECHNO; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.TECHNO; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    my_menu.addBossListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.FF7; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.FF7; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    my_menu.addWowListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent the_arg0)
      {
        if (!my_board.gameIsOver())
        {
          my_current_song = Music.POP; 
          my_music.setSequence(my_current_song);
          my_music.stopMusic();
          my_music.startMusic();
        }
        else
        {
          my_current_song = Music.POP; 
        }
        if (!my_timer.isRunning() && !my_board.gameIsOver())
        {
          my_music.stop();
        }
      }
    });
    
    my_frame.addWindowListener(new WindowAdapter() 
    {
      @Override
      public void windowClosing(final WindowEvent the_event)
      {
        my_timer.stop();
        my_music.stopMusic();
      }
    });
  }
  
  /**
   * Initializes a new game reseting the board.
   */
  public void newGame()
  {
    my_board = new Board();
    my_board.addObserver(this);
    my_timer.setDelay(DELAY / my_starting_difficulty);
    my_timer.start();
    my_music.stopMusic();
    my_music.setSequence(my_current_song);
    my_music.setSpeed(1f);
    my_music.startMusic();
    my_score_display.reset();
    my_score_display.setStartingLevel(my_starting_difficulty);
    my_score_display.updateValues(0);
    my_music.setSpeed((float) (1 + MUSIC_INCREASE * (my_score_display.getLevel() - 1)));
    ((BoardDisplay) my_board_display).setGameOver(false);
    my_board_display.repaint();
    my_next_display.repaint();
  }
  /**
   * Method to cleanup timer and inform player when the game is over.
   */
  public void gameOver()
  {
    ((BoardDisplay) my_board_display).setGameOver(true);
    ((BoardDisplay) my_board_display).repaint();
    my_timer.stop();
    my_music.gameOver();
    my_board.deleteObservers();
  }
    
  @Override
  public void update(final Observable the_arg0, final Object the_arg1)
  {
    if (the_arg1 instanceof Integer && !my_board.gameIsOver())
    {
      my_score_display.updateValues((int) the_arg1);
      my_timer.setDelay(DELAY / my_score_display.getLevel());
      my_music.setSpeed((float) (1 + MUSIC_INCREASE * (my_score_display.getLevel() - 1)));
    }
    else if (the_arg1 != null && (boolean) the_arg1)
    {
      my_score_display.pieceLanded();
    }
    if (my_board.gameIsOver())
    {
      gameOver();
    }
    ((NextPiecePanel) my_next_display).setNextPiece((AbstractPiece) my_board.getNextPiece());
    ((BoardDisplay) my_board_display).setCurrentPiece((AbstractPiece) 
                                                      my_board.getCurrentPiece());
    ((BoardDisplay) my_board_display).setFrozenBlocks(my_board.getFrozenBlocks());
    ((NextPiecePanel) my_next_display).setPieceSize(((BoardDisplay)
        my_board_display).getPieceSize());
    my_board_display.repaint();
    my_next_display.repaint();
    my_score_display.repaint();
  }
  
  /**
   * Inner class to add listener to update the board via timer.
   * @author Jeremiah Stowe
   * @version Spring 2013
   *
   */
  private class BoardListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent the_arg0)
    {
      my_board.step();
    }
    
  }
  
  /**
   * Inner class to track keyboard input for control of pieces.
   * @author Jeremiah Stowe
   * @version Spring 2013
   *
   */
  class ControlListener implements KeyListener
  {

    @Override
    public void keyPressed(final KeyEvent the_arg0)
    {
      if (the_arg0.getKeyCode() == KeyEvent.VK_LEFT)
      {
        my_board.moveLeft();
      }
      if (the_arg0.getKeyCode() == KeyEvent.VK_RIGHT)
      {
        my_board.moveRight();
      }
      if (the_arg0.getKeyCode() == KeyEvent.VK_UP)
      {
        my_board.rotate();
      }
      if (the_arg0.getKeyCode() == KeyEvent.VK_DOWN)
      {
        my_board.moveDown();
      }
      if (the_arg0.getKeyCode() == KeyEvent.VK_SPACE)
      {
        my_board.hardDrop();
      }
    }
    @Override
    public void keyReleased(final KeyEvent the_arg0)
    {
      // TODO Auto-generated method stub 
    }
    @Override
    public void keyTyped(final KeyEvent the_arg0)
    {
      // TODO Auto-generated method stub 
    }
  }
}
