import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TutorMecanografia4 extends JFrame {
    private JTextArea textArea;
    private JTextArea pangramArea;
    private JLabel pangramaSeleccionadoLabel;
    private JLabel pulsacionesCorrectasLabel;
    private JLabel pulsacionesIncorrectasLabel;
    private List<String> pangramas;
    private Random random;
    private int pulsacionesCorrectas;
    private int pulsacionesIncorrectas;
    private String pangramaActual;
    private int contadorEspaciosConsecutivos;
    private boolean shiftPressed;

    public TutorMecanografia4() {
        super("Tutor de Mecanografía4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pangramas = new ArrayList<>();
        random = new Random();
        pulsacionesCorrectas = 0;
        pulsacionesIncorrectas = 0;
        pangramaActual = "";
        contadorEspaciosConsecutivos = 0;
        shiftPressed = false;

        agregarPangramas();
        cargarPangramas();

        JPanel keyboardPanel = new JPanel(new GridLayout(4, 10));
        String[] buttonLabels = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "0", "q", "w", "e", "r", "t", "y", "u", "i",
                "o", "p", "a", "s", "d", "f", "g", "h", "j",
                "k", "l", "ñ", "z", "x", "c", "v", "b", "n",
                "m", " "
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new KeyboardButtonListener());
            keyboardPanel.add(button);
        }

        textArea = new JTextArea();
        textArea.setEditable(true);

        pangramArea = new JTextArea();
        pangramArea.setEditable(true);
        mostrarPangramas();

        pangramaSeleccionadoLabel = new JLabel();
        pangramaSeleccionadoLabel.setHorizontalAlignment(JLabel.CENTER);
        pangramaSeleccionadoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        pulsacionesCorrectasLabel = new JLabel("Pulsaciones correctas: 0");
        pulsacionesIncorrectasLabel = new JLabel("Pulsaciones incorrectas: 0");

        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
        add(new JScrollPane(pangramArea), BorderLayout.NORTH);
        add(pangramaSeleccionadoLabel, BorderLayout.NORTH);
        add(pulsacionesCorrectasLabel, BorderLayout.WEST);
        add(pulsacionesIncorrectasLabel, BorderLayout.EAST);

        setSize(800, 400);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarPangramas();
                dispose();
            }
        });

        // Solicitar el foco para la ventana principal
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                textArea.requestFocusInWindow();
            }
        });

        setVisible(true);

        cambiarPangramaAleatorio();

        // Agregar un DocumentListener al JTextArea para rastrear cambios
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarContadores();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarContadores();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No es relevante para un JTextArea simple
            }
        });
    }

    private class KeyboardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();

            // Obtener el estado de la tecla Shift presionada usando KeyEvent
            boolean isShiftPressed = (e.getModifiers() & ActionEvent.SHIFT_MASK) != 0;

            // Agregar la letra en mayúscula o minúscula según la tecla Shift
            textArea.append(isShiftPressed ? buttonText.toUpperCase() : buttonText);

            if (" ".equals(buttonText)) {
                contadorEspaciosConsecutivos++;
            } else {
                contadorEspaciosConsecutivos = 0;
            }

            if (contadorEspaciosConsecutivos > 3) {
                cambiarPangramaAleatorio();
            }
        }
    }
    private void agregarPangramas() {
        pangramas.add("El veloz murciélago hindú comía feliz cardillo y kiwi.");
        // Agregar más pangramas...
    }

    private void cargarPangramas() {
        // Implementación opcional para cargar pangramas desde un archivo
    }

    private void mostrarPangramas() {
        StringBuilder pangramasTexto = new StringBuilder();
        for (String pangrama : pangramas) {
            pangramasTexto.append(pangrama).append("\n");
        }
        pangramArea.setText(pangramasTexto.toString());
    }

    private void guardarPangramas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt", StandardCharsets.UTF_8))) {
            for (String pangrama : pangramas) {
                writer.write(pangrama);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores, puedes personalizar según tus necesidades
        }
    }

    private void cambiarPangramaAleatorio() {
        contadorEspaciosConsecutivos = 2;
        pangramaActual = pangramas.get(random.nextInt(pangramas.size()));
        pangramaSeleccionadoLabel.setText("Pangrama seleccionado: " + pangramaActual);
    }

    private void actualizarContadores() {
        String textoIngresado = textArea.getText();
        String pangramaSeleccionado = pangramaActual;

        pulsacionesCorrectas = contarPulsacionesCorrectas(textoIngresado, pangramaSeleccionado);
        pulsacionesIncorrectas = contarPulsacionesIncorrectas(textoIngresado, pangramaSeleccionado);

        pulsacionesCorrectasLabel.setText("Pulsaciones correctas: " + pulsacionesCorrectas);
        pulsacionesIncorrectasLabel.setText("Pulsaciones incorrectas: " + pulsacionesIncorrectas);
    }

    private int contarPulsacionesCorrectas(String textoIngresado, String pangramaSeleccionado) {
        int minLen = Math.min(textoIngresado.length(), pangramaSeleccionado.length());
        int correctas = 0;

        for (int i = 0; i < minLen; i++) {
            if (textoIngresado.charAt(i) == pangramaSeleccionado.charAt(i)) {
                correctas++;
            }
        }

        return correctas;
    }

    private int contarPulsacionesIncorrectas(String textoIngresado, String pangramaSeleccionado) {
        int minLen = Math.min(textoIngresado.length(), pangramaSeleccionado.length());
        int incorrectas = 0;

        for (int i = 0; i < minLen; i++) {
            if (textoIngresado.charAt(i) != pangramaSeleccionado.charAt(i)) {
                incorrectas++;
            }
        }

        return incorrectas;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia4();
        });
    }
}
