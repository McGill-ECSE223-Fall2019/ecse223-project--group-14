/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 64 "../../../../../Player.ump"
public class PlayerMove extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerMove Associations
  private Square square;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerMove(Quoridor aQuoridor, Game aGame, Square aSquare, Quoridor aQuoridor)
  {
    super(aQuoridor, aGame);
    if (aSquare == null || aSquare.getPlayerMove() != null)
    {
      throw new RuntimeException("Unable to create PlayerMove due to aSquare");
    }
    square = aSquare;
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create playermove due to quoridor");
    }
  }

  public PlayerMove(Quoridor aQuoridor, Game aGame, String aColumnForSquare, int aRowForSquare, Pawn aPawnForSquare, Quoridor aQuoridorForSquare, Board aBoardForSquare, WallMove aWallMoveForSquare, Quoridor aQuoridor)
  {
    super(aQuoridor, aGame);
    square = new Square(aColumnForSquare, aRowForSquare, aPawnForSquare, aQuoridorForSquare, aBoardForSquare, this, aWallMoveForSquare);
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create playermove due to quoridor");
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
      existingQuoridor.removePlayermove(this);
    }
    quoridor.addPlayermove(this);
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
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePlayermove(this);
    }
    super.delete();
  }

}