package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
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
	
	private Quoridor q;
	public QuoridorPage(){
		q=QuoridorApplication.getQuoridor();
		initComponents();
		refreshData();
	}

	private void initComponents() {
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		newGameButton = new JButton();
		newGameButton.setText("New Game");
		
		saveGameButton = new JButton();
		saveGameButton.setText("Save Game");
		
		loadGameButton = new JButton();
		loadGameButton.setText("Load Game");
		
		resignGameButton = new JButton();
		resignGameButton.setText("Resign Game");
		
		drawGameButton = new JButton();
		drawGameButton.setText("Offer Draw");
		
		JFrame frame = new JFrame("Testing");
		
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
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(errorMessage)
				.addComponent(newGameButton)
				.addComponent(saveGameButton)
				.addComponent(loadGameButton)
				.addComponent(resignGameButton)
				.addComponent(drawGameButton)
				);
		layout.setHorizontalGroup(layout.createParallelGroup().addComponent(errorMessage)
				.addComponent(newGameButton)
				.addComponent(saveGameButton)
				.addComponent(loadGameButton)
				.addComponent(resignGameButton)
				.addComponent(drawGameButton)
				);
		
		pack();
	}

	private void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			//update
		}
		
	}
	
	private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();
		
		error = null;
		
		// call the controller
		//gc.initGame(q);
		/*try {
			gc.initGame();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}*/
		
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
		
		
		
		// update visuals
		error = "resign game"; //for testing
		refreshData();
	}
	
	private void drawGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();
		
		error = null;
		
		
		
		// update visuals
		error = "draw game"; //for testing
		refreshData();
	}
	
}
