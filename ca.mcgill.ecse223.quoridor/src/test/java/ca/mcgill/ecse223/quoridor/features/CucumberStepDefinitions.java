package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
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
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	/**
	 * @author DariusPi
	 * @throws Throwable
	 */
	@When("A new game is initializing")
	public void aNewGameIsInitializing() throws Throwable{
		GameController G= new GameController();
		
		G.initGame(quoridor);
	}
	
	/**
	 * @author louismollick
	 * @throws Throwable
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void givenAWallMoveCandidateExistsAtPos(String sdir, int row, int col) throws Throwable{
		Direction dir = Direction.Horizontal;
		if (sdir.equals("vertical")) {
			dir = Direction.Vertical;
		}
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition pos = game.getCurrentPosition();
		Player player = pos.getPlayerToMove();
		WallMove wmc = game.getWallMoveCandidate();
		
		Tile target = QuoridorApplication.getQuoridor().getBoard().getTileByPos(row,col);
		// Check if the WallMoveCandidate belongs to the current player
		if(wmc != null && player.indexOfWall(wmc.getWallPlaced()) != -1) {
			// Set the WallMoveCandidate's attributes to those specified in input
			if(wmc.getWallDirection() != dir) wmc.setWallDirection(dir);
			if(wmc.getTargetTile().getRow() != row || wmc.getTargetTile().getColumn() != col)
				wmc.setTargetTile(target);
		} else { // If no WallMoveCandidate exists or it is other player's, make a new one with input
			Wall w = pos.getWhiteWallsInStock(0);
			int moveNum = game.numberOfMoves();
			if(moveNum == 0) {
				w.setMove(new WallMove(moveNum+1, 1, player, target, game, dir, pos.getWhiteWallsInStock(0)));
			}else {
				w.setMove(new WallMove(moveNum, game.getMove(moveNum-1).getRoundNumber(), player, target, game, dir, pos.getWhiteWallsInStock(0)));
			}
			game.setWallMoveCandidate(w.getMove());
		}
	}
	
	/**
	 * @author louismollick
	 */
	@When("I try to flip the wall")
	public void iTryToFlipTheWall() throws Throwable{
		GameController gc = new GameController();
		gc.rotateWall();
	}
	
	/**
	 * @author louismollick
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void theWallShallBeRotatedOverTheBoardTo(String sdir){
		Direction dir = Direction.valueOf(sdir);
		assertEquals(dir, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection());
	}
	
	/**
	 * @author louismollick
	 */
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void thenWallMoveCandidateExistsAtPos(String snewdir, int row, int col) throws Throwable{
		Direction newdir = Direction.valueOf(snewdir);
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
		gc.grabWall();
	}
	
	/**
	 * @author louismollick
	 */
//	@Then("I have a wall in my hand over the board")
//	public void thenIHaveAWallInMyHandOverTheBoard() {
//		// GUI-related feature -- TODO for later
//	}
	
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
//	@Then("I shall be notified that I have no more walls")
//	public void iShouldBeNotifiedThatIHaveNoMoreWalls() {
//		// GUI-related feature -- TODO for later
//	}

	@Then("I shall have no walls in my hand")
	public void iShallHaveNoWallsInMyHand() {
	    assertEquals(false, QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */
	@And ("White player chooses a username")
	public void whitePlayerChoosesAUsername() throws Throwable{
		GameController G= new GameController();
		G.assignUsername(quoridor, "white");
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("Black player chooses a username")
	public void blackPlayerChoosesAUsername() throws Throwable{
		GameController G= new GameController();
		G.assignUsername(quoridor, "black");
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("Total thinking time is set")
	public void totalThinkingTimeIsSet() throws Throwable{
		int min=0; int sec=0;
		//setThinkingTime(min, sec,quoridor);
		//todo call thinking time method
		GameStatus aGameStatus = GameStatus.ReadyToStart;
		game.setGameStatus(aGameStatus);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The game is ready to start")
	public void theGameIsReadyToStart() throws Throwable{
		GameStatus aGameStatus = GameStatus.ReadyToStart;
		assertEquals(game.getGameStatus(),aGameStatus); 
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@When ("I start the clock")
	public void iStartTheClock() throws Throwable{
		GameController G= new GameController();
		G.startTheClock(game);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("The board is initialized")
	public void theBoardIsInitialized() throws Throwable{
		assertNotNull(quoridor.getBoard());
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Given ("Next player to set user name is? (.*)")
	public void nextPlayerToSetUserNameIs(String colour) throws Throwable{
		if (colour.compareTo("white")==1) {
			if (player1.hasGameAsWhite()) {
				currentPlayer=player1;
			}
			else {
				currentPlayer=player2;
			}
		}
		else {
			if (player1.hasGameAsBlack()) {
				currentPlayer=player1;
			}
			else {
				currentPlayer=player2;
			}
		}
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@And ("There is existing user? (.*)")
	public void thereIsExistingUser(User name) throws Throwable{
		quoridor.addUser(name);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@When ("The player selects existing? (.*)")
	public void thePlayerSelectsExisting(String name) throws Throwable{
		GameController G= new GameController();
		G.selectUsername(quoridor, name);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The name of player? (.*) in the new game shall be (.*)")
	public void theNameOfPlayerInTheNewGameShallBe(String Colour, String name)throws Throwable{
		if (Colour.compareTo("white")==1) {
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
	public void thereIsNoExistingUser(User name) throws Throwable{
		quoridor.removeUser(name);
	}
	
	/**
	 * @author dariu
	 * @throws Throwable
	 */ 
	@When ("The player provides new user name: ?(.*)")
	public void thePlayerProvidesNewUserName(String name) throws Throwable{
		GameController G= new GameController();
		G.createUsername(quoridor, name);
	}
	
	/**
	 * @author DariusPi
	 * @throws Throwable
	 */ 
	@Then ("The player shall be warned that (.*) already exists")
	public void thePlayerShallBeWarnedThatAlreadyExists(String name) throws Throwable{
		GameController G= new GameController();
		String msg= name+ " already exists";
		assertEquals(G.createUsername(quoridor, name),msg);
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

}
