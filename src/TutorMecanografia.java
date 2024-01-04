import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TutorMecanografia extends JFrame {
    private JTextArea textArea;

    public TutorMecanografia() {
        super("Tutor de Mecanografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el teclado virtual
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

        // Crear el área de texto
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Diseño general de la GUI
        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);

        // Configuración de la ventana
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Agregar un oyente para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Salir del bucle de espera cuando se cierra la ventana
                dispose();
            }
        });

        setVisible(true);
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

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia();
        });
    }
}