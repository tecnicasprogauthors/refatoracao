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
    private ObservableList<Operacao> operacoesConta;

    private Fachada(Persistencia pers)
    {
        operacoes = new Operacoes(pers);
        contas = new Contas(pers, operacoes);
        validacoes = new Validacoes();
    }

    public static Fachada getInstance()
    {
        if(instance == null) instance = new Fachada(Persistencia.getInstance());
        return instance;
    }

    public void setContaAtual(int num)
    {
        numContaAtual = num;
        operacoesConta = FXCollections.observableArrayList(operacoes.operacoesConta(numContaAtual));
    }

    public void deposito(double valor, int dia, int mes, int ano, int hora, int minuto, int segundo, int tipoOperacao)
    {
        if (!validacoes.deposito(valor)) return;
        contas.deposito(valor, dia, numContaAtual);
        operacoes.newOp(dia, mes, ano, hora, minuto, segundo, numContaAtual, contas.getStatus(numContaAtual), valor, tipoOperacao, operacoesConta);
    }

    public void retirada(double valor, int dia, int mes, int ano, int hora, int minuto, int segundo, int tipoOperacao)
    {
        if (!validacoes.retirada(valor, contas.getSaldo(numContaAtual), contas.getLimiteAtual(numContaAtual))) return;
        contas.retirada(valor, dia, numContaAtual);
        operacoes.newOp(dia, mes, ano, hora, minuto, segundo, numContaAtual, contas.getStatus(numContaAtual), valor, tipoOperacao, operacoesConta);
    }

    public ObservableList<Operacao> extrato()
    {
        return operacoesConta;
    }

    public List<Operacao> operacoesConta()
    {
        return operacoes.operacoesConta(numContaAtual);
    }

    public double getSalarioMedio(int mes, int ano)
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
        for (int i = 1; i <= 30; i++)
        {
            for(Operacao op : ops)
            {
                if (op.getDia() == i)
                {
                    if (op.getTipoOperacao() == op.DEBITO) aux -= op.getValorOperacao();
                    else aux += op.getValorOperacao();
                }
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
                if (op.getTipoOperacao() == op.DEBITO)quantidadeDebitos++;
            }
        }
        return quantidadeDebitos;
    }

    public String getCorrentista()
    {
        return contas.getCorrentista(numContaAtual);
    }

    public String getStrStatus()
    {
        return contas.getStrStatus(numContaAtual);
    }

    public double getLimiteRetiradaDiaria()
    {
        return contas.getLimRetiradaDiaria(numContaAtual);
    }

    public double getLimiteAtual()
    {
        return contas.getLimiteAtual(numContaAtual);
    }

    public void save()
    {
        contas.save();
        operacoes.save();
    }

    public boolean existeConta(int num)
    {
        boolean aux = contas.existeConta(num);
        if (!aux) throw new NumberFormatException("Conta invalida");
        return aux;
    }

    public void validaData(int mes, int ano)
    {
        validacoes.validaData(mes, ano);
    }
}