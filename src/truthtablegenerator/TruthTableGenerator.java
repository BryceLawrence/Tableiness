package truthtablegenerator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * TruthTable FX class
 */
public class TruthTableGenerator extends Application {
	
	/**
	 *	Variables
	 */
	private BorderPane root = new BorderPane();
		private MenuBar menuBar = new MenuBar();
			private Menu fileMenu = new Menu("File");
			private Menu modeMenu = new Menu("Mode");
			private Menu helpMenu = new Menu("Help");
		private VBox centerArea = new VBox();
			private HBox buttonsRow = new HBox();
				private Button displayResponseSpeedButton = new Button("Batch Entry");
				private Button displaySpeedButton = new Button("Step Mode");
				private Button modeButton = new Button("Compact View");
			private HBox expressionRow = new HBox();
				private TextField expression = new TextField();
			private BorderPane tableArea = new BorderPane();
	
	// output mode variables
	private String outputDisplaySpeed = new String("Step Mode");
	private String outputResponseSpeed = new String("Batch Entry");
	private String outputMode = new String("Compact View");
	
	int caretLocation;
	
	/**
	 *	Create an Error Box when user messes up
	 * @param errorText the string to display in the error box generated
	 */
	public void createErrorBox(String message) {
		BorderPane pane = new BorderPane();
		Label l = new Label();
		l.setText(message);
		Button b  = new Button("Close");
		
		pane.setCenter(l);
		pane.setBottom(b);
		
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:src\\resources\\errorIcon.png"));
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
				}
		});
		stage.setTitle("Error in Expression");
		stage.setScene(new Scene(pane, 250, 150));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	
	public void createHelpWindow() {
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:src\\resources\\iconSmall.png"));
		
		Button terms  = new Button("Terms");
		Button rules  = new Button("Rules");
		Button hints  = new Button("Hints");
		Button laws = new Button("Logical Equivalences 1");
		Button lawsConditional = new Button("Logical Equivalences 2");
		Button close  = new Button("Close");
		HBox helpButtonsRow = new HBox();
		
		helpButtonsRow.getChildren().addAll(terms, rules, hints, laws, lawsConditional, close);
		
		
		BorderPane pane = new BorderPane();
		TextArea text = new TextArea();
		FileIO f = new FileIO();
		//text.setText(f.loadHelpContents());
		
		text.setEditable(false);
		text.setWrapText(true);
		
		pane.setTop(helpButtonsRow);
		pane.setCenter(text);
		
		terms.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				text.setText(f.loadHelpContents("Terms"));
				}
		});
		rules.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				text.setText(f.loadHelpContents("Rules"));
				}
		});
		hints.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				text.setText(f.loadHelpContents("Hints"));
				}
		});
		laws.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				text.setText(f.loadHelpContents("Laws"));
				}
		});
		lawsConditional.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				text.setText(f.loadHelpContents("LawsConditional"));
				}
		});
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
				}
		});
		
		stage.setTitle("Help");
		stage.setScene(new Scene(pane, 600, 600));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		
	}
	
	/**
	 *	Make the FILE part of menuBar and associated functions
	 *		!NEEDS expression and TT objects for saving and loading
	 * @param primaryStage used to close the parent window in "exit"
	 */
	private void createFileMenuBar(Stage primaryStage) {
		
		MenuItem load = new MenuItem("Load Expression");
		MenuItem saveExpression = new MenuItem("Save Expression");
		MenuItem saveTable = new MenuItem("Save TT");
		MenuItem reset = new MenuItem("Reset Fields");
		MenuItem exit = new MenuItem("Exit");
		
		fileMenu.getItems().addAll(load, saveExpression, saveTable, reset, exit);
		
		//START FILE EVENT HANDLING
		
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String currentDir = System.getProperty("user.dir") + File.separator;
				FileChooserBuilder fcb = FileChooserBuilder.create();
				FileChooser fc = fcb.title("Expression to Load").initialDirectory(new File(currentDir)).build();
				File file = fc.showOpenDialog(primaryStage);
				
				if (file != null) { //null means they cancelled out
					while (!file.toString().endsWith(".txt") || !file.exists()) {
						createErrorBox("Incorrect Expression file");
						file = fc.showOpenDialog(primaryStage);
						if (file == null) {
							break;
						}
					}
					if (file != null) {
						FileIO f = new FileIO();
						expression.setText(f.loadExpression(file.toString()));
						Expression.setEnteredExpression(expression.getText());
						try {
							if (Expression.validate()) {
								Table t = new Table();
								t.makeFullTable();
							}
						} catch (ValidationException ex) {
							// if the function caller was from the evaluate button then tell them what they did wrong, if it was from dynamic
							// update then dont show errors. Also the error "Same" is not an error, more of a dont waste time updating, so 
							// dont display it either
							createErrorBox("Expression Loaded had this error:\n" + ex.getMessage());
							System.out.println(ex.getMessage());
						}
					}
				}
			}
		});
		
		saveExpression.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String currentDir = System.getProperty("user.dir") + File.separator;
				FileChooserBuilder fcb = FileChooserBuilder.create();
				FileChooser fc = fcb.title("Save Expression").initialDirectory(new File(currentDir)).build();
				
				File file = fc.showSaveDialog(primaryStage);
				if(file != null) {
					FileIO f = new FileIO();
					f.saveExpression(file.toString(), expression.getText());
				}
			}
		});
		
		saveTable.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String currentDir = System.getProperty("user.dir") + File.separator;
				FileChooserBuilder fcb = FileChooserBuilder.create();
				FileChooser fc = fcb.title("Save Table").initialDirectory(new File(currentDir)).build();
				
				File file = fc.showSaveDialog(primaryStage);
				if(file != null) {
					//DO STUFF HERE
				}
			}
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				expression.setText("");
				
				//some table updater
				
				outputResponseSpeed = "Batch Entry";
				displayResponseSpeedButton.setText("Batch Entry");
				
				outputMode = "Compact View";
				modeButton.setText("Compact View");
			}
		});
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				primaryStage.close();
			}
		});
		
		//	START Keyboard Accelerators
		 
		//CTRL ALT L
		load.setAccelerator(new KeyCodeCombination(
                        KeyCode.L, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		//CTRL ALT S
		saveExpression.setAccelerator(new KeyCodeCombination(
                        KeyCode.S, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		//CTRL ALT T
		saveTable.setAccelerator(new KeyCodeCombination(
                        KeyCode.T, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		//CTRL ALT R
		reset.setAccelerator(new KeyCodeCombination(
                        KeyCode.R, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		//CTRL ALT E
		exit.setAccelerator(new KeyCodeCombination(
                        KeyCode.E, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		
	}
	
	/**
	 *	Creates the MODE part of the menuBar
	 */
	private void createModeMenuBar() {
		MenuItem compact = new MenuItem("Compact View");
		MenuItem full = new MenuItem("Full View");
		MenuItem batch = new MenuItem("Batch Mode");
		MenuItem dynamic = new MenuItem("Dynamic Mode");
		MenuItem instant = new MenuItem("Instant Display");
		MenuItem step = new MenuItem("Step Display");
		
		
		modeMenu.getItems().addAll(compact, full, batch, dynamic, step, instant);
		
		//START MODE EVENT HANDLING
		
		compact.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputMode = "Compact View";
				modeButton.setText("Compact View");
			}
		});
		
		full.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputMode = "Full View";
				modeButton.setText("Full View");
			}
		});
		batch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputResponseSpeed = "Batch Entry";
				displayResponseSpeedButton.setText("Batch Entry");
			}
		});
		dynamic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputResponseSpeed = "Dynamic Entry";
				displayResponseSpeedButton.setText("Dynamic Entry");
			}
		});
		step.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputDisplaySpeed = "Step Mode";
				displaySpeedButton.setText("Step Mode");
			}
		});
		instant.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputDisplaySpeed = "Instant Mode";
				displaySpeedButton.setText("Instant Mode");
			}
		});
		
		//	START Keyboard Accelerators
		
		//CTRL ALT C
		compact.setAccelerator(new KeyCodeCombination(
                        KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		
		//CTRL ALT F
		full.setAccelerator(new KeyCodeCombination(
                        KeyCode.F, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		
		//CTRL ALT B
		batch.setAccelerator(new KeyCodeCombination(
                        KeyCode.B, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		 
		//CTRL ALT D
		dynamic.setAccelerator(new KeyCodeCombination(
                        KeyCode.D, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN));
		
	}
	
	/**
	 *	Creates the HELP part of the MenuBar
	 */
	private void createHelpMenuBar() {
		MenuItem help = new MenuItem("Help");
		
		helpMenu.getItems().add(help);
		
		//START HELP EVENT HANDLING
		
		help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				createHelpWindow();
			}
		});
	}
	
	/**
	 *	Assemble the menuBar from parts
	 * @param priamryStage the parent stage, used to close parent stage from file.exit
	 */
	private void makeMenuBar(Stage primaryStage) {
		createFileMenuBar(primaryStage); //make file menu
		createModeMenuBar();  //make mode menu
		createHelpMenuBar(); //make help menu
		menuBar.getMenus().addAll(fileMenu, modeMenu, helpMenu); // put it together
	}
	
	/**
	 * Make the logic buttons and toggle buttons
	 */
	private void makeLogicButtons() {
		Button  and = new Button("AND /\\");
		Button  or = new Button("OR \\/");
		Button  imply = new Button("IMPLY -->");
		Button  not = new Button("NOT !");
		Button  left = new Button("(");
		Button  right = new Button(")");
		
		buttonsRow.setSpacing(10);
		buttonsRow.getChildren().addAll(and, or, imply, not, left, right, displayResponseSpeedButton, modeButton, displaySpeedButton);
		
		and.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " /\\ " + exp2);
				caretLocation += 4;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		or.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " \\/ " + exp2);
				caretLocation += 4;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		imply.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " --> " + exp2);
				caretLocation += 5;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		not.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " ~" + exp2);
				caretLocation += 2;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		left.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " (" + exp2);
				caretLocation += 2;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		right.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + ") " + exp2);
				caretLocation += 2;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		displayResponseSpeedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputResponseSpeed.equals("Batch Entry")) {
					outputResponseSpeed = "Dynamic Entry";
					displayResponseSpeedButton.setText(outputResponseSpeed);
				} else {
					outputResponseSpeed = "Batch Entry";
					displayResponseSpeedButton.setText(outputResponseSpeed);
				}		
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
			}
		});
		
		modeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputMode.equals("Full View")) {
					outputMode = "Compact View";
					modeButton.setText(outputMode);
				} else {
					outputMode = "Full View";
					modeButton.setText(outputMode);
				}		
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
			}
		});
		displaySpeedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputDisplaySpeed.equals("Instant Mode")) {
					outputDisplaySpeed = "Step Mode";
					displaySpeedButton.setText(outputDisplaySpeed);
				} else {
					outputDisplaySpeed = "Instant Mode";
					displaySpeedButton.setText(outputDisplaySpeed);
				}		
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
			}
		});
	}
	
	/**
	 * Submits the expression to be validated, and then shows error messages if invalid
	 * or creates a table and calls display function if valid
	 * @param showErrors should error messages be displayed? (dynamic updates shouldn't show errors)
	 */
	private void submitExpression(boolean showErrors) {
		Expression.setEnteredExpression(expression.getText());

			try {
				if (Expression.validate()) {
					Table t = new Table();
					t.makeFullTable();
					//t.calcStep("(~0+~0+~0)*(0+0+0)", 0);
				}
			} catch (ValidationException ex) {
				// if the function caller was from the evaluate button then tell them what they did wrong, if it was from dynamic
				// update then dont show errors. Also the error "Same" is not an error, more of a dont waste time updating, so 
				// dont display it either
				if (showErrors && !ex.getMessage().equals("Same")) { 
					createErrorBox(ex.getMessage());
					System.out.println(ex.getMessage());
				}
			}

		expression.requestFocus();
		expression.deselect(); 
		expression.positionCaret(caretLocation);
	}
	
	/**
	 * Creates the expression bar and submit button
	 */
	private void makeExpressionBar() {
		Button submit = new Button("GO");
		expression.setPrefWidth(500);
		expressionRow.getChildren().addAll(expression, submit);
		
		expression.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				caretLocation = expression.getCaretPosition();
				if (outputResponseSpeed.equals("Dynamic Entry")) {
					submitExpression(false);
				}
			}
		});
		
		expression.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				caretLocation = expression.getCaretPosition();
			}
		});
		
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				submitExpression(true);
			}
		});
	}
	
	 /**
	 * Creates the table area
	 */
        private void makeTableArea() {
            Table t = new Table();
            TableView table = new TableView();
            TableColumn p = new TableColumn("p");
            TableColumn and = new TableColumn("and");
            TableColumn q = new TableColumn("q");
            table.getColumns().addAll(p, and, q);
            final VBox tableVbox = new VBox();
            tableVbox.setSpacing(5);
            tableVbox.setPadding(new Insets(10, 0, 0, 10));
            tableVbox.getChildren().addAll(table);
        //    table.setItems(t.getFullTable());
            tableArea.setCenter(table);
        }
        
        
        /**
	 *	Make the main working area
	 */
	private void makeCenterArea() {
		makeLogicButtons();
		makeExpressionBar();
		makeTableArea();
		centerArea.getChildren().addAll(buttonsRow, expressionRow,tableArea);
		//add to centerArea
	}
	
	/**
	 *	Puts all the parts together and displays everything
	 * @param primaryStage the stage to display everything on.
	 */
	@Override
	public void start(Stage primaryStage) {
		caretLocation = 0;
		
		primaryStage.getIcons().addAll(new Image("file:src\\resources\\icon.png"), new Image("file:src\\resources\\icon.png")); 
		makeMenuBar(primaryStage);
		makeCenterArea();
		
		root.setTop(menuBar);
		root.setCenter(centerArea);	
		
		primaryStage.setTitle("Truth Table Generator");
		Scene scene = new Scene(root, 600, 600);
		
		scene.getAccelerators().put(
			new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_ANY), 
			new Runnable() {
				@Override public void run() {
					submitExpression(true);
				}
			}
		);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		expression.requestFocus();
		expression.deselect(); 
		expression.end(); 
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

   
	
}
