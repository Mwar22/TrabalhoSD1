/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author lucasdejesus
 */
public interface DisponibleToServer extends Remote{
    public boolean feedback(double d) throws RemoteException;
}
