package com.bcopstein.ExercicioRefatoracaoBanco;

public class StateFactory
{
    public final int SILVER = 0;
	public final int GOLD = 1;
	public final int PLATINUM = 2;

    public StateConta deposito(double saldo, StateConta atual)
    {
        if (saldo >= atual.getLimDeposito())
        {
            if (atual.getStrStatus().equals("Silver")) return new StateGold();
            else if (atual.getStrStatus().equals("Gold")) return new StatePlatinum();
        }
        return atual;
    }

    public StateConta retirada(double saldo, StateConta atual)
    {
        if (saldo <= atual.getLimRetirada())
        {
            if (atual.getStrStatus().equals("Platinum")) return new StateGold();
            else if (atual.getStrStatus().equals("Gold")) return new StateSilver();
        }
        return atual;
    }

    public StateConta novaConta(int state)
    {
        if (state == SILVER) return new StateSilver();
        else if (state == GOLD) return new StateGold();
        else if (state == PLATINUM) return new StatePlatinum();
        else return null;
    }
}