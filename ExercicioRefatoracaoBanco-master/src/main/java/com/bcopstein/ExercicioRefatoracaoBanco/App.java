package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    private TelaEntrada telaEntrada;
    private Fachada fachada;
	
    @Override
    public void start(Stage primaryStage) {
    	fachada = Fachada.getInstance();
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage, fachada); 

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        fachada.save();
    }
}

