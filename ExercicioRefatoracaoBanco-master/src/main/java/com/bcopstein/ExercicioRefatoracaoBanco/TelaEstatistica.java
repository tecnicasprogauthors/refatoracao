package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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

public class TelaEstatistica
{
    private int conta;
    private List<Operacao> operacoes;
    private Stage mainStage;
    private Scene cenaEstatistica;
    private Scene cenaOperacoes;
    private Fachada fachada;
    
    private int mesAtual;
    private int anoAtual;

    private int quantDebito;
    private int quantCredito;
    private double valorCreditos;
    private double valorDebitos;

    private TextField tfMes;
    private TextField tfAno;
    private Label totDebitos = new Label();
    private Label quantDebitos = new Label();
    private Label totCreditos = new Label();
    private Label quantCreditos = new Label();


    public TelaEstatistica (int conta, List<Operacao> operacoes, Stage mainStage, Scene cenaOperacoes, int mes, int ano, Fachada fachada)
    {
        this.conta = conta;
        this.operacoes = operacoes;
        this.mainStage = mainStage;
        this.cenaOperacoes = cenaOperacoes;
        this.fachada = fachada;
        mesAtual = mes;
        anoAtual = ano;
        quantDebito = 0;
        quantCredito = 0;
        valorCreditos = 0;
        valorDebitos = 0;
    }

    public Scene getTelaEstatistica()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String dadosCorr = conta+" : "+fachada.getCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        tfMes = new TextField();
        tfAno = new TextField();

        HBox anoMes = new HBox(10);        
        anoMes.setAlignment(Pos.BOTTOM_CENTER);
        anoMes.getChildren().add(new Label("Mes:"));
        anoMes.getChildren().add(tfMes);
        anoMes.getChildren().add(new Label("Ano:"));
        anoMes.getChildren().add(tfAno);
        grid.add(anoMes, 0, 1);
        Button btnTroca = new Button("Trocar");

        anoMes.getChildren().add(btnTroca);

        valorOperacoes(mesAtual, anoAtual);

        HBox debitos = new HBox(30);
        debitos.setAlignment(Pos.BOTTOM_CENTER);
        debitos.getChildren().add(totDebitos);
        debitos.getChildren().add(quantDebitos);

        grid.add(debitos, 0, 3);

        HBox creditos = new HBox(30);
        creditos.setAlignment(Pos.BOTTOM_CENTER);
        creditos.getChildren().add(totCreditos);
        creditos.getChildren().add(quantCreditos);

        grid.add(creditos, 0, 4);

        Text salMedio = new Text("Salario medio: " + fachada.getSalarioMedio(mesAtual, anoAtual));
        grid.add(salMedio, 0, 5);

        Label mesSel = new Label("Mes atual: " + mesAtual);
        Label anoSel = new Label("Ano atual: " + anoAtual);
        HBox data = new HBox(30);
        data.setAlignment(Pos.BOTTOM_CENTER);
        data.getChildren().add(mesSel);
        data.getChildren().add(anoSel);

        grid.add(data, 0, 6);

        Button btnVolta = new Button("Tela de operacoes");
        grid.add(btnVolta, 0, 7);


        btnTroca.setOnAction(e -> 
        {
            Calendar date = Calendar.getInstance();
            try
            {
                int mes = Integer.parseInt(tfMes.getText());
                int ano = Integer.parseInt(tfAno.getText());
                fachada.validaData(mes, ano);
                mesAtual = mes;
                anoAtual = ano;
                mesSel.setText("Mes Atual: " + mesAtual);
                anoSel.setText("Ano atual: " + anoAtual);
                valorOperacoes(mes, ano);
            }
            catch (NumberFormatException ex)
            {
                Alert alert = new Alert(AlertType.WARNING);
  				alert.setTitle("Valor inválido !!");
  				alert.setHeaderText(null);
  				alert.setContentText(ex.getLocalizedMessage() + "");

  				alert.showAndWait();
            }
        });

        btnVolta.setOnAction(e ->
        {
            mainStage.setScene(cenaOperacoes);
        });
        
        cenaEstatistica = new Scene(grid);
        return cenaEstatistica;
    }

    private void valorOperacoes(int mes, int ano)
    {
        totDebitos.setText("Total de debitos: " + fachada.totalDebitos(mes, ano));
        quantDebitos.setText("Quantidade de debitos: " + fachada.quantidadeDebitos(mes, ano));
        totCreditos.setText("Total de creditos: " + fachada.totalCreditos(mes, ano));
        quantCreditos.setText("Quantidade de creditos: " + fachada.quantidadeCreditos(mes, ano));
    }
} 