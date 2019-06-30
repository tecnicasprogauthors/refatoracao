package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;

public class Fachada
{
    private static Fachada instance;
    private Contas contas;
    private Operacoes operacoes;
    private Validacoes validacoes;
    private int numContaAtual;

    private Fachada(Persistencia pers)
    {
        operacoes = new Operacoes(pers);
        contas = new Contas(pers, operacoes);
        validacoes = new Validacoes();
    }

    public static Fachada getIntance()
    {
        if(instance == null) instance = new Fachada(Persistencia.getInstance());
        return instance;
    }

    public void setContaAtual(int num)
    {
        numContaAtual = num;
    }

    public void deposito(double valor, int dia, int mes, int ano, int hora, int minuto, int segundo, int statusConta,
    double valorOperacao, int tipoOperacao)
    {
        contas.deposito(valor, dia, numContaAtual);
        operacoes.newOp(dia, mes, ano, hora, minuto, segundo, numContaAtual, statusConta, valorOperacao, tipoOperacao);
    }

    public void retirada(double valor, int dia, int mes, int ano, int hora, int minuto, int segundo, int statusConta,
    double valorOperacao, int tipoOperacao)
    {
        contas.deposito(valor, dia, numContaAtual);
        operacoes.newOp(dia, mes, ano, hora, minuto, segundo, numContaAtual, statusConta, valorOperacao, tipoOperacao);
    }

    public ListView<Operacao> extrato()
    {
        return new ListView<Operacao>(FXCollections.observableArrayList(operacoes.operacoesConta(numContaAtual)));
    }

    private double getSalarioMedio(int mes, int ano)
    {
        List<Operacao> ops = new ArrayList<Operacao>();
        double saldoPrimeiroDia = 0;
        for (Operacao op : operacoes.operacoesConta(numContaAtual))
        {
            if (op.getMes() == mes && op.getAno() == ano)ops.add(op);
            else if (op.getAno() < ano || (op.getMes() < mes && op.getAno() == ano))
            {
                if (op.getTipoOperacao() == op.DEBITO) saldoPrimeiroDia -= op.getValorOperacao();
                else saldoPrimeiroDia += op.getValorOperacao();
            }
        }
        double soma = 0;
        double aux = saldoPrimeiroDia;
        int j = 0;
        Operacao op = null;
        if (!ops.isEmpty()) op = ops.get(j);
        for (int i = 1; i <= 30; i++)
        {
            while (op != null && op.getDia() == i)
            {
                if (op.getTipoOperacao() == op.DEBITO) aux -= op.getValorOperacao();
                else aux += op.getValorOperacao();
                j++;
                if (j == ops.size()) break;
                op = ops.get(j);
            }
            soma += aux;
        }
        return soma/30;
    }

    public double getSaldo()
    {
        return contas.getSaldo(numContaAtual);
    }

    public double totalCreditos(int mes, int ano)
    {
        double totalCreditos = 0;
        for (Operacao op : operacoes.operacoesConta(numContaAtual))
        {
            if (op.getMes() == mes && op.getAno() == ano) 
            {
                if (op.getTipoOperacao() == op.CREDITO)totalCreditos += op.getValorOperacao();
            }
        }
        return totalCreditos;
    }

    public double totalDebitos(int mes, int ano)
    {
        double totalDebitos = 0;
        for (Operacao op : operacoes.operacoesConta(numContaAtual))
        {
            if (op.getMes() == mes && op.getAno() == ano) 
            {
                if (op.getTipoOperacao() == op.DEBITO)totalDebitos += op.getValorOperacao();
            }
        }
        return totalDebitos;
    }

    public int quantidadeCreditos(int mes, int ano)
    {
        int quantidadeCreditos = 0;
        for (Operacao op : operacoes.operacoesConta(numContaAtual))
        {
            if (op.getMes() == mes && op.getAno() == ano) 
            {
                if (op.getTipoOperacao() == op.CREDITO)quantidadeCreditos++;
            }
        }
        return quantidadeCreditos;
    }

    public int quantidadeDebitos(int mes, int ano)
    {
        int quantidadeDebitos = 0;
        for (Operacao op : operacoes.operacoesConta(numContaAtual))
        {
            if (op.getMes() == mes && op.getAno() == ano) 
            {
                if (op.getTipoOperacao() == op.CREDITO)quantidadeDebitos++;
            }
        }
        return quantidadeDebitos;
    }
}