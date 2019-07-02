package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaOperacoes 
{
	private Stage mainStage; 
	private Scene cenaEntrada;
	private Scene cenaOperacoes;
	private Fachada fachada;
	private ObservableList<Operacao> operacoesConta;

	private int conta; 

	private TextField tfValorOperacao;
	private TextField tfSaldo;

	public TelaOperacoes(Stage mainStage, Scene telaEntrada, int conta, Fachada fachada) { 																					// conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		this.fachada = fachada;
	}

	public Scene getTelaOperacoes() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		fachada.setContaAtual(conta);

        String dadosCorr = conta+" : "+fachada.getCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        String categoria = "Categoria: "+fachada.getStrStatus();
        String limRetDiaria = "Limite retirada diaria: "+fachada.getLimiteRetiradaDiaria();
        
        Label cat = new Label(categoria);
        grid.add(cat, 0, 1);

        Label lim = new Label(limRetDiaria);
        grid.add(lim, 0, 2);
        
        Label tit = new Label("Ultimos movimentos");
        grid.add(tit,0,3);
		
		operacoesConta = fachada.extrato();
        ListView<Operacao> extrato = new ListView<Operacao>(operacoesConta);
        extrato.setPrefHeight(140);
        grid.add(extrato, 0, 4);

        tfSaldo = new TextField();
        tfSaldo.setDisable(true);
        tfSaldo.setText(""+fachada.getSaldo());
        HBox valSaldo = new HBox(20);        
        valSaldo.setAlignment(Pos.BOTTOM_LEFT);
        valSaldo.getChildren().add(new Label("Saldo"));
        valSaldo.getChildren().add(tfSaldo);
        grid.add(valSaldo, 0, 5);        

        tfValorOperacao = new TextField();
        HBox valOper = new HBox(30);        
        valOper.setAlignment(Pos.BOTTOM_CENTER);
        valOper.getChildren().add(new Label("Valor operacao"));
        valOper.getChildren().add(tfValorOperacao);
        grid.add(valOper, 1, 1);        

        Button btnCredito = new Button("Credito");
        Button btnDebito = new Button("Debito");
        Button btnVoltar = new Button("Voltar");
        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btnCredito);
        hbBtn.getChildren().add(btnDebito);
        hbBtn.getChildren().add(btnVoltar);
        grid.add(hbBtn, 1, 2);
		
		Button btnEstatistica = new Button("Tela de estatistica");
		grid.add(btnEstatistica, 1, 5);

        btnCredito.setOnAction(e->{
			try 
			{
        	  double valor = Integer.parseInt(tfValorOperacao.getText());
			  Calendar date = Calendar.getInstance();
			  fachada.deposito(valor, date.get(Calendar.DAY_OF_MONTH),
			  date.get(Calendar.MONTH) + 1,
			  date.get(Calendar.YEAR),
			  date.get(Calendar.HOUR),
			  date.get(Calendar.MINUTE),
			  date.get(Calendar.SECOND),
			  0);    	
			  tfSaldo.setText(""+fachada.getSaldo());
			  cat.setText("Categoria: "+ fachada.getStrStatus());
			  lim.setText("Limite retirada diaria: "+fachada.getLimiteRetiradaDiaria());
			}
        	catch(NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de crédito!!");

				alert.showAndWait();
        	}        	
        });
        
        btnDebito.setOnAction(e->{
        	try {
          	  double valor = Integer.parseInt(tfValorOperacao.getText());
        	  Calendar date = Calendar.getInstance();
			  fachada.retirada(valor, date.get(Calendar.DAY_OF_MONTH),
			  date.get(Calendar.MONTH) + 1,
			  date.get(Calendar.YEAR),
			  date.get(Calendar.HOUR),
			  date.get(Calendar.MINUTE),
			  date.get(Calendar.SECOND),
			  1);  
				tfSaldo.setText(""+fachada.getSaldo());
			  }
			  catch(NumberFormatException ex) {
  				Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Valor inválido !!");
  				alert.setHeaderText(null);
  				alert.setContentText(ex.getLocalizedMessage() + "");

  				alert.showAndWait();
          	}        	
		});
		
		btnEstatistica.setOnAction(e -> {
			Calendar date = Calendar.getInstance();
			TelaEstatistica telEst = new TelaEstatistica(conta, fachada.operacoesConta(), mainStage, cenaOperacoes, date.get(Calendar.MONTH) + 1, date.get(Calendar.YEAR), fachada);
			Scene cena = telEst.getTelaEstatistica();
			mainStage.setScene(cena);
		});

        btnVoltar.setOnAction(e->{
        	mainStage.setScene(cenaEntrada);
        });
		
        cenaOperacoes = new Scene(grid);
        return cenaOperacoes;
	}

}
