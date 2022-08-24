package Controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.util.Arrays;

public class Hamming {

	private static String regex = "[0-1]+";
	
	public static boolean isPwrOf2(int n) {
		if(n == 0) {
			return false;
		}
        while(n != 1){
            if(n % 2 != 0){
            	return false;
            }
            n = n/2;
        }
        return true;
	}
	
	 public static String bitsToBeSend(String bits) {
		if(!bits.matches(regex)){
			return "Digitos invalidos";
		}
		int[] dados;
		int numBitsDadosBrutos = bits.length();
		int numBitsRedundancia;
		int numBitsDadosTransmitir = numBitsDadosBrutos;
		
		for(numBitsRedundancia = 0; Math.pow(2, numBitsRedundancia) - numBitsRedundancia - 1 < numBitsDadosBrutos; numBitsRedundancia++, numBitsDadosTransmitir++);

		dados = new int[numBitsDadosTransmitir];
		
		for(int i = numBitsDadosTransmitir, j = bits.length()-1; i > 0 ; i--) {
			if(!isPwrOf2(i)) {
				dados[i-1] =  Integer.parseInt(Character.toString(bits.charAt(j)));
				j--;
			}
			
			else{
				int numeroDeUns = 0;
				for(int k = i - 1; k < numBitsDadosTransmitir; k+=2*i) {
					for(int l = k;l >= 0 && l < k+i && l <numBitsDadosTransmitir; l++) {
						if(dados[l]== 1) {
							numeroDeUns++;
						}
					}
				}
				
				dados[i - 1] = numeroDeUns%2;
			}
		}
		
		return Arrays.toString(dados).replaceAll("\\[|\\]|,|\\s", "");
	}

	 public static String checkReceivedBits(String bits) {
		if(isPwrOf2(bits.length())){
			showMessageDialog(null, "Tamanho inválido");
			return "Tamanho inválido";
		}
		if(!bits.matches(regex)){
			showMessageDialog(null, "Digitos invalidos");
			return "Digitos invalidos";
		}
		int[] dados;
		int erro = 0;
		double numBitsParidade, numBitsDados, numBitsTotais = bits.length();
		 	
		numBitsParidade = Math.ceil(Math.log(numBitsTotais)/Math.log(2));
		numBitsDados = numBitsTotais - numBitsParidade;
		dados = new int[(int)numBitsDados];
		 	
		for(int i = (int)numBitsTotais, j = (int)numBitsDados-1; i > 0 ; i--) {
			if(!isPwrOf2(i)) {
				dados[j] =  Integer.parseInt(Character.toString(bits.charAt(i-1)));
				j--;
			}
					
			else{
				int numDeUns = 0;
				for(int k = i - 1; k < numBitsTotais; k+=2*i) {
					for(int l = k;l >= 0 && l < k+i && l <numBitsTotais; l++) {
						
						if(k == i - 1 && l == k) {
							
						}else if(Integer.parseInt(Character.toString(bits.charAt(l))) == 1) {
							numDeUns++;
						}
					}
				}
				if(numDeUns%2 != Integer.parseInt(Character.toString(bits.charAt(i-1)))){
					erro += i;
				}
			}
		}
		if(erro == 0) {
		}else{
			showMessageDialog(null, "Erro no bit" + erro + ", retornando dados corrigidos: ");
			erro -= Math.ceil(Math.log(erro)/Math.log(2));
			erro--;
			dados[erro] = (dados[erro] == 1? 0 : 1);
		}
		
		for(int i = 0; i < dados.length; i++) {
			System.out.print(dados[i] + " - ");
		}
		showMessageDialog(null, Arrays.toString(dados).replaceAll("\\[|\\]|,|\\s", ""));
		return Arrays.toString(dados).replaceAll("\\[|\\]|,|\\s", "");
	 }

}
