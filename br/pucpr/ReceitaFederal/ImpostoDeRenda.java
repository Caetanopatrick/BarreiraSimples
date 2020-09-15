package br.pucpr.ReceitaFederal;

import br.pucpr.Empresa.Funcionario;
import br.pucpr.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImpostoDeRenda extends Thread{

    private final BufferedWriter data;

    private double mem;

    public ImpostoDeRenda(File file) throws IOException {
        this.data = new BufferedWriter(new FileWriter(file, true));
    }

    private double calculo(double salarioBruto){
        return salarioBruto *0.2;
    }

    @Override
    public void run() {
        try {
            //Parte 01
            Main.mutexP1.acquire();
                for (Funcionario funcionario : Main.P1){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[1- THREAD 1 - P1] Funcionario (" + funcionario.getCodigo() + ") calculo Imposto de renda: " + mem);
                }
            Main.mutexP1.release();

            //Parte 02
            Main.mutexP2.acquire();
                for (Funcionario funcionario : Main.P2){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[2- THREAD 1 - P2] Funcionario (" + funcionario.getCodigo() + ") calculo Imposto de renda: " + mem);
                }
            Main.mutexP2.release();

            //Parte 03
            Main.mutexP3.acquire();
                for (Funcionario funcionario : Main.P3){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[3- THREAD 1 - P3] Funcionario (" + funcionario.getCodigo() + ") calculo Imposto de renda: " + mem);
                }
            Main.mutexP3.release();

            //Parte 04
            Main.mutexP4.acquire();
                for (Funcionario funcionario : Main.P4){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[4- THREAD 1 - P4] Funcionario (" + funcionario.getCodigo() + ") calculo Imposto de renda: " + mem);
                }
            Main.mutexP4.release();


            //barreira
            Main.mutex.acquire();
                Main.contadorThreads = Main.contadorThreads+1;
                if (Main.contadorThreads == Main.nThreads) Main.barreira.release();
            Main.mutex.release();

            System.out.println("[THREAD 1] chegou na barreira");
            Main.barreira.acquire();
            Main.barreira.release();
            System.out.println("[THREAD 1] passou da barreira");

            //ponto critico
            Main.mutexP1.acquire();
                for (Funcionario funcionario : Main.P1){
                    String txt = "Funcionário: " + funcionario.getCodigo() + " possui salário bruto de R$" + funcionario.getSalarioBruto() + ". Teve o desconto total de R$" + funcionario.getTotalDescontos() + " passando a ficar com o salário liquido de R$" + funcionario.getSalarioLiquido() + ".";
                    data.append(txt);
                    data.newLine();
                    data.flush();
                    System.out.println("[THREAD 1] - imprimiu o Funcionário " + funcionario.getCodigo());
                }
            Main.mutexP1.release();
        } catch (Exception e){
            System.out.println("ERROR: [THREAD 1] - Imposto de Renda");
            e.printStackTrace();
        }
    }
}
