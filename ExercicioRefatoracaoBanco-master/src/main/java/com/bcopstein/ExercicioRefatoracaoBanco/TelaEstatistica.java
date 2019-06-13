package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.GregorianCalendar;
import java.util.List;
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

public class TelaEstatistica
{
    private Conta conta;
    private List<Operacao> operacoes;
    private Stage mainStage;
    private Scene cenaEstatistica;
    private Scene cenaOperacoes;
    private int mesAtual;
    private int anoAtual;

    private int quantDebito;
    private int quantCredito;
    private double valorCreditos;
    private double valorDebitos;

    private TextField tfMes;
    private TextField tfAno;

    public TelaEstatistica (Conta conta, List<Operacao> operacoes, Stage mainStage, Scene cenaOperacoes, int mes, int ano)
    {
        this.conta = conta;
        this.operacoes = operacoes;
        this.mainStage = mainStage;
        this.cenaOperacoes = cenaOperacoes;
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

        String dadosCorr = conta.getNumero()+" : "+conta.getCorrentista();
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

        Label totDebitos = new Label("Total de debitos: " + valorDebitos);
        Label quantDebitos = new Label("Quantidade de debitos: " + quantDebito);

        HBox debitos = new HBox(30);
        debitos.setAlignment(Pos.BOTTOM_CENTER);
        debitos.getChildren().add(totDebitos);
        debitos.getChildren().add(quantDebitos);

        grid.add(debitos, 0, 3);

        Label totCreditos = new Label("Total de creditos: " + valorCreditos);
        Label quantCreditos = new Label("Quantidade de creditos: " + quantCredito);

        HBox creditos = new HBox(30);
        creditos.setAlignment(Pos.BOTTOM_CENTER);
        creditos.getChildren().add(totCreditos);
        creditos.getChildren().add(quantCreditos);

        grid.add(creditos, 0, 4);

        Label salarioMedio = new Label(""+ getSalarioMedio(mesAtual, anoAtual));

        Label mesSel = new Label("Mes atual: " + mesAtual);
        Label anoSel = new Label("Ano atual: " + anoAtual);
        HBox data = new HBox(30);
        data.setAlignment(Pos.BOTTOM_CENTER);
        data.getChildren().add(mesSel);
        data.getChildren().add(anoSel);

        grid.add(data, 0, 5);


        btnTroca.setOnAction(e -> 
        {
            GregorianCalendar date = new GregorianCalendar();
            try
            {
                int mes = Integer.parseInt(tfMes.getText());
                if (mes < 1 || mes > 12) throw new NumberFormatException("Mes invalido");
                int ano = Integer.parseInt(tfAno.getText());
                if (ano > date.get(GregorianCalendar.YEAR) || ano < 1) throw new NumberFormatException("Ano inválido");
                mesAtual = mes;
                anoAtual = ano;
                mesSel.setText("Mes Atual" + mesAtual);
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
        cenaEstatistica = new Scene(grid);
        return cenaEstatistica;
    }

    private void valorOperacoes(int mes, int ano)
    {
        for (Operacao op : operacoes)
        {
            if (op.getMes() == mes && op.getAno() == ano) 
            {
                if (op.getTipoOperacao() == op.DEBITO)
                {
                    valorDebitos += op.getValorOperacao();
                    quantDebito++;
                }
                else
                {
                    valorCreditos += op.getValorOperacao();
                    quantCredito++;
                }
            }
        }
    }

    private double getSalarioMedio(int mes, int ano)
    {
        double soma = 0;
        double saldoPrimeiroDia = 0;
        for (Operacao op : operacoes)
        {
            if (op.getMes() == mes)
            {
                if (op.getTipoOperacao() == op.DEBITO) soma -= op.getValorOperacao();
                else soma += op.getValorOperacao();
            }
            else if (op.getAno() < ano || (op.getMes() < mes && op.getAno() == ano))
            {
                if (op.getTipoOperacao() == op.DEBITO) saldoPrimeiroDia -= op.getValorOperacao();
                else saldoPrimeiroDia += op.getValorOperacao();
            }
        }
        return (saldoPrimeiroDia + soma)/30;
    }
} 