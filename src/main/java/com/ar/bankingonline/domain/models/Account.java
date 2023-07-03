package com.ar.bankingonline.domain.models;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private int number;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

}
