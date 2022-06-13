package com.project2copy.controllers;

import com.project2copy.calculations.Payment;
import com.project2copy.calculations.SavedValues;
import com.project2copy.controllers.MainSceneController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class TableSceneController{

    private Stage stage;
    private Scene scene;
    private Parent root;
    public SavedValues values;

    @FXML
    private TableView table;
    @FXML
    private TableColumn <Payment, Integer> monthCol;
    @FXML
    private TableColumn <Payment, Double> paymentAmountCol;
    @FXML
    private TableColumn <Payment, Double> principalCol;
    @FXML
    private TableColumn <Payment, Double>  interestCol;
    @FXML
    private TableColumn <Payment, Double> remainingAmountCol;

    public void fillTable(ObservableList<Payment> allPayments){
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        paymentAmountCol.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        principalCol.setCellValueFactory(new PropertyValueFactory<>("principal"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        remainingAmountCol.setCellValueFactory(new PropertyValueFactory<>("remainingLoanAmount"));

        table.setItems(allPayments);
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/project2copy/mainScene.fxml"));
        root = mainLoader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        MainSceneController mainSceneController = mainLoader.getController();
        mainSceneController.getValues(this.values);

        stage.show();
    }

}
