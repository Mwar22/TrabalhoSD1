/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista01e19;

import java.io.*;
import static java.lang.System.out;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Felix
 */
public class Lista01E19 {

    static Scanner leia = new Scanner(System.in, "latin1");
    private static List<Conta> contas= new ArrayList<>();
    private static int nDestaAgencia;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        carregaContas();
        out.print("\n\nENTRE COM O NÚMERO DESTA AGENCIA: ");
        nDestaAgencia= leiaInt(999999);
        opInicial();
    }
    /**
     * recebe um número inteiro >=0 até o valor máximo
     * @param maximo valor máximo que pode ser recebido
     * @return numero rebido
     */
    static int leiaInt(int maximo){
        int i;
        do{
            try{
                i=leia.nextInt();
                leia.nextLine();
                if (i>=0 && i<=maximo)return i;
                out.print("NÚMERO INVÁLIDO! Tente novamente: ");
            }catch (Exception e){
                out.print("DIGITE SOMENTE NÚMEROS: ");
                leia.nextLine();
            }
        }while (true);
    }
    
    static double leiaDouble(){
        double d;
        do{
            try{
                d=leia.nextDouble();
                leia.nextLine();
                if (d<0) {
                    out.print("O VALOR DEVE SER MAIOR OU IGUAL A ZERO: ");
                    continue;
                }
                return d;
            }catch (Exception e){
                out.print("DIGITE SOMENTE NÚMEROS: ");
                leia.nextLine();
            }
        }while (true);
    }
    
    static String leiaNome(){
        do{
            String temp = leia.nextLine();
            if (!temp.isEmpty()) return temp;
            out.print("Digite um nome: ");
        } while (true);
    }

    private static void novaConta() {
        do{
            out.println("\n     CADASTRAMENTO DE NOVA CONTA");
            out.println("\nAgência atual: "+nDestaAgencia);
            out.print("\nEntre com o nº da Agência de destino da conta ou 0 (zero) para sair: ");
            int a= leiaInt(999999);
            if (a==0) return;
            out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
            int c= leiaInt(999999);
            if (c==0) return;
            if (!(pegaConta(a, c)==null)) {
                out.print("\nCONTA JÁ EXISTE! Tente novamente:");
                continue;
            }
            out.print("\nEntre com o nome titular da conta ou 0 (zero) para sair: ");
            String titular= leiaNome();
            if (titular.equals("0")) return;
            out.print("\nEntre com o limite da conta ou 0 (zero) para sair: ");
            double limite= leiaDouble();
            if (limite==0) return;
            out.print("\nDigite 1 para conta ESPECIAL ou 0 (zero) para conta COMUM: ");
            int especial= leiaInt(1);
            
            Conta novaConta= new Conta(a, c, titular, 0, limite, especial);
            if (gravaContaNova(novaConta)){
                contas.add(novaConta);
                out.println("\nCONTA CRIADA COM SUCESSO!");
                return;
            } 
            out.println("CONTA NÃO CRIADA!\nOCORREU UM PROBLEMA AO TENTAR CRIAR NOVA CONTA!");
        }while(true);      
    }

    private static void acessaConta() {
        while (true){
            out.println("\n    ACESSAR CONTA");
            out.println("\nAgência atual: "+nDestaAgencia);
            out.print("\nEntre com o nº da Agencia da conta ou 0 (zero) para sair: ");
            int ag= leiaInt(999999);
            if (ag==0) return;
            out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
            int c= leiaInt(999999);
            if (c==0) return;
            Conta conta= pegaConta(ag, c);
            if (conta==null) {
                out.println("\nCONTA OU AGÊNCIA NÃO ENCONTRADA!");
                continue;
            }
            
            opConta(conta);
        }
    }

    private static void saldo(Conta conta) {
        NumberFormat nf= NumberFormat.getCurrencyInstance();
        out.println("\n\nSaldo atual: "+nf.format(conta.getSaldo()));
        out.println("\nImprimindo comprovante...");
        recibo(conta.toString(), "SALDO" , "Saldo atual: "+nf.format(conta.getSaldo()));
        
    }
    
    /**
     * imprime recibo na saída padrão
     * @param t1
     * @param t2 
     */
    private static void recibo(String s1, String s2, String s3){
        out.println("\n______________________________________________");
        out.println("\n               SISTEMA BANCÁRIO");
        out.println("\n"+s1);
        out.println("\n      COMPROVANTE DE "+s2);
        out.println("\n\n\n\n"+s3);
        out.println("\n\n\n"+"                via do cliente");
        out.println("\n______________________________________________\n");
    }

    private static void extrato(Conta conta) {
        out.println("\nImprimindo comprovante do extrato...");
        out.println("\n______________________________________________");
        out.println("\n               SISTEMA BANCÁRIO");
        out.println("\n"+conta.toString());
        out.println("\n\n        COMPROVANTE DE EXTRATO DA CONTA");
        out.println("\n");
        out.println(conta.extrato());
        out.println("\n\n               via do cliente");
        out.println("\n______________________________________________\n");
    }

    private static void saque(Conta conta) {
        out.println("\n\n     SAQUE EM CONTA");
        out.println("\nAgência atual: "+nDestaAgencia);
        out.print("\nENTRE COM O VALOR A SACAR ou 0 (zero) para sair: ");
        double valor= leiaDouble();
        if (valor==0) return;
        String processamento= conta.saque(valor, nDestaAgencia);
        if (!(processamento.charAt(0)=='A')) {
            out.println("\n"+processamento);
            return;
        }
        out.println("\nGravando operação...");
        if (gravaContas()) {
            out.println("\nSaque realizado com sucesso!\nImprimindo recibo...");
            recibo(conta.toString(), "SAQUE", processamento );
        } else out.println("falha no arquivo causou inconsistência de dados,\nreinicie este programa e contate suporte técnico");   
    }

    private static void deposito(Conta conta) {
        out.println("\n\n     DEPÓSITO EM CONTA");
        out.println("\nAgência atual: "+nDestaAgencia);
        out.print("\nENTRE COM O VALOR A DEPOSITAR ou 0 (zero) para cancelar: ");
        double valor= leiaDouble();
        if (valor==0) return;
        String processamento= conta.deposito(valor, nDestaAgencia);
        if (!(processamento.charAt(0)=='A')) {
            out.println("\n"+processamento);
            return;
        }
        out.println("\nGravando operação...");
        if (gravaContas()) {
            out.println("\nDepósito realizado com sucesso!\nImprimindo recibo...");
            recibo(conta.toString(), "DEPÓSITO", processamento );
        } else out.println("falha no arquivo causou inconsistência de dados,\nreinicie este programa e contate suporte técnico");   
    }

    private static void transferencia(Conta conta) {
        Conta contaDestino;
        do{//pega a conta de destino para a transferência
            out.println("\n\n     TRANSFENCIA BANCÁRIA");
            out.println("\nAgência atual: "+nDestaAgencia);
            out.print("\nEntre com o nº da Agencia de DESTINO ou 0 (zero) para sair: ");
            int ag= leiaInt(999999);
            if (ag==0) return;
            out.print("\nEntre com o nº da Conta de DESTINO ou 0 (zero) para sair: ");
            int c= leiaInt(999999);
            if (c==0) return;
            contaDestino= pegaConta(ag, c);
            if (contaDestino==null) {
                 out.println("\nCONTA NÃO ENCONTRADA!");
                continue;
            }
            break;
        }while (true);
        
        out.print("\nENTRE COM O VALOR A TRANSFERIR ou 0 (zero) para sair: ");
        double valor= leiaDouble();
        if (valor==0) return;
        String processamento= conta.transferencia(contaDestino, valor);
        if (!(processamento.charAt(0)=='A')) {
            out.println("\n"+processamento);
            return;
        }
        out.println("\nGravando operação...");
        if (gravaContas()) {
            out.println("\nTransferência realizada com sucesso!\nImprimindo recibo...");
            recibo(conta.toString(), "TRANSFERÊNCIA", processamento );
        } else out.println("falha no arquivo causou inconsistência de dados,\nreinicie este programa e contate suporte técnico");
    }

    private static void pagamento(Conta conta) {
        out.println("\n\n     PAGAMENTO DE TÍTULOS COM DÉBITO EM CONTA");
        out.println("\nAgência atual: "+nDestaAgencia);
        out.print("\nENTRE COM O CÓDIGO DO TITULO A SER PAGO ou 0 (zero) para sair: ");
        String titulo= leiaNome();
        if (titulo.equals("0")) return;
        out.print("\nENTRE COM O VALOR DO TÍTULO ou 0 (zero) para sair: ");
        double valor= leiaDouble();
        if (valor==0) return;
        String processamento= conta.pagamento(valor, nDestaAgencia, titulo);
        if (!(processamento.charAt(0)=='A')) {
            out.println("\n"+processamento);
            return;
        }
        out.println("\nGravando operação...");
        if (gravaContas()) {
            out.println("\nPagamento realizado com sucesso!\nImprimindo recibo...");
            recibo(conta.toString(), "PAGAMENTO DE TÍTULO", processamento );
        } else out.println("falha no arquivo causou inconsistência de dados,\nreinicie este programa e contate suporte técnico");
    }
    
    static boolean carregaContas(){
        try {
            Scanner arq= new Scanner(new FileReader("contasdobanco.txt"));
            if (!arq.hasNextLine()) return false;
            contas.clear();
            while (arq.hasNextLine()){
                String[] campo= arq.nextLine().split(";");
                if (campo.length==6)
                contas.add(new Conta(Integer.parseInt(campo[0]), Integer.parseInt(campo[1]), campo[2],
                        Double.parseDouble(campo[3]), Double.parseDouble(campo[4]), Integer.parseInt(campo[5])));
            }
            arq.close();
        } catch (FileNotFoundException ex) {
            out.println("\n\nARQUIVO DE CONTAS NÃO ENCONTRADO!\nSERÁ CRIADO UM NOVO QUANDO CRIAR NOVAS CONTAS\n");
            return false;
        }
        return true;
    }
    
    /**
     * grava uma conta no arquivo de registro de contas
     * @param conta
     * @return 
     */
    static boolean gravaContaNova(Conta conta){
        try {
            BufferedWriter grava= new BufferedWriter(new FileWriter(new File("contasdobanco.txt"), true));
            grava.write(conta.getnAgencia()+";"+conta.getnConta()+";"+conta.getTitular()+";"+conta.getSaldo()+";"+conta.getLimite()+";"+conta.getStatusEspecial());
            grava.newLine();
            grava.close();
        } catch (IOException ex) {
            out.println("\n\nFALHA AO GRAVAR O ARQUIVO DE CONTAS!\n");
            return false;
        }
        return true;
    }
   
    /**
     * atualiza o arquivo de regitro de contas
     * @return 
     */
    static boolean gravaContas(){
        try {
            BufferedWriter grava= new BufferedWriter(new FileWriter(new File("contasdobanco.txt"), false));
            for (Conta conta: contas){
            grava.write(conta.getnAgencia()+";"+conta.getnConta()+";"+conta.getTitular()+";"+conta.getSaldo()+";"+conta.getLimite()+";"+conta.getStatusEspecial());
            grava.newLine();
            }
            grava.close();
        } catch (IOException ex) {
            out.println("\n\nFALHA AO GRAVAR O ARQUIVO DE CONTAS!\n");
            return false;
        }
        return true;
    }
    
    static int menuInicial(){
        out.println("\n\n          SISTEMA BANCÁRIO\n");
        out.println("\nAgência: "+nDestaAgencia);
        out.println("OPÇÕES:");
        out.println("       0 - ENCERRAR PROGRAMA");
        out.println("       1 - Criar NOVA conta");
        out.println("       2 - ACESSAR uma conta");
        out.print("\nEntre com o número da opção: ");
        return leiaInt(2);  
    }
    
    static void opInicial(){
        while (true){
            int i= menuInicial();
            switch (i){
                case 0: System.exit(0);
                case 1: novaConta(); break;
                case 2: acessaConta(); break;
            }
        }
    }
    
    static int menuConta(Conta contaAtual){
        out.println("\n\n            MENU DA CONTA");
        out.println("\n"+contaAtual.toString());
        out.println("\nOPÇÕES:");
        out.println("       0 - SAIR DA CONTA");
        out.println("       1 - Saldo");
        out.println("       2 - Extrato detalhado");
        out.println("       3 - Saque");
        out.println("       4 - Depósito");
        out.println("       5 - Transferência");
        out.println("       6 - Pagamento de contas");
        out.print("\nEntre com o número da opção: ");
        
        return leiaInt(6);
    }
    
    static void opConta(Conta conta){
        while(true){            
            int opcao= menuConta(conta);
            switch (opcao){
                case 0: return;
                case 1: saldo(conta); break;
                case 2: extrato(conta); break;
                case 3: saque(conta); break;
                case 4: deposito(conta); break;
                case 5: transferencia(conta); break;
                case 6: pagamento(conta); break;
            }
        }
    }
    
    static Conta pegaConta(int agencia, int conta){
        for (Conta c: contas){
            if(c.getnAgencia()==agencia && c.getnConta()==conta){
                return c;
            }
        }
        return null;
    }
}
