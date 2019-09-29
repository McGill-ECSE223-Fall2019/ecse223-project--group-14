/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.sql.Time;

// line 29 "../../../../../Player.ump"
public class Pawn
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pawn Attributes
  private Color color;
  private Time timeRemaining;

  //Pawn Associations
  private Wall wall;
  private Quoridor quoridor;
  private Square square;
  private Player player;
  private Game game;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetPlayer;
  private boolean canSetGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Color aColor, Time aTimeRemaining, Wall aWall, Quoridor aQuoridor, Square aSquare, Player aPlayer, Game aGame)
  {
    cachedHashCode = -1;
    canSetPlayer = true;
    canSetGame = true;
    color = aColor;
    timeRemaining = aTimeRemaining;
    boolean didAddWall = setWall(aWall);
    if (!didAddWall)
    {
      throw new RuntimeException("Unable to create pawn due to wall");
    }
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create pawn due to quoridor");
    }
    if (aSquare == null || aSquare.getPawn() != null)
    {
      throw new RuntimeException("Unable to create Pawn due to aSquare");
    }
    square = aSquare;
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create pawn due to player");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create pawn due to game");
    }
  }

  public Pawn(Color aColor, Time aTimeRemaining, Wall aWall, Quoridor aQuoridor, String aColumnForSquare, int aRowForSquare, Quoridor aQuoridorForSquare, Board aBoardForSquare, PlayerMove aPlayerMoveForSquare, WallMove aWallMoveForSquare, Player aPlayer, Game aGame)
  {
    color = aColor;
    timeRemaining = aTimeRemaining;
    boolean didAddWall = setWall(aWall);
    if (!didAddWall)
    {
      throw new RuntimeException("Unable to create pawn due to wall");
    }
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create pawn due to quoridor");
    }
    square = new Square(aColumnForSquare, aRowForSquare, this, aQuoridorForSquare, aBoardForSquare, aPlayerMoveForSquare, aWallMoveForSquare);
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create pawn due to player");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create pawn due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(Color aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeRemaining(Time aTimeRemaining)
  {
    boolean wasSet = false;
    timeRemaining = aTimeRemaining;
    wasSet = true;
    return wasSet;
  }

  public Color getColor()
  {
    return color;
  }

  public Time getTimeRemaining()
  {
    return timeRemaining;
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
  /* Code from template association_GetOne */
  public Square getSquare()
  {
    return square;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setWall(Wall aWall)
  {
    boolean wasSet = false;
    //Must provide wall to pawn
    if (aWall == null)
    {
      return wasSet;
    }

    //wall already at maximum (10)
    if (aWall.numberOfPawns() >= Wall.maximumNumberOfPawns())
    {
      return wasSet;
    }
    
    Wall existingWall = wall;
    wall = aWall;
    if (existingWall != null && !existingWall.equals(aWall))
    {
      boolean didRemove = existingWall.removePawn(this);
      if (!didRemove)
      {
        wall = existingWall;
        return wasSet;
      }
    }
    wall.addPawn(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
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
      existingQuoridor.removePawn(this);
    }
    if (!quoridor.addPawn(this))
    {
      quoridor = existingQuoridor;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (!canSetPlayer) { return false; }
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePawn(this);
    }
    if (!player.addPawn(this))
    {
      player = existingPlayer;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (!canSetGame) { return false; }
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePawn(this);
    }
    if (!game.addPawn(this))
    {
      game = existingGame;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Pawn compareTo = (Pawn)obj;
  
    if (getPlayer() == null && compareTo.getPlayer() != null)
    {
      return false;
    }
    else if (getPlayer() != null && !getPlayer().equals(compareTo.getPlayer()))
    {
      return false;
    }

    if (getGame() == null && compareTo.getGame() != null)
    {
      return false;
    }
    else if (getGame() != null && !getGame().equals(compareTo.getGame()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getPlayer() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getPlayer().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getGame() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getGame().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetPlayer = false;
    canSetGame = false;
    return cachedHashCode;
  }

  public void delete()
  {
    Wall placeholderWall = wall;
    this.wall = null;
    if(placeholderWall != null)
    {
      placeholderWall.removePawn(this);
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePawn(this);
    }
    Square existingSquare = square;
    square = null;
    if (existingSquare != null)
    {
      existingSquare.delete();
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removePawn(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePawn(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeRemaining" + "=" + (getTimeRemaining() != null ? !getTimeRemaining().equals(this)  ? getTimeRemaining().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "wall = "+(getWall()!=null?Integer.toHexString(System.identityHashCode(getWall())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "square = "+(getSquare()!=null?Integer.toHexString(System.identityHashCode(getSquare())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}