package br.com.cesarschool.poo.titulos.telas;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaTransacao {
    private MediatorOperacao mediator;

    public TelaTransacao() {
        mediator = MediatorOperacao.getInstance();
        criarTela();
    }

    private void criarTela() {
        JFrame frame = new JFrame("Operações de Transação");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelEntidadeCredito = new JLabel("ID Entidade Crédito:");
        JTextField campoEntidadeCredito = new JTextField(10);
        campoEntidadeCredito.setPreferredSize(new Dimension(150, 25));

        JLabel labelEntidadeDebito = new JLabel("ID Entidade Débito:");
        JTextField campoEntidadeDebito = new JTextField(10);
        campoEntidadeDebito.setPreferredSize(new Dimension(150, 25));

        JLabel labelIdAcaoOuTitulo = new JLabel("ID Ação/Título:");
        JTextField campoIdAcaoOuTitulo = new JTextField(10);
        campoIdAcaoOuTitulo.setPreferredSize(new Dimension(150, 25));

        JLabel labelValorOperacao = new JLabel("Valor da Operação:");
        JTextField campoValorOperacao = new JTextField(10);
        campoValorOperacao.setPreferredSize(new Dimension(150, 25));

        JCheckBox checkAcao = new JCheckBox("Operação com Ação");
        JCheckBox checkTituloDivida = new JCheckBox("Operação com Título de Dívida");

        JButton realizarOperacaoButton = new JButton("Realizar Operação");
        JButton gerarExtratoButton = new JButton("Gerar Extrato");

        realizarOperacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int entidadeCredito = Integer.parseInt(campoEntidadeCredito.getText());
                    int entidadeDebito = Integer.parseInt(campoEntidadeDebito.getText());
                    int idAcaoOuTitulo = Integer.parseInt(campoIdAcaoOuTitulo.getText());
                    double valorOperacao = Double.parseDouble(campoValorOperacao.getText());

                    boolean ehAcao = checkAcao.isSelected();
                    boolean ehTituloDivida = checkTituloDivida.isSelected();

                    if (ehAcao && ehTituloDivida) {
                        JOptionPane.showMessageDialog(null, "Selecione apenas uma operação (Ação ou Título de Dívida).");
                        return;
                    }

                    if (!ehAcao && !ehTituloDivida) {
                        JOptionPane.showMessageDialog(null, "Selecione uma operação (Ação ou Título de Dívida).");
                        return;
                    }

                    String resultado = mediator.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valorOperacao);
                    if (resultado != null) {
                        JOptionPane.showMessageDialog(null, resultado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
                        frame.dispose();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        gerarExtratoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int entidade = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da Entidade para gerar extrato:"));
                    Transacao[] transacoes = mediator.gerarExtrato(entidade);

                    if (transacoes.length == 0) {
                        JOptionPane.showMessageDialog(null, "Nenhuma transação encontrada.");
                        return;
                    }

                    StringBuilder extrato = new StringBuilder();
                    for (Transacao transacao : transacoes) {
                        extrato.append("Crédito: ").append(transacao.getEntidadeCredito().getNome()).append("\n");
                        extrato.append("Débito: ").append(transacao.getEntidadeDebito().getNome()).append("\n");
                        if (transacao.getAcao() != null) {
                            extrato.append("Ação: ").append(transacao.getAcao().getNome()).append("\n");
                        } else if (transacao.getTituloDivida() != null) {
                            extrato.append("Título de Dívida: ").append(transacao.getTituloDivida().getNome()).append("\n");
                        }
                        extrato.append("Valor: ").append(transacao.getValorOperacao()).append("\n");
                        extrato.append("Data/Hora: ").append(transacao.getDataHoraOperacao()).append("\n\n");
                    }

                    JOptionPane.showMessageDialog(null, extrato.toString());
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no ID da entidade fornecido.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelEntidadeCredito, gbc);
        gbc.gridx = 1;
        panel.add(campoEntidadeCredito, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelEntidadeDebito, gbc);
        gbc.gridx = 1;
        panel.add(campoEntidadeDebito, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelIdAcaoOuTitulo, gbc);
        gbc.gridx = 1;
        panel.add(campoIdAcaoOuTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelValorOperacao, gbc);
        gbc.gridx = 1;
        panel.add(campoValorOperacao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(checkAcao, gbc);
        gbc.gridx = 1;
        panel.add(checkTituloDivida, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(realizarOperacaoButton, gbc);
        gbc.gridx = 1;
        panel.add(gerarExtratoButton, gbc);

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
        new TelaTransacao();
    }
}
