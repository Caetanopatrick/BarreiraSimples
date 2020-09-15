package br.pucpr.ReceitaFederal;

import br.pucpr.Empresa.Funcionario;
import br.pucpr.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlanoDeSaude extends Thread{

    private final BufferedWriter data;

    private double mem;

    public PlanoDeSaude(File file) throws IOException {
        this.data = new BufferedWriter(new FileWriter(file, true));
    }

    private double calculo(double salarioBruto){
        return salarioBruto *0.02;
    }

    @Override
    public void run() {
        try {
            //Parte 04
            Main.mutexP4.acquire();
                for (Funcionario funcionario : Main.P4){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[1- THREAD 4 - P4] Funcionario (" + funcionario.getCodigo() + ") calculo Plano de Saude: " + mem);
                }
            Main.mutexP4.release();

            //Parte 01
            Main.mutexP1.acquire();
                for (Funcionario funcionario : Main.P1){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[2- THREAD 4 - P1] Funcionario (" + funcionario.getCodigo() + ") calculo Plano de Saude: " + mem);
                }
            Main.mutexP1.release();

            //Parte 02
            Main.mutexP2.acquire();
                for (Funcionario funcionario : Main.P2){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[3- THREAD 4 - P2] Funcionario (" + funcionario.getCodigo() + ") calculo Plano de Saude: " + mem);
                }
            Main.mutexP2.release();

            //Parte 03
            Main.mutexP3.acquire();
                for (Funcionario funcionario : Main.P3){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[4- THREAD 4 - P3] Funcionario (" + funcionario.getCodigo() + ") calculo Plano de Saude: " + mem);
                }
            Main.mutexP3.release();


            Main.mutex.acquire();
                Main.contadorThreads = Main.contadorThreads+1;
                if (Main.contadorThreads == Main.nThreads) Main.barreira.release();
            Main.mutex.release();

            System.out.println("[THREAD 4] chegou na barreira");
            Main.barreira.acquire();
            Main.barreira.release();
            System.out.println("[THREAD 4] passou da barreira");

            //ponto critico
            Main.mutexP4.acquire();
                for (Funcionario funcionario : Main.P4){
                    String txt = "Funcionário: " + funcionario.getCodigo() + " possui salário bruto de R$" + funcionario.getSalarioBruto() + ". Teve o desconto total de R$" + funcionario.getTotalDescontos() + " passando a ficar com o salário liquido de R$" + funcionario.getSalarioLiquido() + ".";
                    data.append(txt);
                    data.newLine();
                    data.flush();
                    System.out.println("[THREAD 4] - imprimiu o Funcionário " + funcionario.getCodigo());
                }
            Main.mutexP4.release();
        } catch (Exception e){
            System.out.println("ERROR: [THREAD 4] - Plano de Saude");
            e.printStackTrace();
        }
    }
}
