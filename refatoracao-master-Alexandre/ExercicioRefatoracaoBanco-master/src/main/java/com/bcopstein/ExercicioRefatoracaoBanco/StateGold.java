package com.bcopstein.ExercicioRefatoracaoBanco;

public class StateGold implements StateConta 
{
    public final double LIMRET_GOLD = 100000.0;
    public final int LIM_GOLD_PLATINUM = 200000;
    public final int LIM_GOLD_SILVER = 25000;

    public double deposito(double valor)
    {
        return valor * 1.01;
    }

    public String getStrStatus()
    {
        return "Gold";
    }
    public double getLimRetiradaDiaria()
    {
        return LIMRET_GOLD;
    }

    public double getLimRetirada()
    {
        return LIM_GOLD_SILVER;
    }

    public double getLimDeposito()
    {
        return LIM_GOLD_PLATINUM;
    }

    public int getStatus()
    {
        return 0;
    }
}