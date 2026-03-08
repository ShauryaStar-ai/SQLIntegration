package LiquiBasePostgressSQL.shaurya.Model;

public class Student {
    private int id;
    private String name;
    private String emailAddress;

    public Student(int id, String name, String emailAddress) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
    }
    public Student( String name, String emailAddress) {
        this.id = -1;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public Student() {
        this.id = -1;
        this.name = "unknown";
        this.emailAddress = "notGiven";
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
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
}
