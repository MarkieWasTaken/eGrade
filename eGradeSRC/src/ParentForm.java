import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class ParentForm extends JFrame {

    public ParentForm(int parentId) {
        setTitle("eGrade – Parent Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Child's Grades");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(title);

        // Get grades from DB
        Map<String, List<GradeItem>> subjectGrades = fetchGradesByParentId(parentId);

        for (Map.Entry<String, List<GradeItem>> entry : subjectGrades.entrySet()) {
            String subject = entry.getKey();
            List<GradeItem> grades = entry.getValue();
            double average = grades.stream().mapToDouble(g -> g.score).average().orElse(0.0);

            mainPanel.add(createSubjectCard(subject, grades, average));
            mainPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
        setVisible(true);
    }

    // Grade model
    static class GradeItem {
        int score;
        String comment;

        public GradeItem(int score, String comment) {
            this.score = score;
            this.comment = comment;
        }
    }

    // Query: fetch all grades grouped by subject for parent’s children
    private Map<String, List<GradeItem>> fetchGradesByParentId(int parentId) {
        Map<String, List<GradeItem>> data = new LinkedHashMap<>();
        String query = """
            SELECT s.name AS subject, g.score, g.comment
            FROM grade g
            JOIN subject s ON g.subject_id = s.id
            JOIN parent_student ps ON g.student_id = ps.student_id
            WHERE ps.parent_id = ?
            ORDER BY s.name
        """;

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String subject = rs.getString("subject");
                int score = rs.getInt("score");
                String comment = rs.getString("comment");

                data.computeIfAbsent(subject, k -> new ArrayList<>())
                        .add(new GradeItem(score, comment));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // UI card for subject
    private JPanel createSubjectCard(String subjectName, List<GradeItem> grades, double avg) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // === TOP: Subject Label ===
        JLabel subjectLabel = new JLabel(subjectName.toUpperCase());
        subjectLabel.setOpaque(true);
        subjectLabel.setForeground(Color.WHITE);
        subjectLabel.setBackground(getSubjectColor(subjectName));
        subjectLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        subjectLabel.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        topPanel.add(subjectLabel);
        card.add(topPanel, BorderLayout.NORTH);

        // === CENTER: Grades and Avg ===
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Grade bubbles (centered vertically)
        JPanel gradePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        gradePanel.setOpaque(false);

        for (GradeItem grade : grades) {
            JLabel badge = new JLabel(String.valueOf(grade.score), SwingConstants.CENTER);
            badge.setPreferredSize(new Dimension(32, 32));
            badge.setOpaque(true);
            badge.setForeground(Color.WHITE);
            badge.setBackground(getGradeColor(grade.score));
            badge.setFont(new Font("Segoe UI", Font.BOLD, 14));
            badge.setToolTipText(grade.comment != null ? grade.comment : "No comment");
            badge.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            badge.setHorizontalAlignment(SwingConstants.CENTER);
            gradePanel.add(badge);
        }

        centerPanel.add(gradePanel, BorderLayout.WEST);

        // Avg grade (right side, vertically centered)
        JLabel avgLabel = new JLabel(String.format("%.2f", avg));
        avgLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        avgLabel.setForeground(new Color(44, 62, 80));
        avgLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel avgWrapper = new JPanel(new BorderLayout());
        avgWrapper.setOpaque(false);
        avgWrapper.add(avgLabel, BorderLayout.EAST);
        avgWrapper.setPreferredSize(new Dimension(80, 40));
        centerPanel.add(avgWrapper, BorderLayout.EAST);

        card.add(centerPanel, BorderLayout.CENTER);
        return card;
    }


    private Color getSubjectColor(String subject) {
        return switch (subject.toLowerCase()) {
            case "mathematics" -> new Color(0, 123, 255);
            case "science" -> new Color(26, 188, 156);
            case "history" -> new Color(241, 196, 15);
            case "english" -> new Color(231, 76, 60);
            case "ict" -> new Color(93, 173, 226);
            default -> new Color(120, 144, 156);
        };
    }

    private Color getGradeColor(int score) {
        if (score >= 90) {
            return new Color(46, 204, 113); // Green
        } else if (score > 75) {
            return new Color(129, 199, 132); // Light Green
        } else if (score > 60) {
            return new Color(255, 152, 0); // Orange
        } else if (score > 50) {
            return new Color(255, 235, 59); // Yellow
        } else {
            return new Color(231, 76, 60); // Red
        }
    }

}
