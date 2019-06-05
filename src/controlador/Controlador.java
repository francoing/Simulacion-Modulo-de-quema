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
    float monti;
    double sol;
    double  R;
    double TERM1,TERM2;
    float RES;
    int Mult;
    
    /*Pantallas del Modelo*/
    private Principal principal;

    /*Variables del Modelo*/
    int H;
    /*Cantidad de Habitantes*/
    float D;
    /*Distancia del punto de Quema*/
    float V;
    /*Velocidad del Viento*/
    float GA;
    /*Grado de estabilidad*/
    int DISY;
    /*Dispersion Z*/
    int DISZ;
    /*Dispersion Y*/
    double L;                           // ayuda de calculo de la forma
    double Form;                         //Formula de la concentracion
    int altura=2;                   // altura de la quema
    float guardarvariable;      //guarda el Gu actual
    int DQ;                     // dia de quema
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
    int O1 = 1;
    int O2 = 1;
    int TIE = 0;                        //Tiempo de quema
    int TIE1 = 0;                     //Acumulador de tiempo del mediano
    int TIE2 = 0;                     //Acumulador de tiempo del pequeño
    int TT1 = 0;                      //Acumulador de tiempo del grande
    int TT2 = 0;                        // Acumulador de tiempo del mediano
    int TT3 = 0;                      // Acumulador de tiempo del pequeño
    float CON;                    //Concentracion 
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
            GA = principal.getvientomaximo(); //Viento maximo
            DISY = principal.getdispersionY();
            DISZ = principal.getdispersionZ();

            /*Fin de toma y Declaracion de variables*/
            System.out.println("habitantes: " + H + "\n distancia:" + D + "\n velocidad minima: " + V + "\n Velocidad maxima: " + GA + "\n dispersion Y: " + DISY + "\n Dispersion Z: " + DISZ);

            
             
                
            // guardarvariable=getGU();  //llama Gu(U)
            while (i <= 150)//while de periodo de zafra
            {

                guardarvariable = getGU();  //llama Gu(U)
                DQ = (int)((-5.76) *Math.log(guardarvariable));
                if (DQ < 1 ) {
                    DQ = 1;
                }
                System.out.println("\nDQ es: " + DQ);

                //i = i + DQ;
                guardarvariable = getGU();  //llama Gu(U)
                if (guardarvariable <= 0.11) {
                    guardarvariable = getGU();
                    T = 70000 + (int) (14000 * guardarvariable);
                    System.out.println("\n Toneladas del Productor grande: " + T);
                    HA = T / 40;
                    P1 = HA * 76;
                    System.out.println("Hectareas quemadas:" + HA);
                    //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                    while (O <= HA) {
                        guardarvariable = getGU();
                        TIE = 10 + (int) (50 * guardarvariable);
                        TT1 = TT1 + TIE;
                        O++;

                    }
                    TT1=TT1/O;
                    System.out.println("Tiempo total de la quema de Hectareas:" + TT1);
                    monti = (float) P1 / TT1;
                    CON=monti;
                    System.out.println("Concentracion de PM10:" + CON);
                    CT = CT + CON;
                    HT = HT + HA;

                    //--------------------------------------------------------------------------------------------------//
                } else {
                    if (guardarvariable <= 0.35) {
                        guardarvariable = getGU();
                        T1 = 4000 + (int) (500 * guardarvariable);
                        System.out.println("\n Toneladas del productor mediano: " + T1);
                        HA1 = T1 / 40;
                        P2 = HA1 * 76;
                        System.out.println("Hectareas quemadas:" + HA1);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O1 <= HA1) {
                            guardarvariable = getGU();
                            TIE1 = 10 + (int) (50 * guardarvariable);
                            TT2 = TT2 + TIE1;
                            O1++;

                        }
                        TT2=TT2/O1;
                        System.out.println("Tiempo total de la quema de Hectareas:" + TT2);
                         monti = (float)P2 / TT2;
                         CON=monti;
                        System.out.println("Concentracion de PM10:" + CON);
                        CT = CT + CON;
                        HT = HT + HA1;
                        //--------------------------------------------------------------------------------------------------//
                    } else {
                        guardarvariable = getGU();
                        T2 = 20 + (int) (20 * guardarvariable);
                        System.out.println("\n Toneladas del Productor pequeño:" + T2);
                        HA2 = T2 / 40;
                        if (HA2 == 0) {
                            HA2 = 1;
                        }
                        P3 = HA2 * 76;
                        System.out.println("Hectareas quemadas:" + HA2+"\nPM10 Generado: "+P3);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O2 <= HA2) {
                            guardarvariable = getGU();
                            TIE2 = 10 + (int) (50 * guardarvariable);
                            System.out.println("\nTiempo parcial:" + TIE2);
                            TT3 = TT3 + TIE2;
                            O2++;

                        }
                        TT3=TT3+O2;
                        System.out.println("Tiempo total de la quema de Hectareas:" + TT3);
                        monti = (float) P3 / TT3;
                        CON = monti;
                        System.out.println("Concentracion de PM10: " + CON);
                        CT = CT + CON;
                        HT = HT + HA2;

                        //--------------------------------------------------------------------------------------------------//
                    }

                }
                //---------------------------------Velocidad----------------------------------------------------------------

                TT1 = 0;
                TT2 = 0;
                TT3 = 0;

                O = 1;
                O1 = 1;
                O2 = 1;
                i = i + DQ;
                
              guardarvariable = getGU();
              VE = V + (GA-V)* guardarvariable;
              System.out.println("\nla velocidad minima del viento es: "+V+"\nla velocidad maxima del viento es: "+GA+"\n Velocidad generada ="+VE);
               
                CON=CON*1000/60;
                System.out.println("\nConcentracion en g/s:" +CON);
                L=(CON)/((2*3.14)*VE*DISY*DISZ);
                System.out.println("\nCon base:"+L);
                Mult=(DISZ*DISZ);
                System.out.println("Multiplicacion:"+Mult);
                TERM2= (double)4/(Mult);
                System.out.println("\nPrimer termino TERM2:"+TERM2);
                TERM1=(V*V)/(2*DISY*DISY);
                
                System.out.println("\nSegundo termino TERM1:"+TERM1);
                R=(TERM2-TERM1);
                System.out.println("\nResultado de la resta:"+R);
                Form=Math.pow((double)L,(double)R)*CON/(DISY*DISY)*0.8;
               
                System.out.println("\nDispersion de PM10: "+ Form +" G/m3");
                //----------------------------------------------comparacion para sacar la poblacion----------------------------------------------
                if (Form <= 0.00007) {
                    
                }else{
                    if(Form<=0.0000375){
                    }
                
                }
                System.out.println("\nSumando: " + i);
                System.out.println("\n--------------------------------------------------------------------------------------------------"); 
            }
            
        }

    }

    public float getGU() {
        double aux2=0.53;
        int proce=0;//inicio de variables auxliares
        float uresultado = 0;
        int aux=0;
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
             if(proce!=0)
            {prc = proce;
            md = modo;
            uresultado = prc / md;}
            else
            {
              if(contador==1){
                 uresultado=(float)aux2;
                 contador=41;
              }
              
              else
              contador=41;
            }
            
             // System.out.println("valor de la formula: " + proce);
             // System.out.println(String.format("Valor: %.3f", uresultado));
            aux = proce; // aux para la siguente semilla
            sem = aux;     // semilla en la siguiente iteracion

            contador++;
        }

        return uresultado;

    }

}
