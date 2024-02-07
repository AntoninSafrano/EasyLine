import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Homecontroller {
    @FXML
    private Button ButtonVoyageur;
    @FXML
    private Button ButtonVoyageursansbagage;
    @FXML
    private Button ajout;
    @FXML
    private Text texthello;

    @FXML
    private void handleButtonPress() {
        loadScene("voyageurId.fxml");
    }

    @FXML
    private void ButtonPress() {
        loadScene("voyageursansbagage.fxml");
    }

    @FXML
    private void Buttonajout() {
        loadScene("voyageurAjout.fxml");
    }


    private void loadScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
