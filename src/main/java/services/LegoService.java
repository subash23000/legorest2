package services;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import data.*;

// REST endpoints

// this is the REST api server for the lego robot system, it receives robot data, stores it in the database and provides endpoints to retrieve statistics and current values.
// here, ReadData and SendData connmunicates with this server.
@Path("/lego")
public class LegoService {
	EntityManagerFactory emf=Persistence.createEntityManagerFactory("lego");	
	
	@Path("/getlego")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLego() {
		return "Lego service Legorest2!";
	}
	
	
	@Path("/setvalues")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Lego setValues(Lego lego) {
	    EntityManager em=emf.createEntityManager();
	    em.getTransaction().begin();
	    em.persist(lego);
	    em.getTransaction().commit();		
		return lego;
	}
	@SuppressWarnings("unchecked")
	@Path("/getvalues")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getValues() {
	    EntityManager em=emf.createEntityManager();
	    em.getTransaction().begin();
		Query q=em.createQuery("select s from Lego s order by s.id desc").setMaxResults(1);
		List<Lego> list=q.getResultList();
		em.getTransaction().commit();
		Lego lego=list.get(0);
		return lego.getId()+"#"+lego.getRun()+"#"+lego.getSpeed()+"#"+lego.getTurn();
	}

	// robot snds values
	@Path("/setvalues/{run}/{speed}/{turn}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Lego setValuesFromRobot(@PathParam("run") int run,
    @PathParam("speed") int speed, @PathParam("turn") int turn) {

    Lego lego = new Lego();
    lego.setRun(run);
    lego.setSpeed(speed);
    lego.setTurn(turn);

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.persist(lego);
    em.getTransaction().commit();

    return lego;
}

// returns how many records exist in the database
	@Path("/count")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String count() {

    EntityManager em = emf.createEntityManager();

    Query q = em.createQuery("select count(l) from Lego l");

    return q.getSingleResult().toString();
}


// returns the average speed of all records in the database
	@Path("/averagespeed")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String averageSpeed() {

    EntityManager em = emf.createEntityManager();

    Query q = em.createQuery("select avg(l.speed) from Lego l");

    return q.getSingleResult().toString();
}

// returns highest recorded speed
	@Path("/maxspeed")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String maxSpeed() {
    EntityManager em = emf.createEntityManager();
    Query q = em.createQuery("select max(l.speed) from Lego l");
    return q.getSingleResult().toString();
}

// returns lowest recorded speed
	@Path("/minspeed")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String minSpeed() {
	EntityManager em = emf.createEntityManager();
	Query q = em.createQuery("select min(l.speed) from Lego l");
	return q.getSingleResult().toString();
	}
}
