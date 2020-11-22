/*
 * Jeremiah Stowe
 * Spring 2013
 */
package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * Class for controlling the music in a game.
 * @author Jeremiah Stowe
 * @version Spring 2013
 *
 */
public class Music
{
  /**
   * Static constant file for the Rock midi track.
   */
  public static final String ROCK = "The_Offspring.mid";
  /**
   * Static constant file for the techno midi track.
   */
  public static final String TECHNO = "basshunter-now_youre_gone.mid";
  /**
   * Static constant file for the alternative midi track.
   */
  public static final String MUSE = "muse-hysteria.mid";
  /**
   * Static constant file for the pop midi track.
   */
  public static final String POP = "nicki_minaj-starships.mid";
  /**
   * Constant static file for the boss music midi track.
   */
  public static final String FF7 = "ff7boss.mid";
  /**
   * Main soundtrack that is played.
   */
  public static final String TRADITIONAL = "TETRISB.MID";
  /**
   * Music played at end of the game.
   */
  private static final String GAME_OVER_TRACK = "daft_punk-GL.mid";
  /**
   * Slower speed for game over playback.
   */
  private static final float SLOW = (float) 0.9;
  /**
   * Sequence for the audio file input.
   */
  private Sequence my_sequence;
  /**
   * MIDI sequencer for control of playback.
   */
  private Sequencer my_sequencer;
  
  /**
   * Basic no arg constructor to initialize fields.
   */
  public Music() 
  {
    try
    {
      my_sequence = MidiSystem.getSequence(new File(TRADITIONAL));
      my_sequencer = MidiSystem.getSequencer();
      my_sequencer.open();
      my_sequencer.setSequence(my_sequence);
    }
    catch (final InvalidMidiDataException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (final IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (final MidiUnavailableException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * Sets the speed of the tracking being played.
   * @param the_speed The new speed to play at.
   */
  public void setSpeed(final float the_speed)
  {
    my_sequencer.setTempoFactor(the_speed);
  }
  
  /**
   * Starts the music loaded in the sequence field.
   */
  public void startMusic()
  {
    try 
    {
      // Create a sequencer for the sequence
      my_sequencer = MidiSystem.getSequencer();
      my_sequencer.open();
      my_sequencer.setSequence(my_sequence);

      // Start playing
      my_sequencer.start();
      my_sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    } 
    catch (final MidiUnavailableException e)
    {
      e.printStackTrace();
      
    }
    catch (final InvalidMidiDataException e) 
    {
      e.printStackTrace();
      
    }
  }
  
  /**
   * Starts the class sequencer.
   */
  public void start()
  {
    my_sequencer.start();
  }
  /**
   * Stops the class sequencer.
   */
  public void stop()
  {
    my_sequencer.stop();
  }
  
  /**
   * Stops the music and closes the open sequence.
   */
  public void stopMusic()
  {
    my_sequencer.stop();
    my_sequencer.close();
  }
  
  /**
   * Starts the soundtrack over at original speed. Stops any music currently playing.
   */
  public void newGame()
  {
    stopMusic();
    setSpeed(1f);
    setSequence(TRADITIONAL);
    startMusic();
  }
  
  /**
   * Plays the game over sound track at end of game.
   */
  public void gameOver()
  {
    stopMusic();
    setSequence(GAME_OVER_TRACK);
    startMusic();
    setSpeed(SLOW);
  }

  /**
   * Set a song into the sequence for the MIDI to play.
   * @param the_song The song to load into the midi sequence.
   */
  public void setSequence(final String the_song)
  {
    try
    {
      my_sequence = MidiSystem.getSequence(new File(the_song));
    }
    catch (final InvalidMidiDataException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (final IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  
  
}
