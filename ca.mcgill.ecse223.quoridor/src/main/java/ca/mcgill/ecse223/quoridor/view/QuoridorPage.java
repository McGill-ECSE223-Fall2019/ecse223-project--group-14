package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class QuoridorPage extends JFrame{

	/**
	 * default 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel errorMessage;
	private String error = null;
	
	private JButton newGameButton;
	private JButton saveGameButton;
	private JButton loadGameButton;
	private JButton resignGameButton;
	private JButton drawGameButton;
	
	private JButton acceptDrawButton;
	private JButton declineDrawButton;
	
	private JButton replayGameButton;
	
	private JButton stepForwardButton;
	private JButton stepBackwardButton;
	private JButton jumpStartButton;
	private JButton jumpEndButton;
	private JButton quitButton;
	
	private Quoridor q;
	public QuoridorPage(){
		q=QuoridorApplication.getQuoridor();
		initComponents();
		refreshData();
	}

	private void initComponents() {
		
		setSize(800, 800);
		
		int buttonH=30;
		int buttonW=125;
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		newGameButton = new JButton();
		newGameButton.setText("New Game");
		
		saveGameButton = new JButton();
		saveGameButton.setText("Save Game");
		saveGameButton.setVisible(false);
		
		loadGameButton = new JButton();
		loadGameButton.setText("Load Game");
		
		resignGameButton = new JButton();
		resignGameButton.setText("Resign Game");
		resignGameButton.setVisible(false);
		
		drawGameButton = new JButton();
		drawGameButton.setText("Offer Draw");
		drawGameButton.setVisible(false);
		
		acceptDrawButton = new JButton();
		acceptDrawButton.setText("Accept");
		acceptDrawButton.setVisible(false);
		
		declineDrawButton = new JButton();
		declineDrawButton.setText("Decline");
		declineDrawButton.setVisible(false);
		
		//replay button
		replayGameButton = new JButton();
		replayGameButton.setText("Replay Mode");
		replayGameButton.setVisible(false);
		
		//replay mode buttons
		stepForwardButton = new JButton();
		stepForwardButton.setText("Step Forward");
		stepForwardButton.setVisible(false);
		
		stepBackwardButton = new JButton();
		stepBackwardButton.setText("Step Backward");
		stepBackwardButton.setVisible(false);
		
		jumpStartButton = new JButton();
		jumpStartButton.setText("Jump Start");
		jumpStartButton.setVisible(false);
		
		jumpEndButton = new JButton();
		jumpEndButton.setText("Jump End");
		jumpEndButton.setVisible(false);
		
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setVisible(false);
		
		//JFrame frame = new JFrame("Testing");
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Quoridor");
		
		newGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newGameButtonActionPerformed(evt);
			}
		});
		
		saveGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveGameButtonActionPerformed(evt);
			}
		});
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
			}
		});
		
		resignGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resignGameButtonActionPerformed(evt);
			}
		});
		
		drawGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				drawGameButtonActionPerformed(evt);
			}
		});
		
		acceptDrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				acceptDrawButtonActionPerformed(evt);
			}
		});
		
		declineDrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				declineDrawButtonActionPerformed(evt);
			}
		});
		
		replayGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				replayGameButtonActionPerformed(evt);
			}
		});
		
		stepForwardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepForwardButtonActionPerformed(evt);
			}
		});
		
		stepBackwardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepBackwardButtonActionPerformed(evt);
			}
		});
		
		jumpStartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpStartButtonActionPerformed(evt);
			}
		});
		
		jumpEndButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpEndButtonActionPerformed(evt);
			}
		});
		
		quitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				quitButtonActionPerformed(evt);
			}
		});
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(errorMessage)
				.addComponent(newGameButton, GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(saveGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(loadGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(resignGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(drawGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(stepForwardButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(stepBackwardButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(jumpStartButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(jumpEndButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(acceptDrawButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addComponent(declineDrawButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup().addComponent(quitButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(replayGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE))
				);
		layout.setHorizontalGroup(layout.createParallelGroup().addComponent(errorMessage)
				.addComponent(newGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(saveGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(loadGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(resignGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(drawGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(stepForwardButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(stepBackwardButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(jumpStartButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(jumpEndButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
				.addComponent(acceptDrawButton,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
				.addComponent(declineDrawButton,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createSequentialGroup().addComponent(quitButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(replayGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE))
				);
		
		//pack();
	}

	private void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			//update
		}
		
	}
	
	private void finishGame() {
		//set screen to results
		
		//todo
		
		newGameButton.setVisible(false);
		saveGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		
		acceptDrawButton.setVisible(false);
		declineDrawButton.setVisible(false);
		
		replayGameButton.setVisible(true);
		quitButton.setVisible(true);
		//pack();
	}
	
	private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();
		
		error = null;
		
		saveGameButton.setVisible(true);
		resignGameButton.setVisible(true);
		drawGameButton.setVisible(true);
		
		// call the controller
		//gc.initGame(q);
		/*try {
			gc.initGame();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}*/
		//pack();
		// update visuals
		error = "new game"; //for testing
		refreshData();
	}
	
	private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();
		error = null;
		
		// update visuals
		error = "save game"; //for testing
		refreshData();
	}
	
	private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();
		error = null;
		
		
		// update visuals
		error = "load game"; //for testing
		refreshData();
	}
	
	private void resignGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;
		//todo
		
		finishGame();
	}
	
	private void drawGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;
		
		newGameButton.setVisible(false);
		saveGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		
		acceptDrawButton.setVisible(true);
		declineDrawButton.setVisible(true);
		
		// update visuals
		error = "draw game"; //for testing
		refreshData();
	}
	
	private void acceptDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		error = null;
		
		//todo
		
		// update visuals
		error = "accept draw"; //for testing
		finishGame();
	}
	
	private void declineDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		error = null;
		
		//todo
		
		
		newGameButton.setVisible(true);
		saveGameButton.setVisible(true);
		loadGameButton.setVisible(true);
		resignGameButton.setVisible(true);
		drawGameButton.setVisible(true);
		
		acceptDrawButton.setVisible(false);
		declineDrawButton.setVisible(false);
		// update visuals
		error = "decline draw"; //for testing
		
	}
	
	
	private void replayGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message		
		error = null;
		
		replayGameButton.setVisible(false);
		
		stepForwardButton.setVisible(true);
		stepBackwardButton.setVisible(true);
		jumpStartButton.setVisible(true);
		jumpEndButton.setVisible(true);
		quitButton.setVisible(true);
		
		refreshData();
	}
	
	private void stepForwardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;

		// update visuals
		error = "step for"; //for testing
		refreshData();
	}
	
	private void stepBackwardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;
		
		// update visuals
		error = "step back"; //for testing
		refreshData();
	}
	
	private void jumpStartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;

		// update visuals
		error = "jump start"; //for testing
		refreshData();
	}
	
	private void jumpEndButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = null;

		// update visuals
		error = "jump end"; //for testing
		refreshData();
	}
	
	private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		error = null;
		
		newGameButton.setVisible(true);
		loadGameButton.setVisible(true);
		
		replayGameButton.setVisible(false);
		stepForwardButton.setVisible(false);
		stepBackwardButton.setVisible(false);
		jumpStartButton.setVisible(false);
		jumpEndButton.setVisible(false);
		quitButton.setVisible(false);
		
		// update visuals
		error = "quit"; //for testing
		refreshData();
	}
	
	
}
