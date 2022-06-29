/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

/**
 *
 * @author silga
 */
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCADO = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);

    private Campo campo;
    
    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        
        addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
         switch(evento){
             case ABRIR:
                 aplicarEstiloAbrir();
                 break;
            case MARCAR:
                 aplicarEstiloMarcar();
                 break;
            case EXPLODIR:
                 aplicarEstiloExplodir();
                 break;
            default:
                 aplicarEstiloPadrao();
             
         }
         SwingUtilities.invokeLater(() -> {
             repaint();
             validate();
         });
    }

    private void aplicarEstiloAbrir() {
        
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        if(campo.isMinado()){
            setBackground(BG_EXPLODIR);
            return;
        }
        
        setBackground(BG_PADRAO);
        
        switch(campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            default:
                setForeground(Color.RED);
        }
        
        String valor = !campo.vizinhancaSegura() 
                ? campo.minasNaVizinhanca() + ""
                : "";
        setText(valor);
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCADO);
        setForeground(Color.BLACK);
        setText("P");
        
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
         setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    //Interface dos eventos do Mause
     @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            campo.abrir();
        }else{
            campo.alternarMarcador();
        }
    }
    
    public void mouseClicked(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    
    
}
