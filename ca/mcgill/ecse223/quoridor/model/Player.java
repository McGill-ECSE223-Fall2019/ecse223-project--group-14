/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 16 "../../../../../Player.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Player> playersByUsername = new HashMap<String, Player>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String username;

  //Player Associations
  private Quoridor quoridor;
  private List<Pawn> pawns;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aUsername, Quoridor aQuoridor)
  {
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username");
    }
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create player due to quoridor");
    }
    pawns = new ArrayList<Pawn>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      playersByUsername.remove(anOldUsername);
    }
    playersByUsername.put(aUsername, this);
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static Player getWithUsername(String aUsername)
  {
    return playersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
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
      existingQuoridor.removePlayer(this);
    }
    quoridor.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Pawn addPawn(Color aColor, Time aTimeRemaining, Wall aWall, Quoridor aQuoridor, Square aSquare, Game aGame)
  {
    return new Pawn(aColor, aTimeRemaining, aWall, aQuoridor, aSquare, this, aGame);
  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    Player existingPlayer = aPawn.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aPawn.setPlayer(this);
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
    //Unable to remove aPawn, as it must always have a player
    if (!this.equals(aPawn.getPlayer()))
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
    playersByUsername.remove(getUsername());
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePlayer(this);
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
            "username" + ":" + getUsername()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}