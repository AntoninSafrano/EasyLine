import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class VoyageurAjoutController {
    @FXML
    private TextField voyageurNom;
    @FXML
    private TextField voyageurAge;
    @FXML
    private TextField voyageurRue;
    @FXML
    private TextField voyageurVille;
    @FXML
    private TextField voyageurAdressePostale;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Text alertText;

    public void clearFields() {
        voyageurNom.setText("");
        voyageurAge.setText("");
        voyageurRue.setText("");
        voyageurVille.setText("");
        voyageurAdressePostale.setText("");
    }

    public void cancelButton(ActionEvent event) {
        clearFields();
    }

    public void confirmButton(ActionEvent event) {
        // on vérifie si tous les champs sont remplis
        if (voyageurNom.getText().isEmpty() || voyageurAge.getText().isEmpty() || voyageurRue.getText().isEmpty()
                || voyageurVille.getText().isEmpty() || voyageurAdressePostale.getText().isEmpty()) {
            alertText.setStyle("-fx-fill: red");
            alertText.setText("Veuillez remplir tous les champs");
            return;
        }

        // on vériie si l'age est un nombre entier
        try {
            Integer.parseInt(voyageurAge.getText());
        } catch (NumberFormatException e) {
            alertText.setStyle("-fx-fill: red");
            alertText.setText("L'age doit être un nombre entier");
            return;
        }

        Voyageur v = new Voyageur(voyageurNom.getText(), Integer.parseInt(voyageurAge.getText()));
        AdressePostale a = new AdressePostale(voyageurRue.getText(), voyageurVille.getText(),
                Integer.parseInt(voyageurAdressePostale.getText()));

        Voyageur v2 = new Voyageur(voyageurNom.getText(), Integer.parseInt(voyageurAge.getText()),
                new AdressePostale(voyageurRue.getText(), voyageurVille.getText(),
                        Integer.parseInt(voyageurAdressePostale.getText())));

        VoyageurDAO vDao = new VoyageurDAO();

        vDao.insertVoyageurWithAdresse(v, a);

        alertText.setStyle("-fx-fill: green");
        alertText.setText("Voyageur bien ajouté");

        v2.displayInfo();
        clearFields();
    }
}
