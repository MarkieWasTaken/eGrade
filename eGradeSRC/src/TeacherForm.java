import javax.swing.*;
import java.awt.*;

public class TeacherForm extends JFrame {
    public TeacherForm(int teacherId) {
        setTitle("eGrade - Teacher Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome, Teacher (ID: " + teacherId + ")");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
