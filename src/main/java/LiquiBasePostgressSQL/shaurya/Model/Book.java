package LiquiBasePostgressSQL.shaurya.Model;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Book {
    private int id;
    private String name;
    private int ownerId;

    public Book(String name, int ownerId) {
        this.id = -1;
        this.name = name;
        this.ownerId = ownerId;
    }
}
