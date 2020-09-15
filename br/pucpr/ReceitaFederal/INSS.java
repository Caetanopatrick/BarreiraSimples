package br.pucpr.ReceitaFederal;

import br.pucpr.Empresa.Funcionario;
import br.pucpr.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class INSS extends Thread{

    private final BufferedWriter data;

    private double mem;

    public INSS (File file) throws IOException {
        this.data = new BufferedWriter(new FileWriter(file, true));
    }

    private double calculo(double salarioBruto){
        return salarioBruto *0.08;
    }

    @Override
    public void run() {
        try {

            //Parte 02
            Main.mutexP2.acquire();
                for (Funcionario funcionario : Main.P2){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                        System.out.println("[1- THREAD 2 - P2] Funcionario (" + funcionario.getCodigo() + ") calculo Previdencia Obrigatoria: " + mem);
                }
            Main.mutexP2.release();

            //Parte 03
            Main.mutexP3.acquire();
                for (Funcionario funcionario : Main.P3){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[2- THREAD 2 - P3] Funcionario (" + funcionario.getCodigo() + ") calculo Previdencia Obrigatoria: " + mem);
                }
            Main.mutexP3.release();

            //Parte 04
            Main.mutexP4.acquire();
                for (Funcionario funcionario : Main.P4){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[3- THREAD 2 - P4] Funcionario (" + funcionario.getCodigo() + ") calculo Previdencia Obrigatoria: " + mem);
                }
            Main.mutexP4.release();

            //Parte 01
            Main.mutexP1.acquire();
                for (Funcionario funcionario : Main.P1){
                    mem = calculo(funcionario.getSalarioBruto());
                    funcionario.setDescontoDeImpostoDeRenda(mem);
                    System.out.println("[4- THREAD 2 - P1] Funcionario (" + funcionario.getCodigo() + ") calculo Previdencia Obrigatoria: " + mem);
                }
            Main.mutexP1.release();

            Main.mutex.acquire();
                Main.contadorThreads = Main.contadorThreads+1;
                if (Main.contadorThreads == Main.nThreads) Main.barreira.release();
            Main.mutex.release();

            System.out.println("[THREAD 2] chegou na barreira");
            Main.barreira.acquire();
            Main.barreira.release();
            System.out.println("[THREAD 2] passou da barreira");

            //ponto critico
            Main.mutexP2.acquire();
                for (Funcionario funcionario : Main.P2){
                    String txt = "Funcionário: " + funcionario.getCodigo() + " possui salário bruto de R$" + funcionario.getSalarioBruto() + ". Teve o desconto total de R$" + funcionario.getTotalDescontos() + " passando a ficar com o salário liquido de R$" + funcionario.getSalarioLiquido() + ".";
                    data.append(txt);
                    data.newLine();
                    data.flush();
                    System.out.println("[THREAD 2] - imprimiu o Funcionário " + funcionario.getCodigo());
                }
            Main.mutexP2.release();
        } catch (Exception e){
            System.out.println("ERROR: [THREAD 2] - INSS");
            e.printStackTrace();
        }
    }
}
