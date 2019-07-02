package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;

public class Validacoes
{
        public boolean deposito(double valor)
        {
            if (valor < 0.0) throw new NumberFormatException("Valor invalido");
            return true;
        }

        public boolean retirada(double valor, double saldo, double limite)
        {
            if (valor < 0.0 || valor > saldo) throw new NumberFormatException("Saldo insuficiente");
            if (valor > limite) throw new NumberFormatException("Limite diário excedido");
            return true;
        }

        public void validaData(int mes, int ano)
        {
            Calendar date = Calendar.getInstance();
            if (mes < 1 || mes > 12) throw new NumberFormatException("Mes invalido");
            if (ano > date.get(Calendar.YEAR) || ano < 1) throw new NumberFormatException("Ano inválido");
        }
}