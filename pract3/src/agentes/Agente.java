/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Math;


/**
 *
 * @author macario
 */
public class Agente extends Thread {

//  THIS PART REFERS TO AN AGENT PROPRIETIES
    String nameAgent; //name fo the agent

    ImageIcon iconAgent; // his icon
    int[][] matrix;
    JLabel board[][]; // The board we are currently in

//    CHARACTERISTICS OF POSITION IN BOARD
    int positionYAgent;
    int positionXAgent;

    JLabel previousSquareInBoard;
    JLabel actualSquareInBoard;
    Random random = new Random(System.currentTimeMillis());


//    ICONS USED WHEN INTERACT WITH THE BOARD
    ImageIcon treeIcon;
    ImageIcon smokeIcon;
    ImageIcon weedTwoIcon;
    ImageIcon weedOneIcon;
    ImageIcon weedThreeIcon;
    ImageIcon crumbsIcon;

// also flags whitn interacting with board

    boolean previousSquareWasTree = false;
    boolean gotWeed = false;
    boolean previousSquareWasWeed = false;

    boolean previousSquareWasNothing = true;

//    Also the agent has the coordinates of all the elements in board

    HashMap<ArrayList<Integer>,Double> weedCoordinates = new HashMap<ArrayList<Integer>, Double>();
    HashMap<ArrayList<Integer>,Double> treeCoordinates = new HashMap<ArrayList<Integer>, Double>();
    HashMap<ArrayList<Integer>,Double> copCoordinates = new HashMap<ArrayList<Integer>, Double>();

