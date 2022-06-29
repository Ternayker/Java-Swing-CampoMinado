/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;


import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.Tabuleiro;

/**
 *
 * @author silga
 */
public class PainelTabuleiro extends JPanel{

    public PainelTabuleiro(Tabuleiro tabuleiro){
        setLayout(new GridLayout(tabuleiro.getColunas(),tabuleiro.getLinhas()));
        
        tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
        
        tabuleiro.registrarObservador(e -> {
            
           SwingUtilities.invokeLater(() -> {
               if(e == true){
               JOptionPane.showMessageDialog(this, "GANHOU!");
               }else{
               JOptionPane.showMessageDialog(this, "PERDEU!");
               }
               
               tabuleiro.reiniciar();
           }); 
        });
    }

    
    
}
