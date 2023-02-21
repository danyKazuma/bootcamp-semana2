package com.nttdata.repository;

import com.nttdata.transaction.model.Transaction;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheMongoRepository<Transaction> {

    public List<Transaction> findByAccount(String account){
        return list("account", account);
    }
    public List<Transaction> findByAccountAndDate(String account, LocalDateTime date){
        return find("account = ?1 and date =?2",account,date).list();
    }
    public List<Transaction> findByDescription(String description){
        String buscar= "(?i).*" + description + ".*";
        return find("description like ?1",buscar).list();
    }
}
