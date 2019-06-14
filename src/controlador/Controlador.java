/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Random;
import vista.*;
import qca.QCA;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author El´s
 */
public class Controlador {

    /*variables Auxiliares*/
    long i = 0;
    float monti;
    double sol;
    double R;
    double TERM1, TERM2;
    float RES;
    int Mult;
    int bandera120 = 0;
    int bandera90 = 0;
    int bandera60 = 0;
    int bandera30 = 0;
    int CDQ = 0;
    int PMA = 0; // variable auxiliar PM 
    int PBA = 0; // variable auxiliar PM 
    int PRA = 0; // variable auxiliar PM 

    /*Pantallas del Modelo*/
    private Principal principal;
    private resultados Resultados;

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
    int altura = 2;                   // altura de la quema
    double guardarvariable;      //guarda el Gu actual
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
    double VE;                        //Velocidad
    int P = 0;                        //Contador de personas 
    int PE1 = 0;
    int PE2 = 0;
    int PM1 = 0;
    int PB1 = 0;
    int PR1 = 0;

    int gaux = 0;             // Variable auxiliar semilla g(u)
    int gconst = 0;             // Variable auxiliar constante g(u)
    int gmod = 0;               // Variable auxiliar modo g(u)
    DefaultTableModel tr= new DefaultTableModel();
    String med;
    String alt;
    String baj;

