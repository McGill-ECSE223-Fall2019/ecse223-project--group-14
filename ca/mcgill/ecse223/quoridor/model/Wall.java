/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 51 "../../../../../Player.ump"
public class Wall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }
  public enum Orientation { Vertical, Horizontal }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private Orientation orientation;
  private boolean isPlaced;

  //Wall Associations
  private Quoridor quoridor;
  private List<Pawn> pawns;
  private Square square;
  private WallMove wallMove;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Orientation aOrientation, boolean aIsPlaced, Quoridor aQuoridor, Square aSquare, WallMove aWallMove)
  {
    orientation = aOrientation;
    isPlaced = aIsPlaced;
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create wall due to quoridor");
    }
    pawns = new ArrayList<Pawn>();
    boolean didAddSquare = setSquare(aSquare);
    if (!didAddSquare)
    {
      throw new RuntimeException("Unable to create wall due to square");
    }
    if (aWallMove == null || aWallMove.getWall() != null)
    {
      throw new RuntimeException("Unable to create Wall due to aWallMove");
    }
    wallMove = aWallMove;
  }

  public Wall(Orientation aOrientation, boolean aIsPlaced, Quoridor aQuoridor, Square aSquare, Quoridor aQuoridorForWallMove, Game aGameForWallMove, Square aSquareForWallMove, Quoridor aQuoridorForWallMove)
  {
    orientation = aOrientation;
    isPlaced = aIsPlaced;
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create wall due to quoridor");
    }
    pawns = new ArrayList<Pawn>();
    boolean didAddSquare = setSquare(aSquare);
    if (!didAddSquare)
    {
      throw new RuntimeException("Unable to create wall due to square");
    }
    wallMove = new WallMove(aQuoridorForWallMove, aGameForWallMove, aSquareForWallMove, this, aQuoridorForWallMove);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrientation(Orientation aOrientation)
  {
    boolean wasSet = false;
    orientation = aOrientation;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPlaced(boolean aIsPlaced)
  {
    boolean wasSet = false;
    isPlaced = aIsPlaced;
    wasSet = true;
    return wasSet;
  }

  public Orientation getOrientation()
  {
    return orientation;
  }

  public boolean getIsPlaced()
  {
    return isPlaced;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsPlaced()
  {
    return isPlaced;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
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
  /* Code from template association_GetOne */
  public Square getSquare()
  {
    return square;
  }
  /* Code from template association_GetOne */
  public WallMove getWallMove()
  {
    return wallMove;
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
      existingQuoridor.removeWall(this);
    }
    quoridor.addWall(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPawnsValid()
  {
    boolean isValid = numberOfPawns() >= minimumNumberOfPawns() && numberOfPawns() <= maximumNumberOfPawns();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPawns()
  {
    return 10;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 10;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPawns()
  {
    return 10;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Pawn addPawn(Color aColor, Time aTimeRemaining, Quoridor aQuoridor, Square aSquare, Player aPlayer, Game aGame)
  {
    if (numberOfPawns() >= maximumNumberOfPawns())
    {
      return null;
    }
    else
    {
      return new Pawn(aColor, aTimeRemaining, this, aQuoridor, aSquare, aPlayer, aGame);
    }
  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    if (numberOfPawns() >= maximumNumberOfPawns())
    {
      return wasAdded;
    }

    Wall existingWall = aPawn.getWall();
    boolean isNewWall = existingWall != null && !this.equals(existingWall);

    if (isNewWall && existingWall.numberOfPawns() <= minimumNumberOfPawns())
    {
      return wasAdded;
    }

    if (isNewWall)
    {
      aPawn.setWall(this);
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
    //Unable to remove aPawn, as it must always have a wall
    if (this.equals(aPawn.getWall()))
    {
      return wasRemoved;
    }

    //wall already at minimum (10)
    if (numberOfPawns() <= minimumNumberOfPawns())
    {
      return wasRemoved;
    }
    pawns.remove(aPawn);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSquare(Square aSquare)
  {
    boolean wasSet = false;
    if (aSquare == null)
    {
      return wasSet;
    }

    Square existingSquare = square;
    square = aSquare;
    if (existingSquare != null && !existingSquare.equals(aSquare))
    {
      existingSquare.removeWall(this);
    }
    square.addWall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeWall(this);
    }
    for(int i=pawns.size(); i > 0; i--)
    {
      Pawn aPawn = pawns.get(i - 1);
      aPawn.delete();
    }
    Square placeholderSquare = square;
    this.square = null;
    if(placeholderSquare != null)
    {
      placeholderSquare.removeWall(this);
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
            "isPlaced" + ":" + getIsPlaced()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orientation" + "=" + (getOrientation() != null ? !getOrientation().equals(this)  ? getOrientation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "square = "+(getSquare()!=null?Integer.toHexString(System.identityHashCode(getSquare())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wallMove = "+(getWallMove()!=null?Integer.toHexString(System.identityHashCode(getWallMove())):"null");
  }
}