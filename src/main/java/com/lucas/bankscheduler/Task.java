/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler;


/**
 *
 * @author Lucas de Jesus
 */
public class Task implements Cloneable{
   
    private final int priority;
    private final String terminal;
    private final Type tp;
    private final Conta origem, destino;
    private final double valor;
    /**Construtor da Classe
     * 
     * @param priority  Indica a prioridade da Task. Números menores indicam maior
     * prioridade. Em particular, 0 indica a máxima prioridade.
     * @param tp    Tipo da conta. Ver enum Type.
     * @param conta Vetor de 2 posições do tipo Conta. o 1º elemento indica uma conta
     * de orígem. o 2º indica a de destino.  Ausências são representadas por null.
     * @param terminal Objeto que 
     */
    public Task(int priority, Type tp, Conta conta, String terminal)
    {
        this.priority = priority;
        this.tp = tp;
        origem = conta;
        destino = null;
        valor = 0;
        this.terminal = terminal;
    }
    
    public Task(int priority, Type tp, Conta conta, double valor, String terminal)
    {
        this.priority = priority;
        this.tp = tp;
        origem = conta;
        destino = null;
        this.valor = valor;
        this.terminal = terminal;
    }
    
    public Task(int priority, Type tp, Conta origem, Conta destino, double valor, String terminal)
    {
        this.priority = priority;
        this.tp = tp;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.terminal = terminal;
    }
    
    //--------------------------------------------------------------------------//
    
    
    public void toStr()
    {
        System.out.println("Priority: "+priority);
        System.out.println("Type: "+tp);
        System.out.println("Terminal: "+terminal);
    }
    
    /** Retorna o tipo de operação à ser feita
     * @author lucasdejesus
     * @return Type
     */
    public Type getType()
    {
        return tp;
    }
    
    /** Obtem a prioridade da dada Task
     * @author lucasdejesus
     * @return int. Representa a prioridade da Task. 0 representa a maior prioridade.
     */
    public int getPriority() {
        return priority;
    }
    
    /**Obtem a string do registry para o cliente requerinte
     * 
     * @return String que representa o nome do cliente no registry
     */
    public String getTerminal()
    {
        return terminal;
    }
    
    /** Compara duas Task's.
     * @author Lucas de Jesus
     * @param task Task a qual se comparará à Task chamadora do método.
     * @return 1, se a prioridade do objeto requerinte é maior. -1 se menor,
     * 0 caso contrário.
     * 
     */
    public int comparePriority(Task task)
    {
        //Quanto maior a prioridade, menor o numero
        if  (priority < task.getPriority())
            return 1;
        else if (priority > task.getPriority())
            return -1;
        else
            return 0;
    }
    
    /**Clona uma Task
     * @author Lucas de Jesus
     * @return Um objeto clone do objeto requerinte
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException
    {
        Task tmp = (Task) super.clone();
        return tmp;
    }
}

