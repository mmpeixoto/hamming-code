package View;
import javax.swing.*;
import java.awt.event.*;
import View.TableWindow;

import Controller.Hamming;

public class MainWindow extends JFrame{
        Ouvinte o = new Ouvinte();
        JTextField bitsToSend = new JTextField();
        JLabel labelA = new JLabel("Bits a enviar");
        JTextField bitsToVerify = new JTextField();
        JLabel labelB = new JLabel("Verificar bits");
        JButton sendBits = new JButton();
        JButton verifyBits = new JButton();
		JButton seeTable = new JButton();

    public MainWindow(){
        this.setTitle("Gerador e verificador de código de Hamming");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320,220);
        this.getContentPane().setLayout(null);

        bitsToSend.setSize(200,30);
    	bitsToSend.setLocation(90, 30);
	
    	labelA.setLocation(10, 33);
    	labelA.setLabelFor(bitsToSend);
    	labelA.setSize(100,20);
	
	
    	bitsToVerify.setSize(200,30);
    	bitsToVerify.setLocation(90, 70);
	
    	labelB.setSize(100,20);
    	labelB.setLocation(10, 73);
		labelB.setLabelFor(bitsToVerify);

        sendBits.setSize(70,30);
		sendBits.setLocation(20, 130);
		sendBits.setText("Enviar");
		sendBits.addMouseListener(o);
	
		verifyBits.setSize(70,30);
		verifyBits.setLocation(115, 130);
		verifyBits.setText("Verificar");
		verifyBits.addMouseListener(o);

		seeTable.setSize(70,30);
		seeTable.setLocation(210, 130);
		seeTable.setText("Fazer tabela");
		seeTable.addMouseListener(o);

        this.add(labelA);
		this.add(bitsToSend);
	
		this.add(labelB);
		this.add(bitsToVerify);

        this.add(sendBits);
		this.add(verifyBits);
		this.add(seeTable);

        this.setVisible(true);
    }
    

    class Ouvinte extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
		    if(e.getComponent() == sendBits ) {
		    	bitsToVerify.setText(Hamming.bitsToBeSend(bitsToSend.getText())); 
		    }else if(e.getComponent() == verifyBits ) {
		    	bitsToSend.setText(Hamming.checkReceivedBits(bitsToVerify.getText())); 
		    }else if(e.getComponent() == seeTable){
				new TableWindow(bitsToVerify.getText());
			}
		}
    }
}
