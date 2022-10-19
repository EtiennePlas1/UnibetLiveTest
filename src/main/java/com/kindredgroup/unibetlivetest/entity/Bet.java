package com.kindredgroup.unibetlivetest.entity;

import com.kindredgroup.unibetlivetest.types.BetState;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "bet")
@Entity
@Data
@Accessors(chain = true)
public class Bet {
    
    public Bet() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private int name;

    @Column(name = "mise")
    private BigDecimal mise;
    
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name="selection_id", nullable=false)
    private Selection selection;

    @Column(name = "state")
    private BetState betState;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;


}
