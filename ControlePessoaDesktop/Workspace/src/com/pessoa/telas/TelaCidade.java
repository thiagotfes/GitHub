/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pessoa.telas;

import com.pessoa.dal.ModuloConexao;
import com.sun.org.apache.xml.internal.dtm.DTM;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author thiag
 */
public class TelaCidade extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCidade
     */
    public TelaCidade() {
        initComponents();
        conn = ModuloConexao.conector();
        txtBCodigo.setDocument(new com.pessoa.dal.SomenteNumero(6));
        txtBCidade.setDocument(new com.pessoa.dal.LimitaNrCaracteres(50));
    }

    //método para limpar campos   
    private void Limpar() {
        txtBCodigo.setText(null);
        txtBCidade.setText(null);
    }

    // Método que armazena as informações da tabela tb_pais em uma combobox cboPais
    private void listarPais() {
        String sql = "select nm_pais from tb_pais;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboPais.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Método que armazena as informações da tabela tb_uf em uma combobox cboUF
    private void listarUF() {
        String sql = "select nm_uf from tb_uf;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboUF.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

    //MÉTODO para consultar usuário pelo código
    private void consultarCodigo() {
        String sql = "select * from tb_cidade where cd_cidade=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtBCodigo.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblCompara.setText(rs.getString(1));
                txtBCodigo.setText(rs.getString(2));
                txtBCidade.setText(rs.getString(3));
                cboUF.setSelectedItem(rs.getString(4));
                cboPais.setSelectedItem(rs.getString(5));

                btnPesquisar.setEnabled(false);
                btnNovo.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnGravar.setEnabled(false);
                cboUF.setEnabled(false);
                cboPais.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//MÉTODO para consultar usuário pelo login
    private void consultarNome() {
        String sql = "select * from tb_cidade where nm_cidade=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtBCidade.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblCompara.setText(rs.getString(1));
                txtBCodigo.setText(rs.getString(2));
                txtBCidade.setText(rs.getString(3));
                cboUF.setSelectedItem(rs.getString(4));
                cboPais.setSelectedItem(rs.getString(5));

                btnPesquisar.setEnabled(false);
                btnNovo.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnGravar.setEnabled(false);

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
        String sql = "insert into tb_cidade(cd_cidade,nm_cidade,cidade_uf,cidade_pais) values(?,?,?,?)";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtBCodigo.getText());
            pst.setString(2, txtBCidade.getText());
            pst.setString(3, cboUF.getSelectedItem().toString());
            pst.setString(4, cboPais.getSelectedItem().toString());

            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtBCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Código:' não pode ficar em branco. Verifique!");
                txtBCodigo.requestFocusInWindow();
            } else if (txtBCidade.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Cidade:' não pode ficar em branco. Verifique!");
                txtBCidade.requestFocusInWindow();
            } else {

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Cidade cadastrada com sucesso");
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(false);
                consultarCodigo();
                if (txtBCodigo.getText().isEmpty()) {
                    btnEditar.setEnabled(false);
                }
            }}

        } catch (HeadlessException | SQLException e) {
            String erro = null;
            erro = e.toString();
            JOptionPane.showConfirmDialog(null, erro);
        }

    }

