package br.pucpr.Empresa;

import java.util.Random;

public class Funcionario {

    private final int codigo;
    private final double salarioBruto;
    private double descontoDeImpostoDeRenda;
    private double descontoINSS;
    private double descontoPrevidenciaPrivada;
    private double descontoPlanoDeSaude;
    private double totalDescontos;
    private double salarioLiquido;


    public Funcionario(int codigo){
        this.codigo = codigo;
        this.salarioBruto = (new Random().nextDouble()) + (new Random().nextInt(4000)+1000);
        this.salarioLiquido = salarioBruto;
    }

    //getters
    public int getCodigo() {
        return codigo;
    }

    public double getSalarioBruto(){
        return this.salarioBruto;
    }

    public double getDescontoDeImpostoDeRenda() {
        return descontoDeImpostoDeRenda;
    }

    public double getDescontoINSS() {
        return descontoINSS;
    }

    public double getDescontoPrevidenciaPrivada() {
        return descontoPrevidenciaPrivada;
    }

    public double getDescontoPlanoDeSaude() {
        return descontoPlanoDeSaude;
    }

    public double getTotalDescontos() {
        return totalDescontos;
    }

    public double getSalarioLiquido() {
        return salarioLiquido;
    }

    //setters
    public void setDescontoDeImpostoDeRenda(double descontoDeImpostoDeRenda) {
        this.descontoDeImpostoDeRenda = descontoDeImpostoDeRenda;
        this.totalDescontos = totalDescontos + descontoDeImpostoDeRenda;
        this.salarioLiquido = salarioLiquido - descontoDeImpostoDeRenda;
    }

    public void setDescontoINSS(int descontoINSS) {
        this.descontoINSS = descontoINSS;
        this.totalDescontos = totalDescontos + descontoINSS;
        this.salarioLiquido = salarioLiquido - descontoINSS;
    }

    public void setDescontoPlanoDeSaude(double descontoPlanoDeSaude) {
        this.descontoPlanoDeSaude = descontoPlanoDeSaude;
        this.totalDescontos = totalDescontos + descontoPlanoDeSaude;
        this.salarioLiquido = salarioLiquido - descontoPlanoDeSaude;
    }

    public void setDescontoPrevidenciaPrivada(double descontoPrevidenciaPrivada) {
        this.descontoPrevidenciaPrivada = descontoPrevidenciaPrivada;
        this.totalDescontos = totalDescontos + descontoPrevidenciaPrivada;
        this.salarioLiquido = salarioLiquido - descontoPrevidenciaPrivada;
    }

    public void setSalarioLiquido(double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public void setTotalDescontos(double totalDescontos) {
        this.totalDescontos = totalDescontos;
    }
}
