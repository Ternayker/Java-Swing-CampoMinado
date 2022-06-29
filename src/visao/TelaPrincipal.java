/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import javax.swing.JFrame;
import modelo.Tabuleiro;

/**
 *
 * @author silga
 */
public class TelaPrincipal extends JFrame{
    
    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(15,15,20);
        
        add(new PainelTabuleiro(tabuleiro));
        
        setTitle("Campo Minado");
        setSize(400,435);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args){
        
        new TelaPrincipal();
        
    }
    
}