//MÉTODO para alterar um usuário
    private void alterar() {
        String sql = "UPDATE tb_cidade SET cd_cidade=?,nm_cidade=?,cidade_uf=?,cidade_pais=? where id_cidade=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtBCodigo.getText());
            pst.setString(2, txtBCidade.getText());
            pst.setString(3, cboUF.getSelectedItem().toString());
            pst.setString(4, cboPais.getSelectedItem().toString());
            pst.setString(5, lblCompara.getText());

            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtBCidade.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Cidade:' não pode ficar em branco. Verifique!");
                txtBCidade.requestFocusInWindow();
            } else {
                        
            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Dados da cidade alterados com sucesso");
                //Desativar campos
                btnCancelar.setEnabled(true);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(false);
                consultarCodigo();
                if (txtBCodigo.getText().isEmpty()) {
                    btnEditar.setEnabled(false);
                }
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
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta cidade?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_cidade where cd_cidade=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtBCodigo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade excluída com sucesso");
                    Limpar();
                    btnNovo.setEnabled(true);
                    btnPesquisar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnExcluir.setEnabled(false);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

//método para salvar
    private void Salvar() {
        String sql = "Select * From tb_cidade Where cd_cidade=? and  id_cidade=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtBCodigo.getText());
            pst.setString(2, lblCompara.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Cidade já registrada no banco de dados. Deseja alterar os dados desta cidade? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtBCodigo.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    alterar();
                }
            } else {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Deseja confirmar o cadastro desta cidade? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtBCodigo.requestFocusInWindow();
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

        Painel_Busca = new javax.swing.JPanel();
        txtBCodigo = new javax.swing.JTextField();
        txtBCidade = new javax.swing.JTextField();
        lblBCodigo = new javax.swing.JLabel();
        lblBCidade = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Painel_Infos = new javax.swing.JPanel();
        lblPais = new javax.swing.JLabel();
        cboPais = new javax.swing.JComboBox<>();
        lblUF = new javax.swing.JLabel();
        cboUF = new javax.swing.JComboBox<>();
        btnAddPais = new javax.swing.JButton();
        btnAddUf = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLocalizar = new javax.swing.JButton();
        lblCompara = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de cidade");
        setToolTipText("");
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

        Painel_Busca.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtBCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtBCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBCodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBCodigoKeyReleased(evt);
            }
        });

        txtBCidade.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtBCidade.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBCidade.setPreferredSize(new java.awt.Dimension(6, 23));
        txtBCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBCidadeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBCidadeKeyReleased(evt);
            }
        });

        lblBCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblBCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBCodigo.setText("Código:");

        lblBCidade.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblBCidade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBCidade.setText("Cidade:");

        btnPesquisar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Painel_BuscaLayout = new javax.swing.GroupLayout(Painel_Busca);
        Painel_Busca.setLayout(Painel_BuscaLayout);
        Painel_BuscaLayout.setHorizontalGroup(
            Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_BuscaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblBCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );
        Painel_BuscaLayout.setVerticalGroup(
            Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_BuscaLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(Painel_BuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBCidade)
                    .addComponent(txtBCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBCodigo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 914, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        Painel_Infos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPais.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPais.setText("País:");

        cboPais.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboPais.setEnabled(false);

        lblUF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblUF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUF.setText("UF:");

        cboUF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboUF.setEnabled(false);

        btnAddPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/add_icon-icons.com_74429.png"))); // NOI18N
        btnAddPais.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddPais.setMaximumSize(new java.awt.Dimension(21, 21));
        btnAddPais.setMinimumSize(new java.awt.Dimension(21, 21));
        btnAddPais.setPreferredSize(new java.awt.Dimension(21, 21));

        btnAddUf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/add_icon-icons.com_74429.png"))); // NOI18N
        btnAddUf.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddUf.setMaximumSize(new java.awt.Dimension(21, 21));
        btnAddUf.setMinimumSize(new java.awt.Dimension(21, 21));
        btnAddUf.setPreferredSize(new java.awt.Dimension(21, 21));

        javax.swing.GroupLayout Painel_InfosLayout = new javax.swing.GroupLayout(Painel_Infos);
        Painel_Infos.setLayout(Painel_InfosLayout);
        Painel_InfosLayout.setHorizontalGroup(
            Painel_InfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_InfosLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblUF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboUF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddUf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(lblPais, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPais, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddPais, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Painel_InfosLayout.setVerticalGroup(
            Painel_InfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel_InfosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(Painel_InfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Painel_InfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPais)
                        .addComponent(cboPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUF)
                        .addComponent(cboUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddPais, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/add_icon-icons.com_74429.png"))); // NOI18N
        btnNovo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNovo.setMaximumSize(new java.awt.Dimension(21, 21));
        btnNovo.setMinimumSize(new java.awt.Dimension(21, 21));
        btnNovo.setPreferredSize(new java.awt.Dimension(21, 21));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/Cancel_icon-icons.com_73703.png"))); // NOI18N
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setEnabled(false);
        btnCancelar.setMaximumSize(new java.awt.Dimension(45, 45));
        btnCancelar.setMinimumSize(new java.awt.Dimension(45, 45));
        btnCancelar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/editUser.png"))); // NOI18N
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.setEnabled(false);
        btnEditar.setMaximumSize(new java.awt.Dimension(45, 45));
        btnEditar.setMinimumSize(new java.awt.Dimension(45, 45));
        btnEditar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/save.png"))); // NOI18N
        btnGravar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGravar.setEnabled(false);
        btnGravar.setMaximumSize(new java.awt.Dimension(45, 45));
        btnGravar.setMinimumSize(new java.awt.Dimension(45, 45));
        btnGravar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/icon1.png"))); // NOI18N
        btnExcluir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcluir.setEnabled(false);
        btnExcluir.setMaximumSize(new java.awt.Dimension(45, 45));
        btnExcluir.setMinimumSize(new java.awt.Dimension(45, 45));
        btnExcluir.setPreferredSize(new java.awt.Dimension(45, 45));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnLocalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/procura.png"))); // NOI18N
        btnLocalizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLocalizar.setMaximumSize(new java.awt.Dimension(45, 45));
        btnLocalizar.setMinimumSize(new java.awt.Dimension(45, 45));
        btnLocalizar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnLocalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCompara.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCompara.setForeground(java.awt.SystemColor.control);
        lblCompara.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCompara.setText("Código:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblCompara, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Painel_Busca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Painel_Infos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Painel_Busca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Painel_Infos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(lblCompara)
                .addGap(222, 222, 222)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(0, 0, 960, 548);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBCodigoKeyReleased

    }//GEN-LAST:event_txtBCodigoKeyReleased

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        listarUF();
        listarPais();
    }//GEN-LAST:event_formComponentShown

    private void txtBCidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBCidadeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBCidadeKeyReleased

    private void txtBCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBCodigoKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarCodigo();
        }
    }//GEN-LAST:event_txtBCodigoKeyPressed

    private void txtBCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBCidadeKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarNome();
        }
    }//GEN-LAST:event_txtBCidadeKeyPressed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        String cd = txtBCodigo.getText();
        String nm = txtBCidade.getText();
        try {
            if (cd.isEmpty()) {
                if (nm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor inserir uma informação para busca.");
                    txtBCodigo.requestFocusInWindow();
                }
                consultarNome();
            } else {
                consultarCodigo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        Salvar();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // Habilitando os componentes para alteração
        consultarCodigo();
        btnNovo.setEnabled(false);
        btnExcluir.setEnabled(true);
        btnGravar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnPesquisar.setEnabled(false);
        txtBCodigo.setEnabled(true);
        txtBCidade.setEnabled(true);
        cboUF.setEnabled(true);
        cboPais.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Object[] options = {"Confirmar", "Cancelar"};
        int i = JOptionPane.showOptionDialog(null,
                "Confirma o cancelamento da ação atual? ", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == JOptionPane.NO_OPTION) {
            txtBCidade.requestFocusInWindow();
        } else if (i == JOptionPane.YES_OPTION) {
            txtBCodigo.setText(null);
            txtBCidade.setText(null);
            btnNovo.setEnabled(true);
            btnPesquisar.setEnabled(true);

            //Desativar campos
            cboUF.setEnabled(false);
            cboPais.setEnabled(false);
            txtBCidade.setEnabled(true);
            txtBCodigo.setEnabled(true);
            btnCancelar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnGravar.setEnabled(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Abre os campos para novo cadastro de pessoa
        if (cboUF.isEnabled()) {
            Object[] options = {"Confirmar", "Cancelar"};
            int i = JOptionPane.showOptionDialog(null,
                    "Existe um cadastro em edição. Deseja cancelar este cadastro em andamento? ", "Atenção",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.NO_OPTION) {
                cboUF.requestFocusInWindow();
            } else if (i == JOptionPane.YES_OPTION) {
                Limpar();
                txtBCodigo.setEnabled(true);
                txtBCidade.setEnabled(true);
                cboUF.setEnabled(true);
                cboPais.setEnabled(true);
                txtBCodigo.requestFocusInWindow();

                //DESATIVANDO COMPONENTES
                btnEditar.setEnabled(false);
                btnGravar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnPesquisar.setEnabled(false);
                btnLocalizar.setEnabled(false);
            }
        } else {
            Limpar();
            txtBCodigo.setEnabled(true);
            txtBCidade.setEnabled(true);
            cboUF.setEnabled(true);
            cboPais.setEnabled(true);
            txtBCodigo.requestFocusInWindow();

            //DESATIVANDO COMPONENTES
            btnEditar.setEnabled(false);
            btnGravar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnPesquisar.setEnabled(false);
            btnLocalizar.setEnabled(false);
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        //chamar a tela de busca
        TelaBuscaCidade buscacidade = new TelaBuscaCidade();
        buscacidade.setVisible(true);
        
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
     if (cboUF.isEnabled()) {
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
    private javax.swing.JPanel Painel_Busca;
    private javax.swing.JPanel Painel_Infos;
    private javax.swing.JButton btnAddPais;
    private javax.swing.JButton btnAddUf;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    public static javax.swing.JComboBox<String> cboPais;
    public static javax.swing.JComboBox<String> cboUF;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBCidade;
    private javax.swing.JLabel lblBCodigo;
    private javax.swing.JLabel lblCompara;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblUF;
    public static javax.swing.JTextField txtBCidade;
    public static javax.swing.JTextField txtBCodigo;
    // End of variables declaration//GEN-END:variables

}
