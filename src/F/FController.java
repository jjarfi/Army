/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package F;

import En.Enscrypt.DES;
import En.Enscrypt.YBitSet;
import En.Enscrypt.YKey;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class FController implements Initializable {

    private char key[] = new char[10];
    private final char subkey1[] = new char[8];
    private final char subkey2[] = new char[8];
    public String keypass;
    @FXML
    private AnchorPane je;
   @FXML
    private TextArea enhtml;
    @FXML
    private JFXPasswordField inpass;
    @FXML
    private JFXButton buttonsave;
    @FXML
    private JFXTextArea txtfinal;

    private void ens() throws FileNotFoundException {
        try {
            keypass = inpass.getText();
            if (inpass.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Password is empty?\nYou must set your password to Encrypt the text.");
                alert.show();
            } else if (keypass.length() < 10) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Password must 10 char");
                alert.show();
            } else {
                Key();
                String textToEncrypt = enhtml.getText();
                String EncryptedText = "";
                char charsToManipulate[] = new char[8];
                for (int i = 0; i < textToEncrypt.length(); i++) {
                    char theChar = textToEncrypt.charAt(i);
                    int value = (int) theChar;
                    String valueString = Integer.toBinaryString(value);
                    while (valueString.length() < 8) {
                        valueString = "0" + valueString;
                    }
                    for (int j = 0; j < 8; j++) {
                        charsToManipulate[j] = valueString.charAt(j);
                    }
                    DES s = new DES();
                    char array1[] = new char[8];
                    array1 = s.IP(charsToManipulate);
                    char array2[] = new char[8];
                    array2 = s.FK(array1, subkey1);
                    char array3[] = new char[8];
                    array3 = s.SW(array2);
                    char array4[] = new char[8];
                    array4 = s.FK(array3, subkey2);
                    char array5[] = new char[8];
                    array5 = s.IP_inverse(array4);
                    String tmp = "";
                    for (int k = 0; k < array5.length; k++) {
                        tmp += String.valueOf(array5[k]);
                    }

                    EncryptedText += (char) (Integer.parseInt(tmp, 2));

                }
                txtfinal.setText(EncryptedText);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Encrypt Success :\n" + EncryptedText);
                alert.show();
                // PrintStream myconsole = new PrintStream(new File("ens.txt"));
                // System.setOut(myconsole);
                //myconsole.println(EncryptedText);
            }

        } catch (NumberFormatException e) {

        }

    }

    private void Key() {
        key = (new YKey(keypass)).generateKey();
        char p10[] = new char[10];
        p10[0] = key[2];
        p10[1] = key[4];
        p10[2] = key[1];
        p10[3] = key[6];
        p10[4] = key[3];
        p10[5] = key[9];
        p10[6] = key[0];
        p10[7] = key[8];
        p10[8] = key[7];
        p10[9] = key[5];
        YBitSet LeftFiveBits = new YBitSet(5);
        YBitSet RightFiveBits = new YBitSet(5);
        for (int i = 0; i < p10.length; i++) {
            if (i < 5) {
                if (p10[i] == '1') {
                    LeftFiveBits.set(i);
                } else if (p10[i] == '0') {
                    LeftFiveBits.clear(i);
                }
            } else {
                if (p10[i] == '1') {
                    RightFiveBits.set(i);
                } else if (p10[i] == '0') {
                    RightFiveBits.clear(i);
                }
            }
        }
        YBitSet LS_1LeftBits = LeftFiveBits.LS_1(5);
        YBitSet LS_1RightBits = RightFiveBits.LS_1(5);
        char theKeyAfterLS_1[] = new char[10];
        char left1[] = LS_1LeftBits.bitSetToCharArray(5);
        char right1[] = LS_1RightBits.bitSetToCharArray(5);
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                theKeyAfterLS_1[i] = left1[i];
            } else {
                theKeyAfterLS_1[i] = right1[i - 5];
            }
        }

        subkey1[0] = theKeyAfterLS_1[5];
        subkey1[1] = theKeyAfterLS_1[2];
        subkey1[2] = theKeyAfterLS_1[6];
        subkey1[3] = theKeyAfterLS_1[3];
        subkey1[4] = theKeyAfterLS_1[7];
        subkey1[5] = theKeyAfterLS_1[4];
        subkey1[6] = theKeyAfterLS_1[9];
        subkey1[7] = theKeyAfterLS_1[8];
        YBitSet LS_2LeftBits = LS_1LeftBits.LS_2(5);
        YBitSet LS_2RightBits = LS_1RightBits.LS_2(5);
        char theKeyAfterLS_2[] = new char[10];
        char left2[] = LS_2LeftBits.bitSetToCharArray(5);
        char right2[] = LS_2RightBits.bitSetToCharArray(5);
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                theKeyAfterLS_2[i] = left2[i];
            } else {
                theKeyAfterLS_2[i] = right2[i - 5];
            }
        }
        subkey2[0] = theKeyAfterLS_2[5];
        subkey2[1] = theKeyAfterLS_2[2];
        subkey2[2] = theKeyAfterLS_2[6];
        subkey2[3] = theKeyAfterLS_2[3];
        subkey2[4] = theKeyAfterLS_2[7];
        subkey2[5] = theKeyAfterLS_2[4];
        subkey2[6] = theKeyAfterLS_2[9];
        subkey2[7] = theKeyAfterLS_2[8];
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
    }

    @FXML
    private void open(ActionEvent event) {
        enhtml.setText("");
    }

    @FXML
    private void Ens(ActionEvent event) throws FileNotFoundException {
        ens();
    }

    public void save() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text Files(*.txt)", "*.txt");
        chooser.getExtensionFilters().add(filter);
        File fl = chooser.showSaveDialog(buttonsave.getScene().getWindow());
        if (fl != null) {
            saveFile(fl);

        }
    }

    private void saveFile(File file) {
        try {
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(file);
            fileOut.write(txtfinal.getText().getBytes());
            fileOut.flush();
            fileOut.close();
            TrayNotification tn = new TrayNotification("ENCRYPT TEXT FILE", "Specified text file successfully generated", NotificationType.SUCCESS);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(1));

        } catch (IOException e) {
            TrayNotification tn = new TrayNotification("ENCRYPT TEXT FILE", "Could not generate specified file", NotificationType.ERROR);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(1));
            System.err.println(e);
        }

    }

    @FXML
    public void saveFile(ActionEvent event) {
        save();
    }

    private void clear() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reset Text");
            alert.setContentText("Are you sure to reset the editor ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                txtfinal.setText("");
                enhtml.setText("");
                inpass.setText("");
            } else {

            }
        } catch (Exception e) {

        }
    }
    @FXML
    private void refresh(ActionEvent event) {
        clear();
    }

}
