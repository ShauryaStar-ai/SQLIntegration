package LiquiBasePostgressSQL.shaurya.Model;

public class Student {
    private int id;
    private String name;
    private String emailAddress;
    private int bankAccNum;
    private double balance;

    public Student() {
        this.id = -1;
        this.bankAccNum = -1;
    }

    public Student( String name, String emailAddress, double balance) {
        this.id = -1;
        this.name = name;
        this.emailAddress = emailAddress;
        this.bankAccNum = -1;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", bankAccNum=" + bankAccNum +
                ", balance=" + balance +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getBankAccNum() {
        return bankAccNum;
    }

    public void setBankAccNum(int bankAccNum) {
        this.bankAccNum = bankAccNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
