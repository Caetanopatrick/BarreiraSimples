package br.pucpr;

import br.pucpr.Empresa.Funcionario;
import br.pucpr.ReceitaFederal.INSS;
import br.pucpr.ReceitaFederal.ImpostoDeRenda;
import br.pucpr.ReceitaFederal.PlanoDeSaude;
import br.pucpr.ReceitaFederal.PrevidenciaPrivada;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {

    //TODO: Arquivo
    public static File parte01 = new File("C:\\Users\\User\\OneDrive\\IDE\\InteliJ\\programacaoDistribuidaParalelaEConcorrente\\barreiraSimples\\src\\br\\pucpr\\Relatorios\\parte1.txt");
    public static File parte02 = new File("C:\\Users\\User\\OneDrive\\IDE\\InteliJ\\programacaoDistribuidaParalelaEConcorrente\\barreiraSimples\\src\\br\\pucpr\\Relatorios\\parte2.txt");
    public static File parte03 = new File("C:\\Users\\User\\OneDrive\\IDE\\InteliJ\\programacaoDistribuidaParalelaEConcorrente\\barreiraSimples\\src\\br\\pucpr\\Relatorios\\parte3.txt");
    public static File parte04 = new File("C:\\Users\\User\\OneDrive\\IDE\\InteliJ\\programacaoDistribuidaParalelaEConcorrente\\barreiraSimples\\src\\br\\pucpr\\Relatorios\\parte4.txt");

    //TODO: Barreira
    public static int nThreads = 4;
    public static int contadorThreads = 0;
    public static Semaphore barreira = new Semaphore(0);

        private static int nFuncionarios = 1; //todo: NÚMERO DE FUNCIONÁRIOS


    //TODO: Funcionarios
    private static int[] multiplosPor4 = {4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60};
    private static int n = multiplosPor4[nFuncionarios];
    private static int[] partes = {(n/4), (n*2)/4, (n*3)/4};
    private static Funcionario[] listaDeFuncionarios = new Funcionario[n];
    public static Funcionario P1[] = new Funcionario[partes[0]];    //ImpostoDeRenda
    public static Funcionario P2[] = new Funcionario[partes[0]];    //INSS
    public static Funcionario P3[] = new Funcionario[partes[0]];    //PrevidenciaPrivada
    public static Funcionario P4[] = new Funcionario[partes[0]];    //PlanoDeSaude

    //TODO: Mutex
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore mutexP1 = new Semaphore(1);
    public static Semaphore mutexP2 = new Semaphore(1);
    public static Semaphore mutexP3 = new Semaphore(1);
    public static Semaphore mutexP4 = new Semaphore(1);

    public static void main(String[] args) throws IOException {

        //Limpar todos os arquivos
        BufferedWriter Parte01 = new BufferedWriter(new FileWriter(parte01, false));
        Parte01.close();   //Limpar o txt
        BufferedWriter Parte02 = new BufferedWriter(new FileWriter(parte02, false));
        Parte02.close();   //Limpar o txt
        BufferedWriter Parte03 = new BufferedWriter(new FileWriter(parte03, false));
        Parte03.close();   //Limpar o txt
        BufferedWriter Parte04 = new BufferedWriter(new FileWriter(parte04, false));
        Parte04.close();   //Limpar o txt

        for (int i = 0; i < multiplosPor4[nFuncionarios]; i++)
            listaDeFuncionarios[i] = new Funcionario(i+1);

        //Separar o array de funcionários em 4 diferentes arrays
        int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0;
        for (int i = 0; i < n; i++){
            if (i < partes[0])                          P1[cont1++] = listaDeFuncionarios[i];
            else if (partes[0] <= i && i < partes[1])   P2[cont2++] = listaDeFuncionarios[i];
            else if (partes[1] <= i && i < partes[2])   P3[cont3++] = listaDeFuncionarios[i];
            else if (partes[2] <= i)                    P4[cont4++] = listaDeFuncionarios[i];
        }

        ImpostoDeRenda impostoDeRenda = new ImpostoDeRenda(parte01);
        INSS inss = new INSS(parte02);
        PrevidenciaPrivada previdenciaPrivada = new PrevidenciaPrivada(parte03);
        PlanoDeSaude planoDeSaude = new PlanoDeSaude(parte04);

        try {
            impostoDeRenda.start();
            inss.start();
            previdenciaPrivada.start();
            planoDeSaude.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
