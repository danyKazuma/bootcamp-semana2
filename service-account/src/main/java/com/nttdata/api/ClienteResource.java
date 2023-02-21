package com.nttdata.api;

import com.nttdata.transaction.model.Client;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.bson.Document;
import java.time.LocalDate;

@Path("/cliente")
@ApplicationScoped
public class ClienteResource {
    private final ReactiveMongoClient reactiveMongoClient;
    public ClienteResource(ReactiveMongoClient reactiveMongoClient) {
        this.reactiveMongoClient = reactiveMongoClient;
    }
    @GET
    @Path("/{id}")
    public Uni<Client> addCustomer(Client client) {
        Document document = new Document()
                .append("name", client.getName())
                .append("lastName", client.getLastName())
                .append("nroDocument", client.getNroDocument())
                .append("typeCustomer", client.getTypeCustomer())
                .append("typeDocument", client.getTypeDocument())
                .append("fechaDeVencimiento", client.getFechaDeVencimiento())
                .append("active", "S");
        return getCollection().insertOne(document).replaceWith(client);
    }
    private ReactiveMongoCollection<Document> getCollection() {
        return reactiveMongoClient.getDatabase("customers").getCollection("customer");
    }

    @GET
    @Path("/listado/{id}")
    public Multi<Client> list() {
        return getCollection().find().map(doc->{
            Client customer = new Client();
            customer.setName(doc.getString("name"));
            customer.setLastName(doc.getString("lastName"));
            customer.setNroDocument(doc.getLong("nroDocument"));
            customer.setTypeCustomer(doc.getInteger("typeCustomer"));
            customer.setTypeDocument(doc.getInteger("typeDocument"));
            customer.setFechaDeVencimiento(LocalDate.parse(doc.getString("fechaDeVencimiento")));
            customer.setActive(doc.getString("active"));
            return customer;
        }).filter(customer->{
            return customer.getActive().equals("S");
        });
    }
}
