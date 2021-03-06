/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pessoa.telas;

import static com.pessoa.telas.TelaPessoa.cboClassificacao;
import java.text.DateFormat;
import java.util.Date;
import javax.*;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import static com.pessoa.telas.TelaCidade.cboUF;
import static com.pessoa.telas.TelaUsuario.txtNome;

/**
 *
 * @author thiag
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Desktop = new javax.swing.JDesktopPane();
        Desktop.getMaximumSize();
        lblData = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        mnPrincipal = new javax.swing.JMenuBar();
        mnArquivo = new javax.swing.JMenu();
        mnArquivo_novo = new javax.swing.JMenu();
        mnArquivo_novoCidade = new javax.swing.JMenuItem();
        mnArquivo_novoEstCivil = new javax.swing.JMenuItem();
        mnArquivo_novoGenero = new javax.swing.JMenuItem();
        mnArquivo_novoPais = new javax.swing.JMenuItem();
        mnArquivo_novoTpPessoa = new javax.swing.JMenuItem();
        mnArquivo_novoUF = new javax.swing.JMenuItem();
        mnArquivo_novoUsuario = new javax.swing.JMenuItem();
        mnArquivo_separador = new javax.swing.JPopupMenu.Separator();
        mnArquivo_fechar = new javax.swing.JMenuItem();
        mnPessoa = new javax.swing.JMenu();
        mnPessoa_novo = new javax.swing.JMenuItem();
        mnPessoa_localizar = new javax.swing.JMenuItem();
        mnFerramentas = new javax.swing.JMenu();
        mnFerramentas_relatorios = new javax.swing.JMenuItem();
        mnFerramentas_localizar = new javax.swing.JMenuItem();
        mnAjuda = new javax.swing.JMenu();
        mnAjuda_sobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Pessoa");
        setMaximumSize(new java.awt.Dimension(970, 600));
        setMinimumSize(new java.awt.Dimension(970, 600));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Desktop.setBackground(java.awt.SystemColor.controlHighlight);
        Desktop.setForeground(java.awt.SystemColor.controlHighlight);
        Desktop.setMaximumSize(new java.awt.Dimension(960, 500));
        Desktop.setMinimumSize(new java.awt.Dimension(960, 500));

        javax.swing.GroupLayout DesktopLayout = new javax.swing.GroupLayout(Desktop);
        Desktop.setLayout(DesktopLayout);
        DesktopLayout.setHorizontalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        DesktopLayout.setVerticalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );

        lblData.setText("Data");

        lblUsuario.setText("Usuário");
        lblUsuario.setToolTipText("Usuário logado.");

        mnArquivo.setText("Arquivo");

        mnArquivo_novo.setText("Novo");

        mnArquivo_novoCidade.setText("Cidade");
        mnArquivo_novoCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnArquivo_novoCidadeActionPerformed(evt);
            }
        });
        mnArquivo_novo.add(mnArquivo_novoCidade);

        mnArquivo_novoEstCivil.setText("Estado civil");
        mnArquivo_novoEstCivil.setEnabled(false);
        mnArquivo_novo.add(mnArquivo_novoEstCivil);

        mnArquivo_novoGenero.setText("Gênero");
        mnArquivo_novoGenero.setEnabled(false);
        mnArquivo_novo.add(mnArquivo_novoGenero);

        mnArquivo_novoPais.setText("País");
        mnArquivo_novoPais.setEnabled(false);
        mnArquivo_novo.add(mnArquivo_novoPais);

        mnArquivo_novoTpPessoa.setText("Classificação de Pessoa");
        mnArquivo_novoTpPessoa.setEnabled(false);
        mnArquivo_novo.add(mnArquivo_novoTpPessoa);

        mnArquivo_novoUF.setText("UF");
        mnArquivo_novoUF.setEnabled(false);
        mnArquivo_novoUF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnArquivo_novoUFActionPerformed(evt);
            }
        });
        mnArquivo_novo.add(mnArquivo_novoUF);

        mnArquivo_novoUsuario.setText("Usuário");
        mnArquivo_novoUsuario.setEnabled(false);
        mnArquivo_novoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnArquivo_novoUsuarioActionPerformed(evt);
            }
        });
        mnArquivo_novo.add(mnArquivo_novoUsuario);

        mnArquivo.add(mnArquivo_novo);
        mnArquivo.add(mnArquivo_separador);

        mnArquivo_fechar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnArquivo_fechar.setText("Fechar");
        mnArquivo_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnArquivo_fecharActionPerformed(evt);
            }
        });
        mnArquivo.add(mnArquivo_fechar);

        mnPrincipal.add(mnArquivo);

        mnPessoa.setText("Pessoa");

        mnPessoa_novo.setText("Novo cadastro");
        mnPessoa_novo.setEnabled(false);
        mnPessoa_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPessoa_novoActionPerformed(evt);
            }
        });
        mnPessoa.add(mnPessoa_novo);

        mnPessoa_localizar.setText("Localizar");
        mnPessoa_localizar.setEnabled(false);
        mnPessoa.add(mnPessoa_localizar);

        mnPrincipal.add(mnPessoa);

        mnFerramentas.setText("Ferramentas");

        mnFerramentas_relatorios.setText("Relatórios");
        mnFerramentas_relatorios.setEnabled(false);
        mnFerramentas.add(mnFerramentas_relatorios);

        mnFerramentas_localizar.setText("Localizar");
        mnFerramentas_localizar.setEnabled(false);
        mnFerramentas.add(mnFerramentas_localizar);

        mnPrincipal.add(mnFerramentas);

        mnAjuda.setText("Ajuda");

        mnAjuda_sobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        mnAjuda_sobre.setText("Sobre");
        mnAjuda_sobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAjuda_sobreActionPerformed(evt);
            }
        });
        mnAjuda.add(mnAjuda_sobre);

        mnPrincipal.add(mnAjuda);

        setJMenuBar(mnPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(756, Short.MAX_VALUE)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Desktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Desktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(986, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnArquivo_novoUFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnArquivo_novoUFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnArquivo_novoUFActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // As linhas abaixo substituem a label lblCarregaDtHora pela data atual do sistema ao inicializar o form
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void mnAjuda_sobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAjuda_sobreActionPerformed
        // Chamando a tela Sobre
        TelaAjudaSobre sobre = new TelaAjudaSobre();
        sobre.setVisible(true);
    }//GEN-LAST:event_mnAjuda_sobreActionPerformed

    private void mnArquivo_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnArquivo_fecharActionPerformed
        // Exibe uma caixa de diálogo
        int sair = JOptionPane.showConfirmDialog(null, "Deseja sair do sistema?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_mnArquivo_fecharActionPerformed

    private void mnArquivo_novoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnArquivo_novoUsuarioActionPerformed
        // as linhas abaixo vão abrir o for TelaUsu dentro do Desktop
        TelaUsuario usuario = new TelaUsuario();
        usuario.setVisible(true);
        Desktop.add(usuario);
    }//GEN-LAST:event_mnArquivo_novoUsuarioActionPerformed

    private void mnPessoa_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPessoa_novoActionPerformed
        // TODO add your handling code here:
        TelaPessoa pessoa = new TelaPessoa();
        pessoa.setVisible(true);
        Desktop.add(pessoa);
    }//GEN-LAST:event_mnPessoa_novoActionPerformed

    private void mnArquivo_novoCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnArquivo_novoCidadeActionPerformed
        // TODO add your handling code here:
        TelaCidade cidade = new TelaCidade();
        cidade.setVisible(true);
        Desktop.add(cidade);
    }//GEN-LAST:event_mnArquivo_novoCidadeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Desktop;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu mnAjuda;
    private javax.swing.JMenuItem mnAjuda_sobre;
    private javax.swing.JMenu mnArquivo;
    private javax.swing.JMenuItem mnArquivo_fechar;
    private javax.swing.JMenu mnArquivo_novo;
    private javax.swing.JMenuItem mnArquivo_novoCidade;
    private javax.swing.JMenuItem mnArquivo_novoEstCivil;
    private javax.swing.JMenuItem mnArquivo_novoGenero;
    private javax.swing.JMenuItem mnArquivo_novoPais;
    private javax.swing.JMenuItem mnArquivo_novoTpPessoa;
    private javax.swing.JMenuItem mnArquivo_novoUF;
    public static javax.swing.JMenuItem mnArquivo_novoUsuario;
    private javax.swing.JPopupMenu.Separator mnArquivo_separador;
    private javax.swing.JMenu mnFerramentas;
    private javax.swing.JMenuItem mnFerramentas_localizar;
    public static javax.swing.JMenuItem mnFerramentas_relatorios;
    private javax.swing.JMenu mnPessoa;
    private javax.swing.JMenuItem mnPessoa_localizar;
    public static javax.swing.JMenuItem mnPessoa_novo;
    private javax.swing.JMenuBar mnPrincipal;
    // End of variables declaration//GEN-END:variables
}
