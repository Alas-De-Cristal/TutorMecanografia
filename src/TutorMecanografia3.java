import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TutorMecanografia3 extends JFrame implements KeyListener {
    private JTextArea textArea;
    private JTextArea pangramArea;
    private JLabel pangramaSeleccionadoLabel;
    private List<String> pangramas;
    private Random random;

    public TutorMecanografia3() {
        super("Tutor de Mecanografía3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar la lista de pangramas y el generador de números aleatorios
        pangramas = new ArrayList<>();
        random = new Random();

        // Cargar pangramas antes de agregar nuevos
        cargarPangramas();

        // Agregar pangramas al archivo y cargarlos a la lista
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

        // Crear el área de texto para la mecanografía
        textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.addKeyListener(this); // Agregar el KeyListener

        // Crear el área para mostrar y editar los pangramas
        pangramArea = new JTextArea();
        pangramArea.setEditable(true);
        mostrarPangramas();

        // Crear el JLabel para mostrar el pangrama seleccionado
        pangramaSeleccionadoLabel = new JLabel();
        pangramaSeleccionadoLabel.setHorizontalAlignment(JLabel.CENTER);
        pangramaSeleccionadoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Diseño general de la GUI
        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
        add(new JScrollPane(pangramArea), BorderLayout.NORTH);
        add(pangramaSeleccionadoLabel, BorderLayout.NORTH);

        // Configuración de la ventana
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Agregar un oyente para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarPangramas();
                dispose();
            }
        });

        setVisible(true);

        // Seleccionar y mostrar un pangrama aleatorio al inicio
        seleccionarPangramaAleatorio();
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt", true))) {
            for (String pangrama : pangramas) {
                writer.write(pangrama);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPangramas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pangramas.txt"))) {
            pangramas.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                pangramas.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarPangramas() {
        StringBuilder pangramasText = new StringBuilder();
        for (String pangrama : pangramas) {
            pangramasText.append(pangrama).append("\n");
        }
        pangramArea.setText(pangramasText.toString());
    }

    private void guardarPangramas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt"))) {
            for (String pangrama : pangramas) {
                writer.write(pangrama);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void seleccionarPangramaAleatorio() {
        if (!pangramas.isEmpty()) {
            int index = random.nextInt(pangramas.size());
            String pangramaSeleccionado = pangramas.get(index);
            pangramaSeleccionadoLabel.setText("Pangrama seleccionado: " + pangramaSeleccionado);
        }
    }

    // Implementación de métodos KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // Este método se llama cuando se pulsa y libera una tecla.
        // Puedes agregar lógica adicional aquí si es necesario.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Este método se llama cuando se presiona una tecla.
        // Puedes agregar lógica adicional aquí si es necesario.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Este método se llama cuando se libera una tecla.
        // Puedes agregar lógica adicional aquí si es necesario.
        seleccionarPangramaAleatorio(); // Actualizar el pangrama seleccionado después de escribir
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia3();
        });
    }
}