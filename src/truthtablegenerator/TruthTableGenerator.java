package truthtablegenerator;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * TruthTable FX class
 * @author McAllister
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
				private Button speedButton = new Button("Batch Entry");
				private Button modeButton = new Button("Compact View");
			private HBox expressionRow = new HBox();
				private TextField expression = new TextField();
			private BorderPane tableArea = new BorderPane();
	
	// output mode variables
	private String outputSpeed = new String("Batch Entry");
	private String outputMode = new String("Compact View");
	
	/**
	 *	Create an Error Box when user messes up
	 * @param errorText the string to display in the error box generated
	 */
	public void createErrorBox(String errorText) {
		BorderPane pane = new BorderPane();
		Button b  = new Button("Close");
		pane.setCenter(b);
		
		Stage stage = new Stage();
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
				}
		});
		stage.setTitle(errorText);
		stage.setScene(new Scene(pane, 250, 150));
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
			public void handle(ActionEvent t) {
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
						// ADD FILE VALIDATION
						// DO STUFF HERE
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
					//DO STUFF HERE
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
				
				outputSpeed = "Batch Entry";
				speedButton.setText("Batch Entry");
				
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
		
		
		modeMenu.getItems().addAll(compact, full, batch, dynamic);
		
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
				outputSpeed = "Batch Entry";
				speedButton.setText("Batch Entry");
			}
		});
		dynamic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputSpeed = "Dynamic Entry";
				speedButton.setText("Dynamic Entry");
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
		MenuItem moreHelp = new MenuItem("More Help");
		
		helpMenu.getItems().addAll(help, moreHelp);
		
		//START HELP EVENT HANDLING
		
		help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				//add handle
			}
		});
		
		moreHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				//add handle
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
		buttonsRow.getChildren().addAll(and, or, imply, not, left, right, speedButton, modeButton);
		
		and.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + " /\\ ");
			}
		});
		or.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + "\\/ ");
			}
		});
		imply.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + " --> ");
			}
		});
		not.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + " ! ");
			}
		});
		left.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + " ( ");
			}
		});
		right.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp = expression.getText();
				expression.setText(exp + " ) ");
			}
		});
		speedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputSpeed.equals("Batch Entry")) {
					outputSpeed = "Dynamic Entry";
					speedButton.setText(outputSpeed);
				} else {
					outputSpeed = "Batch Entry";
					speedButton.setText(outputSpeed);
				}
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
			}
		});
	}
	
	private void makeExpressionBar() {
		Button submit = new Button("GO");
		expression.setPrefWidth(500);
		expressionRow.getChildren().addAll(expression, submit);
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Expression.setEnteredExpression(expression.getText());
				//boolean valid = Expression.validate();
				Expression.cleanup();
				Expression.setFullExpression();
			}
		});
		
		
		//add logic
	}
	
	/**
	 *	Make the main working area
	 */
	private void makeCenterArea() {
		makeLogicButtons();
		makeExpressionBar();
		//make table area
		centerArea.getChildren().addAll(buttonsRow, expressionRow);
		//add to centerArea
	}
	
	/**
	 *	Puts all the parts together and displays everything
	 * @param primaryStage the stage to display everything on.
	 */
	@Override
	public void start(Stage primaryStage) {
		
		makeMenuBar(primaryStage);
		makeCenterArea();
		
		root.setTop(menuBar);
		root.setCenter(centerArea);
		
		primaryStage.setTitle("Truth Table Generator");
		primaryStage.setScene(new Scene(root, 600, 600));
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
