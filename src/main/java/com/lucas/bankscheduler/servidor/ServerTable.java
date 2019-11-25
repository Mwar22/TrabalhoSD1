/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler.servidor;

import java.net.InetAddress;

/**
 *
 * @author lucasdejesus
 */
public class ServerTable {
    private int count;  //contador do número todal de servers
    private int i;      //contador de varredura
    private final Server[] servers;
    
    public ServerTable(int n)
    {
        servers = new Server[n];
        count = 0;
        i = 0;
    }
    
    /** Função que retorna o proximo server listado, em round robin;
     * @author lucasdejesus
     * @return Server selecionado
     */
    public Server nextServer()
    {
        if (i < count)
        {
            return servers[i++];
        }
        else
        {
            i = 0;
            return servers[i];
        }
    }
    
    
    /**Adiciona um novo server à lista
     * @author lucasdejesus
     * @param s Server à ser adicionado.
     * @return false, se o número de servers foi estrapolado. true caso contrário.
     */
    public boolean addServer(Server s)
    {
        if (count > servers.length)
        {
            return false;
        } 
        
        servers[++count] = s;
        return true;
    }
    
    /** Adiciona um novo server à lista
     * @author lucasdejesus
     * @param id String identificadora do server
     * @param ip Endereço ip do server.
     * @return false, se o número de servers foi estrapolado. true caso contrário
     */
    public boolean addServer(String id, String ip)
    {
       return addServer(new Server(id, ip));
    }
}
