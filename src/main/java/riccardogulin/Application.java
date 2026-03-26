package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.AnimalsDAO;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d14pu");

	public static void main(String[] args) {
		EntityManager entityManager = emf.createEntityManager();
		AnimalsDAO ad = new AnimalsDAO(entityManager);

		Dog ringhio = new Dog("Ringhio", 4, 130);
		Dog rex = new Dog("Rex", 10, 10);
		Cat tom = new Cat("Tom", 2, 3);

		ad.save(ringhio);
		ad.save(rex);
		ad.save(tom);

	}
}
