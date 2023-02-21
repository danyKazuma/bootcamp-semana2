package com.nttdata.api;

import com.nttdata.repository.TransactionRepository;
import com.nttdata.transaction.model.Client;
import com.nttdata.transaction.model.Transaction;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import io.smallrye.mutiny.Multi;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
@Path("/transactions")
@Consumes("application/json")
@Produces("application/json")
public class TransactionResource {
    @Inject
    TransactionRepository repository;
    private final ReactiveMongoClient reactiveMongoClient;

    public TransactionResource(ReactiveMongoClient reactiveMongoClient) {
        this.reactiveMongoClient = reactiveMongoClient;
    }

    private ReactiveMongoCollection<Document> getCollection() {
        return reactiveMongoClient.getDatabase("bancodb").getCollection("Transaction");
    }

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
    @GET
    @Path("/uni/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Client> findByNroDocument(Client client) {
        ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("bancodb");
        ReactiveMongoCollection<Document> collection = database.getCollection("Transaction");
        return collection
                .find(new Document("nroDocument", client.getNroDocument())).map(doc->{
                    Client customer = new Client();
                    customer.setName(doc.getString("name"));
                    customer.setLastName(doc.getString("lastName"));
                    //customer.setActive(doc.getString("active"));
                    return customer;
                }).filter(s->s.getActive().equals("S")).toUni();
    }
    @GET
    @Path("/multi/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Multi<Client> list() {
        return getCollection().find().map(doc->{
            Client customer = new Client();
            customer.setName(doc.getString("name"));
            customer.setLastName(doc.getString("lastName"));
            customer.setNroDocument(doc.getString("nroDocument"));
            customer.setTypeDocument(doc.getString("typeDocument"));
            return customer;
        });
    }

}
