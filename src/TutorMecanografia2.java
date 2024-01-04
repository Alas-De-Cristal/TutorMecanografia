import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TutorMecanografia2 extends JFrame {
    private JTextArea textArea;
    private JTextArea pangramArea;

    public TutorMecanografia2() {
        super("Tutor de Mecanografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agregar pangramas al archivo
        agregarPangramas();

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
        textArea.setEditable(true);

        // Crear el área para mostrar los pangramas
        pangramArea = new JTextArea();
        pangramArea.setEditable(true);
        mostrarPangramas();

        // Diseño general de la GUI
        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
        add(new JScrollPane(pangramArea), BorderLayout.NORTH);

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


    private void agregarPangramas() {
        List<String> pangramas = new ArrayList<>();
        pangramas.add("El veloz murciélago hindú comía feliz cardillo y kiwi.");
        pangramas.add("La cigüeña tocaba el saxofón detrás del palenque de paja." );
        pangramas.add("El pingüino Wenceslao hizo kilómetros bajo exhaustiva lluvia y frío, añoraba a su querido cachorro.");
        pangramas.add("Jovencillo emponzoñado de whisky, qué mala figurota exhibes.");
        pangramas.add("Exhíbanse politiquillos zafios, con orejas kilométricas y uñas de gavilán.");
        pangramas.add( "Jovencillo emponzoñado de whisky: ¡qué figurota exhibe!");
        pangramas.add("¡Exijo hablar de un pequeño y fugaz armisticio en Kuwait! ¿Vale?");
        pangramas.add("Le gustaba cenar un exquisito sándwich de jamón con zumo de piña y vodka fría.");
        pangramas.add("El jefe buscó el éxtasis en un imprevisto baño de whisky y gozó como un duque.");
        pangramas.add("El viejo Señor Gómez pedía queso, kiwi y habas, pero le ha tocado un saxofón.");
        pangramas.add( "La cigüeña tocaba cada vez mejor el saxofón y el búho pedía kiwi y queso.");
        pangramas.add("El jefe que goza con un imprevisto busca el éxtasis en un baño de whisky.");
        pangramas.add("Borja quiso el extraño menú de gazpacho, kiwi, uva y flan.");
        pangramas.add("El extraño whisky quemó como fuego la boca del joven López.");
        pangramas.add("Ex-duque gozó con imprevisto baño de flojo whisky.");
        pangramas.add("Fidel exporta gazpacho, jamón, kiwi, viñas y buques.");
        pangramas.add("Joven emponzoñado con whisky, ¡qué mala figura exhibiste! (Modificado).");
        pangramas.add("La vieja cigüeña fóbica quiso empezar hoy un éxodo a Kuwait.");
        pangramas.add("Mi pequeño ex-jefe, loco gozaba vertiendo whisky.");
        pangramas.add("Mi jefe goza porque ve baño de excelente whisky.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt"))) {
            for (String pangrama : pangramas) {
                writer.write(pangrama);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarPangramas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pangramas.txt"))) {
            StringBuilder pangramasText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                pangramasText.append(line).append("\n");
            }
            pangramArea.setText(pangramasText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarPangramas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt"))) {
            writer.write(pangramArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia2();
        });
    }
}
