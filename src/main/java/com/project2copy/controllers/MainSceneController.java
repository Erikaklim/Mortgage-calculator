package com.project2copy.controllers;

import com.project2copy.calculations.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextField loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField,
            filterFromField, filterToField, deferStartField, deferLengthField, deferInterestField;
    @FXML
    private RadioButton linearRadioButton, annuityRadioButton;

    public double amount, interest, deferInterest;
    public int term, filter1, filter2, start, length;

    private SavedValues values;

    private Mortgage mortgage = new Linear(0, 0, 0);

    public void setValues(){
        try{
            amount = Double.parseDouble(loanAmountField.getText());
            interest = Double.parseDouble(interestRateField.getText()) / 12 / 100;
            term = Integer.parseInt(loanTermYearsField.getText()) * 12 + Integer.parseInt(loanTermMonthsField.getText());
            filter1 = Integer.parseInt(filterFromField.getText());
            filter2 = Integer.parseInt(filterToField.getText());
            start = Integer.parseInt(deferStartField.getText());
            length = Integer.parseInt(deferLengthField.getText());
            deferInterest = Double.parseDouble(deferInterestField.getText()) / 12 / 100;


        }catch (NumberFormatException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }

        setMortgageType();
        this.values = new SavedValues(amount, interest * 1200, filter1, filter2, start,
                length, deferInterest * 1200, term / 12, term % 12, mortgage.getClass().getSimpleName());
    }

    public void getValues(SavedValues values){
        loanAmountField.setText(String.valueOf(values.getAmount()));
        interestRateField.setText(String.valueOf(values.getInterest()));
        loanTermYearsField.setText(String.valueOf(values.getTermYears()));
        loanTermMonthsField.setText(String.valueOf(values.getTermMonths()));
        filterFromField.setText(String.valueOf(values.getFilter1()));
        filterToField.setText(String.valueOf(values.getFilter2()));
        deferStartField.setText(String.valueOf(values.getStart()));
        deferLengthField.setText(String.valueOf(values.getLength()));
        deferInterestField.setText(String.valueOf(values.getDeferInterest()));

        getMortgageType(values.getType());
    }

    private void getMortgageType(String type){
        if(type.equals("Annuity")){
            annuityRadioButton.setSelected(true);
        }else if(type.equals("Linear")){
            linearRadioButton.setSelected(true);
        }
    }

    public void setMortgageType(){
        if(linearRadioButton.isSelected()){
            Linear linear = new Linear(amount, term, interest);
            mortgage = linear;

        }else if(annuityRadioButton.isSelected()){
            Annuity annuity = new Annuity(amount, term, interest);
            mortgage = annuity;
        }

        mortgage.calculate(start, length, deferInterest);
    }

    public void exportToFile(ActionEvent event){
        setValues();

        if (!checkValid()) {
            invalidAlert();
        } else {

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text documents (*.txt)", "*.txt"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            try {
                FileWriter fileWriter = new FileWriter(file);

                for (int i = 0; i < mortgage.getAllPayments().size(); i++) {
                    Payment p = mortgage.getAllPayments().get(i);
                    fileWriter.write(String.format("Month : %d, Payment amount: %.2f€, Principal: %.2f€, Interest: %.2f€, Remaining loan amount: %.2f€\n", p.getMonth(), p.getPaymentAmount(), p.getPrincipal(), p.getInterest(), p.getRemainingLoanAmount()));
                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void switchToGraphScene(ActionEvent event) throws IOException {
        setValues();

        if (!checkValid()) {
            invalidAlert();
        } else {
            FXMLLoader graphLoader = new FXMLLoader(getClass().getResource("/com/project2copy/graphScene.fxml"));
            root = graphLoader.load();

            GraphSceneController graphSceneController = graphLoader.getController();
            graphSceneController.values = this.values;
            graphSceneController.fillGraph(mortgage.getAllPayments(), filter1, filter2);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean isFilterSet(){
        return filter1 != 0 && filter2 != 0;
    }

    public void switchToTableScene(ActionEvent event) throws IOException {
        setValues();

        if (!checkValid()) {
            invalidAlert();
        } else {
            FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/com/project2copy/tableScene.fxml"));
            root = tableLoader.load();

            TableSceneController tableSceneController = tableLoader.getController();
            tableSceneController.values = this.values;

            if(isFilterSet()){
                tableSceneController.fillTable(mortgage.getAllPayments(filter1-1, filter2));
            }else{
                tableSceneController.fillTable(mortgage.getAllPayments());
            }

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean checkValid() {
        return amount>=0.0 && term>0 && filter1>=0
                && filter2>=filter1 && start>=0 && (term-start)>=length;
    }

    private void invalidAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input");
        alert.setContentText("Invalid input!");
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

}
