package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ca.mcgill.ecse223.quoridor.controller.GameController;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			//Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided");
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}
	
	@Given("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// GUI-related feature -- TODO for later
	}
	
	@And("^I have a wall in my hand over the board$")
	public void givenIHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	}
	
	@Given("^A new game is initializing$")
	public void aNewGameIsInitializing() throws Throwable {
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1), QuoridorApplication.getQuoridor());
	}

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	/**
	 * @author louismollick
	 * @throws Throwable
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void givenAWallMoveCandidateExistsAtPos(String sdir, int row, int col) throws Throwable{
		Direction dir;
		if(sdir.equals("vertical")) dir = Direction.Vertical;
		else dir = Direction.Horizontal;
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition pos = game.getCurrentPosition();
		Player player = pos.getPlayerToMove();
		WallMove wmc = game.getWallMoveCandidate();
		
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row, col));
		// Check if the WallMoveCandidate belongs to the current player
		if(wmc != null && player.indexOfWall(wmc.getWallPlaced()) != -1) {
			// Set the WallMoveCandidate's attributes to those specified in input
			if(wmc.getWallDirection() != dir) wmc.setWallDirection(dir);
			if(wmc.getTargetTile().getRow() != row || wmc.getTargetTile().getColumn() != col)
				wmc.setTargetTile(target);
		} else { // If no WallMoveCandidate exists or it is other player's, make a new one with input
			Wall w = pos.getWhiteWallsInStock(1);
			int moveNum = game.numberOfMoves();
			int roundNum = 1;
			if(moveNum != 0) {
				roundNum = game.getMove(moveNum-1).getRoundNumber();
			}
			w.setMove(new WallMove(moveNum+1, roundNum, player, target, game, dir, w));
			game.setWallMoveCandidate(w.getMove());
		}
	}
	
	/**
	 * @author louismollick
	 */
	@When("I try to flip the wall")
	public void iTryToFlipTheWall() throws Throwable{
		GameController gc = new GameController();
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		gc.rotateWall(game);
	}
	
	/**
	 * @author louismollick
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void theWallShallBeRotatedOverTheBoardTo(String sdir){
		Direction dir;
		if(sdir.equals("vertical")) dir = Direction.Vertical;
		else dir = Direction.Horizontal;
		assertEquals(dir, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection());
	}
	
	/**
	 * @author louismollick
	 */
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void thenWallMoveCandidateExistsAtPos(String snewdir, int row, int col) throws Throwable{
		Direction newdir;
		if(snewdir.equals("vertical")) newdir = Direction.Vertical;
		else newdir = Direction.Horizontal;
		WallMove w = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		boolean b = (w.getWallDirection().equals(newdir) && w.getTargetTile().getRow() == row &&
				w.getTargetTile().getColumn() == col);
		assertEquals(true, b);
	}
	
	/**
	 * @author louismollick
	 */
	@Given("I have more walls on stock")
	public void iHaveMoreWallsOnStock() throws Throwable{
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player player = game.getCurrentPosition().getPlayerToMove();
		if(!player.hasWalls()) {
			Wall w = new Wall(1, player);
			game.getCurrentPosition().addWhiteWallsInStock(w);
		}
	}
	
	/**
	 * @author louismollick
	 */
	@When("I try to grab a wall from my stock")
	public void iTryToGrabAWallFromMyStock() throws Throwable{
		GameController gc = new GameController();
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		gc.grabWall(game);
	}
	
	/**
	 * @author louismollick
	 */
	@Then("I shall have a wall in my hand over the board")
	public void thenIHaveAWallInMyHandOverTheBoard() throws Throwable{
		// GUI-related feature -- TODO for later
	}
	
	/**
	 * @author louismollick
	 */
	@And("The wall in my hand shall disappear from my stock")
	public void theWallInMyHandShallDisappearFromMyStock() throws Throwable{
		Game g = QuoridorApplication.getQuoridor().getCurrentGame();
		// Get the index of the wallMove candidate. Verify it isn't in player stock
		boolean b = (g.getCurrentPosition().indexOfWhiteWallsInStock(g.getWallMoveCandidate().getWallPlaced()) == -1);
		assertEquals(false, b);
	}
	
	/**
	 * @author louismollick
	 */
	@And("A wall move candidate shall be created at initial position")
	public void aWallMoveCandidateShallBeCreatedAtInitalPosition() throws Throwable{
		Quoridor q = QuoridorApplication.getQuoridor();
		Board b = q.getBoard();
		Game game = q.getCurrentGame();
		WallMove wmc = game.getWallMoveCandidate();
		assertEquals(Direction.Horizontal, wmc.getWallDirection());
		assertEquals(b.getTile(36).getRow(), wmc.getTargetTile().getRow());
		assertEquals(b.getTile(36).getColumn(), wmc.getTargetTile().getColumn());
	}
	
	/**
	 * @author louismollick
	 */
	@Given("I have no more walls on stock")
	public void iHaveNoMoreWallsOnStock() throws Throwable{
		GamePosition pos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		if(!pos.hasWhiteWallsInStock()) {
			for (Wall w : pos.getWhiteWallsInStock()) {
				pos.removeWhiteWallsInStock(w);
			}
		}
	}
	
	/**
	 * @author louismollick
	 */
	@Then("I shall be notified that I have no more walls")
	public void iShouldBeNotifiedThatIHaveNoMoreWalls() {
		// GUI-related feature -- TODO for later
	}
	
	/**
	 * @author louismollick
	 */
	@And ("I shall have no walls in my hand")
	public void iShallHaveNoWallsInMyHand() {
	    assertEquals(false, QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
	}
	
	/**
	 * @author Saifullah
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void givenAWallMoveCandidateExistsAndValidAtPos(String sdir, int row, int col) throws Throwable{
		Direction dir;
		GameController gc = new GameController();
		if(sdir.equals("vertical")) dir = Direction.Vertical;
		else dir = Direction.Horizontal;
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition pos = game.getCurrentPosition();
		Player player = pos.getPlayerToMove();
		WallMove wmc = game.getWallMoveCandidate();
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row ,col));
		
		// Check if the WallMoveCandidate belongs to the current player
				if(wmc != null && player.indexOfWall(wmc.getWallPlaced()) != -1) {
					// Set the WallMoveCandidate's attributes to those specified in input
					if(wmc.getWallDirection() != dir) wmc.setWallDirection(dir);
					if(wmc.getTargetTile().getRow() != row || wmc.getTargetTile().getColumn() != col)
						wmc.setTargetTile(target);
				} else { // If no WallMoveCandidate exists or it is other player's, make a new one with input
					Wall w = pos.getWhiteWallsInStock(1);
					int moveNum = game.numberOfMoves();
					int roundNum = 1;
					if(moveNum != 0) {
						roundNum = game.getMove(moveNum-1).getRoundNumber();
					}
					w.setMove(new WallMove(moveNum+1, roundNum, player, target, game, dir, w));
					game.setWallMoveCandidate(w.getMove());
				}
				
				assertEquals(true, gc.validatePosition(game));
		
	}
	
	/**
	 * @author Saifullah
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void givenAWallMoveCandidateExistsAndNotValidAtPos(String sdir, int row, int col) throws Throwable{
		Direction dir;
		GameController gc = new GameController();
		if(sdir.equals("vertical")) dir = Direction.Vertical;
		else dir = Direction.Horizontal;
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition pos = game.getCurrentPosition();
		Player player = pos.getPlayerToMove();
		WallMove wmc = game.getWallMoveCandidate();
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row ,col));
		
		// Check if the WallMoveCandidate belongs to the current player
				if(wmc != null && player.indexOfWall(wmc.getWallPlaced()) != -1) {
					// Set the WallMoveCandidate's attributes to those specified in input
					if(wmc.getWallDirection() != dir) wmc.setWallDirection(dir);
					if(wmc.getTargetTile().getRow() != row || wmc.getTargetTile().getColumn() != col)
						wmc.setTargetTile(target);
				} else { // If no WallMoveCandidate exists or it is other player's, make a new one with input
					Wall w = pos.getWhiteWallsInStock(1);
					int moveNum = game.numberOfMoves();
					int roundNum = 1;
					if(moveNum != 0) {
						roundNum = game.getMove(moveNum-1).getRoundNumber();
					}
					w.setMove(new WallMove(moveNum+1, roundNum, player, target, game, dir, w));
					game.setWallMoveCandidate(w.getMove());
				}
				
				assertNotEquals(true, gc.validatePosition(game));
		
	}
	
	/**
	 * @author Saifullah
	 */
	@And("My move shall be completed")
	public void MoveCompleted() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean b = false;
		if(currentPlayer != quoridor.getCurrentGame().getWhitePlayer()) {
			b = true;
		}
		assertEquals(true, b);	
	}
	
	/**
	 * @author Saifullah
	 */
	@But("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void WallMoveNotRegistered(String sdir, int row, int col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		WallMove wmc = game.getWallMoveCandidate();
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row ,col));
		assertNotEquals(target, wmc.getTargetTile());
		assertEquals(false, wmc.getWallPlaced());
		assertNotEquals(sdir, wmc.getWallDirection());
	}
		
	/**
	 * @author Saifullah
	 */
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void WallMoveIsRegistered(String sdir, int row, int col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		WallMove wmc = game.getWallMoveCandidate();
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row ,col));
		assertEquals(true, wmc.getWallPlaced());
		assertEquals(sdir, wmc.getWallDirection());
		assertEquals(target, wmc.getTargetTile());
	}
	
	
	/**
	 * @author Saifullah
	 */
	@And("It shall not be my turn to move")
	public void NotMyTurnToMove() {		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean b = false;
		if(currentPlayer != quoridor.getCurrentGame().getWhitePlayer()) {
			b = true;
		}
		assertEquals(true, b);
	}
	
	/**
	 * @author Saifullah
	 */
	@And("I shall not have a wall in my hand")
	public void iShallNotHaveWallInMyHand() {
		assertEquals(false, QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
	}
	
	
	/**
	 * @author Saifullah
	 */
	@And("The wall candidate is at the {string} edge of the board")
	public void WallCandidateAtSide(String side) {
		WallMove wallCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		Tile DesignatedTile = wallCandidate.getTargetTile();
		boolean b = false;
		if(side.equals("left") && DesignatedTile.getColumn() == 1) {
			b = true;
		}
		
		if(side.equals("right") && DesignatedTile.getColumn() == 8) {
			b = true;
		}
		
		if(side.equals("up") && DesignatedTile.getRow() == 1) {
			b = true;
		}
		
		if(side.equals("down") && DesignatedTile.getRow() == 8) {
			b = true;
		}
		
		assertEquals(true, b);		
	}
	
	/**
	 * @author Saifullah
	 */
	@And("The wall candidate is not at the {string} edge of the board")
	public void WallCandidateNotAtSide(String side) {
		WallMove wallCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		Tile DesignatedTile = wallCandidate.getTargetTile();
		boolean b = false;
		if(side.equals("left") && DesignatedTile.getColumn() == 1) {
			b = true;
		}
		
		if(side.equals("right") && DesignatedTile.getColumn() == 8) {
			b = true;
		}
		
		if(side.equals("up") && DesignatedTile.getRow() == 1) {
			b = true;
		}
		
		if(side.equals("down") && DesignatedTile.getRow() == 8) {
			b = true;
		}
		
		assertEquals(false, b);		
	}
	
	/**
	 * @author Saifullah
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void WallIsMovedToPosition(int nrow, int ncol) {
		WallMove wallCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		Tile DesignatedTile = wallCandidate.getTargetTile();
		assertEquals(DesignatedTile.getRow(), nrow);
		assertEquals(DesignatedTile.getColumn(), ncol);			
	}
	
	/**
	 * @author Saifullah
	 */
	@And("It shall be my turn to move")
	public void IsMyTurnToMove() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();
		boolean b = false;
		if(currentPlayer == quoridor.getCurrentGame().getWhitePlayer()) {
			b = true;
		}
		assertEquals(true, b);
	}
	
	/**
	 * @author Saifullah
	 */
	@When("I release the wall in my hand")
	public void iReleaseTheWall() throws Throwable{
		GameController gc = new GameController();
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		gc.dropWall(game);
		
	}
	
	/**
	 * @author Saifullah
	 */
	@When("I try to move the wall {string}")
	public void iMoveWall(String side) {
		GameController gc = new GameController();
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		gc.moveWall(game, side);
	}
	
	/**
	 * @author Saifullah
	 */
	@Then("I shall be notified that my wall move is invalid")
	public void isNotifiedOfInvalidWallMove() {
		// GUI-related feature -- TODO for later
	}
	
	/**
	 * @author Saifullah
	 */
	@Then("I shall be notified that my move is illegal")
	public void isNotifiedOfIllegalMove() {
		// GUI-related feature -- TODO for later
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */
	@When ("A new game is being initialized")
	public void aNewGameIsBeingInitialized() throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.initGame(quoridor);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */
	@And ("White player chooses a username")
	public void whitePlayerChoosesAUsername() throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.assignUsername(quoridor, quoridor.getCurrentGame().getWhitePlayer());
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("Black player chooses a username")
	public void blackPlayerChoosesAUsername() throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.assignUsername(quoridor, quoridor.getCurrentGame().getBlackPlayer());
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("Total thinking time is set")
	public void totalThinkingTimeIsSet() throws Throwable{
		GameController G= new GameController();
		int min=0; int sec=0;
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.setTime(min, sec,quoridor);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The game shall become ready to start")
	public void theGameShallBecomeReadyToStart() throws Throwable{
		GameStatus aGameStatus = GameStatus.ReadyToStart;
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		Game game=quoridor.getCurrentGame();
		assertEquals(game.getGameStatus(),aGameStatus); 
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Given ("The game is ready to start")
	public void theGameIsReadyToStart() throws Throwable{
		aNewGameIsBeingInitialized();
		whitePlayerChoosesAUsername();
		blackPlayerChoosesAUsername();
		totalThinkingTimeIsSet();
		
		GameStatus aGameStatus = GameStatus.ReadyToStart;
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		Game game=quoridor.getCurrentGame();
		game.setGameStatus(aGameStatus);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@When ("I start the clock")
	public void iStartTheClock() throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		Game game=quoridor.getCurrentGame();
		G.startTheClock(game);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The game shall be running")
	public void theGameShallBeRunning() {
		GameStatus aGameStatus = GameStatus.Running;
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		Game game=quoridor.getCurrentGame();
		assertEquals(game.getGameStatus(),aGameStatus); 
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("The board shall be initialized")
	public void theBoardIsInitialized() throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		assertNotNull(quoridor.getBoard());
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Given ("Next player to set user name is? (.*)")
	public void nextPlayerToSetUserNameIs(String color) throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		
		//this creation of new game position was necessary as the currentPosition set by the background method is null
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);
		
		if (color.compareTo("white")==0) {
			GamePosition gamePos=new GamePosition(1, player1Position, player2Position, quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame());
			quoridor.getCurrentGame().setCurrentPosition(gamePos);
		}
		else {
			GamePosition gamePos=new GamePosition(1, player1Position, player2Position, quoridor.getCurrentGame().getBlackPlayer(), quoridor.getCurrentGame());
			quoridor.getCurrentGame().setCurrentPosition(gamePos);
		}
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("There is existing user (.*)")
	public void thereIsExistingUser(String name) throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		User user=new User(name, quoridor);
		quoridor.addUser(user);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@When ("The player selects existing? (.*)")
	public void thePlayerSelectsExisting(String name) throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.selectUsername(quoridor, name);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The name of player? (.*) in the new game shall be (.*)")
	public void theNameOfPlayerInTheNewGameShallBe(String Color, String name)throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		Game game=quoridor.getCurrentGame();
		if (Color.compareTo("white")==1) {
			assertEquals(game.getWhitePlayer().getUser().getName(),name);
		}
		else {
			assertEquals(game.getBlackPlayer().getUser().getName(),name);
		}
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("There is no existing user?(.*)")
	public void thereIsNoExistingUser(String name) throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		List<User> li=quoridor.getUsers();
		if (!li.isEmpty()) {
			for (int index=0; index<li.size();index++) {
				if (li.get(index).getName().compareTo(name)==0) {
					quoridor.removeUser(li.get(index));
					break;
				}
			}
			
		}
	}
	
	/**
	 * @author dariu
	 * @throws Throwable
	 */ 
	@When ("The player provides new user name: ?(.*)")
	public void thePlayerProvidesNewUserName(String name) throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		G.createUsername(quoridor, name);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The player shall be warned that (.*) already exists")
	public void thePlayerShallBeWarnedThatAlreadyExists(String name) throws Throwable{
		GameController G= new GameController();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		String msg= name+ " already exists";
		assertEquals(G.createUsername(quoridor, name),msg);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("Next player to set user name shall be (.*)")
	public void nextlayerToSetUserNameShallBe(String color) throws Throwable{
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		if (color.compareTo("white")==1) {
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove(),quoridor.getCurrentGame().getWhitePlayer());
		}
		else {
			assertEquals(quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove(),quoridor.getCurrentGame().getBlackPlayer());
		}
					
	}
	/**
	 * @author ohuss1
	 * @throws Throwable
	 */
	@Given ("A game position is supplied with pawn coordinate {int}:{int}")//might have problems with : symbol
	public void aGamePositionisSuppliedWithPawnCoordinate(int row, int col) throws Throwable{
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		GamePosition position = game.getCurrentPosition();
		//Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		Tile testTile = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(row,col));
		//PlayerPosition prevalid =new PlayerPosition( game.getCurrentPosition().getPlayerToMove(), testTile);
		//PlayerPosition prevalid2 =new PlayerPosition( game.getCurrentPosition().getPlayerToMove(), testTile);
		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), testTile);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);
		GamePosition testPosition=new GamePosition(1,player1Position,player2Position,(quoridor.getCurrentGame().getWhitePlayer()),game);//has id	
	}
	/**
	 * @author ohuss1
	 * @throws Throwable
	 */
	@When ("Validation of the position is initiated")
	public void validationOfPositionisInitiated()throws Throwable {//game position created in given pass it to game controller
		//try getting position from tile created in Atgiven
		//not allowed to give argument to the above method
		
		GamePosition Prev=GamePosition.getWithId(1);
		GameController gc = new GameController();
		gc.validatePawnPos(Prev);//Takes the GamePosition to get the pawn position we are testing from it
	}
	
	@Then ("The position shall be {string}")//cumcumber syntax for String
	public void thePositionShallBeResult(String result) throws Throwable{
		//GameController G= new GameController();
		//if string ok then set boolean to true 
		//other wise false
		Quoridor quoridor=QuoridorApplication.getQuoridor();
		GameController G= new GameController();
		GamePosition Prev=GamePosition.getWithId(1);
		Game game=quoridor.getCurrentGame();
		assertEquals(result,G.validatePawnPos(Prev));
		
	}
			
			
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@When ("I initiate to load a saved game? (.*)")
	public void iInitiateToLoadASavedGame(String filename) throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		GameController G = new GameController();
		Game game = G.initSaveGameLoad(quoridor, filename);
		game.setGameStatus(GameStatus.Initializing);
		assertNotNull(game);
		assertTrue(quoridor.setCurrentGame(game));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("The position to load is valid")
	public void thePositionToLoadIsValid() throws Throwable {
		GameController G = new GameController();
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertTrue(G.isValid(quoridor.getCurrentGame().getCurrentPosition()));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("The position to load is invalid")
	public void thePositionToLoadIsInvalid() throws Throwable {
		GameController G = new GameController();
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertFalse(G.isValid(quoridor.getCurrentGame().getCurrentPosition()));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@Then ("It shall be? (.*)'s turn")
	public void itShallBeSTurn(String playerColor) throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player player = getPlayer(playerColor);
		GameController G = new GameController();
		assertTrue(G.setCurrentTurn(player, quoridor));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("(.*) shall be at (.*):(.*)")
	public void shallBeAt(String playerColor, int row, int col) throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		Player player = getPlayer(playerColor);
		if (player.hasGameAsWhite()) {
			assertEquals(quoridor.getBoard().getTile(getIndex(row, col)), 
					game.getCurrentPosition().getWhitePosition().getTile());
		} else {
			assertEquals(quoridor.getBoard().getTile(getIndex(row, col)),
					game.getCurrentPosition().getBlackPosition().getTile());
		}
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("(.*) shall have a (.*) wall at (.*):(.*)")
	public void shallHaveAWallAt(String playerColor, Direction orientation, int row, int col) 
			throws Throwable {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Wall[] walls;
		Player player = getPlayer(playerColor);
		if (player.hasGameAsWhite()) {
			walls = new Wall[game.getCurrentPosition().getWhiteWallsOnBoard().size()];
			walls = game.getCurrentPosition().getWhiteWallsInStock().toArray(walls);
			assertTrue(wallPresent(row, col, walls, orientation));
		} else {
			walls = new Wall[game.getCurrentPosition().getBlackWallsOnBoard().size()];
			walls = game.getCurrentPosition().getBlackWallsInStock().toArray(walls);
			assertTrue(wallPresent(row, col, walls, orientation));
		}
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("Both players shall have (.*) in their stacks")
	public void bothPlayersShallHaveInTheirStacks(int remainingWalls) throws Throwable {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(game.getCurrentPosition().getWhiteWallsInStock().size(), remainingWalls);
		assertEquals(game.getCurrentPosition().getBlackWallsInStock().size(), remainingWalls);
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@Then ("The load shall return an error")
	public void theLoadShallReturnAnError() throws Throwable {
		GameController G = new GameController();
		boolean loadFail = false;
		try {
			G.loadGame(QuoridorApplication.getQuoridor());
		} catch (Exception e) {
			loadFail = (e instanceof IOException);
		}
		
		assertTrue(loadFail);
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@When ("The initialization of the board is initiated")
	public void theInitializationOfTheBoardIsInitiated() throws Throwable {
		GameController G = new GameController();
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertNotNull(quoridor.getBoard());
		assertTrue(G.isBoardInitializationInitiated(QuoridorApplication.getQuoridor()));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@Then ("It shall be white player to move")
	public void itShallBeWhitePlayerToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertEquals(quoridor.getCurrentGame().getMoves().size(), 0);
		//correct if there are no moves, as this is a new game and white moves first
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("White's pawn shall be in its initial position")
	public void whitesPawnShallBeInItsInitialPosition() throws Throwable {
		//Initial white pawn position referenced from helper method createUsersAndPlayers
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		PlayerPosition whitePosition = quoridor.getCurrentGame()
				.getCurrentPosition().getWhitePosition();
		assertEquals(whitePosition.getTile(), quoridor.getBoard().getTile(36));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("Black's pawn shall be in its initial position")
	public void blacksPawnShallBeInItsInitialPosition() throws Throwable {
		//Initial black pawn position referenced from helper method createUsersAndPlayers
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		PlayerPosition blackPosition = quoridor.getCurrentGame()
				.getCurrentPosition().getBlackPosition();
		assertEquals(blackPosition.getTile(), quoridor.getBoard().getTile(44));
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("All of White's walls shall be in stock")
	public void allOfWhitesWallsShallBeInStock() throws Throwable {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(game.getCurrentPosition().getWhiteWallsInStock().size(), 10);
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("All of Black's walls shall be in stock")
	public void allOfBlacksWallsShallBeInStock() throws Throwable {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(game.getCurrentPosition().getBlackWallsInStock().size(), 10);
	}
	
	/**
	 * @author FSharp4
	 * @throws Throwable
	 */
	@And ("White's clock shall be counting down")
	public void whitesClockShallBeCountingDown() throws Throwable {
		GameController G = new GameController();
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertTrue(G.isClockCountingDown(quoridor.getCurrentGame().getWhitePlayer()));
	}
	
	
	
	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */

	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i);
			if(wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		//@formatter:off
		/*
		 *  __________
		 * |          |
		 * |          |
		 * |x->    <-x|
		 * |          |
		 * |__________|
		 * 
		 */
		//@formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
		
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);
		
		return playersList;
	}

	private void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}
	
	private int getIndex(int row, int col) {
		return (col - 1) * 9 + (row - 1);
	}
	
	private boolean wallPresent(int row, int col, Wall[] wallsOnBoard, Direction orientation) {
		if (wallsOnBoard.length == 0)
			return false;
		
		for (Wall wall : wallsOnBoard) {
			if (wall.getMove().getTargetTile().getRow() == row 
					&& wall.getMove().getTargetTile().getColumn() == col 
					&& wall.getMove().getWallDirection().equals(orientation)) {
				return true;
			}
		}
		
		return false;
	}
	
	private Player getPlayer(String color) {
		Player player;
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (color.contentEquals("white")) {
			player = quoridor.getCurrentGame().getWhitePlayer();
		} else if (color.contentEquals("black")) {
			player = quoridor.getCurrentGame().getBlackPlayer();
		} else {
			player = null; //not supposed to happen
		}
		return player;
	}

}
