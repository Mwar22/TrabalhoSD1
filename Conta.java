/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista01e19;

import java.io.*;
import java.time.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Felix
 */
public class Conta {
    
    private int nAgencia, nConta;
    private String titular;
    private double saldo, limite;
    private int statusEspecial;
    List<Movimento> movList;
/**
 * 
 * @param nAgencia
 * @param nConta
 * @param titular
 * @param saldo
 * @param limite LIMITE PARA SAQUES
 * @param statusEspecial 
 */
    public Conta(int nAgencia, int nConta, String titular, double saldo, double limite, int statusEspecial) {
        this.nAgencia = nAgencia;
        this.nConta = nConta;
        this.titular = titular;
        this.saldo = saldo;
        this.limite = limite;
        this.statusEspecial = statusEspecial;
        movList= new ArrayList<>();
    }

    public int getnAgencia() {
        return nAgencia;
    }

    public void setnAgencia(int nAgencia) {
        this.nAgencia = nAgencia;
    }

    public int getnConta() {
        return nConta;
    }

    public void setnConta(int nConta) {
        this.nConta = nConta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public int getStatusEspecial() {
        return statusEspecial;
    }
    
    public String getEspecial(){
        if (statusEspecial==1) return "Cliente Especial";
        return "Cliente comum";
    }

    /**
     * marca se a conta é especial ou não
     * @param e e==1 especial; e==0 não especial
     */
    public void setStatusEspecial(int e) {
        this.statusEspecial = e;
    }
    
    @Override
    public String toString(){
        return "Agência: "+nAgencia+" Conta: "+nConta+" "+getEspecial()+"\nTitular: "+titular;
    }
    
    public String saque(double valor, int agOrigem){
        LocalDate ld= LocalDate.now();
        LocalTime lt= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (valor<=0) return "Valor inválido!";
        if (valor>limite) return "Excedeu limite de Saque!";
        if (valor>saldo) return "Saldo insuficiente!";
        Movimento mov= new Movimento("Ag"+agOrigem+" Saque:"+ld+"/"+lt, valor, -1, (saldo-valor));
        
        //GARANTINDO A INDIVISIBILIDADE DA OPERAÇÃO!
        if (movList.add(mov)) {
            if(gravaMovimento(mov)) {
                saldo-= valor;
                return mov.toRecibo();
            }
            else movList.remove(mov);
        }
        return "Operação não concluída!";
    }
    
     public String deposito(double valor, int agOrigem){
        LocalDate ld= LocalDate.now();
        LocalTime lt= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (valor<=0) return "Valor inválido!";
        Movimento mov= new Movimento("Ag"+agOrigem+" Deposito:"+ld+"/"+lt, valor, 1, (saldo+valor));
        
        //GARANTINDO A INDIVISIBILIDADE DA OPERAÇÃO!
        if (movList.add(mov)) {
            if(gravaMovimento(mov)) {
                saldo+= valor;
                return mov.toRecibo();
            }
            else movList.remove(mov);
        }
        return "Operação não concluída!";
    }
    
    public boolean gravaMovimento(Movimento mov){
        try {
            BufferedWriter arq= new BufferedWriter(new FileWriter(new File("ag"+nAgencia+"cc"+nConta+".txt"), true));
            arq.write(mov.toLinha());
            arq.newLine();
            arq.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public String extrato(){
        carregaMovimento();
        if (movList.isEmpty()) return "Sem movimentação";
        String temp="";
        for (Movimento m: movList){
            temp+=m.toExtrato();
        }
        return temp;
    }
    
    public boolean carregaMovimento(){
        try {
            Scanner arq = new Scanner(new FileReader("ag"+nAgencia+"cc"+nConta+".txt"));
            if (!arq.hasNextLine()) return false;
            movList.clear();
            while(arq.hasNextLine()){
                String[] campo= arq.nextLine().split(";");
                if (campo.length==4)
                movList.add(new Movimento(campo[0], Double.parseDouble(campo[1]), Integer.parseInt(campo[2]), Double.parseDouble(campo[3])));
            }
            arq.close();
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }
    
    public String transferencia(Conta destino, double valor){
        LocalDate ld= LocalDate.now();
        LocalTime lt= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (valor>saldo) return "Saldo insuficiente!";
        if (valor<=0) return "Valor inválido!";
        Movimento mov= new Movimento("Ag:"+destino.getnAgencia()+"Cc:"+destino.getnConta()
                +"Transf "+ld+"/"+lt, valor, -1, (saldo-valor));
        
        //GARANTINDO A INDIVISIBILIDADE DA OPERAÇÃO!
        if (movList.add(mov)) {
            if(gravaMovimento(mov)) {
                destino.deposito(valor, nAgencia);
                saldo-= valor;
                return mov.toRecibo();
            }
            else movList.remove(mov);
        }
        return "Operação não concluída!";
    }
    
    public String pagamento(double valor, int agOrigem, String titulo){
        LocalDate ld= LocalDate.now();
        LocalTime lt= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (valor<=0) return "Valor inválido!";
        if (valor>limite) return "Excedeu limite de Saques/pagamentos!";
        if (valor>saldo) return "Saldo insuficiente!";
        Movimento mov= new Movimento("Ag:"+agOrigem+" Pg: "+titulo+"\n"+ld+" as "+lt, valor, -1, (saldo-valor));
        
        //GARANTINDO A INDIVISIBILIDADE DA OPERAÇÃO!
        if (movList.add(mov)) {
            if(gravaMovimento(mov)) {
                saldo-= valor;
                return mov.toRecibo();
            }
            else movList.remove(mov);
        }
        return "Operação não concluída!";
    }
}
