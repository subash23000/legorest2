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

}
