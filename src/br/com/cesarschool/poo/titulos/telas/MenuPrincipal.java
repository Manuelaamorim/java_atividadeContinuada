package br.com.cesarschool.poo.titulos.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {

    public MenuPrincipal() {
        criarMenu();
    }

    private void criarMenu() {
        JFrame frame = new JFrame("Menu Principal");
        JPanel panel = new JPanel(new FlowLayout());
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton acaoButton = new JButton("CRUD Ações");
        acaoButton.setBorder(new RoundedBorder(15));
        acaoButton.setPreferredSize(new Dimension(200, 40));

        JButton operadoraButton = new JButton("CRUD Entidade Operadora");
        operadoraButton.setBorder(new RoundedBorder(15));
        operadoraButton.setPreferredSize(new Dimension(200, 40));

        JButton operacaoButton = new JButton("CRUD Operações");
        operacaoButton.setBorder(new RoundedBorder(15));
        operacaoButton.setPreferredSize(new Dimension(200, 40));

        JButton tituloDividaButton = new JButton("CRUD Títulos de Dívida");
        tituloDividaButton.setBorder(new RoundedBorder(15));
        tituloDividaButton.setPreferredSize(new Dimension(200, 40));

        JButton sairButton = new JButton("Sair");
        sairButton.setBorder(new RoundedBorder(15));
        sairButton.setPreferredSize(new Dimension(200, 40));

        acaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaAcao();
            }
        });

        operadoraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaEntidadeOperadora();
            }
        });

        operacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaTransacao();
            }
        });

        tituloDividaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaTituloDivida();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        panel.add(acaoButton);
        panel.add(operadoraButton);
        panel.add(operacaoButton);
        panel.add(tituloDividaButton);
        panel.add(sairButton);


        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MenuPrincipal();
    }
}
