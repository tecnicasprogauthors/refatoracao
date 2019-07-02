package com.bcopstein.ExercicioRefatoracaoBanco;
public class Conta 
{
	//public final int SILVER = 0;
	//public final int GOLD = 1;
	//public final int PLATINUM = 2;
	//public final int LIM_SILVER_GOLD = 50000;
	//public final int LIM_GOLD_PLATINUM = 200000;
	//public final int LIM_PLATINUM_GOLD = 100000;
	//public final int LIM_GOLD_SILVER = 25000;
	//public final double LIMRET_SILVER = 10000.0;
	//public final double LIMRET_GOLD = 100000.0;
	//public final double LIMRET_PLATINUM = 500000.0;

	private int numero;
	private String correntista;
	private double saldo;
	private double limiteMaximo;
	private double limiteAtual;
	private int diaUltOperacao;
	private StateConta state;
	private StateFactory factory;

	public Conta(int umNumero, String umNome, int state) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		diaUltOperacao = 0;
		factory = new StateFactory();
		this.state = factory.novaConta(state);
		limiteMaximo = getLimRetiradaDiaria();
		limiteAtual = limiteMaximo;
	}
	
	public Conta(int umNumero, String umNome, double umSaldo, int state) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;
		diaUltOperacao = 0;
		factory = new StateFactory();
		this.state = factory.novaConta(state);
		limiteMaximo = getLimRetiradaDiaria();
		limiteAtual = limiteMaximo;
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
		return state.getStatus();
	}
	
	public String getStrStatus() {
		return state.getStrStatus();
	}
	
	public double getLimRetiradaDiaria() 
	{
		return state.getLimRetiradaDiaria();
	}
	
	public void deposito(double valor, int dia) 
	{
		saldo += state.deposito(valor);
		state = factory.deposito(saldo, state);
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
			saldo -= valor;
			limiteAtual -= valor;
			state = factory.retirada(saldo, state);
		}
	}

	public double getLimiteAtual()
	{
		return limiteAtual;
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + ", status=" + getStatus()
				+ "]";
	}
}
