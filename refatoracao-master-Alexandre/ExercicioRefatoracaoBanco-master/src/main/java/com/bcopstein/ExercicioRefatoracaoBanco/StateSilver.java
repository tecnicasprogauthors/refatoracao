package com.bcopstein.ExercicioRefatoracaoBanco;

public class StateSilver implements StateConta
{
    public final double LIMRET_SILVER = 10000.0;
    public final int LIM_SILVER_GOLD = 50000;

    public double deposito(double valor)
    {
        return valor;
    }

    public String getStrStatus()
    {
        return "Silver";
    }
    public double getLimRetiradaDiaria()
    {
        return LIMRET_SILVER;
    }

    public double getLimRetirada()
    {
        return 0;
    }

    public double getLimDeposito()
    {
        return LIM_SILVER_GOLD;
    }

    public int getStatus()
    {
        return 0;
    }
}