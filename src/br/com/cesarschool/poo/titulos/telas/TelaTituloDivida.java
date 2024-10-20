package br.com.cesarschool.poo.titulos.telas;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TelaTituloDivida {
    private MediatorTituloDivida mediator;

    public TelaTituloDivida() {
        mediator = MediatorTituloDivida.getInstance();
        criarTela();
    }

    private void criarTela() {
        JFrame frame = new JFrame("CRUD Título de Dívida");
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

        JLabel labelDataValidade = new JLabel("Data Validade (yyyy-mm-dd):");
        JTextField campoDataValidade = new JTextField(10);
        campoDataValidade.setPreferredSize(new Dimension(150, 25));

        JLabel labelTaxaJuros = new JLabel("Taxa de Juros:");
        JTextField campoTaxaJuros = new JTextField(10);
        campoTaxaJuros.setPreferredSize(new Dimension(150, 25));

        JButton incluirButton = new JButton("Incluir");
        JButton alterarButton = new JButton("Alterar");
        JButton excluirButton = new JButton("Excluir");
        JButton buscarButton = new JButton("Buscar");

        incluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    LocalDate dataValidade = LocalDate.parse(campoDataValidade.getText());
                    double taxaJuros = Double.parseDouble(campoTaxaJuros.getText());

                    TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
                    String resultado = mediator.incluir(titulo);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Título de Dívida incluído com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException | DateTimeParseException ex) {
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
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    LocalDate dataValidade = LocalDate.parse(campoDataValidade.getText());
                    double taxaJuros = Double.parseDouble(campoTaxaJuros.getText());

                    TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
                    String resultado = mediator.alterar(titulo);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Título de Dívida alterado com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException | DateTimeParseException ex) {
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
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String resultado = mediator.excluir(identificador);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Título de Dívida excluído com sucesso!");
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
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    TituloDivida titulo = mediator.buscar(identificador);
                    if (titulo != null) {
                        campoNome.setText(titulo.getNome());
                        campoDataValidade.setText(titulo.getDataValidade().toString());
                        campoTaxaJuros.setText(String.valueOf(titulo.getTaxaJuros()));
                        JOptionPane.showMessageDialog(null, "Título de Dívida encontrado!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Título de Dívida não encontrado.");
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
        panel.add(labelDataValidade, gbc);
        gbc.gridx = 1;
        panel.add(campoDataValidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelTaxaJuros, gbc);
        gbc.gridx = 1;
        panel.add(campoTaxaJuros, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(incluirButton, gbc);
        gbc.gridx = 1;
        panel.add(alterarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
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
        new TelaTituloDivida();
    }
}
