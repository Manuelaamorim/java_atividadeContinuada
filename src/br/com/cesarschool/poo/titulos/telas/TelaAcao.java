package br.com.cesarschool.poo.titulos.telas;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class TelaAcao {
    private MediatorAcao mediator;

    public TelaAcao() {
        mediator = MediatorAcao.getInstance();
        criarTela();
    }

    private void criarTela() {
        JFrame frame = new JFrame("CRUD de Ações");
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

        JLabel labelValorUnitario = new JLabel("Valor Unitário:");
        JTextField campoValorUnitario = new JTextField(10);
        campoValorUnitario.setPreferredSize(new Dimension(150, 25));

        JButton incluirButton = new JButton("Incluir");
        JButton alterarButton = new JButton("Alterar");
        JButton excluirButton = new JButton("Excluir");
        JButton buscarButton = new JButton("Buscar");

        incluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    LocalDate dataValidade = LocalDate.parse(campoDataValidade.getText());
                    double valorUnitario = Double.parseDouble(campoValorUnitario.getText());

                    Acao acao = new Acao(identificador, nome, dataValidade, valorUnitario);
                    String resultado = mediator.incluir(acao);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Ação incluída com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no formato dos dados numéricos. Verifique o identificador ou valor unitário.");
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no formato da data. Use o formato yyyy-mm-dd.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    LocalDate dataValidade = LocalDate.parse(campoDataValidade.getText());
                    double valorUnitario = Double.parseDouble(campoValorUnitario.getText());

                    Acao acao = new Acao(identificador, nome, dataValidade, valorUnitario);
                    String resultado = mediator.alterar(acao);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Ação alterada com sucesso!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no formato dos dados numéricos. Verifique o identificador ou valor unitário.");
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no formato da data. Use o formato yyyy-mm-dd.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    String resultado = mediator.excluir(identificador);
                    JOptionPane.showMessageDialog(null, Objects.requireNonNullElse(resultado, "Ação excluída com sucesso!"));
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int identificador = Integer.parseInt(campoIdentificador.getText());
                    Acao acao = mediator.buscar(identificador);
                    if (acao != null) {
                        campoNome.setText(acao.getNome());
                        campoDataValidade.setText(acao.getDataValidade().toString());
                        campoValorUnitario.setText(String.valueOf(acao.getValorUnitario()));
                        JOptionPane.showMessageDialog(null, "Ação encontrada!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ação não encontrada.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
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
        panel.add(labelValorUnitario, gbc);
        gbc.gridx = 1;
        panel.add(campoValorUnitario, gbc);

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
        new TelaAcao();
    }
}
