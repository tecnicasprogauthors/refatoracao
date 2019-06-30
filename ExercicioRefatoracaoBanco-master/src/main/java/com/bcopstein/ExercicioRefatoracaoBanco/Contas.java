package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Map;

public class Contas
{
    private Map<Integer, Conta> contas;
    private Persistencia pers;
    private Operacoes op;

    public Contas(Persistencia pers, Operacoes op)
    {
        this.pers = pers;
        this.op = op;
        contas = pers.loadContas();
    }
    	
    public double getSaldo(int numero) 
    {
		return contas.get(numero).getSaldo();
	}

	public String getCorrentista(int numero) {
		return contas.get(numero).getCorrentista();
	}
	
    public int getStatus(int numero) 
    	{
		return contas.get(numero).getStatus();
	}
	
	public double getLimRetiradaDiaria(int numero) 
	{
        	return contas.get(numero).getLimRetiradaDiaria();
	}
	
	public void deposito(double valor, int dia, int numero) 
	{
        	contas.get(numero).deposito(valor, dia);
	}

	public void retirada(double valor, int dia, int numero) 
	{
        	contas.get(numero).retirada(valor, dia);
	}

	public double getLimiteAtual(int numero)
	{
        	return contas.get(numero).getLimiteAtual();
	}
}