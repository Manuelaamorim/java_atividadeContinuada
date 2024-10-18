package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

import javax.swing.*;
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
        JPanel panel = new JPanel();
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelIdentificador = new JLabel("Identificador:");
        JTextField campoIdentificador = new JTextField(10);

        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(30);

        JLabel labelAutorizadoAcao = new JLabel("Autorizado Ação:");
        JTextField campoAutorizadoAcao = new JTextField(10);

        JLabel labelSaldoAcao = new JLabel("Saldo Ação:");
        JTextField campoSaldoAcao = new JTextField(10);

        JLabel labelSaldoTituloDivida = new JLabel("Saldo Título Dívida:");
        JTextField campoSaldoTituloDivida = new JTextField(10);

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
                    double autorizadoAcao = Double.parseDouble(campoAutorizadoAcao.getText());
                    double saldoAcao = Double.parseDouble(campoSaldoAcao.getText());
                    double saldoTituloDivida = Double.parseDouble(campoSaldoTituloDivida.getText());

                    EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
                    String resultado = mediator.incluir(entidade);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Entidade incluída com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
                }
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long identificador = Long.parseLong(campoIdentificador.getText());
                    String nome = campoNome.getText();
                    double autorizadoAcao = Double.parseDouble(campoAutorizadoAcao.getText());
                    double saldoAcao = Double.parseDouble(campoSaldoAcao.getText());
                    double saldoTituloDivida = Double.parseDouble(campoSaldoTituloDivida.getText());

                    EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
                    String resultado = mediator.alterar(entidade);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Entidade alterada com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro nos dados fornecidos. Verifique os campos.");
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
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
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
                        campoAutorizadoAcao.setText(String.valueOf(entidade.getAutorizadoAcao()));
                        campoSaldoAcao.setText(String.valueOf(entidade.getSaldoAcao()));
                        campoSaldoTituloDivida.setText(String.valueOf(entidade.getSaldoTituloDivida()));
                        JOptionPane.showMessageDialog(null, "Entidade encontrada!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Entidade não encontrada.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no identificador fornecido.");
                }
            }
        });

        panel.add(labelIdentificador);
        panel.add(campoIdentificador);
        panel.add(labelNome);
        panel.add(campoNome);
        panel.add(labelAutorizadoAcao);
        panel.add(campoAutorizadoAcao);
        panel.add(labelSaldoAcao);
        panel.add(campoSaldoAcao);
        panel.add(labelSaldoTituloDivida);
        panel.add(campoSaldoTituloDivida);

        panel.add(incluirButton);
        panel.add(alterarButton);
        panel.add(excluirButton);
        panel.add(buscarButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaEntidadeOperadora();
    }
}
