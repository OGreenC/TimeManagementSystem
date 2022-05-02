package dtu.timeManagement.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * As of writing this comment this class sole purpose is to run the GUI
 */

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load MainScene.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dtu/timeManagement/app/fxml/MainScene.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("/dtu/timeManagement/app/fxml/style.css").toExternalForm());
		
		// Set and show scene on primaryStage
		primaryStage.setTitle("TimeManagementSystem");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
