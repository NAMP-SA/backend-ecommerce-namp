package com.namp.ecommerce.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name= "\"order\"")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrder")
    private long idOrder;   

    @NotNull(message = "La fecha y hora no puede ser vacío")
    private Timestamp  dateTime;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "idUser")
    private User idUser; 

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_state", referencedColumnName = "idState")
    private State idState; 
    
    @OneToMany(mappedBy = "idOrder", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    public double calculateTotal() {
        return this.orderDetail.stream()
                .mapToDouble(OrderDetail::getSubTotal)
                .sum();
    }

    
}
