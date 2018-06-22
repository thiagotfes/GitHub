/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pessoa.telas;

import java.sql.*;
import com.pessoa.dal.ModuloConexao;
import static com.pessoa.telas.TelaPrincipal.lblUsuario;
import static com.pessoa.telas.TelaPrincipal.mnArquivo_novoUsuario;
import static com.pessoa.telas.TelaPrincipal.mnFerramentas_relatorios;
import static com.pessoa.telas.TelaPrincipal.mnPessoa_novo;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import com.pessoa.dal.WebServiceCep;
import java.util.Scanner;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.text.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.pessoa.dal.SomenteNumero;
import static com.pessoa.telas.TelaCidade.cboUF;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author thiag
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conn = ModuloConexao.conector();
        txtCodigo.setDocument(new com.pessoa.dal.SomenteNumero(6));
        txtNome.setDocument(new com.pessoa.dal.LimitaNrCaracteres(50));
        txtLogin.setDocument(new com.pessoa.dal.LimitaNrCaracteresLow(30));
        txtSenha.setDocument(new com.pessoa.dal.LimitarCaracteres(8));
        txtDtCadastro.setDocument(new com.pessoa.dal.LimitaNrCaracteres(15));
        txtDtAcesso.setDocument(new com.pessoa.dal.LimitaNrCaracteres(15));

    }

//método para limpar campos   
    private void Limpar() {
        txtCodigo.setText(null);
        txtNome.setText(null);
        txtLogin.setText(null);
        txtSenha.setText(null);
        txtDtAcesso.setText(null);
        txtDtCadastro.setText(null);
    }

// Método que armazena as informações da tabela tb_tpusuario em uma combobox cboTipo
    private void listarTipo() {
        String sql = "select nm_tpusuario from tb_tpusuario;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboTipo.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//MÉTODO para consultar usuário pelo código
    private void consultarCodigo() {
        String sql = "select * from tb_usuario where cd_usuario=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblId.setText(rs.getString(1));
                txtCodigo.setText(rs.getString(2));
                txtLogin.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtSenha.setText(rs.getString(5));
                cboTipo.setSelectedItem(rs.getString(6));
                txtDtCadastro.setText(rs.getString(7));
                txtDtAcesso.setText(rs.getString(8));

                btnLocalizar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnNovo.setEnabled(false);
                txtNome.setEnabled(false);
                txtSenha.setEnabled(false);
                cboTipo.setEnabled(false);
                txtDtCadastro.setEnabled(false);
                txtDtAcesso.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//MÉTODO para consultar usuário pelo login
    private void consultarLogin() {
        String sql = "select * from tb_usuario where nm_usuario=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtLogin.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblId.setText(rs.getString(1));
                txtCodigo.setText(rs.getString(2));
                txtLogin.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtSenha.setText(rs.getString(5));
                cboTipo.setSelectedItem(rs.getString(6));
                txtDtCadastro.setText(rs.getString(7));
                txtDtAcesso.setText(rs.getString(8));

                btnLocalizar.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnNovo.setEnabled(false);
                txtNome.setEnabled(false);
                txtSenha.setEnabled(false);
                cboTipo.setEnabled(false);
                txtDtCadastro.setEnabled(false);
                txtDtAcesso.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//MÉTODO para adicionar um usuário
    private void adicionar() {
        String sql = "insert into tb_usuario(cd_usuario,nm_usuario,nome,senhas,tipo,dtcadastro,ultimoacesso) values(?,?,?,?,?,?,?)";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtLogin.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtSenha.getText());
            pst.setString(5, cboTipo.getSelectedItem().toString());
            pst.setString(6, txtDtCadastro.getText());
            pst.setString(7, txtDtAcesso.getText());

            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Código:' não pode ficar em branco. Verifique!");
                txtCodigo.requestFocusInWindow();
            } else if (txtLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Login:' não pode ficar em branco. Verifique!");
                txtLogin.requestFocusInWindow();
            } else if (txtNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Nome:' não pode ficar em branco. Verifique!");
                txtNome.requestFocusInWindow();
            } else if (txtSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Senha:' na guia 'Endereço' não pode ficar em branco. Verifique!");
                txtSenha.requestFocusInWindow();
            } else if (txtDtCadastro.getText().isEmpty()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "O campo 'Dt. Cadastro:' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtDtCadastro.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    //VOlta a fazer a verificação nos campos restantes 
                    if (txtDtAcesso.getText().isEmpty()) {
                        Object[] options1 = {"Confirmar", "Cancelar"};
                        int j = JOptionPane.showOptionDialog(null,
                                "O campo 'Último acesso:' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                options1, options1[0]);
                        if (j == JOptionPane.NO_OPTION) {
                            txtDtAcesso.requestFocusInWindow();
                        } else if (j == JOptionPane.YES_OPTION) {
                            adicionar();
                            Limpar();
                        }
                    }}}
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        btnCancelar.setEnabled(false);
                        btnExcluir.setEnabled(true);
                        btnEditar.setEnabled(true);
                        btnGravar.setEnabled(false);
                        consultarCodigo();
                    if (txtCodigo.getText().isEmpty()){
                        btnEditar.setEnabled(false);
                    }
                    }

                
            
        } catch (HeadlessException | SQLException e) {
            String erro = null;
            erro = e.toString();
            JOptionPane.showConfirmDialog(null, erro);
        }

    }

