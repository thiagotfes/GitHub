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
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author thiag
 */
public class TelaPessoa extends javax.swing.JInternalFrame {

    static boolean IS_MAXIMUM_PROPERTY(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int count = 0;
    String bkCodigo  = null;
    /**
     * Creates new form TelaPessoa
     */
    public TelaPessoa() {
        initComponents();
    
        conn = ModuloConexao.conector();
        txtCodigo.setDocument(new LimitaNroCaracteres(6));
        txtNome.setDocument(new LimitaNroCaracteres(50));
        txtLogradouro.setDocument(new LimitaNroCaracteres(200));
        txtNr.setDocument(new LimitaNroCaracteres(10));
        txtDtCadastro.setDocument(new LimitaNroCaracteres(10));
        txtTelefone.setDocument(new LimitaNroCaracteres(50));
        txtObs.setDocument(new LimitaNroCaracteres(300));

    }

//método para limpar campo de pessoa    
    private void Limpar(){
                txtCodigo.setText(null);
                txtNome.setText(null);
                txtCPFCNPJ.setText(null);
                txtLogradouro.setText(null);
                txtNr.setText(null);
                txtComplemento.setText(null);
                txtCEP.setText(null);
                txtEmail.setText(null);
                txtObs.setText(null);
                txtTelefone.setText(null);
                txtContato.setText(null);
                txtDtCadastro.setText(null);
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

// Método que armazena as informações da tabela tb_cidade em uma combobox cboCidade
    private void listarCidade() {
        String sql = "select nm_cidade from tb_cidade;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboCidade.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

// Método que armazena as informações da tabela tb_bairro em uma combobox cboBairro
    private void listarBairro() {
        String sql = "select nm_bairro from tb_bairro;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboBairro.addItem(rs.getString(1));
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

// Método que armazena as informações da tabela tb_tppessoa em uma combobox cboClassificação
    private void listarClassificacao() {
        String sql = "select nm_tppessoa from tb_tppessoa;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboClassificacao.addItem(rs.getString(1));
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

// Método que armazena as informações da tabela tb_genero em uma combobox cboGenero
private void listarGenero() {
        String sql = "select nm_genero from tb_genero;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboGenero.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

// Método que armazena as informações da tabela tb_genero em uma combobox cboGenero
private void listarEstadoCivil() {
        String sql = "select nm_estadocivil from tb_estadocivil;";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboEstadoCivil.addItem(rs.getString(1));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//Método para limitar caractere de campo    
public class LimitaNroCaracteres extends PlainDocument {

        private int iMaxLength;

        public LimitaNroCaracteres(int maxlen) {
            super();
            iMaxLength = maxlen;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr)
                throws BadLocationException {
            //if (s == null) return;
            if (iMaxLength <= 0) // aceitara qualquer no. de caracteres
            {
                super.insertString(offset, str.toUpperCase(), attr);
                return;
            }
            int ilen = (getLength() + str.length());
            if (ilen <= iMaxLength) // se o comprimento final for menor...
            {
                super.insertString(offset, str.toUpperCase(), attr);   // ...aceita str
            }
        }
    }

//Método para buscar cep
public void buscaCep() {
        //Faz a busca para o cep 58043-280
        WebServiceCep webServiceCep = WebServiceCep.searchCep(txtCEP.getText());
        //A ferramenta de busca ignora qualquer caracter que n?o seja n?mero.

        //caso a busca ocorra bem, imprime os resultados.
        if (webServiceCep.wasSuccessful()) {
            txtLogradouro.setText(webServiceCep.getLogradouroFull());
            cboCidade.setSelectedItem(webServiceCep.getCidade());
            cboBairro.setSelectedItem(webServiceCep.getBairro());
            cboUF.setSelectedItem(webServiceCep.getUf());
            System.out.println("Cep: " + webServiceCep.getCep());
            System.out.println("Logradouro: " + webServiceCep.getLogradouroFull());
            System.out.println("Bairro: " + webServiceCep.getBairro());
            System.out.println("Cidade: "
                    + webServiceCep.getCidade() + "/" + webServiceCep.getUf());

            //caso haja problemas imprime as exce??es.
        } else {
            JOptionPane.showMessageDialog(null, "Erro numero: " + webServiceCep.getResulCode());

            JOptionPane.showMessageDialog(null, "Descrição do erro: " + webServiceCep.getResultText());
        }
    }

//MÉTODO para consultar usuário pelo código
private void consultarCodigo() {
        String sql = "select * from tb_pessoa where cd_pessoa=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblId.setText(rs.getString(1));
                txtCodigo.setText(rs.getString(2));
                txtCPFCNPJ.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtLogradouro.setText(rs.getString(5));
                txtNr.setText(rs.getString(6));
                txtComplemento.setText(rs.getString(7));
                cboCidade.setSelectedItem(rs.getString(8));
                cboUF.setSelectedItem(rs.getString(9));
                cboBairro.setSelectedItem(rs.getString(10));
                cboPais.setSelectedItem(rs.getString(11));
                txtCEP.setText(rs.getString(12));
                txtEmail.setText(rs.getString(13));
                cboGenero.setSelectedItem(rs.getString(15));
                cboEstadoCivil.setSelectedItem(rs.getString(16));
                txtObs.setText(rs.getString(17));
                txtTelefone.setText(rs.getString(18));
                txtContato.setText(rs.getString(19));
                cboClassificacao.setSelectedItem(rs.getString(20));
                txtDtCadastro.setText(rs.getString(21));

                lblCalculaPessoa.setText(rs.getString(14));
                String tppessoa = lblCalculaPessoa.getText();
                if (tppessoa == "Pessoa Jurídica") {
                    ckJuridica.setSelected(true);
                    ckFisica.setSelected(false);
                }
                if (tppessoa == "Pessoa Física") {
                    ckJuridica.setSelected(false);
                    ckFisica.setSelected(true);
                }
                txtCodigo.setEnabled(false);
                txtNome.setEnabled(false);
                txtCPFCNPJ.setEnabled(false);
                                
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                        btnNovo.setEnabled(false);
                        
            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }
 
//MÉTODO para consultar pessoa pelo nome
private void consultarNome() {
        String sql = "select * from tb_pessoa where nm_pessoa=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblId.setText(rs.getString(1));
                txtCodigo.setText(rs.getString(2));
                txtCPFCNPJ.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtLogradouro.setText(rs.getString(5));
                txtNr.setText(rs.getString(6));
                txtComplemento.setText(rs.getString(7));
                cboCidade.setSelectedItem(rs.getString(8));
                cboUF.setSelectedItem(rs.getString(9));
                cboBairro.setSelectedItem(rs.getString(10));
                cboPais.setSelectedItem(rs.getString(11));
                txtCEP.setText(rs.getString(12));
                txtEmail.setText(rs.getString(13));
                cboGenero.setSelectedItem(rs.getString(15));
                cboEstadoCivil.setSelectedItem(rs.getString(16));
                txtObs.setText(rs.getString(17));
                txtTelefone.setText(rs.getString(18));
                txtContato.setText(rs.getString(19));
                cboClassificacao.setSelectedItem(rs.getString(20));
                txtDtCadastro.setText(rs.getString(21));
                String tppessoa;
                lblCalculaPessoa.setText(rs.getString(14));
                tppessoa = lblCalculaPessoa.getText();
                if (tppessoa == "Pessoa Jurídica") {
                    ckJuridica.setSelected(true);
                    ckFisica.setSelected(false);
                }
                if (tppessoa == "Pessoa Física") {
                    ckJuridica.setSelected(false);
                    ckFisica.setSelected(true);
                }
                txtCodigo.setEnabled(false);
                txtNome.setEnabled(false);
                txtCPFCNPJ.setEnabled(false);
                                
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                        btnNovo.setEnabled(false);
                        
            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//MÉTODO para consultar usuário pelo CPF ou CNPJ
private void consultarCPFCNPJ() {
        String sql = "select * from tb_pessoa where cpf_cnpj=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCPFCNPJ.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                lblId.setText(rs.getString(1));
                txtCodigo.setText(rs.getString(2));
                txtCPFCNPJ.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtLogradouro.setText(rs.getString(5));
                txtNr.setText(rs.getString(6));
                txtComplemento.setText(rs.getString(7));
                cboCidade.setSelectedItem(rs.getString(8));
                cboUF.setSelectedItem(rs.getString(9));
                cboBairro.setSelectedItem(rs.getString(10));
                cboPais.setSelectedItem(rs.getString(11));
                txtCEP.setText(rs.getString(12));
                txtEmail.setText(rs.getString(13));
                cboGenero.setSelectedItem(rs.getString(15));
                cboEstadoCivil.setSelectedItem(rs.getString(16));
                txtObs.setText(rs.getString(17));
                txtTelefone.setText(rs.getString(18));
                txtContato.setText(rs.getString(19));
                cboClassificacao.setSelectedItem(rs.getString(20));
                txtDtCadastro.setText(rs.getString(21));

                lblCalculaPessoa.setText(rs.getString(14));
                String tppessoa = lblCalculaPessoa.getText();
                if (tppessoa == "Pessoa Jurídica") {
                    ckJuridica.setSelected(true);
                    ckFisica.setSelected(false);
                }
                if (tppessoa == "Pessoa Física") {
                    ckJuridica.setSelected(false);
                    ckFisica.setSelected(true);
                }
                txtCodigo.setEnabled(false);
                txtNome.setEnabled(false);
                txtCPFCNPJ.setEnabled(false);
                
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                        btnNovo.setEnabled(false);
                        
            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }
    
//MÉTODO para inserir um novo registro na tabela tb_pessoa    
private void adicionar() {
        String sql = "insert into tb_pessoa(cd_pessoa,cpf_cnpj,nm_pessoa,end_pessoa,nrend_pessoa,comend_pessoa,cid_pessoa,uf_pessoa,bairro_pessoa,pais_pessoa,cep_pessoa,email_pessoa,tp_pessoa,genero_pessoa,estadocivil_pessoa,obs_pessoa,telefone,contato,classificacao,dtcadastro) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtCPFCNPJ.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtLogradouro.getText().toUpperCase());
            pst.setString(5, txtNr.getText());
            pst.setString(6, txtComplemento.getText().toUpperCase());
            pst.setString(7, cboCidade.getSelectedItem().toString());
            pst.setString(8, cboUF.getSelectedItem().toString());
            pst.setString(9, cboBairro.getSelectedItem().toString());
            pst.setString(10, cboPais.getSelectedItem().toString());
            pst.setString(11, txtCEP.getText());
            pst.setString(12, txtEmail.getText().toLowerCase());
            if (ckFisica.isSelected()) {
                String F = "Pessoa Física";
                pst.setString(13, F.toUpperCase());
            } else if (ckJuridica.isSelected()) {
                String J = "Pessoa Jurídica";
                pst.setString(13, J.toUpperCase());
            }
            pst.setString(14, cboGenero.getSelectedItem().toString());
            pst.setString(15, cboEstadoCivil.getSelectedItem().toString());
            pst.setString(16, txtObs.getText().toUpperCase());
            pst.setString(17, txtTelefone.getText());
            pst.setString(18, txtContato.getText().toUpperCase());
            pst.setString(19, cboClassificacao.getSelectedItem().toString());
            pst.setString(20, txtDtCadastro.getText());

            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Código:' não pode ficar em branco. Verifique!");
                txtCodigo.requestFocusInWindow();
            } else if (txtCPFCNPJ.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'CPF/CNPJ:' não pode ficar em branco. Verifique!");
                txtCPFCNPJ.requestFocusInWindow();
            } else if (txtNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Nome:' não pode ficar em branco. Verifique!");
                txtNome.requestFocusInWindow();
            } else if (txtLogradouro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Logradouro:' na guia 'Endereço' não pode ficar em branco. Verifique!");
                txtLogradouro.requestFocusInWindow();
            } else if (txtNr.getText().isEmpty()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "O campo 'nº:' na guia 'Endereço' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtNr.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    //VOlta a fazer a verificação nos campos restantes 
                    if (txtComplemento.getText().isEmpty()) {
                        Object[] options1 = {"Confirmar", "Cancelar"};
                        int j = JOptionPane.showOptionDialog(null,
                                "O campo 'Complemento:' na guia 'Endereço' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                options1, options1[0]);
                        if (j == JOptionPane.NO_OPTION) {
                            txtComplemento.requestFocusInWindow();
                        } else if (j == JOptionPane.YES_OPTION) {
                            //VOlta a fazer a verificação nos campos restantes        
                            if (txtCEP.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "O campo 'CEP:' na guia 'Endereço' não pode ficar em branco. Verifique!");
                                txtCEP.requestFocusInWindow();
                            } else if (txtEmail.getText().isEmpty()) {
                                Object[] options2 = {"Confirmar", "Cancelar"};
                                int l = JOptionPane.showOptionDialog(null,
                                        "O campo 'E-mail:' na guia 'Dados gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                        options2, options2[0]);
                                if (l == JOptionPane.NO_OPTION) {
                                    txtEmail.requestFocusInWindow();
                                } else if (l == JOptionPane.YES_OPTION) {
                                    //VOlta a fazer a verificação nos campos restantes  
                                    if (txtTelefone.getText().isEmpty()) {
                                        Object[] options3 = {"Confirmar", "Cancelar"};
                                        int m = JOptionPane.showOptionDialog(null,
                                                "O campo 'Telefone:' na guia 'Dados Gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                options3, options3[0]);
                                        if (m == JOptionPane.NO_OPTION) {
                                            txtTelefone.requestFocusInWindow();
                                        } else if (m == JOptionPane.YES_OPTION) {
                                            //VOlta a fazer a verificação nos campos restantes
                                            if (txtContato.getText().isEmpty()) {
                                                Object[] options4 = {"Confirmar", "Cancelar"};
                                                int n = JOptionPane.showOptionDialog(null,
                                                        "O campo 'Contato:' na guia 'Dados Gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                        options4, options4[0]);
                                                if (n == JOptionPane.NO_OPTION) {
                                                    txtContato.requestFocusInWindow();
                                                } else if (n == JOptionPane.YES_OPTION) {
                                                    adicionar();
                                                    backupPessoa();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Pessoa cadastrada com sucesso!");
                btnCancelar.setEnabled(false);
                btnExcluir.setEnabled(true);
                btnEditar.setEnabled(true);
                btnGravar.setEnabled(false);
                Limpar();
            }

        } catch (HeadlessException | SQLException e) {
            String erro = null;
            erro = e.toString();
            JOptionPane.showConfirmDialog(null, erro);
        }

    }
    
//método para alterar o cadastro de uma pessoa
private void alterar(){
        String sql = "UPDATE tb_pessoa SET cd_pessoa=?,cpf_cnpj=?,nm_pessoa=?,end_pessoa=?,nrend_pessoa=?,comend_pessoa=?,cid_pessoa=?,uf_pessoa=?,bairro_pessoa=?,pais_pessoa=?,cep_pessoa=?,email_pessoa=?,tp_pessoa=?,genero_pessoa=?,estadocivil_pessoa=?,obs_pessoa=?,telefone=?,contato=?,classificacao=?,dtcadastro=? where idpessoa=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtCPFCNPJ.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtLogradouro.getText().toUpperCase());
            pst.setString(5, txtNr.getText());
            pst.setString(6, txtComplemento.getText().toUpperCase());
            pst.setString(7, cboCidade.getSelectedItem().toString());
            pst.setString(8, cboUF.getSelectedItem().toString());
            pst.setString(9, cboBairro.getSelectedItem().toString());
            pst.setString(10, cboPais.getSelectedItem().toString());
            pst.setString(11, txtCEP.getText());
            pst.setString(12, txtEmail.getText().toLowerCase());
            if (ckFisica.isSelected()) {
                String F = "Pessoa Física";
                pst.setString(13, F.toUpperCase());
            } else if (ckJuridica.isSelected()) {
                String J = "Pessoa Jurídica";
                pst.setString(13, J.toUpperCase());
            }
            pst.setString(14, cboGenero.getSelectedItem().toString());
            pst.setString(15, cboEstadoCivil.getSelectedItem().toString());
            pst.setString(16, txtObs.getText().toUpperCase());
            pst.setString(17, txtTelefone.getText());
            pst.setString(18, txtContato.getText().toUpperCase());
            pst.setString(19, cboClassificacao.getSelectedItem().toString());
            pst.setString(20, txtDtCadastro.getText());
            pst.setString(21, lblId.getText());
            
            //VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS    
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Código:' não pode ficar em branco. Verifique!");
                txtCodigo.requestFocusInWindow();
            } else if (txtCPFCNPJ.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'CPF/CNPJ:' não pode ficar em branco. Verifique!");
                txtCPFCNPJ.requestFocusInWindow();
            } else if (txtNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Nome:' não pode ficar em branco. Verifique!");
                txtNome.requestFocusInWindow();
            } else if (txtLogradouro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo 'Logradouro:' na guia 'Endereço' não pode ficar em branco. Verifique!");
                txtLogradouro.requestFocusInWindow();
            } else if (txtNr.getText().isEmpty()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "O campo 'nº:' na guia 'Endereço' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtNr.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                    //VOlta a fazer a verificação nos campos restantes 
                    if (txtComplemento.getText().isEmpty()) {
                        Object[] options1 = {"Confirmar", "Cancelar"};
                        int j = JOptionPane.showOptionDialog(null,
                                "O campo 'Complemento:' na guia 'Endereço' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                options1, options1[0]);
                        if (j == JOptionPane.NO_OPTION) {
                            txtComplemento.requestFocusInWindow();
                        } else if (j == JOptionPane.YES_OPTION) {
                            //VOlta a fazer a verificação nos campos restantes        
                            if (txtCEP.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "O campo 'CEP:' na guia 'Endereço' não pode ficar em branco. Verifique!");
                                txtCEP.requestFocusInWindow();
                            } else if (txtEmail.getText().isEmpty()) {
                                Object[] options2 = {"Confirmar", "Cancelar"};
                                int l = JOptionPane.showOptionDialog(null,
                                        "O campo 'E-mail:' na guia 'Dados gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                        options2, options2[0]);
                                if (l == JOptionPane.NO_OPTION) {
                                    txtEmail.requestFocusInWindow();
                                } else if (l == JOptionPane.YES_OPTION) {
                                    //VOlta a fazer a verificação nos campos restantes  
                                    if (txtTelefone.getText().isEmpty()) {
                                        Object[] options3 = {"Confirmar", "Cancelar"};
                                        int m = JOptionPane.showOptionDialog(null,
                                                "O campo 'Telefone:' na guia 'Dados Gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                options3, options3[0]);
                                        if (m == JOptionPane.NO_OPTION) {
                                            txtTelefone.requestFocusInWindow();
                                        } else if (m == JOptionPane.YES_OPTION) {
                                            //VOlta a fazer a verificação nos campos restantes
                                            if (txtContato.getText().isEmpty()) {
                                                Object[] options4 = {"Confirmar", "Cancelar"};
                                                int n = JOptionPane.showOptionDialog(null,
                                                        "O campo 'Contato:' na guia 'Dados Gerais' está em branco. Deseja gravar assim mesmo? ", "Atenção",
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                        options4, options4[0]);
                                                if (n == JOptionPane.NO_OPTION) {
                                                    txtContato.requestFocusInWindow();
                                                } else if (n == JOptionPane.YES_OPTION) {
                                                    alterar();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            int adicionado = pst.executeUpdate();
            if(adicionado >0){
                JOptionPane.showMessageDialog(null,"Dados da pessoa alterados com sucesso!");
                Limpar();
            //Desativar campos 
            cboClassificacao.setEnabled(false);
            cboGenero.setEnabled(false);
            cboEstadoCivil.setEnabled(false);
            cboBairro.setEnabled(false);
            cboCidade.setEnabled(false);
            cboPais.setEnabled(false);
            cboUF.setEnabled(false);
            txtTelefone.setEnabled(false);
            txtContato.setEnabled(false);
            txtEmail.setEnabled(false);
            txtObs.setEnabled(false);
            txtLogradouro.setEnabled(false);
            txtCEP.setEnabled(false);
            txtNr.setEnabled(false);
            txtComplemento.setEnabled(false);
            txtDtCadastro.setEnabled(false);
            ckFisica.setEnabled(false);
            ckJuridica.setEnabled(false);
            //Tratamento dos botões
            btnNovo.setEnabled(true);
            btnLocalizar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnGravar.setEnabled(false);
                
            } 
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }

//MÉTODOS BACKUP DE PESSOA
private void backupPessoa(){
    String sql = "insert into bkpessoa(cd_pessoa,cpf_cnpj,nm_pessoa,end_pessoa,nrend_pessoa,comend_pessoa,cid_pessoa,uf_pessoa,bairro_pessoa,pais_pessoa,cep_pessoa,email_pessoa,tp_pessoa,genero_pessoa,estadocivil_pessoa,obs_pessoa,telefone,contato,classificacao,dtcadastro) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtCPFCNPJ.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtLogradouro.getText().toUpperCase());
            pst.setString(5, txtNr.getText());
            pst.setString(6, txtComplemento.getText().toUpperCase());
            pst.setString(7, cboCidade.getSelectedItem().toString());
            pst.setString(8, cboUF.getSelectedItem().toString());
            pst.setString(9, cboBairro.getSelectedItem().toString());
            pst.setString(10, cboPais.getSelectedItem().toString());
            pst.setString(11, txtCEP.getText());
            pst.setString(12, txtEmail.getText().toLowerCase());
            if (ckFisica.isSelected()) {
                String F = "Pessoa Física";
                pst.setString(13, F.toUpperCase());
            } else if (ckJuridica.isSelected()) {
                String J = "Pessoa Jurídica";
                pst.setString(13, J.toUpperCase());
            }
            pst.setString(14, cboGenero.getSelectedItem().toString());
            pst.setString(15, cboEstadoCivil.getSelectedItem().toString());
            pst.setString(16, txtObs.getText().toUpperCase());
            pst.setString(17, txtTelefone.getText());
            pst.setString(18, txtContato.getText().toUpperCase());
            pst.setString(19, cboClassificacao.getSelectedItem().toString());
            pst.setString(20, txtDtCadastro.getText());

            int adicionado = pst.executeUpdate();

        } catch (HeadlessException | SQLException e) {
            String erro = null;
            erro = e.toString();
            JOptionPane.showConfirmDialog(null, erro);
        }

    }
private void updatebkPessoa(){
     String sql = "UPDATE bkpessoa SET cd_pessoa=?,cpf_cnpj=?,nm_pessoa=?,end_pessoa=?,nrend_pessoa=?,comend_pessoa=?,cid_pessoa=?,uf_pessoa=?,bairro_pessoa=?,pais_pessoa=?,cep_pessoa=?,email_pessoa=?,tp_pessoa=?,genero_pessoa=?,estadocivil_pessoa=?,obs_pessoa=?,telefone=?,contato=?,classificacao=?,dtcadastro=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtCPFCNPJ.getText());
            pst.setString(3, txtNome.getText().toUpperCase());
            pst.setString(4, txtLogradouro.getText().toUpperCase());
            pst.setString(5, txtNr.getText());
            pst.setString(6, txtComplemento.getText().toUpperCase());
            pst.setString(7, cboCidade.getSelectedItem().toString());
            pst.setString(8, cboUF.getSelectedItem().toString());
            pst.setString(9, cboBairro.getSelectedItem().toString());
            pst.setString(10, cboPais.getSelectedItem().toString());
            pst.setString(11, txtCEP.getText());
            pst.setString(12, txtEmail.getText().toLowerCase());
            if (ckFisica.isSelected()) {
                String F = "Pessoa Física";
                pst.setString(13, F.toUpperCase());
            } else if (ckJuridica.isSelected()) {
                String J = "Pessoa Jurídica";
                pst.setString(13, J.toUpperCase());
            }
            pst.setString(14, cboGenero.getSelectedItem().toString());
            pst.setString(15, cboEstadoCivil.getSelectedItem().toString());
            pst.setString(16, txtObs.getText().toUpperCase());
            pst.setString(17, txtTelefone.getText());
            pst.setString(18, txtContato.getText().toUpperCase());
            pst.setString(19, cboClassificacao.getSelectedItem().toString());
            pst.setString(20, txtDtCadastro.getText());
            
            int adicionado = pst.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }
    
}
private void delbkPessoa(){
    //método para confirmar a exclusão
        
        String sql = "delete from bkpessoa where cd_pessoa=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,recupera.getText());
            if (count > 0) {
            pst.executeUpdate();
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
}
private void contabkPessoa(){
    String sql = "SELECT COUNT(*) as count FROM BKPESSOA  where cd_pessoa =?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            while (rs.next()){
                count = rs.getInt("count");  
            } 
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex);
        }
}
private void backupExec(){
    
        String sql = "select * from bkpessoa where cd_pessoa=?";
        btnGravar.setEnabled(true);
        btnLocalizar.setEnabled(false);
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(false);
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, recupera.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Limpar();
                txtCodigo.setText(rs.getString(2));
                txtCPFCNPJ.setText(rs.getString(3));
                txtNome.setText(rs.getString(4));
                txtLogradouro.setText(rs.getString(5));
                txtNr.setText(rs.getString(6));
                txtComplemento.setText(rs.getString(7));
                cboCidade.setSelectedItem(rs.getString(8));
                cboUF.setSelectedItem(rs.getString(9));
                cboBairro.setSelectedItem(rs.getString(10));
                cboPais.setSelectedItem(rs.getString(11));
                txtCEP.setText(rs.getString(12));
                txtEmail.setText(rs.getString(13));
                cboGenero.setSelectedItem(rs.getString(15));
                cboEstadoCivil.setSelectedItem(rs.getString(16));
                txtObs.setText(rs.getString(17));
                txtTelefone.setText(rs.getString(18));
                txtContato.setText(rs.getString(19));
                cboClassificacao.setSelectedItem(rs.getString(20));
                txtDtCadastro.setText(rs.getString(21));

                lblCalculaPessoa.setText(rs.getString(14));
                String tppessoa = lblCalculaPessoa.getText();
                if (tppessoa == "Pessoa Jurídica") {
                    ckJuridica.setSelected(true);
                    ckFisica.setSelected(false);
                }
                if (tppessoa == "Pessoa Física") {
                    ckJuridica.setSelected(false);
                    ckFisica.setSelected(true);
                }
                txtCodigo.setEnabled(false);
                txtNome.setEnabled(false);
                txtCPFCNPJ.setEnabled(false);
                                
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnLocalizar.setEnabled(false);
                        btnCancelar.setEnabled(true);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(false);
                        btnNovo.setEnabled(false);
                        
                
                        
            } else {
                JOptionPane.showMessageDialog(null, "Código não encontrado.");
                Limpar();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
    }

//método para excluir o cadastro e uma pessoa
private void Excluir(){
    //método para confirmar a exclusão
    int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja remover esta pessoa?", "Atenção", JOptionPane.   YES_NO_OPTION);
    if (confirma == JOptionPane.YES_OPTION){
        String sql = "delete from tb_pessoa where cd_pessoa=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,txtCodigo.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Pessoa excluída com sucesso");
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
        String sql = "Select * From tb_pessoa Where cd_pessoa=?";
        try {
        pst = conn.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Pessoa já registrada no banco de dados. Deseja alterar os dados desta pessoa? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                        alterar();
                        updatebkPessoa();
                }
            } else {
                Object[] options = {"Confirmar", "Cancelar"};
                int i = JOptionPane.showOptionDialog(null,
                        "Deseja confirmar o cadastro desta pessoa? ", "Atenção",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocusInWindow();
                } else if (i == JOptionPane.YES_OPTION) {
                        adicionar();
                        delbkPessoa();
                }
            }
                btnNovo.setEnabled(true);
                btnLocalizar.setEnabled(true);
                btnCancelar.setEnabled(false);
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnGravar.setEnabled(false);
                
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
    }
    
}    

//======================================================================

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabDados = new javax.swing.JTabbedPane();
        DadosGerais_Panel = new javax.swing.JPanel();
        cboGenero = new javax.swing.JComboBox<>();
        lblGenero = new javax.swing.JLabel();
        cboEstadoCivil = new javax.swing.JComboBox<>();
        lblEstadoCivil = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter telefone = new javax.swing.text.MaskFormatter("(##) #####-####");

            txtTelefone = new javax.swing.JFormattedTextField(telefone);
        }catch(Exception e){
        }
        lblEmail = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        txtObs.setDocument(new LimitaNroCaracteres(300));
        lblObs = new javax.swing.JLabel();
        lblClassificacao = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        lblContato = new javax.swing.JLabel();
        txtContato = new javax.swing.JTextField();
        txtContato.setDocument(new LimitaNroCaracteres(50));
        txtEmail = new javax.swing.JTextField();
        txtEmail.setDocument(new LimitaNroCaracteres(100));
        cboClassificacao = new javax.swing.JComboBox<>();
        recupera = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        Endereco_Panel = new javax.swing.JPanel();
        txtLogradouro = new javax.swing.JTextField();
        txtLogradouro.setDocument(new LimitaNroCaracteres(200));
        lblLogradouro = new javax.swing.JLabel();
        txtNr = new javax.swing.JTextField();
        txtNr.setDocument(new SomenteNumero(6));
        cboBairro = new javax.swing.JComboBox<>();
        lblBairro = new javax.swing.JLabel();
        cboCidade = new javax.swing.JComboBox<>();
        lblCidade = new javax.swing.JLabel();
        cboPais = new javax.swing.JComboBox<>();
        lblPais = new javax.swing.JLabel();
        lblCep = new javax.swing.JLabel();
        cboUF = new javax.swing.JComboBox<>();
        lblUF = new javax.swing.JLabel();
        btnBuscaCep = new javax.swing.JButton();
        lblNr = new javax.swing.JLabel();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        txtCEP = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter cep = new javax.swing.text.MaskFormatter("#####-###");

            txtCEP = new javax.swing.JFormattedTextField(cep);
        }catch(Exception e){
        }
        lblCalculaPessoa = new javax.swing.JLabel();
        Panel_Botoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnLocalizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnRec = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCodigo.setDocument(new SomenteNumero(6));
        lblDtCadastro = new javax.swing.JLabel();
        txtDtCadastro = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data = new javax.swing.text.MaskFormatter("##/##/####");

            txtDtCadastro = new javax.swing.JFormattedTextField(data);
        }catch(Exception e){
        }
        txtNome = new javax.swing.JTextField();
        txtNome.setDocument(new LimitaNroCaracteres(50));
        lblNome = new javax.swing.JLabel();
        lblCPFCNPJ = new javax.swing.JLabel();
        txtCPFCNPJ = new javax.swing.JTextField();
        txtCPFCNPJ.setDocument(new SomenteNumero(15));
        ckFisica = new javax.swing.JRadioButton();
        ckJuridica = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Pessoa");
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(960, 548));
        setMinimumSize(new java.awt.Dimension(960, 548));
        setPreferredSize(new java.awt.Dimension(960, 548));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
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
                formInternalFrameOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        tabDados.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tabDados.setMaximumSize(new java.awt.Dimension(982, 287));
        tabDados.setMinimumSize(new java.awt.Dimension(982, 287));

        DadosGerais_Panel.setMaximumSize(new java.awt.Dimension(982, 287));
        DadosGerais_Panel.setMinimumSize(new java.awt.Dimension(982, 287));

        cboGenero.setEnabled(false);
        cboGenero.setNextFocusableComponent(cboEstadoCivil);

        lblGenero.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblGenero.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGenero.setText("Gênero:");

        cboEstadoCivil.setEnabled(false);
        cboEstadoCivil.setNextFocusableComponent(txtObs);

        lblEstadoCivil.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstadoCivil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEstadoCivil.setText("Estado civil:");

        txtTelefone.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTelefone.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTelefone.setEnabled(false);
        txtTelefone.setNextFocusableComponent(txtContato);

        lblEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText("E-mail:");

        txtObs.setColumns(20);
        txtObs.setRows(5);
        txtObs.setEnabled(false);
        txtObs.setNextFocusableComponent(txtCEP);
        jScrollPane1.setViewportView(txtObs);

        lblObs.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblObs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs.setText("Observação:");

        lblClassificacao.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblClassificacao.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClassificacao.setText("Classificação:");

        lblTelefone.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTelefone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefone.setText("Telefone:");

        lblContato.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblContato.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContato.setText("Pessoa de contato:");

        txtContato.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtContato.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtContato.setEnabled(false);
        txtContato.setNextFocusableComponent(txtEmail);

        txtEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail.setEnabled(false);
        txtEmail.setNextFocusableComponent(cboGenero);

        cboClassificacao.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboClassificacao.setEnabled(false);
        cboClassificacao.setNextFocusableComponent(txtTelefone);

        recupera.setForeground(java.awt.SystemColor.control);
        recupera.setText("jLabel1");

        lblId.setForeground(java.awt.SystemColor.control);

        javax.swing.GroupLayout DadosGerais_PanelLayout = new javax.swing.GroupLayout(DadosGerais_Panel);
        DadosGerais_Panel.setLayout(DadosGerais_PanelLayout);
        DadosGerais_PanelLayout.setHorizontalGroup(
            DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblContato, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContato, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                        .addComponent(lblGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(141, 141, 141)
                                        .addComponent(lblEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosGerais_PanelLayout.createSequentialGroup()
                                        .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(46, 46, 46)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblObs, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(recupera, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblId)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        DadosGerais_PanelLayout.setVerticalGroup(
            DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObs)
                    .addComponent(lblClassificacao)
                    .addComponent(cboClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefone)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContato)
                            .addComponent(txtContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGenero)
                            .addComponent(lblEstadoCivil))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DadosGerais_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DadosGerais_PanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recupera)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(lblId)
                .addContainerGap())
        );

        tabDados.addTab("Dados Gerais", DadosGerais_Panel);

        txtLogradouro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtLogradouro.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtLogradouro.setEnabled(false);
        txtLogradouro.setNextFocusableComponent(txtNr);

        lblLogradouro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblLogradouro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLogradouro.setText("Logradouro:");

        txtNr.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNr.setEnabled(false);
        txtNr.setNextFocusableComponent(txtComplemento);

        cboBairro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboBairro.setEnabled(false);
        cboBairro.setNextFocusableComponent(cboCidade);

        lblBairro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblBairro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBairro.setText("Bairro:");

        cboCidade.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboCidade.setEnabled(false);
        cboCidade.setNextFocusableComponent(cboUF);

        lblCidade.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCidade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCidade.setText("Cidade:");

        cboPais.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboPais.setEnabled(false);
        cboPais.setNextFocusableComponent(btnGravar);

        lblPais.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPais.setText("País:");

        lblCep.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCep.setText("CEP:");

        cboUF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboUF.setEnabled(false);
        cboUF.setNextFocusableComponent(cboPais);

        lblUF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblUF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUF.setText("UF:");

        btnBuscaCep.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnBuscaCep.setText("Busca CEP");
        btnBuscaCep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscaCep.setNextFocusableComponent(txtLogradouro);
        btnBuscaCep.setPreferredSize(new java.awt.Dimension(73, 24));
        btnBuscaCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaCepActionPerformed(evt);
            }
        });

        lblNr.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNr.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNr.setText("nº:");

        lblComplemento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblComplemento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblComplemento.setText("Complemento:");

        txtComplemento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtComplemento.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtComplemento.setEnabled(false);
        txtComplemento.setNextFocusableComponent(cboBairro);

        txtCEP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCEP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCEP.setEnabled(false);
        txtCEP.setNextFocusableComponent(btnBuscaCep);

        javax.swing.GroupLayout Endereco_PanelLayout = new javax.swing.GroupLayout(Endereco_Panel);
        Endereco_Panel.setLayout(Endereco_PanelLayout);
        Endereco_PanelLayout.setHorizontalGroup(
            Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Endereco_PanelLayout.createSequentialGroup()
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Endereco_PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Endereco_PanelLayout.createSequentialGroup()
                                .addComponent(lblCep, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscaCep, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Endereco_PanelLayout.createSequentialGroup()
                                .addComponent(lblComplemento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Endereco_PanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNr, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNr, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Endereco_PanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPais, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPais, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblUF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboUF, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        Endereco_PanelLayout.setVerticalGroup(
            Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Endereco_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCep)
                    .addComponent(btnBuscaCep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogradouro)
                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNr)
                    .addComponent(txtNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComplemento)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBairro)
                    .addComponent(cboBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCidade)
                    .addComponent(cboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUF)
                    .addComponent(cboUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(Endereco_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(cboPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        tabDados.addTab("Endereço", Endereco_Panel);

        lblCalculaPessoa.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        lblCalculaPessoa.setForeground(java.awt.SystemColor.control);

        Panel_Botoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Panel_Botoes.setPreferredSize(new java.awt.Dimension(835, 61));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/addUser.png"))); // NOI18N
        btnNovo.setToolTipText("Novo cadastro");
        btnNovo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNovo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNovo.setMaximumSize(new java.awt.Dimension(65, 54));
        btnNovo.setMinimumSize(new java.awt.Dimension(65, 54));
        btnNovo.setPreferredSize(new java.awt.Dimension(65, 60));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnLocalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/searchUser.png"))); // NOI18N
        btnLocalizar.setToolTipText("Localizar cadastro");
        btnLocalizar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLocalizar.setMaximumSize(new java.awt.Dimension(65, 60));
        btnLocalizar.setMinimumSize(new java.awt.Dimension(65, 60));
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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/closeUser.png"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar edição");
        btnCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setEnabled(false);
        btnCancelar.setMaximumSize(new java.awt.Dimension(65, 60));
        btnCancelar.setMinimumSize(new java.awt.Dimension(65, 60));
        btnCancelar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/editUser.png"))); // NOI18N
        btnEditar.setToolTipText("Editar cadastro");
        btnEditar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.setEnabled(false);
        btnEditar.setMaximumSize(new java.awt.Dimension(65, 60));
        btnEditar.setMinimumSize(new java.awt.Dimension(65, 60));
        btnEditar.setPreferredSize(new java.awt.Dimension(65, 60));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/del.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir cadastro");
        btnExcluir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcluir.setEnabled(false);
        btnExcluir.setMaximumSize(new java.awt.Dimension(65, 60));
        btnExcluir.setMinimumSize(new java.awt.Dimension(65, 60));
        btnExcluir.setPreferredSize(new java.awt.Dimension(65, 60));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pessoa/icone/save.png"))); // NOI18N
        btnGravar.setToolTipText("Gravar cadastro");
        btnGravar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGravar.setEnabled(false);
        btnGravar.setMaximumSize(new java.awt.Dimension(65, 60));
        btnGravar.setMinimumSize(new java.awt.Dimension(65, 60));
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

        btnRec.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnRec.setText("Recuperar");
        btnRec.setToolTipText("Recuperar pessoa excluída");
        btnRec.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRec.setMaximumSize(new java.awt.Dimension(65, 60));
        btnRec.setMinimumSize(new java.awt.Dimension(65, 60));
        btnRec.setPreferredSize(new java.awt.Dimension(65, 60));
        btnRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecActionPerformed(evt);
            }
        });
        btnRec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRecKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout Panel_BotoesLayout = new javax.swing.GroupLayout(Panel_Botoes);
        Panel_Botoes.setLayout(Panel_BotoesLayout);
        Panel_BotoesLayout.setHorizontalGroup(
            Panel_BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_BotoesLayout.createSequentialGroup()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRec, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Panel_BotoesLayout.setVerticalGroup(
            Panel_BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_BotoesLayout.createSequentialGroup()
                .addGroup(Panel_BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGravar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLocalizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        lblCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        lblDtCadastro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDtCadastro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDtCadastro.setText("Data de cadastro:");

        txtDtCadastro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDtCadastro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDtCadastro.setEnabled(false);

        txtNome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        lblNome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNome.setText("Nome:");

        lblCPFCNPJ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCPFCNPJ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCPFCNPJ.setText("CPF:");

        txtCPFCNPJ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCPFCNPJ.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCPFCNPJ.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCPFCNPJInputMethodTextChanged(evt);
            }
        });
        txtCPFCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFCNPJKeyPressed(evt);
            }
        });

        ckFisica.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ckFisica.setSelected(true);
        ckFisica.setText("Pessoa Física");
        ckFisica.setEnabled(false);
        ckFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckFisicaActionPerformed(evt);
            }
        });

        ckJuridica.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ckJuridica.setText("Pessoa Jurídica");
        ckJuridica.setEnabled(false);
        ckJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckJuridicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNome))
                    .addComponent(lblCPFCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCPFCNPJ, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckFisica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckJuridica))
                    .addComponent(txtNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodigo)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNome)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCPFCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCPFCNPJ))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ckFisica)
                        .addComponent(ckJuridica)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabDados, javax.swing.GroupLayout.PREFERRED_SIZE, 896, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_Botoes, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(lblCalculaPessoa)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(Panel_Botoes, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCalculaPessoa)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabDados.getAccessibleContext().setAccessibleName("Dados gerais");

        setSize(new java.awt.Dimension(960, 548));
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // Chama os métodos que carregam as informações das ComboBox
        txtCodigo.setDocument(new SomenteNumero(6));
        btnRec.setVisible(false);
    }//GEN-LAST:event_formInternalFrameActivated

    private void ckFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckFisicaActionPerformed
        // TODO add your handling code here:
        if (ckFisica.isSelected()) {
            ckJuridica.setSelected(false);
            lblCPFCNPJ.setText("CPF:");

        } else {
            ckJuridica.setSelected(true);
            lblCPFCNPJ.setText("CNPJ:");

        }

    }//GEN-LAST:event_ckFisicaActionPerformed

    private void ckJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckJuridicaActionPerformed
        // TODO add your handling code here:
        if (ckJuridica.isSelected()) {
            ckFisica.setSelected(false);
            lblCPFCNPJ.setText("CNPJ:");

        } else {
            ckFisica.setSelected(true);
            lblCPFCNPJ.setText("CPF:");

        }
    }//GEN-LAST:event_ckJuridicaActionPerformed

    private void txtCPFCNPJInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCPFCNPJInputMethodTextChanged

    }//GEN-LAST:event_txtCPFCNPJInputMethodTextChanged

    private void btnBuscaCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaCepActionPerformed
        // TODO add your handling code here:
        buscaCep();
    }//GEN-LAST:event_btnBuscaCepActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarNome();

        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            consultarNome();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        // TODO add your handling code here:
        String cd = txtCodigo.getText();
        String nm = txtNome.getText();
        String id = txtCPFCNPJ.getText();
        try {
            if (cd.isEmpty()) {
                if (nm.isEmpty()) {
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Favor inserir uma informação para busca.");
                        txtCodigo.requestFocusInWindow();
                    } else {
                        consultarCPFCNPJ();
                    }
                } else {
                    consultarNome();
                }
            } else {
                consultarCodigo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
        }
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btnLocalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLocalizarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String cd = txtCodigo.getText();
        String nm = txtNome.getText();
        String id = txtCPFCNPJ.getText();
        try {
            if (cd.isEmpty()) {
                if (nm.isEmpty()) {
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Favor inserir uma informação para busca.");
                        txtCodigo.requestFocusInWindow();
                    } else {
                    }
                } else {
                    consultarNome();
                }
            } else {
                consultarCodigo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
        }
        }

    }//GEN-LAST:event_btnLocalizarKeyPressed

    private void txtCPFCNPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFCNPJKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarCPFCNPJ();
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            consultarCPFCNPJ();
        }
    }//GEN-LAST:event_txtCPFCNPJKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        //Chama o método adicionar
        Salvar();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Abre os campos para novo cadastro de pessoa

        if (txtContato.isEnabled()) {
            Object[] options = {"Confirmar", "Cancelar"};
            int i = JOptionPane.showOptionDialog(null,
                    "Existe um cadastro em edição. Deseja cancelar este cadastro em andamento? ", "Atenção",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.NO_OPTION) {
                txtContato.requestFocusInWindow();
            } else if (i == JOptionPane.YES_OPTION) {
                Limpar();
                txtCodigo.setEnabled(true);
                txtNome.setEnabled(true);
                txtCPFCNPJ.setEnabled(true);
                txtLogradouro.setEnabled(true);
                txtNr.setEnabled(true);
                txtComplemento.setEnabled(true);
                txtCEP.setEnabled(true);
                txtEmail.setEnabled(true);
                txtObs.setEnabled(true);
                txtTelefone.setEnabled(true);
                txtContato.setEnabled(true);
                txtDtCadastro.setEnabled(true);
                cboClassificacao.setEnabled(true);
                cboGenero.setEnabled(true);
                cboEstadoCivil.setEnabled(true);
                cboCidade.setEnabled(true);
                cboUF.setEnabled(true);
                cboBairro.setEnabled(true);
                cboPais.setEnabled(true);
                ckFisica.setEnabled(true);
                ckJuridica.setEnabled(true);
                txtCodigo.requestFocusInWindow();
                //DESATIVANDO COMPONENTES
                btnEditar.setEnabled(false);
                btnGravar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnLocalizar.setEnabled(false);
                btnBuscaCep.setEnabled(true);
            }
        } else {
            Limpar();
            txtCodigo.setEnabled(true);
            txtNome.setEnabled(true);
            txtCPFCNPJ.setEnabled(true);
            txtLogradouro.setEnabled(true);
            txtNr.setEnabled(true);
            txtComplemento.setEnabled(true);
            txtCEP.setEnabled(true);
            txtEmail.setEnabled(true);
            txtObs.setEnabled(true);
            txtTelefone.setEnabled(true);
            txtContato.setEnabled(true);
            txtDtCadastro.setEnabled(true);
            cboClassificacao.setEnabled(true);
            cboGenero.setEnabled(true);
            cboEstadoCivil.setEnabled(true);
            cboCidade.setEnabled(true);
            cboUF.setEnabled(true);
            cboBairro.setEnabled(true);
            cboPais.setEnabled(true);
            ckFisica.setEnabled(true);
            ckJuridica.setEnabled(true);
            txtCodigo.requestFocusInWindow();
            //DESATIVANDO COMPONENTES
            btnEditar.setEnabled(false);
            btnGravar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnLocalizar.setEnabled(false);
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // Habilitando os componentes para alteração
        btnNovo.setEnabled(false);
        btnExcluir.setEnabled(true);
        btnGravar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnLocalizar.setEnabled(false);
        txtCodigo.setEnabled(true);
        txtNome.setEnabled(true);
        txtCPFCNPJ.setEnabled(true);
        txtLogradouro.setEnabled(true);
        txtNr.setEnabled(true);
        txtComplemento.setEnabled(true);
        txtCEP.setEnabled(true);
        txtEmail.setEnabled(true);
        txtObs.setEnabled(true);
        txtTelefone.setEnabled(true);
        txtContato.setEnabled(true);
        txtDtCadastro.setEnabled(true);
        cboClassificacao.setEnabled(true);
        cboGenero.setEnabled(true);
        cboEstadoCivil.setEnabled(true);
        cboCidade.setEnabled(true);
        cboUF.setEnabled(true);
        cboBairro.setEnabled(true);
        cboPais.setEnabled(true);
        ckJuridica.setEnabled(true);
        ckFisica.setEnabled(true);

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        Object[] options = {"Confirmar", "Cancelar"};
        int i = JOptionPane.showOptionDialog(null,
                "Confirma o cancelamento da ação atual? ", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == JOptionPane.NO_OPTION) {
            txtContato.requestFocusInWindow();
        } else if (i == JOptionPane.YES_OPTION) {
            txtCodigo.setText(null);
            txtNome.setText(null);
            txtCPFCNPJ.setText(null);
            txtLogradouro.setText(null);
            txtNr.setText(null);
            txtComplemento.setText(null);
            txtCEP.setText(null);
            txtEmail.setText(null);
            txtObs.setText(null);
            txtTelefone.setText(null);
            txtContato.setText(null);
            txtDtCadastro.setText(null);
            txtCodigo.setEnabled(true);
            txtNome.setEnabled(true);
            txtCPFCNPJ.setEnabled(true);
            btnNovo.setEnabled(true);
            btnLocalizar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnGravar.setEnabled(false);
            //Desativar campos 
            cboClassificacao.setEnabled(false);
            cboGenero.setEnabled(false);
            cboEstadoCivil.setEnabled(false);
            cboBairro.setEnabled(false);
            cboCidade.setEnabled(false);
            cboPais.setEnabled(false);
            cboUF.setEnabled(false);
            txtTelefone.setEnabled(false);
            txtContato.setEnabled(false);
            txtEmail.setEnabled(false);
            txtObs.setEnabled(false);
            txtLogradouro.setEnabled(false);
            txtCEP.setEnabled(false);
            txtNr.setEnabled(false);
            txtComplemento.setEnabled(false);
            txtDtCadastro.setEnabled(false);
            ckFisica.setEnabled(false);
            ckJuridica.setEnabled(false);
            
            
            
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        listarCidade();
        listarBairro();
        listarPais();
        listarUF();
        listarGenero();
        listarEstadoCivil();
        listarClassificacao();
        btnBuscaCep.setEnabled(false);
        btnRec.setVisible(false);
    }//GEN-LAST:event_formComponentShown

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Salvar();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            consultarCodigo();

        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            consultarCodigo();

        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        btnBuscaCep.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        backupPessoa();
        Excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formKeyPressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel1KeyPressed

    private void btnRecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRecKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String rec = JOptionPane.showInputDialog( "Informe o código da pessoa excluída:");
            recupera.setText(rec);
            backupExec();
            contabkPessoa();

        }
    }//GEN-LAST:event_btnRecKeyPressed

    private void btnRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecActionPerformed
        // TODO add your handling code here:
        String rec = JOptionPane.showInputDialog( "Informe o código da pessoa excluída:");
        recupera.setText(rec);
        backupExec();
        contabkPessoa();
        System.out.println(count);
    }//GEN-LAST:event_btnRecActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (cboClassificacao.isEnabled()) {
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
//====================
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DadosGerais_Panel;
    private javax.swing.JPanel Endereco_Panel;
    private javax.swing.JPanel Panel_Botoes;
    private javax.swing.JButton btnBuscaCep;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    public static javax.swing.JButton btnRec;
    private javax.swing.JComboBox<String> cboBairro;
    private javax.swing.JComboBox<String> cboCidade;
    public static javax.swing.JComboBox<String> cboClassificacao;
    private javax.swing.JComboBox<String> cboEstadoCivil;
    private javax.swing.JComboBox<String> cboGenero;
    private javax.swing.JComboBox<String> cboPais;
    private javax.swing.JComboBox<String> cboUF;
    private javax.swing.JRadioButton ckFisica;
    private javax.swing.JRadioButton ckJuridica;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCPFCNPJ;
    private javax.swing.JLabel lblCalculaPessoa;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblClassificacao;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblContato;
    private javax.swing.JLabel lblDtCadastro;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLogradouro;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNr;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblUF;
    private javax.swing.JLabel recupera;
    private javax.swing.JTabbedPane tabDados;
    private javax.swing.JTextField txtCEP;
    private javax.swing.JTextField txtCPFCNPJ;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtContato;
    private javax.swing.JTextField txtDtCadastro;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNr;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
