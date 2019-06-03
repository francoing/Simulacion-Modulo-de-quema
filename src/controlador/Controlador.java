/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Random;
import vista.*;
import qca.QCA;

/**
 *
 * @author El´s
 */
public class Controlador {

    /*variables Auxiliares*/
    long i = 0;
    /*Pantallas del Modelo*/
    private Principal principal;

    /*Variables del Modelo*/
    int H;
    /*Cantidad de Habitantes*/
    float D;
    /*Distancia del punto de Quema*/
    float V;
    /*Velocidad del Viento*/
    String GA;
    /*Grado de estabilidad*/
    int DISY;
    /*Dispersion Z*/
    int DISZ;
    /*Dispersion Y*/
    float guardarvariable;      //guarda el Gu actual
    long DQ;                     // dia de quema
    int T;                         //Toneledas gran produtor
    int T1;                         // Toneladas de mediano productor
    int T2;                         // Toneladas de Pequeño producto
    int HA;                         //Hectares gran productor
    int HA1;                        //Hectares por el Mediano Producto
    int HA2;                        //Hectares por el Pequeño Productor
    int HT = 0;                       //contador total de hectareas
    int P1;                         //contaminacion producida por el gran productor
    int P2;                         //contaminacion producida por el mediano Productor
    int P3;                         //contanminacion Producida por el Pequeño productor
    int O = 1;                        //contador para las Hectareas
    int TIE = 0;                        //Tiempo de quema
    int TIE1 = 0;                     //Acumulador de tiempo del mediano
    int TIE2 = 0;                     //Acumulador de tiempo del pequeño
    int TT1 = 0;                      //Acumulador de tiempo del grande
    int TT2 = 0;                        // Acumulador de tiempo del mediano
    int TT3 = 0;                      // Acumulador de tiempo del pequeño
    float CON;                     //Concentracion 
    float CT = 0;                   //Concentracion Total
    float VE;                        //Velocidad

    public Controlador() {
        principal = new Principal();
    }

    public void ejecutar() {
        principal.setLocationRelativeTo(null);
        principal.setControlador(this);
        principal.setVisible(true);
    }

    public void emp(String atri) {
        if (atri.equals(principal.BTN_NUEVA_SIM)) {
            /*Toma y Declaracion de variables*/
            H = principal.gethabitantes();
            D = principal.getdistancia();
            V = principal.getvelocidad();
            GA = principal.getestablidad();
            DISY = principal.getdispersionY();
            DISZ = principal.getdispersionZ();

            /*Fin de toma y Declaracion de variables*/
            System.out.println("habitantes: " + H + "\n distancia:" + D + "\n velocidad: " + V + "\n estabilidad: " + GA + "\n dispersion Y: " + DISY + "\n Dispersion: " + DISZ);

            // guardarvariable=getGU();  //llama Gu(U)
            while (i <= 150)//while de periodo de zafra
            {

                guardarvariable = getGU();  //llama Gu(U)
                DQ = Math.round((-5.76) * Math.log((int) guardarvariable));
                System.out.println("\nDQ es: " + DQ);

                //i = i + DQ;
                guardarvariable = getGU();  //llama Gu(U)
                if (guardarvariable <= 0.11) {
                    guardarvariable = getGU();
                    T = 70000 + 14000 * (int) guardarvariable;
                    System.out.println("\n Toneguardarvariableladas del productor grande: " + T);
                    HA = T / 40;
                    P1 = HA * 76;
                    System.out.println("Hectareas quemadas:" + HA);
                    //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                    while (O <= HA) {
                        guardarvariable = getGU();
                        TIE = 10 + 50 * (int) guardarvariable;
                        TT1 = TT1 + TIE;
                        O = O++;

                    }

                    CON = P1 / TT1;
                    System.out.println("Concentracion de PM10:" + CON);
                    CT = CT + CON;
                    HT = HT + HA;

                    //--------------------------------------------------------------------------------------------------//
                } else {
                    if (guardarvariable <= 0.35) {
                        guardarvariable = getGU();
                        T1 = 4000 + 500 * (int) guardarvariable;
                        System.out.println("\n Toneladas del productor mediano: " + T1);
                        HA1 = T1 / 40;
                        P2 = HA1 * 76;
                        System.out.println("Hectareas quemadas:" + HA1);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O <= HA1) {
                            guardarvariable = getGU();
                            TIE1 = 10 + 50 * (int) guardarvariable;
                            TT2 = TT2 + TIE1;
                            O = O++;

                        }

                        CON = P2 / TT2;
                        System.out.println("Concentracion de PM10:" + CON);
                        CT = CT + CON;
                        HT = HT + HA1;
                        //--------------------------------------------------------------------------------------------------//
                    } else {
                        guardarvariable = getGU();
                        T2 = 20 + 20 * (int) guardarvariable;
                        System.out.println("\n Toneladas del Productor pequeño:" + T2);
                        HA2 = T1 / 40;
                        P3 = HA2 * 76;
                        System.out.println("Hectareas quemadas:" + HA2);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O <= HA2) {
                            guardarvariable = getGU();
                            TIE2 = 10 + 50 * (int) guardarvariable;
                            TT3 = TT3 + TIE2;
                            O = O++;

                        }

                        CON = P3 / TT3;
                        System.out.println("Concentracion de PM10:" + CON);
                        CT = CT + CON;
                        HT = HT + HA2;

                        //--------------------------------------------------------------------------------------------------//
                    }

                }
                //---------------------------------Velocidad----------------------------------------------------------------

                guardarvariable = getGU();
                VE = 8 + 12 * (int) guardarvariable;

                
                
                i = i + DQ;
            }

        }

    }

    public float getGU() {
        int proce;//inicio de variables auxliares
        float uresultado = 0;
        int aux;
        float prc = 0; //auxiliar de flotantes
        float md = 0;   // axuliar de flotantes
        int contador = 1;//fin de varaiables auxiliares
        Random Jar = new Random();//x numero pseudoaleatorio
        int iterative = Jar.nextInt(21) + 10; // limitando entre 10-31 numeros pseudoaleatorios.
        //  System.out.println("\nNumero Pseudoaleatorios: " + iterative);
        Random constant = new Random();//Constante Multiplicativo
        int nconstant = constant.nextInt(9000) + 1000; // limitando entre 1000-9000 el constante multiplicativo.
        //  System.out.println("\nA: " + nconstant);
        Random modular = new Random();// Numero pseudoaleatorio
        int modo = modular.nextInt(800) + 100; // limitando entre 100-900 el modulo.
        //   System.out.println("\nel modulo es: " + modo);
        Random semilla = new Random();//Numero Semilla
        int sem = semilla.nextInt(4000) + 1000; // limitando entre 1000-5000 el modulo.
        //  System.out.println("\nel Valor semilla inicial es: " + sem);

        while (contador <= iterative) {
            proce = (nconstant * sem) % modo;  //formula Conguencial multiplicativo
            prc = proce;
            md = modo;
            uresultado = prc / md;

            //   System.out.println("valor de proce: " + proce);
            //  System.out.println(String.format("Valor: %.3f", uresultado));
            aux = proce; // aux para la siguente semilla
            sem = aux;     // semilla en la siguiente iteracion

            contador++;
        }

        return uresultado;

    }
}
