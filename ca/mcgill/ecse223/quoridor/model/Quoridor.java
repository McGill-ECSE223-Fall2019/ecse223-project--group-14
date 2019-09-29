/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 3 "../../../../../Player.ump"
public class Quoridor
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quoridor Associations
  private List<Player> players;
  private List<Board> boards;
  private List<Pawn> pawns;
  private List<Square> squares;
  private List<Wall> walls;
  private List<Move> moves;
  private List<PlayerMove> playermoves;
  private List<WallMove> wallmoves;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Quoridor()
  {
    players = new ArrayList<Player>();
    boards = new ArrayList<Board>();
    pawns = new ArrayList<Pawn>();
    squares = new ArrayList<Square>();
    walls = new ArrayList<Wall>();
    moves = new ArrayList<Move>();
    playermoves = new ArrayList<PlayerMove>();
    wallmoves = new ArrayList<WallMove>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Board getBoard(int index)
  {
    Board aBoard = boards.get(index);
    return aBoard;
  }

  public List<Board> getBoards()
  {
    List<Board> newBoards = Collections.unmodifiableList(boards);
    return newBoards;
  }

  public int numberOfBoards()
  {
    int number = boards.size();
    return number;
  }

  public boolean hasBoards()
  {
    boolean has = boards.size() > 0;
    return has;
  }

  public int indexOfBoard(Board aBoard)
  {
    int index = boards.indexOf(aBoard);
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
  public PlayerMove getPlayermove(int index)
  {
    PlayerMove aPlayermove = playermoves.get(index);
    return aPlayermove;
  }

  public List<PlayerMove> getPlayermoves()
  {
    List<PlayerMove> newPlayermoves = Collections.unmodifiableList(playermoves);
    return newPlayermoves;
  }

  public int numberOfPlayermoves()
  {
    int number = playermoves.size();
    return number;
  }

  public boolean hasPlayermoves()
  {
    boolean has = playermoves.size() > 0;
    return has;
  }

  public int indexOfPlayermove(PlayerMove aPlayermove)
  {
    int index = playermoves.indexOf(aPlayermove);
    return index;
  }
  /* Code from template association_GetMany */
  public WallMove getWallmove(int index)
  {
    WallMove aWallmove = wallmoves.get(index);
    return aWallmove;
  }

  public List<WallMove> getWallmoves()
  {
    List<WallMove> newWallmoves = Collections.unmodifiableList(wallmoves);
    return newWallmoves;
  }

  public int numberOfWallmoves()
  {
    int number = wallmoves.size();
    return number;
  }

  public boolean hasWallmoves()
  {
    boolean has = wallmoves.size() > 0;
    return has;
  }

  public int indexOfWallmove(WallMove aWallmove)
  {
    int index = wallmoves.indexOf(aWallmove);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(String aUsername)
  {
    return new Player(aUsername, this);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Quoridor existingQuoridor = aPlayer.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPlayer.setQuoridor(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a quoridor
    if (!this.equals(aPlayer.getQuoridor()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoards()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Board addBoard(Game aGame)
  {
    return new Board(this, aGame);
  }

  public boolean addBoard(Board aBoard)
  {
    boolean wasAdded = false;
    if (boards.contains(aBoard)) { return false; }
    Quoridor existingQuoridor = aBoard.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aBoard.setQuoridor(this);
    }
    else
    {
      boards.add(aBoard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoard(Board aBoard)
  {
    boolean wasRemoved = false;
    //Unable to remove aBoard, as it must always have a quoridor
    if (!this.equals(aBoard.getQuoridor()))
    {
      boards.remove(aBoard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBoardAt(Board aBoard, int index)
  {  
    boolean wasAdded = false;
    if(addBoard(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoards()) { index = numberOfBoards() - 1; }
      boards.remove(aBoard);
      boards.add(index, aBoard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBoardAt(Board aBoard, int index)
  {
    boolean wasAdded = false;
    if(boards.contains(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoards()) { index = numberOfBoards() - 1; }
      boards.remove(aBoard);
      boards.add(index, aBoard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBoardAt(aBoard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Pawn addPawn(Color aColor, Time aTimeRemaining, Wall aWall, Square aSquare, Player aPlayer, Game aGame)
  {
    return new Pawn(aColor, aTimeRemaining, aWall, this, aSquare, aPlayer, aGame);
  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    Quoridor existingQuoridor = aPawn.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPawn.setQuoridor(this);
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
    //Unable to remove aPawn, as it must always have a quoridor
    if (!this.equals(aPawn.getQuoridor()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSquares()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Square addSquare(String aColumn, int aRow, Pawn aPawn, Board aBoard, PlayerMove aPlayerMove, WallMove aWallMove)
  {
    return new Square(aColumn, aRow, aPawn, this, aBoard, aPlayerMove, aWallMove);
  }

  public boolean addSquare(Square aSquare)
  {
    boolean wasAdded = false;
    if (squares.contains(aSquare)) { return false; }
    Quoridor existingQuoridor = aSquare.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aSquare.setQuoridor(this);
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
    //Unable to remove aSquare, as it must always have a quoridor
    if (!this.equals(aSquare.getQuoridor()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Wall addWall(Wall.Orientation aOrientation, boolean aIsPlaced, Square aSquare, WallMove aWallMove)
  {
    return new Wall(aOrientation, aIsPlaced, this, aSquare, aWallMove);
  }

  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    Quoridor existingQuoridor = aWall.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aWall.setQuoridor(this);
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
    //Unable to remove aWall, as it must always have a quoridor
    if (!this.equals(aWall.getQuoridor()))
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
    Quoridor existingQuoridor = aMove.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aMove.setQuoridor(this);
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
    //Unable to remove aMove, as it must always have a quoridor
    if (!this.equals(aMove.getQuoridor()))
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
  public static int minimumNumberOfPlayermoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayerMove addPlayermove(Game aGame, Square aSquare)
  {
    return new PlayerMove(this, aGame, aSquare, this);
  }

  public boolean addPlayermove(PlayerMove aPlayermove)
  {
    boolean wasAdded = false;
    if (playermoves.contains(aPlayermove)) { return false; }
    Quoridor existingQuoridor = aPlayermove.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPlayermove.setQuoridor(this);
    }
    else
    {
      playermoves.add(aPlayermove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayermove(PlayerMove aPlayermove)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayermove, as it must always have a quoridor
    if (!this.equals(aPlayermove.getQuoridor()))
    {
      playermoves.remove(aPlayermove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayermoveAt(PlayerMove aPlayermove, int index)
  {  
    boolean wasAdded = false;
    if(addPlayermove(aPlayermove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayermoves()) { index = numberOfPlayermoves() - 1; }
      playermoves.remove(aPlayermove);
      playermoves.add(index, aPlayermove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayermoveAt(PlayerMove aPlayermove, int index)
  {
    boolean wasAdded = false;
    if(playermoves.contains(aPlayermove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayermoves()) { index = numberOfPlayermoves() - 1; }
      playermoves.remove(aPlayermove);
      playermoves.add(index, aPlayermove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayermoveAt(aPlayermove, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWallmoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WallMove addWallmove(Game aGame, Square aSquare, Wall aWall)
  {
    return new WallMove(this, aGame, aSquare, aWall, this);
  }

  public boolean addWallmove(WallMove aWallmove)
  {
    boolean wasAdded = false;
    if (wallmoves.contains(aWallmove)) { return false; }
    Quoridor existingQuoridor = aWallmove.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aWallmove.setQuoridor(this);
    }
    else
    {
      wallmoves.add(aWallmove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWallmove(WallMove aWallmove)
  {
    boolean wasRemoved = false;
    //Unable to remove aWallmove, as it must always have a quoridor
    if (!this.equals(aWallmove.getQuoridor()))
    {
      wallmoves.remove(aWallmove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallmoveAt(WallMove aWallmove, int index)
  {  
    boolean wasAdded = false;
    if(addWallmove(aWallmove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallmoves()) { index = numberOfWallmoves() - 1; }
      wallmoves.remove(aWallmove);
      wallmoves.add(index, aWallmove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallmoveAt(WallMove aWallmove, int index)
  {
    boolean wasAdded = false;
    if(wallmoves.contains(aWallmove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallmoves()) { index = numberOfWallmoves() - 1; }
      wallmoves.remove(aWallmove);
      wallmoves.add(index, aWallmove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallmoveAt(aWallmove, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (boards.size() > 0)
    {
      Board aBoard = boards.get(boards.size() - 1);
      aBoard.delete();
      boards.remove(aBoard);
    }
    
    while (pawns.size() > 0)
    {
      Pawn aPawn = pawns.get(pawns.size() - 1);
      aPawn.delete();
      pawns.remove(aPawn);
    }
    
    while (squares.size() > 0)
    {
      Square aSquare = squares.get(squares.size() - 1);
      aSquare.delete();
      squares.remove(aSquare);
    }
    
    while (walls.size() > 0)
    {
      Wall aWall = walls.get(walls.size() - 1);
      aWall.delete();
      walls.remove(aWall);
    }
    
    while (moves.size() > 0)
    {
      Move aMove = moves.get(moves.size() - 1);
      aMove.delete();
      moves.remove(aMove);
    }
    
    while (playermoves.size() > 0)
    {
      PlayerMove aPlayermove = playermoves.get(playermoves.size() - 1);
      aPlayermove.delete();
      playermoves.remove(aPlayermove);
    }
    
    while (wallmoves.size() > 0)
    {
      WallMove aWallmove = wallmoves.get(wallmoves.size() - 1);
      aWallmove.delete();
      wallmoves.remove(aWallmove);
    }
    
  }

}