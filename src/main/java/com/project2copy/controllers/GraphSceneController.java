package com.project2copy.controllers;

import com.project2copy.calculations.Payment;
import com.project2copy.calculations.SavedValues;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public SavedValues values;

    @FXML
    private LineChart chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    public void fillGraph(ObservableList<Payment> allPayments, int filter1, int filter2){
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();

        for(Payment p : allPayments){
            series.getData().add(new XYChart.Data(p.getMonth(), p.getPaymentAmount()));
        }

        if(filter1 == 0 && filter2 == 0){
            setXAxisRange(0, allPayments.get(allPayments.size()-1).getMonth());
        }else{
            setXAxisRange(filter1, filter2);
        }

        yAxis.setLowerBound(0);
        yAxis.setUpperBound(allPayments.get(0).getPaymentAmount() * 2);
        chart.getData().clear();
        chart.getData().add(series);

    }

    private void setXAxisRange(int from, int to){
        xAxis.setLowerBound(from);
        xAxis.setUpperBound(to);
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