//MÉTODO para alterar um usuário
    private void alterar() {
        String sql = "UPDATE tb_usuario SET cd_usuario=?,nm_usuario=?,nome=?,senhas=?,tipo=?,dtcadastro=?,ultimoacesso=? where id_usuario=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtLogin.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtSenha.getText());
            pst.setString(5, cboTipo.getSelectedItem().toString());
            pst.setString(6, txtDtCadastro.getText());
            pst.setString(7, txtDtAcesso.getText());
            pst.setString(8, lblId.getText());

            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Código:' não pode ficar em branco. Verifique!");
                txtCodigo.requestFocusInWindow();
            } else if (txtLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Login:' não pode ficar em branco. Verifique!");
                txtLogin.requestFocusInWindow();
            } else if (txtNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Nome:' não pode ficar em branco. Verifique!");          
                txtNome.requestFocusInWindow();
            } else if (txtSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Senha:' não pode ficar em branco. Verifique!");
                txtSenha.requestFocusInWindow();
            } else if (txtDtCadastro.getText().isEmpty()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "O campo 'Dt. Cadastro:' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtDtCadastro.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    //VOlta a fazer a verificação nos campos restantes 
                    if (txtDtAcesso.getText().isEmpty()) {
                        Object[] options1 = {"Confirmar", "Cancelar"};
                        int j = JOptionPane.showOptionDialog(null,
                                "O campo 'Último acesso:' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                options1, options1[0]);
                        if (j == JOptionPane.NO_OPTION) {
                            txtDtAcesso.requestFocusInWindow();
                            btnGravar.setEnabled(true);
                        } else if (j == JOptionPane.YES_OPTION) {
                            alterar();
                            Limpar();
                        }
                    }}}
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                        //Desativar campos
                        btnCancelar.setEnabled(false);
                        btnExcluir.setEnabled(false);
                        btnEditar.setEnabled(true);
                        btnGravar.setEnabled(false);
                        consultarCodigo();
                        if (txtCodigo.getText().isEmpty()){
                        btnEditar.setEnabled(false);
                    }
                    }

                
            
        } catch (HeadlessException | SQLException e) {
            String erro = null;
            erro = e.toString();
            JOptionPane.showConfirmDialog(null, erro);
        }

    }

