/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 21 "../../../../../Player.ump"
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private boolean replayModeActive;

  //Autounique Attributes
  private int id;

  //Game Associations
  private Board board;
  private List<Move> moves;
  private List<Pawn> pawns;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(boolean aReplayModeActive, Board aBoard)
  {
    replayModeActive = aReplayModeActive;
    id = nextId++;
    if (aBoard == null || aBoard.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aBoard");
    }
    board = aBoard;
    moves = new ArrayList<Move>();
    pawns = new ArrayList<Pawn>();
  }

  public Game(boolean aReplayModeActive, Quoridor aQuoridorForBoard)
  {
    replayModeActive = aReplayModeActive;
    id = nextId++;
    board = new Board(aQuoridorForBoard, this);
    moves = new ArrayList<Move>();
    pawns = new ArrayList<Pawn>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReplayModeActive(boolean aReplayModeActive)
  {
    boolean wasSet = false;
    replayModeActive = aReplayModeActive;
    wasSet = true;
    return wasSet;
  }

  public boolean getReplayModeActive()
  {
    return replayModeActive;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isReplayModeActive()
  {
    return replayModeActive;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Move getMove(int index)
  {
    Move aMove = moves.get(index);
    return aMove;
  }

  public List<Move> getMoves()
  {
    List<Move> newMoves = Collections.unmodifiableList(moves);
    return newMoves;
  }

  public int numberOfMoves()
  {
    int number = moves.size();
    return number;
  }

  public boolean hasMoves()
  {
    boolean has = moves.size() > 0;
    return has;
  }

  public int indexOfMove(Move aMove)
  {
    int index = moves.indexOf(aMove);
    return index;
  }
  /* Code from template association_GetMany */
  public Pawn getPawn(int index)
  {
    Pawn aPawn = pawns.get(index);
    return aPawn;
  }

  public List<Pawn> getPawns()
  {
    List<Pawn> newPawns = Collections.unmodifiableList(pawns);
    return newPawns;
  }

  public int numberOfPawns()
  {
    int number = pawns.size();
    return number;
  }

  public boolean hasPawns()
  {
    boolean has = pawns.size() > 0;
    return has;
  }

  public int indexOfPawn(Pawn aPawn)
  {
    int index = pawns.indexOf(aPawn);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addMove(Move aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    Game existingGame = aMove.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aMove.setGame(this);
    }
    else
    {
      moves.add(aMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMove(Move aMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aMove, as it must always have a game
    if (!this.equals(aMove.getGame()))
    {
      moves.remove(aMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveAt(Move aMove, int index)
  {  
    boolean wasAdded = false;
    if(addMove(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMoveAt(Move aMove, int index)
  {
    boolean wasAdded = false;
    if(moves.contains(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMoveAt(aMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Pawn addPawn(Color aColor, Time aTimeRemaining, Wall aWall, Quoridor aQuoridor, Square aSquare, Player aPlayer)
  {
    return new Pawn(aColor, aTimeRemaining, aWall, aQuoridor, aSquare, aPlayer, this);
  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    Game existingGame = aPawn.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aPawn.setGame(this);
    }
    else
    {
      pawns.add(aPawn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePawn(Pawn aPawn)
  {
    boolean wasRemoved = false;
    //Unable to remove aPawn, as it must always have a game
    if (!this.equals(aPawn.getGame()))
    {
      pawns.remove(aPawn);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPawnAt(Pawn aPawn, int index)
  {  
    boolean wasAdded = false;
    if(addPawn(aPawn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawns()) { index = numberOfPawns() - 1; }
      pawns.remove(aPawn);
      pawns.add(index, aPawn);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePawnAt(Pawn aPawn, int index)
  {
    boolean wasAdded = false;
    if(pawns.contains(aPawn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawns()) { index = numberOfPawns() - 1; }
      pawns.remove(aPawn);
      pawns.add(index, aPawn);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPawnAt(aPawn, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
    for(int i=moves.size(); i > 0; i--)
    {
      Move aMove = moves.get(i - 1);
      aMove.delete();
    }
    for(int i=pawns.size(); i > 0; i--)
    {
      Pawn aPawn = pawns.get(i - 1);
      aPawn.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "replayModeActive" + ":" + getReplayModeActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}