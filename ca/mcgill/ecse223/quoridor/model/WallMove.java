/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 70 "../../../../../Player.ump"
public class WallMove extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WallMove Associations
  private Square square;
  private Wall wall;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WallMove(Quoridor aQuoridor, Game aGame, Square aSquare, Wall aWall, Quoridor aQuoridor)
  {
    super(aQuoridor, aGame);
    if (aSquare == null || aSquare.getWallMove() != null)
    {
      throw new RuntimeException("Unable to create WallMove due to aSquare");
    }
    square = aSquare;
    if (aWall == null || aWall.getWallMove() != null)
    {
      throw new RuntimeException("Unable to create WallMove due to aWall");
    }
    wall = aWall;
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create wallmove due to quoridor");
    }
  }

  public WallMove(Quoridor aQuoridor, Game aGame, String aColumnForSquare, int aRowForSquare, Pawn aPawnForSquare, Quoridor aQuoridorForSquare, Board aBoardForSquare, PlayerMove aPlayerMoveForSquare, Orientation aOrientationForWall, boolean aIsPlacedForWall, Quoridor aQuoridorForWall, Square aSquareForWall, Quoridor aQuoridor)
  {
    super(aQuoridor, aGame);
    square = new Square(aColumnForSquare, aRowForSquare, aPawnForSquare, aQuoridorForSquare, aBoardForSquare, aPlayerMoveForSquare, this);
    wall = new Wall(aOrientationForWall, aIsPlacedForWall, aQuoridorForWall, aSquareForWall, this);
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create wallmove due to quoridor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Square getSquare()
  {
    return square;
  }
  /* Code from template association_GetOne */
  public Wall getWall()
  {
    return wall;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
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
      existingQuoridor.removeWallmove(this);
    }
    quoridor.addWallmove(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Square existingSquare = square;
    square = null;
    if (existingSquare != null)
    {
      existingSquare.delete();
    }
    Wall existingWall = wall;
    wall = null;
    if (existingWall != null)
    {
      existingWall.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeWallmove(this);
    }
    super.delete();
  }

}