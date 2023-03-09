/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author macario
 */
public class Escenario extends JFrame
{

    
    private JLabel[][] tablero;     
    private int[][] matrix;
    private final int dim = 15;

    private ImageIcon robot1;
    private ImageIcon robot2;
    private ImageIcon obstacleIcon;
    private ImageIcon sampleIcon;
    private ImageIcon actualIcon;
    private ImageIcon motherIcon;

    private ImageIcon smokeIcon;
    
    private Agente brandom;
    private Agente emi;

    public ArrayList coordenadasMotherShip = new ArrayList<Integer>();

    public ArrayList coordenadasSample = new ArrayList<Integer>();

    public ArrayList coordenadasObstacle = new ArrayList<Integer>();

    private final BackGroundPanel fondo = new BackGroundPanel(new ImageIcon("imagenes/scene.png"));
    private final JMenu settings = new JMenu("Settings"); //Parte 1 del menu
    private final JRadioButtonMenuItem obstacle = new JRadioButtonMenuItem("Puerk");
    private final JRadioButtonMenuItem sample = new JRadioButtonMenuItem("Buds");
    private final JRadioButtonMenuItem motherShip = new JRadioButtonMenuItem("spot");
    
    public Escenario()
    {
        this.setContentPane(fondo);
        this.setTitle("Agentes");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(50,50,dim*50+35,dim*50+85);
        initComponents();
    }
        
    public void initComponents()
    {


        ButtonGroup settingsOptions = new ButtonGroup();
        settingsOptions.add(sample);
        settingsOptions.add(obstacle);       
        settingsOptions.add(motherShip);
        
        JMenuBar barraMenus = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem run  = new JMenuItem("Run");

        JMenuItem exit   = new JMenuItem("Exit");
              
        this.setJMenuBar(barraMenus);
        barraMenus.add(file);
        barraMenus.add(settings);
        file.add(run);
        file.add(exit);
        settings.add(motherShip);
        settings.add(obstacle);
        settings.add(sample);
            
        robot1 = new ImageIcon("imagenes/persona1.png");
        robot1 = new ImageIcon(robot1.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        robot2 = new ImageIcon("imagenes/persona2.png");
        robot2 = new ImageIcon(robot2.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        obstacleIcon = new ImageIcon("imagenes/cop.png");
        obstacleIcon = new ImageIcon(obstacleIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        sampleIcon = new ImageIcon("imagenes/hemp.png");
        sampleIcon = new ImageIcon(sampleIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        motherIcon = new ImageIcon("imagenes/tree.png");
        motherIcon = new ImageIcon(motherIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        //Added 06/03/2023
        smokeIcon = new ImageIcon("imagenes/smoke.png");
        smokeIcon = new ImageIcon(smokeIcon.getImage().getScaledInstance(50,50,  java.awt.Image.SCALE_SMOOTH));
        
        this.setLayout(null);
        formaPlano();  
        
        exit.addActionListener(evt -> gestionaSalir(evt));
        run.addActionListener(evt -> gestionaRun(evt));
        obstacle.addItemListener(evt -> gestionaObstacle(evt));
        sample.addItemListener(evt -> gestionaSample(evt));
        motherShip.addItemListener(evt -> gestionaMotherShip(evt));

              
            
        class MyWindowAdapter extends WindowAdapter
        {
            public void windowClosing(WindowEvent eventObject)
            {
		goodBye();
            }
        }
        addWindowListener(new MyWindowAdapter());
        
        // Crea 2 agentes
        mapeoCoordenadas(coordenadasMotherShip);
        mapeoCoordenadas(coordenadasSample);
        mapeoCoordenadas(coordenadasObstacle);
        brandom = new Agente("Brandom",robot1, matrix, tablero, coordenadasMotherShip, coordenadasSample, coordenadasObstacle);
        emi = new Agente("Emi",robot2, matrix, tablero, coordenadasMotherShip, coordenadasSample, coordenadasObstacle);





        
    }
        
    private void gestionaSalir(ActionEvent eventObject)
    {
        goodBye();
    }

    private void goodBye()
    {
        System.exit(0);
    }

    private void formaPlano()
    {
        tablero = new JLabel[dim][dim];
        matrix = new int[dim][dim];
        
        int i, j;
        
        for(i=0;i<dim;i++)
            for(j=0;j<dim;j++)
            {
                matrix[i][j]=0;
                tablero[i][j]=new JLabel();
                tablero[i][j].setBounds(j*50+10,i*50+10,50,50);
                tablero[i][j].setBorder(BorderFactory.createDashedBorder(Color.white));
                tablero[i][j].setOpaque(false);
                this.add(tablero[i][j]);
                
                tablero[i][j].addMouseListener(new MouseAdapter() // Este listener nos ayuda a agregar poner objetos en la rejilla
                    {
                        @Override
                        public void mousePressed(MouseEvent e) 
                        {
                               insertaObjeto(e);

                        }   
                
                        @Override
                        public void mouseReleased(MouseEvent e) 
                        {
                                insertaObjeto(e);
                        }   
                
                    });
                                
            }
    }


        
    private void gestionaObstacle(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected()) { //Seleccion desde el menú
            actualIcon = obstacleIcon;
        }
        else actualIcon = null;
    }
    private void gestionaSample(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected())
           actualIcon = sampleIcon;
        else actualIcon = null;   
    }
    private void gestionaMotherShip(ItemEvent eventObject)
    {
        JRadioButtonMenuItem opt = (JRadioButtonMenuItem) eventObject.getSource();
        if(opt.isSelected())
           actualIcon = motherIcon;
        else actualIcon = null;   
    }
    private void gestionaRun(ActionEvent eventObject)
    {
        mapeoCoordenadas(coordenadasMotherShip);
        mapeoCoordenadas(coordenadasSample);
        mapeoCoordenadas(coordenadasObstacle);
        if(!brandom.isAlive()) brandom.start();
        if(!emi.isAlive()) emi.start();
        settings.setEnabled(false);
    }
       
    public void insertaObjeto(MouseEvent e)
    {
        JLabel casilla = (JLabel) e.getSource();

        if(actualIcon!=null) casilla.setIcon(actualIcon);
        if(actualIcon == motherIcon){

            System.out.println("has puesto un spot");
            System.out.println("X: " + mapeo(casilla.getX()) + " Y: " + mapeo(casilla.getY()));
            coordenadasMotherShip.add(mapeo(casilla.getX()));
            coordenadasMotherShip.add(mapeo(casilla.getY()));

        }

        if (actualIcon == obstacleIcon){
            System.out.println("has puesto un puerco");
            coordenadasObstacle.add(mapeo(casilla.getX()));
            coordenadasObstacle.add(mapeo(casilla.getY()));

        }
        if (actualIcon == sampleIcon){
            System.out.println("has puesto una mariwana");
            coordenadasSample.add(mapeo(casilla.getX()));
            coordenadasSample.add(mapeo(casilla.getY()));
        }
    }

    private int mapeo(Integer x){
        if (x > 10){
            return (x - 10) / 50;
        }else{
            return x - 10;
        }
    }

    private void mapeoCoordenadas(ArrayList<Integer> arreglo){
        int remover = 2;
        int tam = arreglo.size()/4;
        for(int i = 0; i < tam ; i++){
            arreglo.remove(remover);

            arreglo.remove(remover);
            remover += 2;

        }
    }


}
