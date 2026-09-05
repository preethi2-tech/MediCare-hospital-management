import ui.LoginFrame;
import util.DBConnection;
import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "--check-db".equals(args[0])) {
            try {
                DBConnection.getConnection().close();
                System.out.println("DATABASE_OK");
            } catch (SQLException ex) {
                System.err.println("DATABASE_ERROR: " + ex.getMessage());
                System.exit(1);
            }
            return;
        }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
