import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class LibraryGUI extends JFrame {

    int count = 0;
    String[] arr = new String[100];
    JTextArea displayArea;

    public LibraryGUI() {

        setTitle("Mini Library System");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(displayArea);

        JButton b1 = new JButton("Display Books");
        JButton b2 = new JButton("Add Book");
        JButton b3 = new JButton("Search Book");
        JButton b4 = new JButton("Remove Book");
        JButton b5 = new JButton("Count Books");
        JButton b6 = new JButton("Save & Exit");

        JPanel buttons = new JPanel(new GridLayout(6, 1, 10, 10));
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttons.add(b6);

        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.EAST);

        // ===== READ FROM FILE =====
        try {
            File book = new File("books.txt");
            if (!book.exists()) book.createNewFile();

            Scanner reader = new Scanner(book);
            while (reader.hasNextLine()) {
                if (count < arr.length) {
                    arr[count] = reader.nextLine();
                    count++;
                }
            }
            reader.close();
        } catch (Exception e) {
            displayArea.setText("File not found");
        }

        b1.addActionListener(e -> runChoice(1));
        b2.addActionListener(e -> runChoice(2));
        b3.addActionListener(e -> runChoice(3));
        b4.addActionListener(e -> runChoice(4));
        b5.addActionListener(e -> runChoice(5));
        b6.addActionListener(e -> runChoice(6));
    }

    void runChoice(int choice) {

        switch (choice) {

            case 1:
                displayArea.setText("your book list \n");
                if (count == 0) {
                    displayArea.append("No books \n");
                    break;
                }
                for (int i = 0; i < count; i++) {
                    displayArea.append((i + 1) + ". " + arr[i] + "\n");
                }
                break;

            case 2:
                String y = JOptionPane.showInputDialog("Enter new book:");
                if (y == null || y.trim().isEmpty()) break;

                boolean add = true;

                for (int i = 0; i < count; i++) {
                    if (y.equalsIgnoreCase(arr[i])) {
                        add = false;
                        break;
                    }
                }

                if (count == arr.length) {
                    JOptionPane.showMessageDialog(null, "Library is full");
                    break;
                }

                if (add) {
                    arr[count] = y.trim();
                    count++;
                    JOptionPane.showMessageDialog(null,
                            "The book added successfully");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "The book already exists");
                }
                break;

            case 3:
                String z = JOptionPane.showInputDialog("Enter the book title:");
                if (z == null || z.trim().isEmpty()) break;

                boolean found = false;

                for (int i = 0; i < count; i++) {
                    if (z.equalsIgnoreCase(arr[i])) {
                        found = true;
                        break;
                    }
                }

                if (found)
                    displayArea.setText("The Book Found");
                else
                    displayArea.setText("The Book not Found");
                break;

            case 4:
                String r = JOptionPane.showInputDialog(
                        "Enter the book title to remove:");
                if (r == null || r.trim().isEmpty()) break;

                boolean removed = false;

                for (int i = 0; i < count; i++) {
                    if (r.equalsIgnoreCase(arr[i])) {

                        for (int j = i; j < count - 1; j++) {
                            arr[j] = arr[j + 1];
                        }

                        count--;
                        removed = true;
                        break;
                    }
                }

                if (!removed)
                    JOptionPane.showMessageDialog(null, "Book not found");
                else
                    JOptionPane.showMessageDialog(null,
                            "Book removed successfully");
                break;

            case 5:
                JOptionPane.showMessageDialog(null,
                        "The Total Books: " + count);
                break;

            case 6:
                try {
                    PrintWriter writer = new PrintWriter("books.txt");
                    for (int i = 0; i < count; i++) {
                        writer.println(arr[i]);
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null,
                            "Saved successfully");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Error saving file");
                }
                System.exit(0);
                break;

            default:
                displayArea.setText(
                        "Wrong number\nPlease enter number (1-6)");
        }
    }

    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}