/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pet
 */
public class Scheduler implements Runnable{
    private final int max_tasks;
    LinkedList<Task> local_queue;
    
    //variáveis que server de entrada e saída do escalonador
    private Task input = null; 
    private Task output = null;
    private boolean push_it = false;
   
    
    public Scheduler(int max_tasks) throws CloneNotSupportedException
    {
        super();
        //instancia uma nova lista
        local_queue = new LinkedList<>();
        this.max_tasks = max_tasks;

    }

    //------------------------------------------------------------------------//
    //método à ser executado quando a thread estiver sendo executada
    
    @Override
    public void run()
    {
        while(true)
        {
            
            if (getPushIt() && getInput() != null)
            {
                System.out.println("OK");

                local_queue.add(input);

                try {

                    quickSort(0, local_queue.size()-1);
                    Task tmp = local_queue.getLast();


                    local_queue.removeLast();
                    output = tmp;


                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
                }
                input = null;
                push_it = false;
            }
        }
    }
    
    //-------------------------------------------------------------------------//

    /** Função cujo objetivo é verificar se o limite de requisições estourou
     * 
     * @return true, se sim. false caaso o contrário.
     */
    public synchronized boolean isFull()
    {
        return (local_queue.size()== max_tasks);       
    }

    public void outputHasBeenRead()
    {
        output = null;        
    }
    
    /** Obtem a Task a ser executada
     * @author lucasdejesus
     * @return Task a ser executada. null caso contrário
     */
    public synchronized Task getOutput() {
            return output;
    }
    public synchronized void setOutput(Task output) {
        this.output = output;
    }
    
    public synchronized Task getInput()
    {
        return input;
    }
      
    public synchronized void setInput(Task input) {
        
        this.input = input;
        push_it = true;
    }

    
    public synchronized boolean getPushIt()
    {
        return push_it;
    }

    public synchronized void setPush_it(boolean push_it) {
        this.push_it = push_it;
    }
    
    //-------------------------------------------------------------------------//
    private synchronized void quickSort(int start, int end) throws CloneNotSupportedException
    {
        int i = start;
        int j = end;
        int pivot_index = (start+end)/2;
        
        
        Task pivot = local_queue.get(pivot_index);
        Task rem1, rem2;
        
        while (i < j)
        {
            
            //procura por elementos cuja prioridade seja maior que a do  pivo (inicio->pivo)
            //ou seja incrementa/vasculha até encontrar um elemento que tenha prioridade superior
            while (local_queue.get(i).comparePriority(pivot) == -1 && i < end)
            {
                i++;
            }//procura por elementos cuja prioridade seja menor que a do  pivo (fim->pivo)
            //ou seja incrementa/vasculha até encontrar um elemento que tenha prioridade inferior
            while (local_queue.get(j).comparePriority(pivot)== 1 && j > start)
            {
                j--;
            }
            
            //realiza a troca dos elementos
            if (i < j)
            {
                rem1 = local_queue.remove(i);
                local_queue.add(j-1, rem1);
                
                rem2 = local_queue.remove(j);
                local_queue.add(i, rem2);
                

                
                i++;
                j--;
                
            }
            
        }
        
        
        if (j > start)
            quickSort(start, j);
        
        if (i <  end)
        {
            if (i!=j)
                quickSort(i, end);
            else
                quickSort(i+1, end);
        }
    }
    
    
}
