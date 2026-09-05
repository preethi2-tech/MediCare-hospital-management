package ui;

import model.User;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private static final Color INK = new Color(18, 55, 63);
    private static final Color TEAL = new Color(18, 137, 119);
    private static final Color MIST = new Color(241, 247, 245);

    public DashboardFrame(User user) {
        setTitle("MediCare | Workspace"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1080, 680); setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout()); root.setBackground(MIST);
        root.add(sidebar(user), BorderLayout.WEST); root.add(workspace(user), BorderLayout.CENTER); setContentPane(root);
    }

    private JPanel sidebar(User user) {
        JPanel panel = new JPanel(); panel.setPreferredSize(new Dimension(235, 0)); panel.setBackground(INK); panel.setBorder(BorderFactory.createEmptyBorder(28, 20, 24, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("+  MediCare"); brand.setForeground(Color.WHITE); brand.setFont(new Font("SansSerif", Font.BOLD, 25)); panel.add(brand);
        JLabel descriptor = new JLabel("HOSPITAL WORKSPACE"); descriptor.setForeground(new Color(126, 207, 193)); descriptor.setFont(new Font("SansSerif", Font.BOLD, 10)); descriptor.setBorder(BorderFactory.createEmptyBorder(7, 2, 35, 0)); panel.add(descriptor);
        String[] modules = {"Overview", "Patients", "Appointments", "Doctors", "Medical records", "Prescriptions", "Billing", "Reports"};
        for (String module : modules) {
            JButton button = new JButton(module); button.setAlignmentX(Component.LEFT_ALIGNMENT); button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); button.setHorizontalAlignment(SwingConstants.LEFT); button.setFont(new Font("SansSerif", Font.PLAIN, 14)); button.setForeground(module.equals("Overview") ? INK : new Color(218, 235, 232)); button.setBackground(module.equals("Overview") ? new Color(153, 226, 209) : INK); button.setBorder(BorderFactory.createEmptyBorder(0, 13, 0, 8)); button.setFocusPainted(false); button.addActionListener(e -> openModule(module)); panel.add(button); panel.add(Box.createVerticalStrut(7));
        }
        panel.add(Box.createVerticalGlue()); JLabel account = new JLabel("Signed in as " + user.getRole()); account.setForeground(new Color(164, 193, 191)); account.setFont(new Font("SansSerif", Font.PLAIN, 12)); panel.add(account);
        return panel;
    }

    private JPanel workspace(User user) {
        JPanel panel = new JPanel(new BorderLayout(0, 25)); panel.setBackground(MIST); panel.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        JLabel welcome = new JLabel("Good morning, " + user.getFullName()); welcome.setForeground(INK); welcome.setFont(new Font("SansSerif", Font.BOLD, 28)); top.add(welcome, BorderLayout.WEST);
        JLabel date = new JLabel("TODAY  ·  HOSPITAL OPERATIONS"); date.setForeground(new Color(92, 123, 125)); date.setFont(new Font("SansSerif", Font.BOLD, 11)); top.add(date, BorderLayout.EAST); panel.add(top, BorderLayout.NORTH);
        JPanel content = new JPanel(); content.setOpaque(false); content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JLabel overview = new JLabel("Overview"); overview.setForeground(new Color(83, 111, 113)); overview.setFont(new Font("SansSerif", Font.PLAIN, 15)); content.add(overview); content.add(Box.createVerticalStrut(13));
        JPanel cards = new JPanel(new GridLayout(1, 3, 14, 0)); cards.setOpaque(false); cards.add(metric("PATIENTS", "02", "Registered today", new Color(219, 240, 232))); cards.add(metric("APPOINTMENTS", "01", "Scheduled today", new Color(226, 237, 247))); cards.add(metric("PENDING BILLS", "01", "Needs attention", new Color(250, 235, 207))); content.add(cards); content.add(Box.createVerticalStrut(28));
        JPanel action = new JPanel(new BorderLayout(18, 0)); action.setBackground(Color.WHITE); action.setBorder(BorderFactory.createEmptyBorder(25, 26, 25, 26)); JLabel actionTitle = new JLabel("Keep care moving"); actionTitle.setForeground(INK); actionTitle.setFont(new Font("SansSerif", Font.BOLD, 20)); action.add(actionTitle, BorderLayout.NORTH); JLabel actionText = new JLabel("Open a workspace section to review records, coordinate visits, or manage billing."); actionText.setForeground(new Color(95, 117, 118)); actionText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); action.add(actionText, BorderLayout.CENTER); JButton patients = new JButton("View patients  〉"); patients.setForeground(Color.WHITE); patients.setBackground(TEAL); patients.setFont(new Font("SansSerif", Font.BOLD, 13)); patients.setFocusPainted(false); patients.setBorder(BorderFactory.createEmptyBorder(11, 15, 11, 15)); patients.addActionListener(e -> openModule("Patients")); action.add(patients, BorderLayout.EAST); content.add(action); panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel metric(String label, String value, String caption, Color color) { JPanel card = new JPanel(); card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); card.setBackground(color); card.setBorder(BorderFactory.createEmptyBorder(18, 19, 18, 19)); JLabel heading = new JLabel(label); heading.setForeground(new Color(67, 104, 102)); heading.setFont(new Font("SansSerif", Font.BOLD, 10)); card.add(heading); JLabel number = new JLabel(value); number.setForeground(INK); number.setFont(new Font("SansSerif", Font.BOLD, 31)); card.add(number); JLabel foot = new JLabel(caption); foot.setForeground(new Color(88, 117, 116)); foot.setFont(new Font("SansSerif", Font.PLAIN, 12)); card.add(foot); return card; }

    private void openModule(String module) {
        switch (module) { case "Patients" -> new PatientFrame().setVisible(true); case "Appointments" -> new AppointmentFrame().setVisible(true); case "Doctors" -> new DoctorFrame().setVisible(true); case "Medical records" -> new MedicalRecordFrame().setVisible(true); case "Prescriptions" -> new PrescriptionFrame().setVisible(true); case "Billing" -> new BillingFrame().setVisible(true); case "Reports" -> new ReportsFrame().setVisible(true); }
    }
}
