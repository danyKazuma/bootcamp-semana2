package com.nttdata.api;

import com.nttdata.repository.TransactionRepository;
import com.nttdata.transaction.model.Transaction;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/transactions")
@Consumes("application/json")
@Produces("application/json")
public class TransactionResource {
    @Inject
    TransactionRepository repository;

    @POST
    public Transaction addTransaction(Transaction tx) {
        repository.persist(tx);
        return tx;
    }

    @GET
    @Path("/{account}")
    public List<Transaction> getTransactions(@PathParam("account") String account) {
        return repository.findByAccount(account);
    }

    @GET
    @Path("/{account}/date/{date}")
    public List<Transaction> getTransactionsByDate(@PathParam("account") String account, @PathParam("date") String dateString) {
        LocalDateTime localDate = LocalDateTime.parse(dateString + "T00:00:00");
        return repository.findByAccountAndDate(account, localDate);
    }

    @GET
    @Path("/search/{word}")
    public List<Transaction> getByDescription(@PathParam("word") String word) {
        return repository.findByDescription(word);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id")String id, Transaction transaction){
        Transaction tx=repository.findById(new ObjectId(id));
        if(tx!=null){
            transaction.id=new ObjectId(id);
            repository.update(transaction);
            return Response.status(200).build();
        }
        return Response.status(404).entity("Identificador "+ id+ " no encontrado").build();
    }
}
