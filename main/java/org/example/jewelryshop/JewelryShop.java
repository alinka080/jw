package org.example.jewelryshop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;

public class JewelryShop extends Application {

    @Override
    public void start(Stage stage) throws IOException {
       Manager.openLoginStage(stage);
    } 

    public static void main(String[] args) {
        launch();
   }
}