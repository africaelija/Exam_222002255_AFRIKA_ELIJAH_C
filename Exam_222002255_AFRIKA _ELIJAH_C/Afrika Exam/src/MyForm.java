import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class MyForm extends JFrame {

    private JTextField textname, textage;
    private JRadioButton male, female;

    public MyForm() {
        super("STUDENT INFORMATION BIT LEVEL 2"); 
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        add(nameLabel);

        textname = new JTextField();
        textname.setBounds(120, 20, 150, 25);
        add(textname);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 60, 80, 25);
        add(ageLabel);

        textage = new JTextField();
        textage.setBounds(120, 60, 150, 25);
        add(textage);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(20, 100, 80, 25);
        add(genderLabel);

        male = new JRadioButton("Male");
        male.setBounds(120, 100, 80, 25);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(220, 100, 80, 25);
        add(female);

        JButton addButton = new JButton("ADD");
        addButton.setBounds(120, 140, 80, 25);
        addButton.addActionListener((ActionEvent e) -> {
            saveData();
        });
        add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(220, 140, 80, 25);
        updateButton.addActionListener((ActionEvent e) -> {
            updateData();
        });
        add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(320, 140, 80, 25);
        deleteButton.addActionListener((ActionEvent e) -> {
            deleteData();
        });
        add(deleteButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void saveData() {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/africa", "root", "")) {
                connection.setAutoCommit(false);  // Disable auto-commit

                String query = "INSERT INTO exam(name, age, gender) VALUES(?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, textname.getText());
                    preparedStatement.setInt(2, Integer.parseInt(textage.getText()));

                    if (male.isSelected()) {
                        preparedStatement.setString(3, "Male");
                    } else if (female.isSelected()) {
                        preparedStatement.setString(3, "Female");
                    }

                    preparedStatement.executeUpdate();
                }

                connection.commit();  // Commit changes

                // Display success message
                JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            // Display error message
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateData() {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/africa", "root", "")) {
                connection.setAutoCommit(false);  // Disable auto-commit

                String query = "UPDATE exam SET age=?, gender=? WHERE name=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, Integer.parseInt(textage.getText()));

                    if (male.isSelected()) {
                        preparedStatement.setString(2, "Male");
                    } else if (female.isSelected()) {
                        preparedStatement.setString(2, "Female");
                    }

                    preparedStatement.setString(3, textname.getText());
                    preparedStatement.executeUpdate();
                }

                connection.commit();  // Commit changes

                // Display success message
                JOptionPane.showMessageDialog(this, "Data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            // Display error message
            JOptionPane.showMessageDialog(this, "Error updating data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteData() {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/africa", "root", "")) {
                connection.setAutoCommit(false);  // Disable auto-commit

                String query = "DELETE FROM exam WHERE name=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, textname.getText());
                    preparedStatement.executeUpdate();
                }

                connection.commit();  // Commit changes

                // Display success message
                JOptionPane.showMessageDialog(this, "Data deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Display error message
            JOptionPane.showMessageDialog(this, "Error deleting data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MyForm().setVisible(true);
        });
    }
}