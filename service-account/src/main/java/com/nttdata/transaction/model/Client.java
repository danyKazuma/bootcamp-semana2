package com.nttdata.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
public class Client extends PanacheEntity {
    @Column(length = 40,unique = true)
    public String name;
    private String lastName;
    private Long nroDocument;
    private int typeCustomer;
    private int typeDocument;
    private String active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public LocalDate fechaDeVencimiento;

    private static String getDateNow(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date).toString();
    }
}
