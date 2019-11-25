/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler;

/**
 *
 * @author lucasdejesus
 */
public enum Type {
    SAQUE(2), SALDO(1), EXTRATO(3), TRANSFERENCIA(4);
    private final int priority;
    Type(int p)
    {
        this.priority = p;
    }
}
