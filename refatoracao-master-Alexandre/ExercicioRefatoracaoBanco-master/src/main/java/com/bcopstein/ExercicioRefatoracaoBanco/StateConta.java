package com.bcopstein.ExercicioRefatoracaoBanco;

public interface StateConta
{
    public double deposito(double valor);
    public String getStrStatus();
    public double getLimRetiradaDiaria();
    public double getLimRetirada();
    public double getLimDeposito();
    public int getStatus();
}