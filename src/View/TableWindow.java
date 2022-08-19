package View;
import java.awt.Color;
import javax.swing.*;

import Controller.Hamming;

public class TableWindow {
    JScrollPane barraRolagem;
    public TableWindow(String bits){
        JFrame teste = new JFrame();
//        Color[] cores = [Color.blue, Color.pink, Color.green];
//        javax.swing.table.TableColumn col;
        teste.setTitle("Gerador e verificador de código de Hamming");
        teste.setResizable(false);
        teste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teste.setSize(800,250);
        String [] colunas = generateColums(bits);
        Object [][] dados = generateData(bits); 

        JTable tabela = new JTable(dados, colunas);
        
        tabela.setFillsViewportHeight(true);
        barraRolagem = new JScrollPane(tabela);
        teste.add(barraRolagem);

        
        teste.setVisible(true);
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
                    int aux = (int)Math.pow(2, i-1); //2
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
    }
}
