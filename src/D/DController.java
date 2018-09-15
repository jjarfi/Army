/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class DController implements Initializable {

     @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane JEE;

    @FXML
    private AnchorPane je;
    AnchorPane F,G,H;

    private void setNode(Node node) {
        je.getChildren().clear();
        je.getChildren().add(node);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            F = FXMLLoader.load(getClass().getResource("/F/F.fxml"));
            G = FXMLLoader.load(getClass().getResource("/G/G.fxml"));
            H = FXMLLoader.load(getClass().getResource("/H/H.fxml"));
           
           
        } catch (IOException e) {

        }
  
    }

    @FXML
    private void moveF(ActionEvent event) {
        setNode(F);
    }
    @FXML
    private void moveG(ActionEvent event) {
        setNode(G);
    }
    @FXML
    private void moveH(ActionEvent event) {
        setNode(H);
    }
}
