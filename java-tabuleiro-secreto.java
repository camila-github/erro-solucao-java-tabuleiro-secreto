import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TabuleiroSecreto {
  	public static void main(String[] args) {
    		List<String> entradasDeDados = new ArrayList<String>();
  	  	Scanner sc = new Scanner(System.in);
          
  		  while(sc.hasNext()) entradasDeDados.add(sc.nextLine());
  
        Iterator<String> it = entradasDeDados.iterator();
        String linha = it.next();
        Integer tamanhoTabuleiro = Integer.parseInt(linha.split(" ")[0]);
        Integer quantOperacoes = Integer.parseInt(linha.split(" ")[1]);
  
        List<Map<String, Integer>> operacoes = lerOperacoes(quantOperacoes, it);
  
        Integer tabuleiro[][] = criarTabuleiro(tamanhoTabuleiro);
  
        for(Map<String, Integer> operacao : operacoes) {
            int tipoOperacao;
          
            if(operacao.get("tipoOperacao") == 1) {
                tipoOperacao = 1;
                tabuleiro = realizarOperacao(operacao, tabuleiro, tamanhoTabuleiro, tipoOperacao);
  
            } else if(operacao.get("tipoOperacao") == 2) {
                tipoOperacao = 2;
                tabuleiro = realizarOperacao(operacao, tabuleiro, tamanhoTabuleiro, tipoOperacao);
  
            } else if(operacao.get("tipoOperacao") == 3) {
                tipoOperacao = 3;
                Map<Integer, Integer> repeticoesNumero = lerRepeticoes(operacao, tabuleiro, tamanhoTabuleiro, tipoOperacao);
                System.out.println(avaliaMaiorNumeroComMaiorRepeticao(repeticoesNumero));
  
            } else if(operacao.get("tipoOperacao") == 4) {
                tipoOperacao = 4;
                Map<Integer, Integer> repeticoesNumero = lerRepeticoes(operacao, tabuleiro, tamanhoTabuleiro, tipoOperacao);
                Integer maiorNumeroComMaiorRepeticao = avaliaMaiorNumeroComMaiorRepeticao(repeticoesNumero);
                System.out.println(maiorNumeroComMaiorRepeticao);
            }
        }
    }
      
    private static List<Map<String, Integer>> lerOperacoes(Integer quantOperacoes, Iterator<String> it) {
          
        List<Map<String, Integer>> operacoes = new ArrayList<Map<String, Integer>>();
         
        for(int i = 0; i < quantOperacoes; i++) {
            String strOperacao = it.next();
            Integer tipoOperacao = Integer.parseInt(strOperacao.split(" ")[0]);
              
            if(tipoOperacao == 1 || tipoOperacao == 2) {
                Integer numLinColX = Integer.parseInt(strOperacao.split(" ")[1]);
                Integer valorR = Integer.parseInt(strOperacao.split(" ")[2]);
                Map<String, Integer> operacao = new HashMap<String, Integer>();
                
                operacao.put("tipoOperacao", tipoOperacao);
                operacao.put("numLinColX", numLinColX);
                operacao.put("valorR", valorR);
                operacoes.add(operacao);
              
            } else if(tipoOperacao == 3 || tipoOperacao == 4) {
                Integer numLinColX = Integer.parseInt(strOperacao.split(" ")[1]);
                Map<String, Integer> operacao = new HashMap<String, Integer>();
          
                operacao.put("tipoOperacao", tipoOperacao);
                operacao.put("numLinColX", numLinColX);
                operacoes.add(operacao);
            }
        }
        return operacoes;
    }
  
    private static Integer[][] criarTabuleiro(int tamanhoTabuleiro) {
          
        Integer tabuleiro[][] = new Integer[tamanhoTabuleiro][tamanhoTabuleiro];
          
        for(int i = 0; i < tamanhoTabuleiro; i++) {
            for(int j = 0; j < tamanhoTabuleiro; j++) tabuleiro[i][j] = 0;
        }
        
        return tabuleiro;
    }
  
    private static Integer[][] realizarOperacao(Map<String, Integer> operacao, 
                                                      Integer[][] tabuleiro, 
                                                      int tamanhoTabuleiro,
                                                      int tipoOperacao) {
        
        int colLin = operacao.get("numLinColX") - 1;
    
        if(tipoOperacao == 1) {
            for(int lin = 0; lin < tamanhoTabuleiro; lin++) tabuleiro[colLin][lin] = operacao.get("valorR");
          
        } else if(tipoOperacao == 2){
            for(int col = 0; col < tamanhoTabuleiro; col++) tabuleiro[col][colLin] = operacao.get("valorR");
        }
      
        return tabuleiro;
    }
  
    private static Map<Integer, Integer> lerRepeticoes(Map<String, Integer> operacao, 
                                                        Integer[][] tabuleiro, 
                                                        Integer tamanhoTabuleiro,
                                                        int tipoOperacao) {
                                                          
        Map<Integer, Integer> repeticoesNumero = new HashMap<Integer, Integer>();
        
        int colLin = 0;
        int qt = 0;
        int pivo = 0;
    
        if(tipoOperacao == 3) {
            colLin = operacao.get("numLinColX") - 1;
           
            for(int lin = 0; lin < tamanhoTabuleiro; lin++) {
                pivo = tabuleiro[colLin][lin];
      
                if(repeticoesNumero.containsKey(pivo)) {
                    qt = repeticoesNumero.get(pivo);
                    qt++;
                    repeticoesNumero.put(pivo, qt);
            
                } else {
                    repeticoesNumero.put(pivo, 1);
                }
            }
    
        } else if(tipoOperacao == 4){
            colLin = operacao.get("numLinColX") - 1;
        
            for(int col = 0; col < tamanhoTabuleiro; col++) {
                  pivo = tabuleiro[col][colLin];
            
              if(repeticoesNumero.containsKey(pivo)) {
                  qt = repeticoesNumero.get(pivo);
                  qt++;
                  repeticoesNumero.put(pivo, qt);
          
              } else {
                  repeticoesNumero.put(pivo, 1);
              }
            }
        }
        return repeticoesNumero;
    }
  
    
    public static Integer avaliaMaiorNumeroComMaiorRepeticao(Map<Integer, Integer> repeticoesNumero) {
          
        List<Integer> maioresRepeticoes = new ArrayList<Integer>();
        Integer maiorRepeticao = Collections.max (repeticoesNumero.values());
  
        List<Integer> chaves = new ArrayList<Integer>(repeticoesNumero.keySet());
          
        for(Integer chave : chaves) {
            Integer valorRepeticao = repeticoesNumero.get(chave);
            if(valorRepeticao >= maiorRepeticao) maioresRepeticoes.add(chave);
        }
  
        Integer maiorNumeroComMaiorRepeticao = Collections.max(maioresRepeticoes);
        return maiorNumeroComMaiorRepeticao;
    }
}