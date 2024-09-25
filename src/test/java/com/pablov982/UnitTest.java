package com.pablov982;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {

    @BeforeAll
    public static void antesQueTodo(){
    }

    @BeforeEach
    public void antesCadaUno(){
        System.out.println("Antes cada metodo");
    }


    @Test
    public void suma() {
        Calculadora calculadora = new Calculadora();
        int a = 1;
        int b = 2;
        int response = calculadora.sumar(a, b);
        assertEquals(response,3);
    }

    @Test
    public void restar() {
        Calculadora calculadora = new Calculadora();
        int a = 5;
        int b = 2;
        int response = calculadora.restar(a, b);
        assertEquals(response,3);
    }

}
