/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 43 "../../../../../Player.ump"
public class Square
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Square Attributes
  private String column;
  private int row;

  //Square Associations
  private Pawn pawn;
  private List<Wall> walls;
  private Quoridor quoridor;
  private Board board;
  private PlayerMove playerMove;
  private WallMove wallMove;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Square(String aColumn, int aRow, Pawn aPawn, Quoridor aQuoridor, Board aBoard, PlayerMove aPlayerMove, WallMove aWallMove)
  {
    column = aColumn;
    row = aRow;
    if (aPawn == null || aPawn.getSquare() != null)
    {
      throw new RuntimeException("Unable to create Square due to aPawn");
    }
    pawn = aPawn;
    walls = new ArrayList<Wall>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create square due to quoridor");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create square due to board");
    }
    if (aPlayerMove == null || aPlayerMove.getSquare() != null)
    {
      throw new RuntimeException("Unable to create Square due to aPlayerMove");
    }
    playerMove = aPlayerMove;
    if (aWallMove == null || aWallMove.getSquare() != null)
    {
      throw new RuntimeException("Unable to create Square due to aWallMove");
    }
    wallMove = aWallMove;
  }

  public Square(String aColumn, int aRow, Color aColorForPawn, Time aTimeRemainingForPawn, Wall aWallForPawn, Quoridor aQuoridorForPawn, Player aPlayerForPawn, Game aGameForPawn, Quoridor aQuoridor, Board aBoard, Quoridor aQuoridorForPlayerMove, Game aGameForPlayerMove, Quoridor aQuoridorForPlayerMove, Quoridor aQuoridorForWallMove, Game aGameForWallMove, Wall aWallForWallMove, Quoridor aQuoridorForWallMove)
  {
    column = aColumn;
    row = aRow;
    pawn = new Pawn(aColorForPawn, aTimeRemainingForPawn, aWallForPawn, aQuoridorForPawn, this, aPlayerForPawn, aGameForPawn);
    walls = new ArrayList<Wall>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create square due to quoridor");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create square due to board");
    }
    playerMove = new PlayerMove(aQuoridorForPlayerMove, aGameForPlayerMove, this, aQuoridorForPlayerMove);
    wallMove = new WallMove(aQuoridorForWallMove, aGameForWallMove, this, aWallForWallMove, aQuoridorForWallMove);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColumn(String aColumn)
  {
    boolean wasSet = false;
    column = aColumn;
    wasSet = true;
    return wasSet;
  }

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public String getColumn()
  {
    return column;
  }

  public int getRow()
  {
    return row;
  }
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
  }
  /* Code from template association_GetMany */
  public Wall getWall(int index)
  {
    Wall aWall = walls.get(index);
    return aWall;
  }

  public List<Wall> getWalls()
  {
    List<Wall> newWalls = Collections.unmodifiableList(walls);
    return newWalls;
  }

  public int numberOfWalls()
  {
    int number = walls.size();
    return number;
  }

  public boolean hasWalls()
  {
    boolean has = walls.size() > 0;
    return has;
  }

  public int indexOfWall(Wall aWall)
  {
    int index = walls.indexOf(aWall);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public PlayerMove getPlayerMove()
  {
    return playerMove;
  }
  /* Code from template association_GetOne */
  public WallMove getWallMove()
  {
    return wallMove;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Wall addWall(Wall.Orientation aOrientation, boolean aIsPlaced, Quoridor aQuoridor, WallMove aWallMove)
  {
    return new Wall(aOrientation, aIsPlaced, aQuoridor, this, aWallMove);
  }

  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    Square existingSquare = aWall.getSquare();
    boolean isNewSquare = existingSquare != null && !this.equals(existingSquare);
    if (isNewSquare)
    {
      aWall.setSquare(this);
    }
    else
    {
      walls.add(aWall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWall(Wall aWall)
  {
    boolean wasRemoved = false;
    //Unable to remove aWall, as it must always have a square
    if (!this.equals(aWall.getSquare()))
    {
      walls.remove(aWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallAt(Wall aWall, int index)
  {  
    boolean wasAdded = false;
    if(addWall(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallAt(Wall aWall, int index)
  {
    boolean wasAdded = false;
    if(walls.contains(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallAt(aWall, index);
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
      existingQuoridor.removeSquare(this);
    }
    quoridor.addSquare(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removeSquare(this);
    }
    board.addSquare(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Pawn existingPawn = pawn;
    pawn = null;
    if (existingPawn != null)
    {
      existingPawn.delete();
    }
    for(int i=walls.size(); i > 0; i--)
    {
      Wall aWall = walls.get(i - 1);
      aWall.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeSquare(this);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeSquare(this);
    }
    PlayerMove existingPlayerMove = playerMove;
    playerMove = null;
    if (existingPlayerMove != null)
    {
      existingPlayerMove.delete();
    }
    WallMove existingWallMove = wallMove;
    wallMove = null;
    if (existingWallMove != null)
    {
      existingWallMove.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "column" + ":" + getColumn()+ "," +
            "row" + ":" + getRow()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pawn = "+(getPawn()!=null?Integer.toHexString(System.identityHashCode(getPawn())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playerMove = "+(getPlayerMove()!=null?Integer.toHexString(System.identityHashCode(getPlayerMove())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wallMove = "+(getWallMove()!=null?Integer.toHexString(System.identityHashCode(getWallMove())):"null");
  }
}