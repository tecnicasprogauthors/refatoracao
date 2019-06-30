package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.List;
import java.util.stream.Collectors;

public class Operacoes
{
    private List<Operacao> operacoes;
    private Persistencia pers;

    public Operacoes(Persistencia pers)
    {
        this.pers = pers;
        operacoes = pers.loadOperacoes();
    }

    public void newOp(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
    double valorOperacao, int tipoOperacao)
    {
        operacoes.add(new Operacao(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, tipoOperacao));
    }

    public List<Operacao> operacoesConta(int numero)
    {
        return operacoes
        .stream()
        .filter(op -> op.getNumeroConta() == numero)
        .collect(Collectors.toList())
        ;
    }
}