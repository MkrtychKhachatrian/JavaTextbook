package khachatrian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Color;
import khachatrian.model.enums.Size;
import khachatrian.model.enums.Material;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "textbook")
public class Textbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private double price;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private Material material;

    public Textbook(String name, double price, Size s, Color c, Subject sub, Material type) {
        this.name = name;
        this.price = price;
        this.size = s;
        this.color = c;
        this.subject = sub;
    }

}
