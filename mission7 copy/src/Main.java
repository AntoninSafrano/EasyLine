import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage panel) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("voyageurAjout.fxml"));
        Scene scene = new Scene(root, 600, 300);
        panel.setTitle("EasyLine");
        panel.setScene(scene);
        panel.show();
    }
}
