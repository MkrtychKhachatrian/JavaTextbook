package khachatrian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_user")
public class RoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @NotBlank
    private String name;
    @Min(1)
    private int levelAccess;

    public RoleUser (String name, int levelAccess) {
        this.name = name;
        this.levelAccess = levelAccess;
    }
}
