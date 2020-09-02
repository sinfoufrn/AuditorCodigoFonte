/*
 * Universidade Federal do Rio Grande do Norte
 * Superintendência de Informática
 * Diretoria de Sistemas
 * Equipe Orbitais
 *
 * É proibido usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e / ou vender cópias
 * desse Software sem estar de acordo com os termos da cooperação da UFRN.
 *
 * O aviso de copyright acima deve ser incluído em todas as cópias ou partes substanciais do Software.
 *
 * AuditorCodigoFonte
 * br.ufrn.eligere.gui
 * GridBagLayoutUtil
 * 01/09/2020 12:51
 */
package br.ufrn.eligere.gui;

import javax.swing.*;
import java.awt.*;

/**
 * GridBagLayout util class
 *
 * @author Jadson Santos - jadson@info.ufrn.br
 * @version 1.0
 * @since 01/09/2020
 */
public class GridBagLayoutUtil {

    public static void configureLayout(JPanel centerPanel,
                                       JLabel dateLabel, JTextField dateField,  JLabel hashLabel, JTextField hashField,
                                       JButton chooseFiles, JButton verify, JButton clear) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.ipady = 30;      //make this component tall

        // row 0   HASH INPUT ////////////////
        gbc.gridy = 0;

        gbc.gridx = 0; // column 0
        gbc.gridwidth = 4; //columns wide
        gbc.weightx=0.4;
        gbc.fill = GridBagConstraints.NONE;
        centerPanel.add(hashLabel, gbc);

        gbc.gridx = 4; // column 4
        gbc.gridwidth = 8; //columns wide
        gbc.weightx=0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(hashField, gbc);


        //////////// row 1  DATE INPUT ////////////////
        gbc.gridy = 1;

        gbc.gridx = 0; // column 0
        gbc.gridwidth = 6; //columns wide
        gbc.weightx=0.4;
        gbc.fill = GridBagConstraints.NONE;
        centerPanel.add(dateLabel, gbc);

        gbc.gridx = 4; // column 6
        gbc.gridwidth = 4; //columns wide
        gbc.weightx=0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(dateField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        //////////////// row 2 FILE INPUT ////////////////
        gbc.gridy = 2;

        gbc.gridx = 0; // column 0
        gbc.gridwidth = 12; //12 columns wide
        gbc.weightx=1.0;
        centerPanel.add(chooseFiles, gbc);

        //////////////// row 3 ACTIONS BUTTON  ////////////////
        gbc.gridy = 3;

        gbc.gridx = 5; // column 0
        gbc.gridwidth = 2; //2 columns wide
        centerPanel.add(verify, gbc);

        gbc.gridx = 7; // column 0
        gbc.gridwidth = 2; //2 columns wide
        centerPanel.add(clear, gbc);
    }

}

