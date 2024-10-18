package br.com.cesarschool.poo.titulos.telas;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuPrincipal {
    public MenuPrincipal() {
        criarMenu();
    }

    private void criarMenu() {
        JFrame frame = new JFrame("Menu Principal");
        JPanel panel = new JPanel();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton acaoButton = new JButton("CRUD Ações");
        JButton operadoraButton = new JButton("CRUD Entidade Operadora");
        JButton operacaoButton = new JButton("CRUD Operações");
        JButton tituloDividaButton = new JButton("CRUD Títulos de Dívida");
        JButton sairButton = new JButton("Sair");

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
        new MenuPrincipal();
    }
}
