import java.io.*;
import java.util.*;

public class Account {
    private static final String CSV_FILE = "accounts.csv";
    private String username;
    private String password;
    private double balance;
    private List<String> transactions;

    public Account(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Password verification
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    // Deposit method
    public void deposit(double amount, String bankName, String accountNumber) {
        balance += amount;
        String transaction = "Deposited " + amount + " via " + bankName + " (" + accountNumber + ")";
        transactions.add(transaction);
        System.out.println(transaction);
    }

    // Withdrawal method
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            String transaction = "Withdrew " + amount;
            transactions.add(transaction);
            System.out.println(transaction);
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    // Transaction history
    public List<String> getTransactions() {
        return transactions;
    }

    public double getBalance(){
        return this.balance;
    }
     
    // Method to update balance directly
     public void updateBalance(double amount) {
        balance += amount;  // Adjust the balance by the amount
        String transaction = (amount >= 0) ? "Added " + amount : "Deducted " + (-amount); // Record the change
        transactions.add(transaction);
        System.out.println(transaction);
    }

    
    // Save accounts to CSV
    public static void saveAccounts(List<Account> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Account account : accounts) {
                writer.write(account.username + "," + account.password + "," + account.balance);
                writer.newLine();
            }
        }
    }

    // Load accounts from CSV
    public static List<Account> loadAccounts() throws IOException {
        List<Account> accounts = new ArrayList<>();
        File file = new File(CSV_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.add(new Account(username, password, balance));
                }
            }
        }
        return accounts;
    }
}
