package khachatrian.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import khachatrian.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Textbook> textbooks = new LinkedList<>();

    public Order (User user, OrderStatus s) {
        this.user = user;
        this.status = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        for (int i = 0; i < textbooks.size(); i++) {
            if(!Objects.equals(textbooks.get(i), order.textbooks.get(i)))
                return false;
        }
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, textbooks);
    }
}


