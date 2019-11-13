/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista01e19;

import java.text.NumberFormat;

/**
 *
 * @author Luiz Felix
 */
public class Movimento {
    
    private String desc;
    private double valor, saldo;
    private int dc; //dc==débito/crédito débito: -1 / crédito: +1

    public Movimento(String desc, double valor, int dc, double saldo) {
        this.desc = desc;
        this.valor = valor*dc;
        this.dc = dc;
        this.saldo = saldo;
    }
    
    public String debOuCred(){
        if (dc==-1) return "Debitado";
        return "Creditado";
    }
    
    /**
     * linha para ser gravada no arquivo
     * @return linha para ser gravada no arquivo
     */
    public String toLinha(){
        return desc+";"+valor+";"+dc+";"+saldo;
    }
    
    /**
     * String para extrato
     * @return String para extrato
     */
     public String toExtrato(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return desc+"\n"+debOuCred()+": "+nf.format(valor)+"\nSaldo: "+nf.format(saldo)+"\n\n";
    }
     
     /**
      * 
      * @return String para recibos
      */
     public String toRecibo(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return desc+"\n"+debOuCred()+" "+nf.format(valor);
    }
     
}
