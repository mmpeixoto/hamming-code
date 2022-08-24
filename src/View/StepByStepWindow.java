package View;
import java.awt.Color;
import java.awt.Component;
import Controller.Hamming;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class StepByStepWindow extends JFrame{

    static int passoLinha = 0;
    static int passoColuna = 0;
    String bitsAux;

    
    JScrollPane barraRolagem;
    public StepByStepWindow(String bits){
    
        Color[] cores;
        Ouvinte o = new Ouvinte();

		JButton nextStep = new JButton();

        cores = new Color[3];
        cores[0] = new Color(255, 179, 204);
        cores[1] = new Color(179, 204, 255);
        cores[2] = new Color(204, 204, 204);
        
        this.setTitle("Gerador e verificador de código de Hamming");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800,450);


        String [] colunas = generateColums(bits);
        Object [][] dados = generateData(bits); 

        JTable tabela = new JTable(dados, colunas){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            Component c = super.prepareRenderer(renderer, row, column);
            if(getValueAt(row, column) != null && getValueAt(row,column) != ""){
                c.setBackground(cores[row % 3]);
            }else{
                c.setBackground(Color.white);
            }
            return c;
            }
            public boolean isCellEditable(int row, int column) {
                return false;
             }
        };

        tabela.getTableHeader().setReorderingAllowed(false);
        barraRolagem = new JScrollPane(tabela);
        barraRolagem.setSize(800,200);
		barraRolagem.setLocation(0, 0);
        
        nextStep.setSize(70,30);
		nextStep.setLocation(0, 300);
		nextStep.setText("Próximo");
		nextStep.addMouseListener(o);
        
        this.add(barraRolagem);
        this.add(nextStep);
        
        this.setVisible(true);
    }

    public JScrollPane generateTable(String bits){
        return barraRolagem;
    }

    public static String[] generateColums(String bits){
        String[] colums = new String[bits.length()];

        for(int i = 0, j = 0, k = 0; i < bits.length(); i++){
            if(Hamming.isPwrOf2(i+1)){
                colums[i] = "P" + (j + 1);
                j++;
            }else{
                colums[i] = "D" + (k + 1);
                k++;
            }
        }
    
        return colums;
    }

    public static Object[][] generateData(String bits){
        double numBitsParidade, numBitsTotais = bits.length();
		 	
		numBitsParidade = Math.ceil(Math.log(numBitsTotais)/Math.log(2));

        Object[][] matrix = new Object[(int)numBitsParidade + 2][(int)numBitsTotais];

        for(int i = 0; i < numBitsParidade + 2; i++){
            for(int j = 0; j < numBitsTotais; j++){
                if(i == 0){
                    if(!Hamming.isPwrOf2(j + 1)){
                        matrix[i][j] = new Object();
                        matrix[i][j] = bits.charAt(j);
                    }
                    else{
                        matrix[i][j] = "";
                    }
                }else if(i == numBitsParidade + 1){
                    matrix[i][j] = new Object();
                    matrix[i][j] = bits.charAt(j);
                }else{
                    matrix[i][j] = new Object();
                    matrix[i][j] = "";
                    int aux = (int)Math.pow(2, i-1);
                    for(int l = aux;l >= 0 && l < j+aux+1 && l <=numBitsTotais; l+=2*aux){
                        for(int k = l;k >= 0 && k < l+aux &&  k <=numBitsTotais; k++){
                            matrix[i][k-1] = bits.charAt(k-1);
                        }
					}
                    j += 2*aux;
                    j--;
                }
            }
        }
        
        return matrix;
    } class Ouvinte extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
            System.out.println("oi");
		}
    }
}

