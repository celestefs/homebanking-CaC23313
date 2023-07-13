package com.ar.bankingonline.domain.models;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    private User owner;

}
