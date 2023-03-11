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
public class Agente extends Thread {

    String nombre;
    int a, b;
    int i, i_new;
    int j, j_new;
    int[] sensores = {0,1,2,3};
    int isaved;
    int jsaved;
    int banderaMovimiento=0;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];

    JLabel casillaAnterior;
    JLabel casillaActual;

    Random aleatorio = new Random(System.currentTimeMillis());

    ArrayList coordenadasMotherShip = new ArrayList<Integer>();

    ArrayList coordenadasSample = new ArrayList<Integer>();

    ArrayList coordenadasObstacle = new ArrayList<Integer>();

    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][], ArrayList<Integer> coordenadasMotherShip, ArrayList<Integer> coordenadasSample, ArrayList<Integer> coordenadasObstacle) {
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


    public void run() {

        int dirRow = 1;
        int dirCol = 1;

        isaved=i;
        jsaved=j;



        while (true) {
            casillaAnterior = tablero[isaved][jsaved];
            int[] validPosition = {0, 1, 0, -1, 1, 0, -1, 0};
            //show(sensores);
            if(sensorNorte()){
                sensores[0] = -1;
            }else{
                sensores[0] = 0;
            }
            if(sensorSur()){
                sensores[1] = -1;
            }else{
                sensores[1] = 1;
            }
            if(sensorEste()){
                sensores[2] = -1;
            }else{
                sensores[2] = 2;
            }
            if(sensorOeste()){
                sensores[3] = -1;
            }else{
                sensores[3] = 3;
            }

            int index  = aleatorio.nextInt(sensores.length);
            index = sensores[index];

            switch (index) {
                case 0://norte
                    dirRow = validPosition[6];
                    dirCol = validPosition[7];
                    break;
                case 1://sur
                    dirRow = validPosition[4];
                    dirCol = validPosition[5];
                    break;
                case 2://este
                    dirRow = validPosition[0];
                    dirCol = validPosition[1];
                    break;
                case 3://oeste
                    dirRow = validPosition[2];
                    dirCol = validPosition[3];
                    break;
                default:
                    //System.out.println("Este elemento ya no está permitido");
                    dirCol = 0;
                    dirRow = 0;
                    break;
            }

        if(index!=-1) {
            //The agents cannot go out of the matrix
            if (i > matrix.length - 2) {
                dirRow = -1;
                dirCol = 0;
            }
            if (i < 1) {
                dirRow = 1;
                dirCol = 0;
            }

            if (j > matrix.length - 2) {
                dirCol = -1;
                dirRow = 0;
            }
            if (j < 1) {
                dirCol = 1;
                dirRow = 0;
            }
            //Add to the position
            i = i + dirRow;
            j = j + dirCol;

            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
            tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
            isaved=i;
            jsaved=j;
        }


            try {
                sleep(90+ aleatorio.nextInt(100));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
    public synchronized void show(int[] inputArr) {
        for(int i=0;i<inputArr.length;i++){
            System.out.println(inputArr[i]);
        }

    }


    public synchronized int objectoDetectado(int i_new, int j_new){
        //System.out.println(this.nombre + " - Y:" + i + "-- X:" + j)

        //System.out.println("OBSTACLE: "+ this.coordenadasObstacle);

        for(int recorrido = 0; recorrido < this.coordenadasObstacle.size(); recorrido++){ //Recorre todos los elementos
            a = (int)this.coordenadasObstacle.get(recorrido);
            b = (int)this.coordenadasObstacle.get(recorrido+1);
            //System.out.println("ACTUAL DATA " + " (i,j) =  " + i_new + "," + j_new + " "+ "  a,b "+ a + "," + b);
            recorrido=recorrido+1;
            if(b==i_new && a==j_new){
                //La dirección del sensor detectó un policia, desvia el rumbo hasta que el proximo movimiento no
                //tenga un policia
                //System.out.println("CORRELE GORDOOOOOOOOOO CORRELE");
                return 1;
            }
        }
        return 0; //Evitar bugs ¿
    }


    public synchronized boolean sensorNorte(){
        if(i>0){
            i_new = i-1;
            if(tablero[i_new][j].getIcon()!=null){
                if(objectoDetectado(i_new,j)==1){
                        return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }

        return false;
    }
    public synchronized boolean sensorSur(){
        if(i<14){
            i_new = i+1; //no es necesario esto, despues lo actualizo
            if(tablero[i_new][j].getIcon()!=null){
                if(objectoDetectado(i_new,j)==1){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }
    public synchronized boolean sensorEste(){
        if(j<14 && banderaMovimiento==0){
            j_new = j+1;
            if(tablero[i][j+1].getIcon()!=null){
                if(objectoDetectado(i,j_new)==1){
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }
    public synchronized boolean sensorOeste(){
        if(j>0 && banderaMovimiento==0){
            j_new = j-1;
            if(tablero[i][j-1].getIcon()!=null){
                if(objectoDetectado(i,j_new)==1){
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }




}
