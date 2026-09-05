# MediCare Hospital Management System

Java 17 Swing application with JDBC/MySQL persistence.

## Run

1. Create a MySQL database named `medicare_db` and execute `database/medicare_db.sql`.
2. Set your MySQL password for the current PowerShell session:
   `$env:MEDICARE_DB_PASSWORD = 'your-real-password'`
3. Run `./run.ps1` from the project folder.

The login screen authenticates active users from the `users` table. Sample credentials from the schema use password `password123`. If MySQL is unavailable, `admin / password123` opens a clearly labeled demo session so the Swing interface can still be previewed.
