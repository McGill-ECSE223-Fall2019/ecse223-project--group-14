/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 38 "../../../../../Player.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Square> squares;
  private Quoridor quoridor;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Quoridor aQuoridor, Game aGame)
  {
    squares = new ArrayList<Square>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create board due to quoridor");
    }
    if (aGame == null || aGame.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame");
    }
    game = aGame;
  }

  public Board(Quoridor aQuoridor, boolean aReplayModeActiveForGame)
  {
    squares = new ArrayList<Square>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create board due to quoridor");
    }
    game = new Game(aReplayModeActiveForGame, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Square getSquare(int index)
  {
    Square aSquare = squares.get(index);
    return aSquare;
  }

  public List<Square> getSquares()
  {
    List<Square> newSquares = Collections.unmodifiableList(squares);
    return newSquares;
  }

  public int numberOfSquares()
  {
    int number = squares.size();
    return number;
  }

  public boolean hasSquares()
  {
    boolean has = squares.size() > 0;
    return has;
  }

  public int indexOfSquare(Square aSquare)
  {
    int index = squares.indexOf(aSquare);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSquares()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Square addSquare(String aColumn, int aRow, Pawn aPawn, Quoridor aQuoridor, PlayerMove aPlayerMove, WallMove aWallMove)
  {
    return new Square(aColumn, aRow, aPawn, aQuoridor, this, aPlayerMove, aWallMove);
  }

  public boolean addSquare(Square aSquare)
  {
    boolean wasAdded = false;
    if (squares.contains(aSquare)) { return false; }
    Board existingBoard = aSquare.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aSquare.setBoard(this);
    }
    else
    {
      squares.add(aSquare);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSquare(Square aSquare)
  {
    boolean wasRemoved = false;
    //Unable to remove aSquare, as it must always have a board
    if (!this.equals(aSquare.getBoard()))
    {
      squares.remove(aSquare);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSquareAt(Square aSquare, int index)
  {  
    boolean wasAdded = false;
    if(addSquare(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSquareAt(Square aSquare, int index)
  {
    boolean wasAdded = false;
    if(squares.contains(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSquareAt(aSquare, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    if (aQuoridor == null)
    {
      return wasSet;
    }

    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      existingQuoridor.removeBoard(this);
    }
    quoridor.addBoard(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=squares.size(); i > 0; i--)
    {
      Square aSquare = squares.get(i - 1);
      aSquare.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeBoard(this);
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}