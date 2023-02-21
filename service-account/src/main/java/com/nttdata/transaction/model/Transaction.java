package com.nttdata.transaction.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.time.LocalDate;

public class Transaction extends PanacheMongoEntity {
    public String account;
    public Double value;
    public LocalDate date;
    public tipoCliente type;
    public String description;
    public String active;
    public String name;
    public String lastName;
    public String nroDocument;
    public String typeDocument;

    public Transaction(){}
    public String getAccount(){
        return account;
    }
    public void setAccount(){
        this.account=account;
    }
    public Double getValue(){
        return value;
    }
    public void setValue(Double value){
        this.value=value;
    }
    public tipoCliente getType(){
        return type;
    }
    public void setType(tipoCliente type){
        this.type=type;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date){
        this.date=date;
    }
    public String getActive(){return active;}

    public void setAccount(String account) {
        this.account = account;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNroDocument() {
        return nroDocument;
    }

    public void setNroDocument(String nroDocument) {
        this.nroDocument = nroDocument;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }
}
