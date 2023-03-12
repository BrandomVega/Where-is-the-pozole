/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Math;


/**
 *
 * @author macario
 */
public class Agente extends Thread
{

    String nombre;
<<<<<<< HEAD
    ImageIcon motherIcon;
    ImageIcon smokeIcon;

=======
    int a,b;
>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];
    
    JLabel casillaAnterior;
    JLabel casillaActual;
    boolean cop = false;
    boolean eraArbol = false;





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
        motherIcon = new ImageIcon("imagenes/tree.png");
        motherIcon = new ImageIcon(motherIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        smokeIcon = new ImageIcon("imagenes/smoke.png");
        smokeIcon = new ImageIcon(smokeIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        this.eraArbol = false;




    }




    public void run()
    {

<<<<<<< HEAD
=======
        int dirRow=1;
        int dirCol=1;
        sensorNorte();
        sensorSur();
        sensorOeste();
        sensorEste();

>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35
        while(true) {



            casillaAnterior = tablero[i][j];
<<<<<<< HEAD
/*
                dirRow = aleatorio.nextInt(-1, 2); //only can move into -1 or 1
                dirCol = aleatorio.nextInt(-1, 2);

*/
=======
>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35

            ArrayList<Integer> dirCol_dirRow = this.movimiento();


<<<<<<< HEAD
=======
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
>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35
            //Add to the position
            i=i+dirCol_dirRow.get(0);
            j=j+dirCol_dirRow.get(1);


<<<<<<< HEAD

            if(sensorArbolMariwuano()){
                actualizarPosicionDejaArbol();
            }else{
                actualizarPosicion();
                if(sensorMineral()){
                    this.sensorGradiente();
                }
=======
            try
            {
                sleep(1000+aleatorio.nextInt(1000));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35
            }

            //moneedita pasa esto
//            this.sensorGradiente();

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
<<<<<<< HEAD

        //System.out.println("Row: " + i + " Col:"    + j);
        if(this.eraArbol){
            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
            casillaAnterior.setIcon(motherIcon); // Elimina su figura de la casilla anterior
            casillaActual.setIcon(null);
            casillaActual.setIcon(icon);
            this.eraArbol = false;
        }else{
            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
            casillaActual.setIcon(icon); // Pone su figura en la nueva casilla
        }


    }
    public synchronized void actualizarPosicionDejaArbol() {
        casillaActual = tablero[i][j];


        casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        casillaActual.setIcon(null);
        casillaActual.setIcon(smokeIcon);
        this.eraArbol = true;

        //System.out.println("Row: " + i + " Col:"    + j);


    }

    public synchronized boolean sensorNaveNodriza(ArrayList<Integer> coordenadasNave){
        if(i == coordenadasNave.get(1) && j == coordenadasNave.get(0)){
            return true;
        }else{
            return false;
        }

    }
    public synchronized void sensorGradiente(){
        double actual_distance = 0;
        double next_distance = 0;
        int aux_x =0;
        int aux_y = 0;

        ArrayList<Integer> coordenadasNave = this.naveMasCercana();
        System.out.println("=======================================: ");
        ArrayList<Integer> dirCol_dirRow = this.movimiento();

        while (!sensorNaveNodriza(coordenadasNave)){
            System.out.println("NAVE MAS CERCA: "+ coordenadasNave);
            aux_x = j - coordenadasNave.get(0);
            aux_y = i - coordenadasNave.get(1);
            actual_distance = Math.sqrt(Math.pow((aux_x),2) + Math.pow((aux_y),2));
            System.out.println("Distancia: "+ actual_distance);

            // MOVE for next
            dirCol_dirRow = this.movimiento();
            aux_y=i+dirCol_dirRow.get(0)- coordenadasNave.get(1);
            aux_x=j+dirCol_dirRow.get(1) - coordenadasNave.get(0);


            //Add position

            next_distance = Math.sqrt(Math.pow((aux_x),2) + Math.pow((aux_y),2));
            System.out.println("Distancia Sig: "+ next_distance);

            if (next_distance < actual_distance){
                casillaAnterior = tablero[i][j];
                i=i+dirCol_dirRow.get(0);
                j=j+dirCol_dirRow.get(1);


                if(sensorArbolMariwuano()){
                    actualizarPosicionDejaArbol();
                }else{

                    System.out.println("---------CORRREEEE----------");
                    casillaActual = tablero[i][j];
                    casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
                    casillaActual.setIcon(icon); // Pone su figura en la nueva casilla
                }

            }

        }
        System.out.println("---------SIUUUUU----------");



    }
    public synchronized ArrayList<Integer> naveMasCercana(){
        ArrayList<Integer> coordenadas = new ArrayList<Integer>();
        double distance = 0;
        double low_distance = 1000000;
        int x = 0;
        int y = 0;
        for(int z = 0; z < this.coordenadasMotherShip.size(); z++){
            int aux_x = j - (int)this.coordenadasMotherShip.get(z);
            int aux_y = i - (int)this.coordenadasMotherShip.get(z+1);
            distance = Math.sqrt(Math.pow((aux_x),2) + Math.pow((aux_y),2));


            if (distance < low_distance){
                low_distance = distance;
                x = (int)this.coordenadasMotherShip.get(z);
                y = (int)this.coordenadasMotherShip.get(z+1);
            }
            z++;
        }
        coordenadas.add(x);
        coordenadas.add(y);
        return coordenadas;
    }


    public synchronized ArrayList<Integer> movimiento(){


        ArrayList<Integer> dirCol_dirRow = this.randomDir();

        while(i + dirCol_dirRow.get(0) > matrix.length-1 || i+ dirCol_dirRow.get(0) < 0 || j+ dirCol_dirRow.get(1) > matrix.length-1 || j+ + dirCol_dirRow.get(1) < 0 || esUnaCop(i + dirCol_dirRow.get(0), j+ dirCol_dirRow.get(1) ,"kk")){
            dirCol_dirRow = this.randomDir();
        }

        try
        {
            sleep(200+aleatorio.nextInt(1));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return dirCol_dirRow;

    }

    public synchronized ArrayList<Integer> randomDir(){
        //        Y = i / X = j
//        DirCol / DirRow
        int[] norte ={ 1, 0} ;
        int[] sur = {-1, 0};
        int[] este = {0,1};
        int[] oeste = {0,-1};
        ArrayList<Integer> dirCol_dirRow = new ArrayList<Integer>();

        int random = aleatorio.nextInt(0, 4);
        switch (random) {
            case 0:
                dirCol_dirRow.add(norte[0]);
                dirCol_dirRow.add(norte[1]);
                break;
            case 1:
                dirCol_dirRow.add(sur[0]);
                dirCol_dirRow.add(sur[1]);
                break;
            case 2:
                dirCol_dirRow.add(este[0]);
                dirCol_dirRow.add(este[1]);
                break;
            case 3:
                dirCol_dirRow.add(oeste[0]);
                dirCol_dirRow.add(oeste[1]);
                break;
        }
        return dirCol_dirRow;

    }

    public synchronized boolean esUnaCop(int i_new, int j_new, String sensor) {
        int a = 0;
        int b = 0;
        for (int recorrido = 0; recorrido < this.coordenadasObstacle.size(); recorrido++) { //Recorre todos los elementos
            a = (int) this.coordenadasObstacle.get(recorrido);
            b = (int) this.coordenadasObstacle.get(recorrido + 1);
            //System.out.println("ACTUAL DATA " + " (i,j) =  " + i_new + "," + j_new + " "+ "  a,b "+ a + "," + b);
            recorrido = recorrido + 1;
            if (b == i_new && a == j_new) {
                System.out.println("><<<<<<<<<<<<<<<<<<<" + sensor + "<<<<<<<<<<<<<<<<<<<<");
                System.out.println("CORRELE GORDOOOOOOOOOO CORRELE");
                return true;
            }
        }

        return false;
    }

    public synchronized boolean sensorMineral(){
        int a = 0;
        int b = 0;

        for (int recorrido = 0; recorrido < this.coordenadasSample.size(); recorrido++) { //Recorre todos los elementos
            a = (int) this.coordenadasSample.get(recorrido);
            b = (int) this.coordenadasSample.get(recorrido + 1);
            //System.out.println("ACTUAL DATA " + " (i,j) =  " + i_new + "," + j_new + " "+ "  a,b "+ a + "," + b);
            recorrido = recorrido + 1;
            if (b == i && a == j) {
                System.out.println("><<<<<<<<<<<<<<<<<<<"  + "<<<<<<<<<<<<<<<<<<<<");
                System.out.println("AGARRALA GORDOOOOOOOOOOO");
                return true;
            }
        }
        return false;

    }
    public synchronized boolean sensorArbolMariwuano(){
        int a = 0;
        int b = 0;

        for (int recorrido = 0; recorrido < this.coordenadasMotherShip.size(); recorrido++) { //Recorre todos los elementos
            a = (int) this.coordenadasMotherShip.get(recorrido);
            b = (int) this.coordenadasMotherShip.get(recorrido + 1);
            //System.out.println("ACTUAL DATA " + " (i,j) =  " + i_new + "," + j_new + " "+ "  a,b "+ a + "," + b);
            recorrido = recorrido + 1;
            if (b == i && a == j) {
                System.out.println("><<<<<<<<<<<<<<<<<<<"  + "<<<<<<<<<<<<<<<<<<<<");
                System.out.println("ARRBOOOOOOLLLLL");
                return true;
            }
        }
        return false;

    }








=======
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


>>>>>>> 6e88159efa5268b53e6216bcd95d50190eed5a35
    
}
