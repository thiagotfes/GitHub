/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pessoa.dal;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

/**
 *
 * @author thiag
 */
public class SomenteNumero extends PlainDocument 
{
    private int iMaxLength;
    public SomenteNumero(int maxlen) {
        super();
        iMaxLength = maxlen;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
                    throws BadLocationException {
        //if (s == null) return;
        if (iMaxLength <= 0)        // aceitara qualquer no. de caracteres
        {
            super.insertString(offset, str.toUpperCase().replaceAll("[^0-9]", ""), attr);
            return;
        }
        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength)    // se o comprimento final for menor...
            super.insertString(offset, str.toUpperCase().replaceAll("[^0-9]", ""), attr);   // ...aceita str
        }
}

