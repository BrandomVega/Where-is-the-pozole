/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author macario
 */
public class Agente extends Thread
{

    String nombre;
    int a,b;
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];
    
    JLabel casillaAnterior;
    JLabel casillaActual;

    Random aleatorio = new Random(System.currentTimeMillis());

    ArrayList coordenadasMotherShip = new ArrayList<Integer>();

    ArrayList coordenadasSample = new ArrayList<Integer>();

    ArrayList coordenadasObstacle = new ArrayList<Integer>();
    
    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][], ArrayList<Integer> coordenadasMotherShip, ArrayList<Integer> coordenadasSample, ArrayList<Integer> coordenadasObstacle)
    {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;
        this.coordenadasMotherShip = coordenadasMotherShip;
        this.coordenadasObstacle = coordenadasObstacle;
        this.coordenadasSample = coordenadasSample;


        
        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);


    }




    public void run()
    {

        int dirRow=1;
        int dirCol=1;
        sensorNorte();
        sensorSur();
        sensorOeste();
        sensorEste();

        while(true) {



            casillaAnterior = tablero[i][j];

            int[] validPosition = {0, 1, 0, -1, 1, 0, -1, 0};
            int index = aleatorio.nextInt(0, 4);


            switch (index) {
                case 0:
                    dirRow = validPosition[0];
                    dirCol = validPosition[1];
                    break;
                case 1:
                    dirRow = validPosition[2];
                    dirCol = validPosition[3];
                    break;
                case 2:
                    dirRow = validPosition[4];
                    dirCol = validPosition[5];
                    break;
                case 3:
                    dirRow = validPosition[6];
                    dirCol = validPosition[7];
                    break;
            }


            //The agents cannot go out of the matrix
            if(i > matrix.length-2){
                dirRow=-1;
                dirCol=0;
            }
            if(i < 1){
                dirRow=1;
                dirCol=0;
            }

            if(j > matrix.length-2){
                dirCol=-1;
                dirRow=0;
            }
            if(j < 1){
                dirCol=1;
                dirRow=0;
            }
            //Add to the position
            i=i+dirRow;
            j=j+dirCol;

            actualizarPosicion();

            try
            {
                sleep(1000+aleatorio.nextInt(1000));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

                      
    }

    public synchronized void objectoDetectado(int i_new, int j_new){
        //System.out.println(this.nombre + " - Y:" + i + "-- X:" + j)

        System.out.println("OBSTACLE: "+ this.coordenadasObstacle);

        for(int recorrido = 0; recorrido < this.coordenadasObstacle.size(); recorrido++){ //Recorre todos los elementos
            a = (int)this.coordenadasObstacle.get(recorrido);
            b = (int)this.coordenadasObstacle.get(recorrido+1);
            System.out.println("ACTUAL DATA " + " (i,j) =  " + i_new + "," + j_new + " "+ "  a,b "+ a + "," + b);
            recorrido=recorrido+1;
            if(b==i_new && a==j_new){
                System.out.println("CORRELE GORDOOOOOOOOOO CORRELE");
            }
        }




    }

    public synchronized void actualizarPosicion() {
        casillaActual = tablero[i][j];
        /*
        System.out.println("Tamano:" + (matrix.length-2));
        System.out.println(this.nombre + " - Y:" + i + "-- X:" + j);
        System.out.println("-----------------------------------------------");
        System.out.println("MOTHER: "+ this.coordenadasMotherShip);
        System.out.println("-----------------------------------------------");
        System.out.println("OBSTACLE: "+ this.coordenadasObstacle);
        System.out.println("-----------------------------------------------");
        System.out.println("SAMPLE:" + this.coordenadasSample);
        System.out.println("-----------------------------------------------");
        */

        //CONTINUA CON SU CAMINO
        casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
        sensorNorte();
        sensorSur();
        sensorOeste();
        sensorEste();



    }

    public synchronized void sensorNorte(){
        if(i>0){
            int i_new = i-1;
            if(tablero[i_new][j].getIcon()!=null){
                System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> N O R T E <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                objectoDetectado(i_new,j);
            }
        }
    }
    public synchronized void sensorSur(){
        if(i<14){
            int i_new = i+1;
            if(tablero[i_new][j].getIcon()!=null){
                System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> S  U  R <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                objectoDetectado(i_new,j);
            }
        }
    }
    public synchronized void sensorEste(){
        if(j<14){
            if(tablero[i][j+1].getIcon()!=null){
                System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> E S T E <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }
    }
    public synchronized void sensorOeste(){
        if(j>0){
            if(tablero[i][j-1].getIcon()!=null){
                System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> O E S T E  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }
    }


    
}
