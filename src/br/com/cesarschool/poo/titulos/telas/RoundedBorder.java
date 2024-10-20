package br.com.cesarschool.poo.titulos.telas;
import javax.swing.border.AbstractBorder;
import java.awt.*;
public class RoundedBorder extends AbstractBorder {

    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY); // Cor da borda
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Aumentando ainda mais os Insets para garantir que o texto tenha espaço dentro do campo
        return new Insets(12, 20, 12, 20); // Ajustando os valores conforme necessário
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = 20;  // Espaço maior à esquerda
        insets.right = 20; // Espaço maior à direita
        insets.top = 12;   // Espaço maior no topo
        insets.bottom = 12;// Espaço maior no fundo
        return insets;
    }
}
