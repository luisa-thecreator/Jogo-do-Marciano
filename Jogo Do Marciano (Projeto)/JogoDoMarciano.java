import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Jogo do Marciano — adivinhe o número de 1 a 100 (Java Swing).
 */
public class JogoDoMarciano extends JFrame {

    private int numeroMarciano;
    private int tentativas;
    private JLabel statusLabel;
    private JLabel tentativasLabel;
    private JTextField inputField;
    private JButton tryButton;

    public JogoDoMarciano() {
        super("Jogo do Marciano");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 420));
        setLocationRelativeTo(null);

        numeroMarciano = drawNumber();
        tentativas = 0;

        configureLookAndFeel();

        GradientPanel root = new GradientPanel();
        root.setLayout(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(28, 32, 28, 32));

        JLabel title = new JLabel("Jogo do Marciano", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(new EmptyBorder(0, 0, 8, 0));

        JLabel subtitle = new JLabel("Adivinhe o número secreto entre 1 e 100", SwingConstants.CENTER);
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        subtitle.setForeground(new Color(220, 245, 232));

        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);

        GlassCard card = new GlassCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(28, 32, 28, 32));

        statusLabel = new JLabel();
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
        statusLabel.setForeground(new Color(35, 55, 45));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setStatus("Tente adivinhar o número de 1 a 100!");

        tentativasLabel = new JLabel("Tentativas: 0", SwingConstants.CENTER);
        tentativasLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        tentativasLabel.setForeground(new Color(60, 100, 75));
        tentativasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tentativasLabel.setBorder(new EmptyBorder(16, 0, 20, 0));

        inputField = new JTextField(8);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        inputField.setMaximumSize(new Dimension(220, 48));
        inputField.setPreferredSize(new Dimension(220, 48));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 160, 120), 2, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verificarPalpite();
                }
            }
        });

        tryButton = new JButton("TENTAR");
        tryButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        tryButton.setForeground(Color.WHITE);
        tryButton.setBackground(new Color(46, 180, 110));
        tryButton.setFocusPainted(false);
        tryButton.setBorderPainted(false);
        tryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tryButton.setMaximumSize(new Dimension(200, 46));
        tryButton.setPreferredSize(new Dimension(200, 46));
        tryButton.addActionListener(this::onTry);

        JButton novoJogo = new JButton("Novo jogo");
        novoJogo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        novoJogo.setForeground(new Color(70, 120, 90));
        novoJogo.setContentAreaFilled(false);
        novoJogo.setBorderPainted(false);
        novoJogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        novoJogo.setBorder(new EmptyBorder(18, 0, 0, 0));
        novoJogo.addActionListener(e -> reiniciarJogo());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridy = 0;
        card.add(statusLabel, gc);

        gc.gridy = 1;
        card.add(tentativasLabel, gc);

        gc.gridy = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(4, 0, 0, 0);
        card.add(inputField, gc);

        gc.gridy = 3;
        gc.insets = new Insets(18, 0, 0, 0);
        card.add(tryButton, gc);

        gc.gridy = 4;
        gc.insets = new Insets(0, 0, 0, 0);
        card.add(novoJogo, gc);

        root.add(header, BorderLayout.NORTH);
        root.add(card, BorderLayout.CENTER);

        setContentPane(root);
        pack();
    }

    private static void configureLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("nimbusBase", new Color(35, 110, 75));
                    UIManager.put("nimbusBlueGrey", new Color(200, 220, 210));
                    UIManager.put("control", new Color(248, 252, 249));
                    break;
                }
            }
        } catch (Exception ignored) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e2) {
                // fallback default LAF
            }
        }
    }

    private void onTry(ActionEvent e) {
        verificarPalpite();
    }

    private static int drawNumber() {
        return 1 + (int) (Math.random() * 100);
    }

    private void setStatus(String text) {
        String esc = escapeHtml(text);
        statusLabel.setText("<html><body style='text-align:center;margin:0'>" + esc.replace("\n", "<br>") + "</body></html>");
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private void verificarPalpite() {
        String raw = inputField.getText().trim();
        if (raw.isEmpty()) {
            return;
        }
        int palpite;
        try {
            palpite = Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return;
        }
        if (palpite < 1 || palpite > 100) {
            setStatus("Digite um número entre 1 e 100.");
            return;
        }

        tentativas++;
        tentativasLabel.setText("Tentativas: " + tentativas);

        if (palpite < numeroMarciano) {
            setStatus("O Marciano diz: MAIOR!");
        } else if (palpite > numeroMarciano) {
            setStatus("O Marciano diz: MENOR!");
        } else {
            setStatus("Parabéns! Você acertou em " + tentativas + " tentativas!");
            JOptionPane.showMessageDialog(
                    this,
                    "Parabéns!\n\nVocê acertou em " + tentativas + " tentativas!",
                    "Vitória!",
                    JOptionPane.INFORMATION_MESSAGE
            );
            reiniciarJogo();
        }
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    private void reiniciarJogo() {
        numeroMarciano = drawNumber();
        tentativas = 0;
        tentativasLabel.setText("Tentativas: 0");
        setStatus("Novo jogo! Tente adivinhar de novo.");
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogoDoMarciano frame = new JogoDoMarciano();
            frame.setVisible(true);
            frame.inputField.requestFocusInWindow();
        });
    }

    /** Fundo em gradiente verde (tema marciano). */
    static final class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(22, 88, 62),
                    w, h, new Color(52, 148, 108)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }

    /** Painel “vidro” com cantos arredondados. */
    static final class GlassCard extends JPanel {
        GlassCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            RoundRectangle2D rr = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 24, 24);
            g2.setColor(new Color(255, 255, 255, 230));
            g2.fill(rr);
            g2.setColor(new Color(255, 255, 255, 140));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(rr);
            g2.dispose();
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}
