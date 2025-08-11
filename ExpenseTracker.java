import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker extends Frame implements ActionListener {
    
    private CardLayout cardLayout;
    private Panel mainPanel;
    
    
    private TextField amountField;
    private Choice categoryChoice;
    private TextArea descriptionArea;
    private Button addExpenseBtn;
    

    private TextArea viewExpensesArea;
    private Button refreshViewBtn;
    

    private TextArea summaryArea;
    private Button refreshSummaryBtn;
    

    private Button addExpenseNavBtn, viewExpensesNavBtn, summaryNavBtn;
    private static final String EXPENSES_FILE = "expenses.txt";
    
    
    class Expense {
        String amount;
        String category;
        String description;
        
        public Expense(String amount, String category, String description) {
            this.amount = amount;
            this.category = category;
            this.description = description;
        }
        
        @Override
        public String toString() {
            return "Amount: $" + amount + " | Category: " + category + " | Description: " + description;
        }
    }
    
    public ExpenseTracker() {
        setupUI();
        setupEventHandlers();
        setVisible(true);
    }
    
    private void setupUI() {
        setTitle("Expense Tracker");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        
        Panel navPanel = new Panel(new FlowLayout());
        navPanel.setBackground(new Color(240, 240, 240));
        
        addExpenseNavBtn = new Button("Add Expense");
        viewExpensesNavBtn = new Button("View Expenses");
        summaryNavBtn = new Button("Expense Summary");
        
        navPanel.add(addExpenseNavBtn);
        navPanel.add(viewExpensesNavBtn);
        navPanel.add(summaryNavBtn);
        
        add(navPanel, BorderLayout.NORTH);
        
      
        cardLayout = new CardLayout();
        mainPanel = new Panel(cardLayout);
        
    
        mainPanel.add(createAddExpensePanel(), "ADD_EXPENSE");
        mainPanel.add(createViewExpensesPanel(), "VIEW_EXPENSES");
        mainPanel.add(createSummaryPanel(), "SUMMARY");
        
        add(mainPanel, BorderLayout.CENTER);
        
        cardLayout.show(mainPanel, "ADD_EXPENSE");
        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
    
    private Panel createAddExpensePanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
       
        Label titleLabel = new Label("Add New Expense", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
  
        Panel formPanel = new Panel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new Label("Amount ($):"), gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        amountField = new TextField(20);
        formPanel.add(amountField, gbc);
        
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new Label("Category:"), gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        categoryChoice = new Choice();
        categoryChoice.add("Food");
        categoryChoice.add("Transportation");
        categoryChoice.add("Entertainment");
        categoryChoice.add("Utilities");
        categoryChoice.add("Healthcare");
        categoryChoice.add("Shopping");
        categoryChoice.add("Education");
        categoryChoice.add("Other");
        formPanel.add(categoryChoice, gbc);
        
 
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new Label("Description:"), gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0.5;
        descriptionArea = new TextArea(4, 20);
        formPanel.add(descriptionArea, gbc);
        
       
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        addExpenseBtn = new Button("Add Expense");
        addExpenseBtn.setBackground(new Color(76, 175, 80));
        addExpenseBtn.setForeground(Color.WHITE);
        formPanel.add(addExpenseBtn, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private Panel createViewExpensesPanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
     
        Label titleLabel = new Label("View All Expenses", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
      
        viewExpensesArea = new TextArea();
        viewExpensesArea.setEditable(false);
        viewExpensesArea.setBackground(new Color(250, 250, 250));
        panel.add(viewExpensesArea, BorderLayout.CENTER);
        
   
        Panel buttonPanel = new Panel(new FlowLayout());
        refreshViewBtn = new Button("Refresh");
        refreshViewBtn.setBackground(new Color(33, 150, 243));
        refreshViewBtn.setForeground(Color.WHITE);
        buttonPanel.add(refreshViewBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private Panel createSummaryPanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        
        Label titleLabel = new Label("Expense Summary", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        
        summaryArea = new TextArea();
        summaryArea.setEditable(false);
        summaryArea.setBackground(new Color(250, 250, 250));
        panel.add(summaryArea, BorderLayout.CENTER);
        
        
        Panel buttonPanel = new Panel(new FlowLayout());
        refreshSummaryBtn = new Button("Refresh Summary");
        refreshSummaryBtn.setBackground(new Color(156, 39, 176));
        refreshSummaryBtn.setForeground(Color.WHITE);
        buttonPanel.add(refreshSummaryBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        
        addExpenseNavBtn.addActionListener(this);
        viewExpensesNavBtn.addActionListener(this);
        summaryNavBtn.addActionListener(this);
        
        
        addExpenseBtn.addActionListener(this);
        refreshViewBtn.addActionListener(this);
        refreshSummaryBtn.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == addExpenseNavBtn) {
            cardLayout.show(mainPanel, "ADD_EXPENSE");
        } else if (source == viewExpensesNavBtn) {
            cardLayout.show(mainPanel, "VIEW_EXPENSES");
            loadAndDisplayExpenses();
        } else if (source == summaryNavBtn) {
            cardLayout.show(mainPanel, "SUMMARY");
            generateSummary();
        } else if (source == addExpenseBtn) {
            addExpense();
        } else if (source == refreshViewBtn) {
            loadAndDisplayExpenses();
        } else if (source == refreshSummaryBtn) {
            generateSummary();
        }
    }
    
    private void addExpense() {
        String amount = amountField.getText().trim();
        String category = categoryChoice.getSelectedItem();
        String description = descriptionArea.getText().trim();
        
        
        if (amount.isEmpty() || description.isEmpty()) {
            showMessage("Please fill in all fields!");
            return;
        }
        
        try {
            
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            showMessage("Please enter a valid amount!");
            return;
        }
        
      
        try (FileWriter fw = new FileWriter(EXPENSES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write(amount + "|" + category + "|" + description);
            bw.newLine();
            
            showMessage("Expense added successfully!");
           
            amountField.setText("");
            descriptionArea.setText("");
            categoryChoice.select(0);
            
        } catch (IOException e) {
            showMessage("Error saving expense: " + e.getMessage());
        }
    }
    
    private void loadAndDisplayExpenses() {
        StringBuilder sb = new StringBuilder();
        
        try (FileReader fr = new FileReader(EXPENSES_FILE);
             BufferedReader br = new BufferedReader(fr)) {
            
            String line;
            int count = 1;
            
            sb.append("=== ALL EXPENSES ===\n\n");
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    sb.append(count++).append(". ");
                    sb.append("Amount: $").append(parts[0]);
                    sb.append(" | Category: ").append(parts[1]);
                    sb.append(" | Description: ").append(parts[2]);
                    sb.append("\n\n");
                }
            }
            
            if (count == 1) {
                sb.append("No expenses found. Add some expenses first!");
            }
            
        } catch (FileNotFoundException e) {
            sb.append("No expenses file found. Add some expenses first!");
        } catch (IOException e) {
            sb.append("Error reading expenses: ").append(e.getMessage());
        }
        
        viewExpensesArea.setText(sb.toString());
    }
    
    private void generateSummary() {
        List<Expense> expenses = loadExpenses();
        
        if (expenses.isEmpty()) {
            summaryArea.setText("No expenses found. Add some expenses first!");
            return;
        }
        
        
        double totalAmount = 0;
        java.util.Map<String, Double> categoryTotals = new java.util.HashMap<>();
        
        for (Expense expense : expenses) {
            try {
                double amount = Double.parseDouble(expense.amount);
                totalAmount += amount;
                
                categoryTotals.merge(expense.category, amount, Double::sum);
            } catch (NumberFormatException e) {
                
            }
        }
        
        // Generate summary
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        sb.append("=== EXPENSE SUMMARY ===\n\n");
        sb.append("Total Expenses: $").append(df.format(totalAmount)).append("\n");
        sb.append("Number of Expenses: ").append(expenses.size()).append("\n\n");
        
        sb.append("=== BY CATEGORY ===\n\n");
        for (java.util.Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() / totalAmount) * 100;
            sb.append(entry.getKey()).append(": $").append(df.format(entry.getValue()));
            sb.append(" (").append(df.format(percentage)).append("%)\n");
        }
        
        summaryArea.setText(sb.toString());
    }
    
    private List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        
        try (FileReader fr = new FileReader(EXPENSES_FILE);
             BufferedReader br = new BufferedReader(fr)) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    expenses.add(new Expense(parts[0], parts[1], parts[2]));
                }
            }
            
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }
        
        return expenses;
    }
    
    private void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        
        Label messageLabel = new Label(message, Label.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);
        
        Button okButton = new Button("OK");
        okButton.addActionListener(e -> dialog.dispose());
        
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.dispose();
            }
        });
        
        dialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ExpenseTracker();
    }
}