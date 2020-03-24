package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Dictionary d = new Dictionary();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextArea txtToSpell;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtSpelled;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnClearText;

    @FXML
    private Label lblTime;

    @FXML
    void doClearText(ActionEvent event) {
    	choiceBox.setDisable(false);
    	btnSpellCheck.setDisable(false);
    	txtSpelled.clear();
    	txtToSpell.clear();
    	lblErrors.setText("");
    	lblTime.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	String toSpell = txtToSpell.getText();
    	btnClearText.setDisable(false);
    	btnSpellCheck.setDisable(true);
    	choiceBox.setDisable(true);
    	
    	//Controllo che venga inserito qualcosa
    	if(toSpell.length()==0 || toSpell == null) {
    		txtSpelled.setText("Inserisci almeno una parola e premi Clear Text per riniziare");
    		return;
    	}
    	
    	//Rimuovo punteggiatura
    	toSpell = toSpell.toLowerCase().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");

    	List<String> lToSpell = Arrays.asList(toSpell.split(" "));
    	
    	//Controllo che non siano inseriti solo segni di punteggiatura
    	if(lToSpell.size()==0){
    		txtSpelled.setText("Inserisci almeno una parola e premi Clear Text per riniziare");
    		return;
    	}
    	
    	//Caricamento dizionario
    	d.loadDictionary(choiceBox.getValue());
    	
    	//Faccio lo splelling e misuro il tempo
    	double start = System.nanoTime();
    	List<RichWord> ls = d.spellCheckText(lToSpell);
    	double stop = System.nanoTime();
    		
    	txtSpelled.setText(d.printError(ls));
    	lblTime.setText("Spell check completed in "+((stop-start)/1000000000)+" seconds");
    	lblErrors.setText("The text contains "+d.countError(ls)+" errors");
    	
    }

    @FXML
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtToSpell != null : "fx:id=\"txtToSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSpelled != null : "fx:id=\"txtSpelled\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";

        choiceBox.setItems(FXCollections.observableArrayList("English","Italian"));
		choiceBox.setValue("English");
        
    }
}