    ArrayList auxCoordinatesWeed = new ArrayList<Integer>();

//     Crumbs variables
    int remainingWeed=0;
    int howFarIsItCount=0;
    boolean timeToReturn = false;

    
    public Agente(String nameAgent, ImageIcon iconAgent, int[][] matrix, JLabel board[][],HashMap<ArrayList<Integer>,Double> treeCoordinates, HashMap<ArrayList<Integer>,Double> weedCoordinates, HashMap<ArrayList<Integer>,Double> copCoordinates) {
        //  THIS PART REFERS TO AN AGENT PROPRIETIES
        this.nameAgent = nameAgent;
        this.iconAgent = iconAgent;
        this.matrix = matrix;
        this.board = board;
        this.treeCoordinates = treeCoordinates;
        this.copCoordinates = copCoordinates;
        this.weedCoordinates = weedCoordinates;


        //  THIS PART REFERS TO THE POSITION IN BOARD
        this.positionYAgent = random.nextInt(matrix.length);
        this.positionXAgent = random.nextInt(matrix.length);
        board[positionYAgent][positionXAgent].setIcon(iconAgent);

//        ICONS THAT WILL BE USEFUL INTERACTING WITH BOARD

        treeIcon = new ImageIcon("imagenes/tree.png");
        treeIcon = new ImageIcon(treeIcon.getImage().getScaledInstance(50,50,  Image.SCALE_SMOOTH));
        smokeIcon = new ImageIcon("imagenes/smoke.png");
        smokeIcon = new ImageIcon(smokeIcon.getImage().getScaledInstance(50,50,  Image.SCALE_SMOOTH));
        weedOneIcon = new ImageIcon("imagenes/hemp.png");
        weedOneIcon = new ImageIcon(weedOneIcon.getImage().getScaledInstance(50,50,  Image.SCALE_SMOOTH));
        weedTwoIcon = new ImageIcon("imagenes/2plantas.png");
        weedTwoIcon = new ImageIcon(weedTwoIcon.getImage().getScaledInstance(50,50,  Image.SCALE_SMOOTH));
        weedThreeIcon = new ImageIcon("imagenes/3plantas.png");
        weedThreeIcon = new ImageIcon(weedThreeIcon.getImage().getScaledInstance(50,50,  Image.SCALE_SMOOTH));
        crumbsIcon = new ImageIcon("imagenes/migas.png");
        crumbsIcon = new ImageIcon(crumbsIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

//        FLAGS FOR TREE, COP AND GOT WEED
        this.previousSquareWasTree = false;
        this.gotWeed = false;
        this.previousSquareWasWeed = false;
        this.previousSquareWasNothing = true;

    }




    public void run() {
        while(true) {
//            Lets play
            
            previousSquareInBoard = board[positionYAgent][positionXAgent];

//            Next move 
            ArrayList<Integer> newYPosition_newXPosition = move2aNewPosition();
            int nextYPosition = newYPosition_newXPosition.get(0);
            int nextXPosition = newYPosition_newXPosition.get(1);
            positionYAgent = positionYAgent + nextYPosition;
            positionXAgent = positionXAgent + nextXPosition;
            

//          We are in a new position so lets check if we are in a weed or a tree
//            For the animation or just we got a weed

            updatePositionInBoard();
            if(isActualSquareAWeed()) {
                this.bestFirstSearch();
            }
        }

                      
    }

    public synchronized void updatePositionInBoard() {
//        System.out.println("===========================Actualiza");
        actualSquareInBoard = board[positionYAgent][positionXAgent];
        if (previousSquareWasTree) {
            if (gotWeed) {
//                System.out.println("TRAE MOTA PARA ACTUALIZAR");
                previousSquareInBoard.setIcon(smokeIcon);
                remainingWeed--;
                timeToReturn = true;
                try {
                    sleep(500 + random.nextInt(1));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                previousSquareInBoard.setIcon(treeIcon);
                gotWeed = false;

            } else {
                previousSquareInBoard.setIcon(treeIcon);
            }
            previousSquareWasTree = false;
        } else if (previousSquareWasWeed) {
            howFarIsItCount++;
//            System.out.println("EL anterior es marihuana");
            if (weedCoordinates.get(auxCoordinatesWeed) <= 0.0) {
                previousSquareInBoard.setIcon(null);
                remainingWeed = 0;
            } else if (weedCoordinates.get(auxCoordinatesWeed) <= 1.0) {
                previousSquareInBoard.setIcon(weedOneIcon);
                remainingWeed = 1;
            } else {
                previousSquareInBoard.setIcon(weedTwoIcon);
                remainingWeed = 2;
            }
            previousSquareWasWeed = false;

        } else {
            previousSquareInBoard.setIcon(null); // Elimina su figura de la casilla anterior
        }
        System.out.println("El personaje tiene que volver por: " + remainingWeed + " plantas");

// When the agents have gone at least one block away from weed, it starts to put the crumbs
        if(remainingWeed>1 && howFarIsItCount==0){
            previousSquareInBoard.setIcon(crumbsIcon);
        }
        howFarIsItCount=0;


        actualSquareInBoard.setIcon(null);
        actualSquareInBoard.setIcon(iconAgent);
        sleep();
        if(isActualSquareATree()){
            previousSquareWasTree = true;
        }else if(isActualSquareAWeed()){
//
//            System.out.println("Y OTRA VEX ENCONTRASTE");
            previousSquareWasWeed = true;
        }

    }





    public synchronized boolean iAmInATree(ArrayList<Integer> treeCoordinates){
        int coordinateYOfTree = treeCoordinates.get(1);
        int coordinateXOfTree = treeCoordinates.get(0);
        if(positionYAgent == coordinateYOfTree && positionXAgent == coordinateXOfTree){
            return true;
        }else{
            return false;
        }

    }
    public synchronized void bestFirstSearch(){
        double actualDistance2Tree = 0;
        double nextDistance2GO = 0;
        int aux4CalculatingDistanceX =0;
        int aux4CalculatingDistanceY = 0;

        ArrayList<Integer> nearestTreeCoordinates = findNearestTreeCoordinates();
        System.out.println("=======================================: ");
        ArrayList<Integer> nextYPosition_nextXPosition = move2aNewPosition();



        while (!iAmInATree(nearestTreeCoordinates)){
//            System.out.println("=====///////////////////////// ");
//            System.out.println("NAVE MAS CERCA: "+ treeCoordinates);
            aux4CalculatingDistanceX = positionXAgent - nearestTreeCoordinates.get(0);
            aux4CalculatingDistanceY = positionYAgent - nearestTreeCoordinates.get(1);
            actualDistance2Tree = Math.sqrt(Math.pow((aux4CalculatingDistanceX),2) + Math.pow((aux4CalculatingDistanceY),2));
//            System.out.println("Distancia: "+ actualDistance2Tree);

            // MOVE for next
            nextYPosition_nextXPosition = move2aNewPosition();
            aux4CalculatingDistanceY= positionYAgent +nextYPosition_nextXPosition.get(0)- nearestTreeCoordinates.get(1);
            aux4CalculatingDistanceX= positionXAgent +nextYPosition_nextXPosition.get(1) - nearestTreeCoordinates.get(0);


            //Add position

            nextDistance2GO = Math.sqrt(Math.pow((aux4CalculatingDistanceX),2) + Math.pow((aux4CalculatingDistanceY),2));
//            System.out.println("Distancia Sig: "+ nextDistance2GO);

            if (nextDistance2GO <= actualDistance2Tree + 0.3){
                previousSquareInBoard = board[positionYAgent][positionXAgent];

                positionYAgent = positionYAgent +nextYPosition_nextXPosition.get(0);
                positionXAgent = positionXAgent +nextYPosition_nextXPosition.get(1);
//                if(gotWeed){
//                    System.out.println("TRAES MOTA EH");
//                }
//
//                if(previousSquareWasWeed){
//                    System.out.println("El anterior era weed");
//                }

                updatePositionInBoard();


            }

        }
//        System.out.println("---------SIUUUUU----------");



    }
    public synchronized ArrayList<Integer> findNearestTreeCoordinates(){
        ArrayList<Integer> treeNearestCoordinates = new ArrayList<Integer>();
        double distance2Tree = 0;
        double shortestDistance2Tree = 100000000;
        int treeXCoordinate = 0;
        int treeYCoordinate = 0;

        int positionY = 1;
        int positionX = 0;

//        System.out.println("COORDENADAS TREEE--"+ treeCoordinates);
        for (ArrayList<Integer> treeCoordinate: treeCoordinates.keySet()) {
            int aux4CalculatingDistanceX = positionXAgent - treeCoordinate.get(positionX);
            int aux4CalculatingDistanceY = positionYAgent - treeCoordinate.get(positionY);
            distance2Tree = Math.sqrt(Math.pow((aux4CalculatingDistanceX),2) + Math.pow((aux4CalculatingDistanceY),2));
            System.out.println(distance2Tree +" -- "+ shortestDistance2Tree);

            if (distance2Tree < shortestDistance2Tree){
                shortestDistance2Tree = distance2Tree;
                treeXCoordinate = treeCoordinate.get(positionX);
                treeYCoordinate = treeCoordinate.get(positionY);
            }

        }
        treeNearestCoordinates.add(treeXCoordinate);
        treeNearestCoordinates.add(treeYCoordinate);
        System.out.println(treeNearestCoordinates);
        return treeNearestCoordinates;
    }


    public synchronized ArrayList<Integer> move2aNewPosition(){
//        x --> 1
//        y --> 0

        ArrayList<Integer> nextYPosition_nextXPosition = this.randomNewPosition();
        int newYPosition = nextYPosition_nextXPosition.get(0);
        int newXPosition = nextYPosition_nextXPosition.get(1);
        int sizeOfBoard = matrix.length-1;
//        Delimitation the agent position to the board
//        Also checking that we are not in a cop
        int nextXPosition = positionXAgent + newXPosition;
        int nextYPosition = positionYAgent + newYPosition;

        //When it has done his path, it needs to return within the space
        if (remainingWeed>=1 && timeToReturn){
            while(nextYPosition > sizeOfBoard || nextYPosition < 0 || nextXPosition > sizeOfBoard || nextXPosition < 0 || isNextSquareEmpty(newYPosition,newXPosition)) {
                //            Finding new positions to X and Y
                nextYPosition_nextXPosition = this.randomNewPosition();
                newYPosition = nextYPosition_nextXPosition.get(0);
                newXPosition = nextYPosition_nextXPosition.get(1);

                //            Adding those position to think about the next position
                nextXPosition = positionXAgent + newXPosition;
                nextYPosition = positionYAgent + newYPosition;
            }
        }else{
            while(nextYPosition > sizeOfBoard || nextYPosition < 0 || nextXPosition > sizeOfBoard || nextXPosition < 0 || isNextSquareACop(nextYPosition, nextXPosition)) {

                    //            Finding new positions to X and Y
                    nextYPosition_nextXPosition = this.randomNewPosition();
                    newYPosition = nextYPosition_nextXPosition.get(0);
                    newXPosition = nextYPosition_nextXPosition.get(1);

                    //            Adding those position to think about the next position
                    nextXPosition = positionXAgent + newXPosition;
                    nextYPosition = positionYAgent + newYPosition;
            }
        }

        return nextYPosition_nextXPosition;
    }

    public synchronized ArrayList<Integer> randomNewPosition(){
        //        Y = i / X = j
//        DirCol / DirRow
        int[] moveNorth ={ 1, 0} ;
        int[] moveSouth = {-1, 0};
        int[] moveEast = {0,1};
        int[] moveWeast = {0,-1};
        ArrayList<Integer> newY_newX = new ArrayList<Integer>();

        int random = this.random.nextInt(0, 4);
        switch (random) {
            case 0:
                newY_newX.add(moveNorth[0]);
                newY_newX.add(moveNorth[1]);
                break;
            case 1:
                newY_newX.add(moveSouth[0]);
                newY_newX.add(moveSouth[1]);
                break;
            case 2:
                newY_newX.add(moveEast[0]);
                newY_newX.add(moveEast[1]);
                break;
            case 3:
                newY_newX.add(moveWeast[0]);
                newY_newX.add(moveWeast[1]);
                break;
        }
        return newY_newX;

    }

    public synchronized boolean isNextSquareACop(int nextYPosition, int nextXPosition) {
        int positionY = 1;
        int positionX = 0;
        for (ArrayList<Integer> copCoordinate: copCoordinates.keySet()) {
            if(copCoordinate.get(positionY) == nextYPosition && copCoordinate.get(positionX) == nextXPosition){
                return true;
            }
        }
        return false;
    }
    public synchronized boolean isNextSquareWeed(int nextYPosition, int nextXPosition) {
        int positionY = 1;
        int positionX = 0;
        for (ArrayList<Integer> weedCoordinate: weedCoordinates.keySet()) {
            if(weedCoordinate.get(positionY) == nextYPosition && weedCoordinate.get(positionX) == nextXPosition){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isNextSquareTree(int nextYPosition, int nextXPosition) {
        int positionY = 1;
        int positionX = 0;
        for (ArrayList<Integer> treeCoordinate: treeCoordinates.keySet()) {
            if(treeCoordinate.get(positionY) == nextYPosition && treeCoordinate.get(positionX) == nextXPosition){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isNextSquareEmpty(int y, int x) {
        actualSquareInBoard = board[y][x];
        if(actualSquareInBoard.getIcon()==null){
            return true;
        }
        return false;

    }



    public synchronized boolean isActualSquareAWeed(){
//        i = y
        int positionY = 1;
        int positionX = 0;
//        Iterating through all the hash map
        for (ArrayList<Integer> weedCoordinateInHashMap: weedCoordinates.keySet()) {
//            System.out.println("Coordenada en hash = "+ coordinatesInHashMap + "-- nuevas: "+ coordinates)
//            Checking if actual position is a coordinate in weed coordinates
            if(weedCoordinateInHashMap.get(positionY) == positionYAgent && weedCoordinateInHashMap.get(positionX) == positionXAgent){
//              Also checking if there is still some weed in that square
                Double weedsLeftOver = weedCoordinates.get(weedCoordinateInHashMap);
                this.auxCoordinatesWeed = weedCoordinateInHashMap;

                if(weedsLeftOver > 0.0 && !gotWeed){
//                    Eliminating a weed in that square
                    weedCoordinates.put(weedCoordinateInHashMap, weedsLeftOver- 1.0);
//                    System.out.println("AGARRALA GORDOOOOOOOOOOO");
//                    System.out.println(weedCoordinates);

//                    Taking the actual coordinates of the weed

//                    So you have one weed in your hands
                    this.gotWeed = true;
                    previousSquareWasWeed = true;

                }else{
                    System.out.println("Shit theres is no more weed here");
                }
                return true;
            }
        }
        return false;

    }
    public synchronized boolean isActualSquareATree(){
        int positionY = 1;
        int positionX = 0;
        for (ArrayList<Integer> treeCoordinate: treeCoordinates.keySet()) {
            if(treeCoordinate.get(positionY) == positionYAgent && treeCoordinate.get(positionX) == positionXAgent){
                return true;
            }
        }
        return false;
    }
    public synchronized void sleep(){
        try
        {
            sleep(100+ random.nextInt(1));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
    
    
}
