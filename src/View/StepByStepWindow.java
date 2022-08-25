package View;
import java.awt.Color;
import java.awt.Component;
import Controller.Hamming;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class StepByStepWindow extends JFrame{

    static JTextField textoNumeroDeUns = new JTextField();
    static JLabel labelNumeroDeUns = new JLabel();
    static int passoLinha = 0;
    static int passoColuna = 0;
    static Object [][] dados, dadosAux;
    String bitsAux;
    JTable tabela;
    Color[] cores = new Color[3];

    
    JScrollPane barraRolagem;
    public StepByStepWindow(String bits){
    
        
        Ouvinte o = new Ouvinte();
		JButton nextStep = new JButton();
        // JLabel instrucaoVermelho = new JLabel();

        cores[0] = new Color(255, 179, 204);
        cores[1] = new Color(179, 204, 255);
        cores[2] = new Color(204, 204, 204);
        
        this.setTitle("Gerador e verificador de código de Hamming");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800,450);


        String [] colunas = generateColums(bits);
        generateData(bits); 

        tabela = new JTable(dadosAux, colunas){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component c = super.prepareRenderer(renderer, row, column);
                if(row == passoLinha && column == passoColuna){
                    c.setBackground(Color.red);
                }else if(getValueAt(row, column) != null && getValueAt(row,column) != ""){
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

        textoNumeroDeUns.setSize(70,30);
        textoNumeroDeUns.setEditable(false);
        textoNumeroDeUns.setLocation(200, 300);
        textoNumeroDeUns.setText("");
        labelNumeroDeUns.setSize(100,30);
        labelNumeroDeUns.setLocation(100, 300);
        labelNumeroDeUns.setText("Numero de Uns");
        
        nextStep.setSize(70,30);
		nextStep.setLocation(0, 300);
		nextStep.setText("Próximo");
		nextStep.addMouseListener(o);
        
        this.add(textoNumeroDeUns);
        this.add(barraRolagem);
        this.add(nextStep);
        this.add(labelNumeroDeUns);
        
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

    public static void generateData(String bits){
        double numBitsParidade, numBitsTotais = bits.length();
		 	
		numBitsParidade = Math.ceil(Math.log(numBitsTotais)/Math.log(2));

        dados = new Object[(int)numBitsParidade + 2][(int)numBitsTotais];
        dadosAux = new Object[(int)numBitsParidade + 2][(int)numBitsTotais];

        for(int i = 0; i < numBitsParidade + 2; i++){
            for(int j = 0; j < numBitsTotais; j++){
                if(i == 0){
                    if(!Hamming.isPwrOf2(j + 1)){
                        dados[i][j] = new Object();
                        dadosAux[i][j] = new Object();
                        dados[i][j] = bits.charAt(j);
                        dadosAux[i][j] = "";
                    }
                    else{
                        dados[i][j] = "";
                        dadosAux[i][j] = "";
                    }
                }else if(i == numBitsParidade + 1){
                    dados[i][j] = new Object();
                    dadosAux[i][j] = new Object();
                    dados[i][j] = bits.charAt(j);
                    dadosAux[i][j] = "";
                }else{
                    dados[i][j] = new Object();
                    dadosAux[i][j] = new Object();
                    dados[i][j] = "";
                    dadosAux[i][j] = "";
                    int aux = (int)Math.pow(2, i-1);
                    for(int l = aux;l >= 0 && l < j+aux+1 && l <=numBitsTotais; l+=2*aux){
                        for(int k = l;k >= 0 && k < l+aux &&  k <=numBitsTotais; k++){
                            dados[i][k-1] = bits.charAt(k-1);
                            dadosAux[i][k-1] = "";
                        }
					}
                    j += 2*aux;
                    j--;
                }
            }
        }
    } class Ouvinte extends MouseAdapter{
        
        boolean trava = true;
    
        int numDeUns = 0;
		public void mouseClicked(MouseEvent e) {

            if(passoLinha == 0 || passoLinha == dadosAux.length -1){
                dadosAux[passoLinha][passoColuna] = dados[passoLinha][passoColuna];
                passoColuna++;
                if(passoColuna == dadosAux[passoLinha].length){
                    passoColuna = 0;
                    passoLinha++;
                }
                tabela.repaint();
            }else{
                if(passoColuna == Math.pow(2, passoLinha-1)-1 && !trava){
                    dadosAux[passoLinha][passoColuna] = dados[passoLinha][passoColuna];
                    tabela.repaint();
                    passoLinha++;
                    passoColuna = 0;
                    numDeUns = 0;
                    textoNumeroDeUns.setText(Integer.toString(numDeUns));
                    trava = (passoLinha == dadosAux.length-1 ? false : true);
                }
                if(passoColuna == Math.pow(2, passoLinha-1)-1 && trava){
                    trava = false;
                }else{
                    dadosAux[passoLinha][passoColuna] = dados[passoLinha][passoColuna];
                    tabela.repaint();
                }
    
                if(dadosAux[passoLinha][passoColuna] != null){
                    if(tabela.getValueAt(passoLinha, passoColuna) != "" && passoLinha != 0 && passoLinha != dadosAux.length-1 && passoColuna != Math.pow(2, passoLinha-1)-1){
                        if((Character)tabela.getValueAt(passoLinha, passoColuna) == "1".charAt(0)){
                           textoNumeroDeUns.setText(Integer.toString(++numDeUns));
                        }
                    }
                }
                
                
                
    
                passoColuna++;
                tabela.repaint();
    
                if(passoLinha != 0 && passoLinha != dadosAux.length-1 && passoColuna == dadosAux[passoLinha].length){
                    passoColuna = (int)Math.pow(2, passoLinha-1)-1;
                }
            }
		}
    }
}

