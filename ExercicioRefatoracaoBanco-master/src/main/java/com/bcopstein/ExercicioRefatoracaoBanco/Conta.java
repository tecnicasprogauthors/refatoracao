package com.bcopstein.ExercicioRefatoracaoBanco;
public class Conta 
{
	public final int SILVER = 0;
	public final int GOLD = 1;
	public final int PLATINUM = 2;
	public final int LIM_SILVER_GOLD = 50000;
	public final int LIM_GOLD_PLATINUM = 200000;
	public final int LIM_PLATINUM_GOLD = 100000;
	public final int LIM_GOLD_SILVER = 25000;
	public final double LIMRET_SILVER = 10000.0;
	public final double LIMRET_GOLD = 100000.0;
	public final double LIMRET_PLATINUM = 500000.0;

	private int numero;
	private String correntista;
	private double saldo;
	private int status;
	private double limiteMaximo;
	private double limiteAtual;
	private int diaUltOperacao;

	public Conta(int umNumero, String umNome) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		status = SILVER;
		limiteMaximo = getLimRetiradaDiaria();
		limiteAtual = limiteMaximo;
		diaUltOperacao = 0;
	}
	
	public Conta(int umNumero, String umNome,double umSaldo, int umStatus) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;
		status = umStatus;
		limiteMaximo = getLimRetiradaDiaria();
		limiteAtual = limiteMaximo;
		diaUltOperacao = 0;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public Integer getNumero() {
		return numero;
	}
	
	public String getCorrentista() {
		return correntista;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getStrStatus() {
		switch(status) {
		case SILVER:  return "Silver";
		case GOLD:  return "Gold";
		case PLATINUM:  return "Platinum";
		default: return "none";
		}
	}
	
	public double getLimRetiradaDiaria() 
	{
		switch(status) {
		case SILVER:  return LIMRET_SILVER;
		case GOLD:  return LIMRET_GOLD;
		case PLATINUM:  return LIMRET_PLATINUM;
		default: return 0.0;
		}
	}
	
	public void deposito(double valor, int dia) 
	{
		if (status == SILVER) 
		{
			saldo += valor;
			if (saldo >= LIM_SILVER_GOLD) 
			{
				status = GOLD;
			}
		} 
		else if (status == GOLD) 
		{
			saldo += valor * 1.01;
			if (saldo >= LIM_GOLD_PLATINUM) 
			{
				status = PLATINUM;
			}
		} 
		else if (status == PLATINUM) 
		{
			saldo += valor * 1.025;
		}
		limiteMaximo = getLimRetiradaDiaria();
		diaUltOperacao = dia;
	}

	public void retirada(double valor, int dia) 
	{
		if (diaUltOperacao != dia) limiteAtual = limiteMaximo;
		diaUltOperacao = dia;
		
		if (saldo - valor < 0.0 || valor > limiteAtual) return;
		else 
		{
			saldo = saldo - valor;
			limiteAtual -= valor;
			if (status == PLATINUM) 
			{
				if (saldo < LIM_PLATINUM_GOLD) 
				{
					status = GOLD;
				}
			} 
			else if (status == GOLD) 
			{
				if (saldo < LIM_GOLD_SILVER) 
				{
					status = SILVER;
				}
			}
		}
	}

	public double getLimiteAtual()
	{
		return limiteAtual;
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + ", status=" + status
				+ "]";
	}
}
