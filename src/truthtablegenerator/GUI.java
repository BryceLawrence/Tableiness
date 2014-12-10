package truthtablegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * TruthTable FX class
 */
public class GUI extends Application {
	
	/**
	 *	Variables
	 */
	private BorderPane root = new BorderPane();
		private MenuBar menuBar = new MenuBar();
			private Menu fileMenu = new Menu("File");
			private Menu modeMenu = new Menu("Mode");
			private Menu helpMenu = new Menu("Help");
		private VBox centerArea = new VBox();
			private HBox toggleButtonRow = new HBox();
				private Button displayResponseSpeedButton = new Button();
				private Button displaySpeedButton = new Button();
				private Button modeButton = new Button();
			private HBox logicButtonRow = new HBox();
			private HBox expressionRow = new HBox();
				private TextField expression = new TextField();
					private BorderPane tableArea = new BorderPane();
	
	// output mode variables
	private String outputDisplaySpeed = new String("Instant");
	private String outputResponseSpeed = new String("Batch");
	private String outputMode = new String("Compact");
	
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
		
		Scene scene = new Scene(pane, 250, 150);
		scene.getAccelerators().put(
			new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_ANY), 
			new Runnable() {
				@Override public void run() {
					b.fire();
				}
			}
		);
		
		stage.setTitle("Error in Expression");
		stage.setScene(scene);
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
		
		// Stuff to turn help into HTML view
                
                WebView termsWeb = new WebView();
                WebEngine termsEngine = termsWeb.getEngine();
                termsEngine.load("file:///C:\\Users/McAllister/Desktop/School/Java/TruthTableGenerator/src/resources/terms.html");
                
                WebView rulesWeb = new WebView();
                WebEngine rulesEngine = rulesWeb.getEngine();
                rulesEngine.load("file:///C:\\Users/McAllister/Desktop/School/Java/TruthTableGenerator/src/resources/rules.html");
                
                WebView hintsWeb = new WebView();
                WebEngine hintsEngine = hintsWeb.getEngine();
                hintsEngine.load("file:///C:\\Users/McAllister/Desktop/School/Java/TruthTableGenerator/src/resources/hints.html");
                
                WebView laws1Web = new WebView();
                WebEngine laws1Engine = laws1Web.getEngine();
                laws1Engine.load("file:///C:\\Users/McAllister/Desktop/School/Java/TruthTableGenerator/src/resources/LogicalEquivalences1.html");
                
                WebView laws2Web = new WebView();
                WebEngine laws2Engine = laws2Web.getEngine();
                laws2Engine.load("file:///C:\\Users/McAllister/Desktop/School/Java/TruthTableGenerator/src/resources/LogicalEquivalences2.html");
                
		BorderPane pane = new BorderPane();
		//FileIO f = new FileIO();
		//TextArea text = new TextArea(f.loadHelpContents("Terms"));
		//text.setText(f.loadHelpContents());
		
		//text.setEditable(false);
		//text.setWrapText(true);
		
		pane.setTop(helpButtonsRow);
		//pane.setCenter(text);
		pane.setCenter(termsWeb);
                
		terms.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                            //text.setText(f.loadHelpContents("Terms"));
                            pane.setCenter(termsWeb);
                        }
		});
		rules.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                            //text.setText(f.loadHelpContents("Rules"));
                            pane.setCenter(rulesWeb);
                        }
		});
		hints.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                            //text.setText(f.loadHelpContents("Hints"));
                            pane.setCenter(hintsWeb);
			}
		});
		laws.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                            //text.setText(f.loadHelpContents("Laws"));
                            pane.setCenter(laws1Web);
			}
		});
		lawsConditional.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                            //text.setText(f.loadHelpContents("LawsConditional"));
                            pane.setCenter(laws2Web);   
			}
		});
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
				}
		});
		
		stage.setTitle("Help");
                stage.setMinWidth(620);
                stage.setMinHeight(640);
		stage.setScene(new Scene(pane, 600, 600));
		stage.show();
		
	}
	
	/**
	 *	Make the FILE part of menuBar and associated functions
	 *		!NEEDS expression and TT objects for saving and loading
	 * @param primaryStage used to close the parent window in "exit"
	 */
	private void createFileMenuBar(Stage primaryStage) {
		
		MenuItem load = new MenuItem("Load Expression");
		MenuItem saveExpression = new MenuItem("Save Expression");
		MenuItem saveTable = new MenuItem("Save LaTeX Table");
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
						submitExpression(true);
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
                                    if (Expression.expressionExists()) {
                                        try {
                                            Expression.validate();
                               
                                            FileIO f = new FileIO();
                                            FullTableGenerator ft = new FullTableGenerator();
                                            CompactTableGenerator ct = new CompactTableGenerator();
                                            f.saveLaTeXTable(file.toString(), ct.getTable(), ft.getTable());
                                        } catch (ValidationException e) {
                                            createErrorBox("Cannot Save Table for Invalid Expression");
                                        }
                                        
                                        
                                    } else {
                                        createErrorBox("Cannont Save Table for Empty Expression");
                                    }

                                }     
                    }
                });
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				expression.setText("");
				
				//some table updater
				
				outputResponseSpeed = "Batch";
				displayResponseSpeedButton.setGraphic(
					new ImageView(ImageGetter.getTeXImage("Batch \\leftarrow Dynamic")));
				
				outputMode = "Compact";
				modeButton.setGraphic(
					new ImageView(ImageGetter.getTeXImage("Compact \\leftarrow Full")));
				
				outputDisplaySpeed = "Instant";
				displaySpeedButton.setGraphic(
					new ImageView(ImageGetter.getTeXImage("instant \\leftarrow Step")));
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
				outputMode = "Compact";
				modeButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Compact \\leftarrow Full")));
				submitExpression(false);
			}
		});
		
		full.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputMode = "Full";
				modeButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Compact \\rightarrow Full")));
				submitExpression(false);
			}
		});
		batch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputResponseSpeed = "Batch";
				displayResponseSpeedButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Batch \\leftarrow Dynamic")));
			}
		});
		dynamic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				outputResponseSpeed = "Dynamic";
				displayResponseSpeedButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Batch \\rightarrow Dynamic")));
				submitExpression(false);
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
	
	private void makeToggleButtons() {

		displaySpeedButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Instant \\leftarrow Step")));
		modeButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Compact \\leftarrow Full")));
		displayResponseSpeedButton.setGraphic(
				new ImageView(ImageGetter.getTeXImage("Batch \\leftarrow Dynamic")));
		
		toggleButtonRow.getChildren().addAll(displayResponseSpeedButton, modeButton);
		displayResponseSpeedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputResponseSpeed.equals("Batch")) {
					outputResponseSpeed = "Dynamic";
					displayResponseSpeedButton.setGraphic(
							new ImageView(ImageGetter.getTeXImage("Batch \\rightarrow Dynamic")));
					submitExpression(false);
				} else {
					outputResponseSpeed = "Batch";
					displayResponseSpeedButton.setGraphic(
							new ImageView(ImageGetter.getTeXImage("Batch \\leftarrow Dynamic")));
				}		
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
			}
		});
		
		modeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(outputMode.equals("Full")) {
					outputMode = "Compact";
					modeButton.setGraphic(
							new ImageView(ImageGetter.getTeXImage("Compact \\leftarrow Full")));
				} else {
					outputMode = "Full";
					modeButton.setGraphic(
							new ImageView(ImageGetter.getTeXImage("Compact \\rightarrow Full")));
				}				
					submitExpression(false);
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
			}
		});
                toggleButtonRow.setSpacing(14);
                toggleButtonRow.setPadding(new Insets(0,0,7,0));
	}
	
	/**
	 * Make the logic buttons
	 */
	private void makeLogicButtons() {
		Button  not = new Button();
			not.setGraphic(new ImageView(ImageGetter.getTeXImage("\\lnot")));
								
		Button  and = new Button();
			and.setGraphic(new ImageView(ImageGetter.getTeXImage("\\land")));
                
		Button  or = new Button();
			or.setGraphic(new ImageView(ImageGetter.getTeXImage("\\lor")));
                
		Button  imply = new Button();
			imply.setGraphic(new ImageView(ImageGetter.getTeXImage("\\rightarrow")));
								
		Button  iff = new Button();
			iff.setGraphic(new ImageView(ImageGetter.getTeXImage("\\leftrightarrow")));
                
		Button  left = new Button();
			left.setGraphic(new ImageView(ImageGetter.getTeXImage("(")));
                
		Button  right = new Button();
			right.setGraphic(new ImageView(ImageGetter.getTeXImage(")")));
								
		Button p = new Button();
			p.setGraphic(new ImageView(ImageGetter.getTeXImage("p")));
		Button q = new Button();
			q.setGraphic(new ImageView(ImageGetter.getTeXImage("q")));
		Button r = new Button();
			r.setGraphic(new ImageView(ImageGetter.getTeXImage("r")));
		Button s = new Button();
			s.setGraphic(new ImageView(ImageGetter.getTeXImage("s")));
		Button t = new Button();
			t.setGraphic(new ImageView(ImageGetter.getTeXImage("t")));
		Button u = new Button();
			u.setGraphic(new ImageView(ImageGetter.getTeXImage("u")));
		
		logicButtonRow.setSpacing(14);
                logicButtonRow.setPadding(new Insets(0,0,7,0));
		logicButtonRow.getChildren().addAll(not, and, or, imply, iff,left, right, p, q, r, s, t, u);
		
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
				if (outputResponseSpeed.equals("Dynamic")) {
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
				if (outputResponseSpeed.equals("Dynamic")) {
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
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});
		iff.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + " <-> " + exp2);
				caretLocation += 5;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
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
				if (outputResponseSpeed.equals("Dynamic")) {
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
				if (outputResponseSpeed.equals("Dynamic")) {
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
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});
		
		p.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "p" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});	
		q.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "q" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});	
		r.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "r" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});	
		s.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "s" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});	
		t.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "t" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
			}
		});	
		u.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String exp1 = expression.getText().substring(0, caretLocation);
				String exp2 = expression.getText().substring(caretLocation);
				expression.setText(exp1 + "u" + exp2);
				caretLocation += 1;
				expression.requestFocus();
				expression.deselect(); 
				expression.positionCaret(caretLocation);
				if (outputResponseSpeed.equals("Dynamic")) {
					submitExpression(false);
				}
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
			if (expression.getText().equals("")) {
				makeTableDisplay();
			}
			else {
				try {
					if (Expression.validate()) {
						makeTableDisplay();
					}
				} catch (ValidationException ex) {
					// if the function caller was from the evaluate button then tell them what they did wrong, if it was from dynamic
					// update then dont show errors. Also the error "Same" is not an error, more of a dont waste time updating, so 
					// dont display it either
					if (showErrors && !ex.getMessage().equals("Same")) { 
						createErrorBox(ex.getMessage());
					}
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
		Button submit = new Button("Evaluate");
		//expression.setPrefWidth(500);
		expressionRow.getChildren().addAll(expression, submit);
		
		expression.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				caretLocation = expression.getCaretPosition();
				if (outputResponseSpeed.equals("Dynamic")) {
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
		expressionRow.setSpacing(7);
                expressionRow.setHgrow(expression, Priority.ALWAYS);

		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				submitExpression(true);
			}
		});
	}

	/**
	* Displays the created table for the submitted expression
	*/
	private void makeTableDisplay() {
		List<List<String>> table = null;
		TableView<Row> tableView =  new TableView<>();
		tableView.setId("table");
		if (!expression.getText().equals("")) {
			//switch to handle the selected mode
			if (outputMode.equals("Compact")) {
				CompactTableGenerator compTable = new CompactTableGenerator();
				table = compTable.getTable();
			}
			else {
				FullTableGenerator fullTable = new FullTableGenerator();
				table = fullTable.getTable();
			}
			//convert to rows.
			List<Row> rowList = new ArrayList<>();
			for(int i = 1; i < table.size(); i++) {
					Row newRow = new Row(table.get(i));
					rowList.add(newRow);
			}
			ObservableList<Row> tableRowList = FXCollections.observableArrayList(rowList);                        

			LaTeXConverter lc = new LaTeXConverter();
			for(int i = 0; i < table.get(0).size(); i++) {  
				final int j = i;
				TableColumn<Row, String> column = new TableColumn<>();
				String head = lc.toTex(table.get(0).get(i));
				if (! expression.getText().equals("")) {
				column.setGraphic(
					new ImageView(ImageGetter.getTeXImage(head)));
				}
				column.setCellValueFactory(new Callback<CellDataFeatures<Row, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Row, String> r) {
						// r.getValue() returns the Row instance for a particular TableView row
						return r.getValue().getDataAt(j);
					}});

				// keeps columns from being sorted
				column.setSortable(false);

				tableView.getColumns().add(column); 
			}
			tableView.setItems(tableRowList);
			tableArea.setPadding(new Insets(7,0,0,0));
		}
		tableArea.setCenter(tableView);
	}

/**
 *	Make the main working area
 */
private void makeCenterArea() {
	makeToggleButtons();
	makeLogicButtons();
	makeExpressionBar();
	centerArea.setVgrow(tableArea, Priority.ALWAYS);
	centerArea.setPadding(new Insets(10,10,10,10));
	makeTableDisplay();
	centerArea.getChildren().addAll(toggleButtonRow, logicButtonRow, expressionRow, tableArea);
                
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
                
                //Locks window to a minimum size
                primaryStage.setMinWidth(620);
                primaryStage.setMinHeight(640);
                
		makeMenuBar(primaryStage);
		makeCenterArea();
                
		root.setTop(menuBar);
		root.setCenter(centerArea);
	
		primaryStage.setTitle("Truth Table Generator");
		Scene scene = new Scene(root, 600, 600);
		
		scene.getStylesheets().add("/truthtablegenerator/newstyle.css");
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
