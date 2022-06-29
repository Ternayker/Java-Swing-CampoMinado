/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author silga
 */
public class Tabuleiro implements CampoObservador{
    private final int linhas;
    private final int colunas;
    private final int minas;
          
    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<Boolean>> observadores = new ArrayList();
    
    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        geraCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void paraCada(Consumer<Campo> funcao){
        campos.forEach(funcao);
    }
           
    
    public void registrarObservador(Consumer<Boolean> observador){
        observadores.add(observador);
    }
    
    public void notificarObservadores(boolean resultado){
        observadores.stream()
        .forEach(o -> o.accept(resultado));
    } 
    
    public void abrir(int linha, int coluna){
            campos.parallelStream()
              .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
              .findFirst()
              .ifPresent(c -> c.abrir()); 
    }
    
    
    public void alterarMarcador(int linha, int coluna){
        campos.parallelStream()
              .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
              .findFirst()
              .ifPresent(c -> c.alternarMarcador());
    }
    
    private void geraCampos() {
        for(int linha = 0;linha < linhas;linha++){
            for(int coluna = 0;coluna < colunas;coluna++){
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }
            
    }

    private void associarVizinhos() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.addVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
            
        }while(minasArmadas < minas);
    }
        
    public boolean ojetivoAlcancado(){
        return campos.stream().allMatch(c -> c.ojetivoAlcancado());
    }
    
    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if(evento == CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservadores(false);
        }else if(ojetivoAlcancado()){
            notificarObservadores(true);
        }
    }
    
        private void mostrarMinas(){
        campos.stream()
        .filter(c -> c.isMinado())
        .filter(c -> !c.isMarcado())
        .forEach(c -> c.setAberto(true));
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }
        
}