    public Controlador() {
        principal = new Principal();
        Resultados = new resultados();
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
                Random semilla = new Random();//Numero Semilla Aleatorio
                int sem = semilla.nextInt(1000) + 2000; // limitando entre 1000-3000 la semilla.
                Random constante = new Random();//Numero de Constante multiplicativa aleatorio
                int consta = constante.nextInt(4000) + 2000; // limitando entre 2000-6000 el modulo.
                Random modu = new Random();// Modo aleatorio
                int mod = modu.nextInt(300) + 300; // limitando entre 300-600 el modulo.
                guardarvariable = getGU(sem, consta, mod);  //llama Gu(U)
                DQ = (int) ((-5.76) * Math.log(guardarvariable));
                if (DQ < 1) {
                    DQ = 1;
                }
                System.out.println("\nDQ es: " + DQ);

                guardarvariable = getGU(gaux, gconst, gmod);  //llama Gu(U)
                if (guardarvariable <= 0.11) {
                    guardarvariable = getGU(gaux, gconst, gmod);
                    T = 70000 + (int) (14000 * guardarvariable);
                    System.out.println("\n Toneladas del Productor grande: " + T);
                    HA = T / 40;
                    P1 = HA * 76;
                    System.out.println("Hectareas quemadas:" + HA);
                    //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                    while (O <= HA) {
                        guardarvariable = getGU(gaux, gconst, gmod);
                        TIE = 10 + (int) (50 * guardarvariable);
                        TT1 = TT1 + TIE;
                        O++;

                    }
                    TT1 = TT1 / O;
                    System.out.println("Tiempo total de la quema de Hectareas:" + TT1);
                    monti = (float) P1 / TT1;
                    CON = monti;
                    System.out.println("Concentracion de PM10:" + CON + "KG/M");
                    CT = CT + CON;
                    HT = HT + HA;

                    //--------------------------------------------------------------------------------------------------//
                } else {
                    if (guardarvariable <= 0.38) {
                        guardarvariable = getGU(gaux, gconst, gmod);
                        T1 = 4000 + (int) (500 * guardarvariable);
                        System.out.println("\n Toneladas del productor mediano: " + T1);
                        HA1 = T1 / 40;
                        P2 = HA1 * 76;
                        System.out.println("Hectareas quemadas:" + HA1);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O1 <= HA1) {
                            guardarvariable = getGU(gaux, gconst, gmod);
                            TIE1 = 10 + (int) (50 * guardarvariable);
                            TT2 = TT2 + TIE1;
                            O1++;

                        }
                        TT2 = TT2 / O1;
                        System.out.println("Tiempo total de la quema de Hectareas:" + TT2);
                        monti = (float) P2 / TT2;
                        CON = monti;
                        System.out.println("Concentracion de PM10:" + CON + "KG/M");
                        CT = CT + CON;
                        HT = HT + HA1;
                        //--------------------------------------------------------------------------------------------------//
                    } else {
                        guardarvariable = getGU(gaux, gconst, gmod);
                        T2 = 20 + (int) (20 * guardarvariable);
                        System.out.println("\n Toneladas del Productor pequeño:" + T2);
                        HA2 = T2 / 40;
                        if (HA2 == 0) {
                            HA2 = 1;
                        }
                        P3 = HA2 * 76;
                        System.out.println("Hectareas quemadas:" + HA2 + "\nPM10 Generado: " + P3);
                        //---------------------- Tiempo de quema de las Hectareas--------------------------------------------//
                        while (O2 <= HA2) {
                            guardarvariable = getGU(gaux, gconst, gmod);
                            TIE2 = 10 + (int) (50 * guardarvariable);
                            System.out.println("\nTiempo parcial:" + TIE2);
                            TT3 = TT3 + TIE2;
                            O2++;

                        }
                        TT3 = TT3 + O2;
                        System.out.println("Tiempo total de la quema de Hectareas:" + TT3);
                        monti = (float) P3 / TT3;
                        CON = monti;
                        System.out.println("Concentracion de PM10: " + CON + "KG/M");
                        CT = CT + CON;
                        HT = HT + HA2;

                        //--------------------------------------------------------------------------------------------------//
                    }

                }
                //---------------------------------Velocidad----------------------------------------------------------------


                guardarvariable = getGU(gaux, gconst, gmod);
                VE = V + (GA - V) * guardarvariable;
                System.out.println("\nla velocidad minima del viento es: " + V + "\nla velocidad maxima del viento es: " + GA + "\n Velocidad generada =" + VE);

                CON = CON * 1000 / 60;
                System.out.println("\nConcentracion en g/s:" + CON);
                L = (CON) / ((2 * 3.14) * VE * DISY * DISZ);
                //System.out.println("\nCon base:" + L);
                Mult = (DISZ * DISZ);
                // System.out.println("Multiplicacion:" + Mult);
                TERM2 = (double) 4 / (Mult);
                //  System.out.println("\nPrimer termino TERM2:" + TERM2);
                TERM1 = (V * V) / (2 * DISY * DISY);

                // System.out.println("\nSegundo termino TERM1:" + TERM1);
                R = (TERM2 - TERM1);
                //System.out.println("\nResultado de la resta:" + R);
                Form = (Math.pow((double) L, (double) R)) * CON / (DISY * DISY) * 0.8;
                Form = Form * 1000;

                System.out.println("\nDispersion de PM10: " + Form + " Mlg/m3");
                //----------------------------------------------comparacion para sacar la poblacion----------------------------------------------
                P = 0;
                int PR = 0;       //personas con riesgo alto
                int PE = 0;
                int PE1 = 0;
                int PE2 = 0;
                int PM = 0;
                int PB = 0;
                if (Form <= 90) {
                    if (Form <= 32.5) {
                        while (PE1 <= H) {
                            guardarvariable = getGU(gaux, gconst, gmod);
                            if (guardarvariable <= 0.03) {
                                PB++;
                                PE1++;

                            } else {
                                PE1++;
                            }
                        }
                         baj="Bajo";
                        PB1 = PB1 + PB;
                        System.out.println("Personas de Riesgo Bajo:" + PB);
                    } else {
                        while (PE2 <= H) {
                            guardarvariable = getGU(gaux, gconst, gmod);
                            if (guardarvariable <= 0.06) {
                                PM++;
                                PE2++;
                            } else {
                                PE2++;
                            }
                        }
                        med="Medio";
                        PM1 = PM1 + PM;
                        System.out.println("Personas de Riesgo de nivel medio:" + PM);
                    }

                } else {
                    while (PE <= H) {
                        guardarvariable = getGU(gaux, gconst, gmod);
                        if (guardarvariable <= 0.15) {
                            PR++;
                            PE++;
                        } else {
                            PE++;
                        }
                    }
                     alt="Alto";
                    PR1 = PR1 + PR;
                    System.out.println("Personas Alto Riesgo:" + PR);
                }

                CDQ = CDQ + 1;
                System.out.println("\nDias: " + CDQ);
                i = i + DQ;

                if (i >= 120 && bandera120 == 0) {

                    PMA = PM1 / CDQ;
                    PBA = PB1 / CDQ;
                    PRA = PR1 / CDQ;

                    System.out.println("\n La personas con riesgo de enfermendad media: " + PMA + "\n La personas con riesgo de enfermendad Alta: " + PRA + "\n La personas con riesgo de enfermendad Baja: " + PBA + "\n La concentracion a los 30 dias es: " + CT + "KG/M");

                    bandera120 = 1;
                } else {

                    if (i >= 90 && bandera90 == 0) {

                        PMA = PM1 / CDQ;
                        PBA = PB1 / CDQ;
                        PRA = PR1 / CDQ;

                        System.out.println("\n La personas con riesgo de enfermendad media: " + PMA + "\n La personas con riesgo de enfermendad Alta: " + PRA + "\n La personas con riesgo de enfermendad Baja: " + PBA + "\n La concentracion a los 30 dias es: " + CT + "KG/M");

                        bandera90 = 1;
                    } else {
                        if (i >= 60 && bandera60 == 0) {

                            PMA = PM1 / CDQ;
                            PBA = PB1 / CDQ;
                            PRA = PR1 / CDQ;

                            System.out.println("\n La personas con riesgo de enfermendad media: " + PMA + "\n La personas con riesgo de enfermendad Alta: " + PRA + "\n La personas con riesgo de enfermendad Baja: " + PBA + "\n La concentracion a los 30 dias es: " + CT + "KG/M");
                            bandera60 = 1;
                        } else {

                            if (i >= 30 && bandera30 == 0) {
                                PMA = PM1 / CDQ;
                                PBA = PB1 / CDQ;
                                PRA = PR1 / CDQ;

                                System.out.println("\n La personas con riesgo de enfermendad media: " + PMA + "\n La personas con riesgo de enfermendad Alta: " + PRA + "\n La personas con riesgo de enfermendad Baja: " + PBA + "\n La concentracion a los 30 dias es: " + CT + "KG/M");
                                bandera30 = 1;
                            } else {

                            }
                        }
                    }
                }

                System.out.println("\nSumando: " + i);
                System.out.println("\n--------------------------------------------------------------------------------------------------");
                
               
                
                
                TT1 = 0;
                TT2 = 0;
                TT3 = 0;

                O = 1;
                O1 = 1;
                O2 = 1;
                agregar();
            }
            System.out.println("\n-----------------------------------------------Informe Final de Periodo de Zafra-------------------------------------------");
            System.out.println("Emision total de gas PM10:" + CON + "Mlg/Cm3");
            System.out.println("Total de dias de quema:" + CDQ);
            System.out.println("Total de Hectareas quemadas:" + HT);
            System.out.println("Personas afectadas con nivel de contaminacion alto:" + PR1 / CDQ);
            System.out.println("Personas afectadas con nivel de contaminacion medio:" + PM1 / CDQ);
            System.out.println("Personas afectadas con nivel de contaminacion Bajo:" + PB1 / CDQ);

        }

        mostrar();
    }

    public double getGU(int a, int b, int c) {

        //  System.out.println("\nConstante multiplicativa: " + gconst);
        //  System.out.println("\nResto: "+gmod);
        double aux2 = 0.53;

        int proce = 0;//inicio de variables auxliares
        float prc = 0; //auxiliar de flotantes
        float md = 0;   // axuliar de flotantes
        double uresultado = 0;

        proce = (b * a) % c;  //formula Conguencial multiplicativo
        if (proce != 0) {
            prc = proce;
            md = c;
            uresultado = prc / md;

        } else {
            uresultado = aux2;
        }

        gaux = proce;             // Proxima semilla - Variable Global
        gconst = b;
        gmod = c;
        // System.out.println("\n Semilla Actual: "+gaux);
        //   System.out.println (String.format ("Valor:% .3f", uresultado));

        return uresultado;

    }

    public void mostrar() {
        
        Resultados.setVisible(true);
    }
    public void agregar(){
        String sepequeño="pequeño";
        String semediano="mediano";
        String segrande="grande";
        tr.addColumn("DQ");
        tr.addColumn("Toneladas de caña");
        tr.addColumn("Tipo de productor");
        tr.addColumn("Hectareas quemadas");
        tr.addColumn("PM10 generado");
        tr.addColumn("Tiempo de quema en minutos");
        tr.addColumn("Velocidad generada");
        tr.addColumn("Dispersion de PM10");
        tr.addColumn("Nivel de riesgo");
        tr.addColumn("Personas afectadas");

        String datos[] = new String[9];

       int Hector=HA+HA1+HA2;
           Hector=Hector*40;
           int H1=Hector/40;
     
        datos[0] = Integer.toString(DQ);
        datos[1] = Integer.toString(Hector);
        
        if(Hector<3)
      {
       datos[2] = sepequeño;
          datos[5]=Integer.toString(TT3);
         datos[8]=baj;
         datos[9]=Integer.toString(PBA);
      }
        else
            {   if(Hector >1000)
                 {      datos[2] = segrande;
                    datos[5]=Integer.toString(TT1);
                     datos[8]=alt;
                     datos[9]=Integer.toString(PRA);
                  }
            else{
                datos[2] = semediano;
                datos[5]=Integer.toString(TT2);
                          datos[8]=med;
                          datos[9]=Integer.toString(PMA);
            }
        }
        datos[3]=Integer.toString(H1);
        datos[4]=Float.toString(CON);
      
        datos[6]=Double.toString(VE);
        datos[7]=Double.toString(Form);
        
        
      
                
        tr.addRow(datos);
        
        Hector=0;
     }
}
