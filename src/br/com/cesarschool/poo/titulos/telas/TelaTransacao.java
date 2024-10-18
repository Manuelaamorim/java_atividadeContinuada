package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

import javax.swing.*;
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
        JPanel panel = new JPanel();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelEntidadeCredito = new JLabel("ID Entidade Crédito:");
        JTextField campoEntidadeCredito = new JTextField(10);

        JLabel labelEntidadeDebito = new JLabel("ID Entidade Débito:");
        JTextField campoEntidadeDebito = new JTextField(10);

        JLabel labelIdAcaoOuTitulo = new JLabel("ID Ação/Título:");
        JTextField campoIdAcaoOuTitulo = new JTextField(10);

        JLabel labelValorOperacao = new JLabel("Valor da Operação:");
        JTextField campoValorOperacao = new JTextField(10);

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
                    JOptionPane.showMessageDialog(null, resultado);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
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
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no ID da entidade fornecido.");
                }
            }
        });

        panel.add(labelEntidadeCredito);
        panel.add(campoEntidadeCredito);
        panel.add(labelEntidadeDebito);
        panel.add(campoEntidadeDebito);
        panel.add(labelIdAcaoOuTitulo);
        panel.add(campoIdAcaoOuTitulo);
        panel.add(labelValorOperacao);
        panel.add(campoValorOperacao);
        panel.add(checkAcao);
        panel.add(checkTituloDivida);
        panel.add(realizarOperacaoButton);
        panel.add(gerarExtratoButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaTransacao();
    }
}

