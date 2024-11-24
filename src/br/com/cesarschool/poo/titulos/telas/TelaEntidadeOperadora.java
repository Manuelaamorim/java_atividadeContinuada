package br.com.cesarschool.poo.titulos.telas;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEntidadeOperadora {
    private MediatorEntidadeOperadora mediator;

    public TelaEntidadeOperadora() {
        mediator = MediatorEntidadeOperadora.getInstance();
        criarTela();
    }

    private void criarTela() {
        JFrame frame = new JFrame("CRUD Entidade Operadora");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelIdentificador = new JLabel("Identificador:");
        JTextField campoIdentificador = new JTextField(10);
        campoIdentificador.setPreferredSize(new Dimension(150, 25));

        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(30);
        campoNome.setPreferredSize(new Dimension(250, 25));

        JLabel labelAutorizadoAcao = new JLabel("Autorizado Ação:");
        JComboBox<Double> comboAutorizadoAcao = new JComboBox<>(new Double[]{});
        comboAutorizadoAcao.setPreferredSize(new Dimension(150, 25));

        JLabel labelSaldoAcao = new JLabel("Saldo Ação:");
        JTextField campoSaldoAcao = new JTextField(10);
        campoSaldoAcao.setPreferredSize(new Dimension(150, 25));

        JLabel labelSaldoTituloDivida = new JLabel("Saldo Título Dívida:");
        JTextField campoSaldoTituloDivida = new JTextField(10);
        campoSaldoTituloDivida.setPreferredSize(new Dimension(150, 25));

        JButton incluirButton = new JButton("Incluir");
        JButton alterarButton = new JButton("Alterar");
        JButton excluirButton = new JButton("Excluir");
        JButton buscarButton = new JButton("Buscar");

        incluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long identificador = Long.parseLong(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    double autorizadoAcao = (Double) comboAutorizadoAcao.getSelectedItem();
                    double saldoAcao = Double.parseDouble(campoSaldoAcao.getText());
                    double saldoTituloDivida = Double.parseDouble(campoSaldoTituloDivida.getText());

                    EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
                    entidade.creditarSaldoAcao(saldoAcao);
                    entidade.creditarSaldoTituloDivida(saldoTituloDivida);
                    String resultado = mediator.incluir(entidade);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Entidade incluída com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long identificador = Long.parseLong(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    double autorizadoAcao = (Double) comboAutorizadoAcao.getSelectedItem();
                    double saldoAcao = Double.parseDouble(campoSaldoAcao.getText());
                    double saldoTituloDivida = Double.parseDouble(campoSaldoTituloDivida.getText());

                    EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
                    entidade.creditarSaldoAcao(saldoAcao);
                    entidade.creditarSaldoTituloDivida(saldoTituloDivida);
                    String resultado = mediator.alterar(entidade);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Entidade alterada com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long identificador = Long.parseLong(campoIdentificador.getText());
                    String resultado = mediator.excluir((int) identificador);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Entidade excluída com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long identificador = Long.parseLong(campoIdentificador.getText());
                    EntidadeOperadora entidade = mediator.buscar((int) identificador);
                    if (entidade != null) {
                        campoNome.setText(entidade.getNome());
                        comboAutorizadoAcao.setSelectedItem(entidade.getAutorizadoAcao());
                        campoSaldoAcao.setText(String.valueOf(entidade.getSaldoAcao()));
                        campoSaldoTituloDivida.setText(String.valueOf(entidade.getSaldoTituloDivida()));
                        JOptionPane.showMessageDialog(null, "Entidade encontrada!");
                        frame.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Entidade não encontrada.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelIdentificador, gbc);
        gbc.gridx = 1;
        panel.add(campoIdentificador, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelNome, gbc);
        gbc.gridx = 1;
        panel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelAutorizadoAcao, gbc);
        gbc.gridx = 1;
        panel.add(comboAutorizadoAcao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelSaldoAcao, gbc);
        gbc.gridx = 1;
        panel.add(campoSaldoAcao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelSaldoTituloDivida, gbc);
        gbc.gridx = 1;
        panel.add(campoSaldoTituloDivida, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(incluirButton, gbc);
        gbc.gridx = 1;
        panel.add(alterarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(excluirButton, gbc);
        gbc.gridx = 1;
        panel.add(buscarButton, gbc);

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
        new TelaEntidadeOperadora();
    }
}
