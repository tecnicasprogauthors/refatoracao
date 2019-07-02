package com.bcopstein.ExercicioRefatoracaoBanco;

public class StatePlatinum implements StateConta 
{
    public final double LIMRET_PLATINUM = 500000.0;
    public final int LIM_PLATINUM_GOLD = 100000;

    public double deposito(double valor)
    {
        return valor * 1.025;
    }

    public String getStrStatus()
    {
        return "Platinum";
    }
    public double getLimRetiradaDiaria()
    {
        return LIMRET_PLATINUM;
    }

    public double getLimRetirada()
    {
        return LIM_PLATINUM_GOLD;
    }

    public double getLimDeposito()
    {
        return 0;
    }

    public int getStatus()
    {
        return 2;
    }
}