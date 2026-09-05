package ui;

import dao.UserDAO;
import model.User;
import util.DBConnection;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField(18);
    private final JPasswordField password = new JPasswordField(18);
    private final JLabel status = new JLabel(" ");
    public LoginFrame() {
        setTitle("MediCare | Secure sign in"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(880,540); setLocationRelativeTo(null);
        JPanel root = new JPanel(new GridLayout(1, 2));

        JPanel welcome = new JPanel(new GridBagLayout());
        welcome.setBackground(new Color(14, 55, 67));
        GridBagConstraints left = new GridBagConstraints(); left.gridx = 0; left.fill = GridBagConstraints.HORIZONTAL; left.insets = new Insets(8, 42, 8, 42);
        JLabel mark = new JLabel("+  MediCare"); mark.setForeground(new Color(236, 250, 246)); mark.setFont(new Font("SansSerif", Font.BOLD, 30));
        left.gridy = 0; welcome.add(mark, left);
        JLabel line = new JLabel("Care that feels human."); line.setForeground(new Color(117, 222, 198)); line.setFont(new Font("SansSerif", Font.BOLD, 25));
        left.gridy = 1; welcome.add(line, left);
        JTextArea copy = new JTextArea("One calm workspace for your\nhospital team, patients, and care."); copy.setEditable(false); copy.setOpaque(false); copy.setForeground(new Color(198, 221, 220)); copy.setFont(new Font("SansSerif", Font.PLAIN, 16));
        left.gridy = 2; left.insets = new Insets(20, 42, 8, 42); welcome.add(copy, left);
        JLabel note = new JLabel("SECURE HOSPITAL OPERATIONS"); note.setForeground(new Color(255, 190, 103)); note.setFont(new Font("SansSerif", Font.BOLD, 11));
        left.gridy = 3; left.insets = new Insets(45, 42, 8, 42); welcome.add(note, left);

        JPanel form = new JPanel(new GridBagLayout()); form.setBackground(new Color(248, 251, 250));
        GridBagConstraints right = new GridBagConstraints(); right.gridx = 0; right.weightx = 1; right.fill = GridBagConstraints.HORIZONTAL; right.insets = new Insets(6, 48, 6, 48);
        JLabel heading = new JLabel("Welcome back"); heading.setForeground(new Color(20, 43, 50)); heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        right.gridy = 0; form.add(heading, right);
        JLabel subheading = new JLabel("Sign in to continue to your workspace"); subheading.setForeground(new Color(91, 111, 115)); subheading.setFont(new Font("SansSerif", Font.PLAIN, 14));
        right.gridy = 1; right.insets = new Insets(0, 48, 28, 48); form.add(subheading, right);
        username.setFont(new Font("SansSerif", Font.PLAIN, 15)); username.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(190, 208, 207)), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        password.setFont(new Font("SansSerif", Font.PLAIN, 15)); password.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(190, 208, 207)), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        right.gridy = 2; right.insets = new Insets(5, 48, 2, 48); form.add(new JLabel("USERNAME"), right);
        right.gridy = 3; form.add(username, right);
        right.gridy = 4; right.insets = new Insets(14, 48, 2, 48); form.add(new JLabel("PASSWORD"), right);
        right.gridy = 5; right.insets = new Insets(5, 48, 14, 48); form.add(password, right);
        JButton signIn = new JButton("Sign in  〉"); signIn.setFont(new Font("SansSerif", Font.BOLD, 15)); signIn.setForeground(Color.WHITE); signIn.setBackground(new Color(17, 132, 117)); signIn.setFocusPainted(false); signIn.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18)); signIn.addActionListener(e -> login());
        right.gridy = 6; right.insets = new Insets(5, 48, 12, 48); form.add(signIn, right);
        status.setFont(new Font("SansSerif", Font.PLAIN, 12)); status.setForeground(new Color(172, 94, 28));
        if (!DBConnection.testConnection()) status.setText("Database not connected  ·  Demo login available");
        right.gridy = 7; right.insets = new Insets(8, 48, 6, 48); form.add(status, right);
        JLabel footer = new JLabel("MediCare Hospital Management System"); footer.setForeground(new Color(125, 143, 144)); footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        right.gridy = 8; right.insets = new Insets(24, 48, 8, 48); form.add(footer, right);
        root.add(welcome); root.add(form); add(root);
        getRootPane().setDefaultButton(signIn);
    }
    private void login(){
        try { User user=new UserDAO().authenticate(username.getText().trim(),new String(password.getPassword())); if(user==null){status.setText("Invalid username or password.");return;} dispose(); new DashboardFrame(user).setVisible(true); }
        catch(Exception ex){
            if ("admin".equals(username.getText().trim())
                    && "password123".equals(new String(password.getPassword()))) {
                User demoUser = new User("admin", "", "", "ADMIN",
                        "System Administrator", "admin@medicare.com", "9000000001");
                dispose();
                new DashboardFrame(demoUser).setVisible(true);
                return;
            }
            String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
            status.setText("Database unavailable. Demo login: admin / password123");
        }
    }
}
