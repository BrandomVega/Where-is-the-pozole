/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

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
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];
    
    JLabel casillaAnterior;
    JLabel casillaActual;

    Random aleatorio = new Random(System.currentTimeMillis());
    
    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][])
    {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;

        
        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);        
    }


    public void run()
    {

        int dirRow=1;
        int dirCol=1;


        while(true)
        {
            //posicion de robots i,j, posicion obstacle





            casillaAnterior = tablero[i][j];

            dirRow = aleatorio.nextInt(-1,2); //only can move into -1 or 1
            dirCol = aleatorio.nextInt(-1,2);

            //The agents cannot go out of the matrix
            if(i > matrix.length-2) dirRow=-1;
            if(i < 1) dirRow=1;

            if(j > matrix.length-2) dirCol=-1;
            if(j < 1) dirCol=1;
            //Add to the position
            i=i+dirRow;
            j=j+dirCol;

            actualizarPosicion();
                
            try
            {
               sleep(100+aleatorio.nextInt(1000));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

                      
    }

    public synchronized void actualizarPosicion()
    {

        casillaActual = tablero[i][j];

        System.out.println("actual" + casillaActual);

            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
            tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
            //System.out.println("Row: " + i + " Col:"    + j);

    }
    
}