//método para excluir o cadastro de um usuário
    private void Excluir() {
        //método para confirmar a exclusão
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_usuario where cd_usuario=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtCodigo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso");
                    Limpar();
                    btnNovo.setEnabled(true);
                    btnLocalizar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnExcluir.setEnabled(false);
                    btnGravar.setEnabled(false);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

//método para salvar
    private void Salvar() {
        String sql = "Select * From tb_usuario Where cd_usuario=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Usuário já registrado no banco de dados. Deseja alterar os dados deste usuário? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    alterar();
                }
            } else {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Deseja confirmar o cadastro deste usuário? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    adicionar();
                }
            }
            btnNovo.setEnabled(true);
            btnLocalizar.setEnabled(true);
            btnCancelar.setEnabled(false);
            btnExcluir.setEnabled(false);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Painel_Botao = new javax.swing.JPanel();
        btnLocalizar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        Painel_Busca = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        lblLogin = new javax.swing.JLabel();
        Painel_Info = new javax.swing.JPanel();
        txtSenha = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        lblDtCadastro = new javax.swing.JLabel();
        txtDtCadastro = new javax.swing.JTextField();
        try{    javax.swing.text.MaskFormatter data = new javax.swing.text.MaskFormatter("##/##/####");       txtDtCadastro = new javax.swing.JFormattedTextField(data);    }catch(Exception e){   }
        txtDtAcesso = new javax.swing.JTextField();
        try{    javax.swing.text.MaskFormatter data = new javax.swing.text.MaskFormatter("##/##/####");       txtDtCadastro = new javax.swing.JFormattedTextField(data);    }catch(Exception e){   }
        lblDtAcesso = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox<>();
        lblId = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de usuário");
        setMaximumSize(new java.awt.Dimension(960, 548));
        setMinimumSize(new java.awt.Dimension(960, 548));
        setPreferredSize(new java.awt.Dimension(960, 548));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        Painel_Botao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Painel_Botao.setPreferredSize(new java.awt.Dimension(896, 69));

        btnLocalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/searchUser.png"))); // NOI18N
        btnLocalizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLocalizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLocalizar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnLocalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocalizarActionPerformed(evt);
            }
        });
        btnLocalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLocalizarKeyPressed(evt);
            }
        });

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/addUser.png"))); // NOI18N
        btnNovo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNovo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNovo.setPreferredSize(new java.awt.Dimension(65, 60));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        btnNovo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNovoKeyPressed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/editUser.png"))); // NOI18N
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.setEnabled(false);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        btnEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEditarKeyPressed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/closeUser.png"))); // NOI18N
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setEnabled(false);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/save.png"))); // NOI18N
        btnGravar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGravar.setEnabled(false);
        btnGravar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGravar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });
        btnGravar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGravarKeyPressed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/del.png"))); // NOI18N
        btnExcluir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcluir.setEnabled(false);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setPreferredSize(new java.awt.Dimension(65, 60));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        btnExcluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExcluirKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout Painel_BotaoLayout = new javax.swing.GroupLayout(Painel_Botao);
        Painel_Botao.setLayout(Painel_BotaoLayout);
        Painel_BotaoLayout.setHorizontalGroup(
            Painel_BotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_BotaoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Painel_BotaoLayout.setVerticalGroup(
            Painel_BotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_BotaoLayout.createSequentialGroup()
                .addGroup(Painel_BotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Painel_Busca.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCodigo.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.setNextFocusableComponent(txtLogin);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        txtLogin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtLogin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtLogin.setNextFocusableComponent(txtNome);
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
        });

        lblLogin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblLogin.setText("Login:");

        javax.swing.GroupLayout Painel_BuscaLayout = new javax.swing.GroupLayout(Painel_Busca);
        Painel_Busca.setLayout(Painel_BuscaLayout);
        Painel_BuscaLayout.setHorizontalGroup(
            Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_BuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Painel_BuscaLayout.setVerticalGroup(
            Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Painel_BuscaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblLogin)
                        .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodigo)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        Painel_Info.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSenha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSenha.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSenha.setEnabled(false);
        txtSenha.setNextFocusableComponent(cboTipo);

        lblSenha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSenha.setText("Senha:");

        txtNome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNome.setEnabled(false);
        txtNome.setNextFocusableComponent(txtSenha);

        lblNome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNome.setText("Nome:");

        lblDtCadastro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDtCadastro.setText("Dt. Cadastro:");

        txtDtCadastro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDtCadastro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDtCadastro.setEnabled(false);
        txtDtCadastro.setNextFocusableComponent(txtDtAcesso);

        txtDtAcesso.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDtAcesso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDtAcesso.setEnabled(false);

        lblDtAcesso.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDtAcesso.setText("Último acesso:");

        lblTipo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTipo.setText("Tipo:");

        cboTipo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboTipo.setEnabled(false);
        cboTipo.setNextFocusableComponent(txtDtCadastro);

        javax.swing.GroupLayout Painel_InfoLayout = new javax.swing.GroupLayout(Painel_Info);
        Painel_Info.setLayout(Painel_InfoLayout);
        Painel_InfoLayout.setHorizontalGroup(
            Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Painel_InfoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Painel_InfoLayout.createSequentialGroup()
                                .addComponent(lblTipo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Painel_InfoLayout.createSequentialGroup()
                                .addComponent(lblSenha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(200, 200, 200)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addComponent(lblDtAcesso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDtAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addComponent(lblDtCadastro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Painel_InfoLayout.setVerticalGroup(
            Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_InfoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDtCadastro)
                            .addComponent(txtDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDtAcesso)
                            .addComponent(txtDtAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Painel_InfoLayout.createSequentialGroup()
                        .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSenha)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(Painel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        lblId.setForeground(java.awt.SystemColor.control);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblId))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Painel_Busca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Painel_Botao, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                        .addComponent(Painel_Info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Painel_Botao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Painel_Busca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Painel_Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblId)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        setBounds(0, 0, 960, 548);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        listarTipo();
    }//GEN-LAST:event_formComponentShown

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (txtNome.isEnabled()){
            
        } else
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarCodigo();
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed

        String cd = txtCodigo.getText();
        String nm = txtNome.getText();
        try {
            if (cd.isEmpty()) {
                if (nm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor inserir uma informação para busca.");
                    txtCodigo.requestFocusInWindow();
                }
                consultarLogin();
            } else {
                consultarCodigo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
        }


    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btnLocalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLocalizarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String cd = txtCodigo.getText();
            String nm = txtNome.getText();
            try {
                if (cd.isEmpty()) {
                    if (nm.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Favor inserir uma informação para busca.");
                        txtCodigo.requestFocusInWindow();
                    } else {
                        consultarLogin();
                    }
                } else {
                    consultarCodigo();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
            }
        }
    }//GEN-LAST:event_btnLocalizarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Object[] options = {"Confirmar", "Cancelar"};
        int i = JOptionPane.showOptionDialog(null,
                "Confirma o cancelamento da ação atual? ", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == JOptionPane.NO_OPTION) {
            txtNome.requestFocusInWindow();
        } else if (i == JOptionPane.YES_OPTION) {
            txtCodigo.setText(null);
            txtLogin.setText(null);
            txtNome.setText(null);
            txtSenha.setText(null);
            txtDtCadastro.setText(null);
            txtDtAcesso.setText(null);
            btnNovo.setEnabled(true);
            btnLocalizar.setEnabled(true);

            //Desativar campos 
            cboTipo.setEnabled(false);
            txtNome.setEnabled(false);
            txtSenha.setEnabled(false);
            txtDtAcesso.setEnabled(false);
            txtDtCadastro.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnGravar.setEnabled(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Object[] options = {"Confirmar", "Cancelar"};
            int i = JOptionPane.showOptionDialog(null,
                    "Confirma o cancelamento da ação atual? ", "Atenção",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.NO_OPTION) {
                txtNome.requestFocusInWindow();
            } else if (i == JOptionPane.YES_OPTION) {
                txtCodigo.setText(null);
                txtLogin.setText(null);
                txtNome.setText(null);
                txtSenha.setText(null);
                txtDtCadastro.setText(null);
                txtDtAcesso.setText(null);

                //Desativar campos 
                cboTipo.setEnabled(false);
                txtNome.setEnabled(false);
                txtSenha.setEnabled(false);
                txtDtAcesso.setEnabled(false);
                txtDtCadastro.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // Habilitando os componentes para alteração
        consultarCodigo();
        btnNovo.setEnabled(false);
        btnExcluir.setEnabled(true);
        btnGravar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnLocalizar.setEnabled(false);
        txtCodigo.setEnabled(true);
        txtNome.setEnabled(true);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);
        txtDtCadastro.setEnabled(true);
        txtDtAcesso.setEnabled(true);
        cboTipo.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEditarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEditarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Habilitando os componentes para alteração
            consultarCodigo();
            btnNovo.setEnabled(false);
            btnExcluir.setEnabled(true);
            btnGravar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnLocalizar.setEnabled(false);
            txtCodigo.setEnabled(true);
            txtNome.setEnabled(true);
            txtLogin.setEnabled(true);
            txtSenha.setEnabled(true);
            txtDtCadastro.setEnabled(true);
            txtDtAcesso.setEnabled(true);
            cboTipo.setEnabled(true);
        }
    }//GEN-LAST:event_btnEditarKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Excluir();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        Salvar();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Salvar();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Abre os campos para novo cadastro de pessoa
        if (txtNome.isEnabled()) {
            Object[] options = {"Confirmar", "Cancelar"};
            int i = JOptionPane.showOptionDialog(null,
                    "Existe um cadastro em edição. Deseja cancelar este cadastro em andamento? ", "Atenção",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.NO_OPTION) {
                txtNome.requestFocusInWindow();
            } else if (i == JOptionPane.YES_OPTION) {
                Limpar();
                txtCodigo.setEnabled(true);
                txtNome.setEnabled(true);
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                txtDtCadastro.setEnabled(true);
                txtDtAcesso.setEnabled(true);
                cboTipo.setEnabled(true);
                txtCodigo.requestFocusInWindow();

                //DESATIVANDO COMPONENTES
                btnEditar.setEnabled(false);
                btnGravar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnLocalizar.setEnabled(false);
            }
        } else {
            Limpar();
            txtCodigo.setEnabled(true);
            txtNome.setEnabled(true);
            txtLogin.setEnabled(true);
            txtSenha.setEnabled(true);
            txtDtCadastro.setEnabled(true);
            txtDtAcesso.setEnabled(true);
            cboTipo.setEnabled(true);
            txtCodigo.requestFocusInWindow();

            //DESATIVANDO COMPONENTES
            btnEditar.setEnabled(false);
            btnGravar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnLocalizar.setEnabled(false);
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnNovoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNovoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Abre os campos para novo cadastro de pessoa
            if (txtNome.isEnabled()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Existe um cadastro em edição. Deseja cancelar este cadastro em andamento? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtNome.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    Limpar();
                    txtCodigo.setEnabled(true);
                    txtNome.setEnabled(true);
                    txtLogin.setEnabled(true);
                    txtSenha.setEnabled(true);
                    txtDtCadastro.setEnabled(true);
                    txtDtAcesso.setEnabled(true);
                    cboTipo.setEnabled(true);
                    txtCodigo.requestFocusInWindow();

                    //DESATIVANDO COMPONENTES
                    btnEditar.setEnabled(false);
                    btnGravar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                    btnLocalizar.setEnabled(false);
                }
            } else {
                Limpar();
                txtCodigo.setEnabled(true);
                txtNome.setEnabled(true);
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                txtDtCadastro.setEnabled(true);
                txtDtAcesso.setEnabled(true);
                cboTipo.setEnabled(true);
                txtCodigo.requestFocusInWindow();

                //DESATIVANDO COMPONENTES
                btnEditar.setEnabled(false);
                btnGravar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnLocalizar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnNovoKeyPressed

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (txtNome.isEnabled()){
            
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarLogin();
        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
      if (txtNome.isEnabled()) {
            Object[] options = {"Confirmar", "Cancelar"};
            int i = JOptionPane.showOptionDialog(null,
                    "Existe um cadastro em edição. Deseja cancelar este cadastro em andamento? ", "Atenção",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.NO_OPTION) {
                cboUF.requestFocusInWindow();
            } else if (i == JOptionPane.YES_OPTION) { 
                dispose();
            }
      }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Painel_Botao;
    private javax.swing.JPanel Painel_Busca;
    private javax.swing.JPanel Painel_Info;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JComboBox<String> cboTipo;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDtAcesso;
    private javax.swing.JLabel lblDtCadastro;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDtAcesso;
    private javax.swing.JTextField txtDtCadastro;
    private javax.swing.JTextField txtLogin;
    public static javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
