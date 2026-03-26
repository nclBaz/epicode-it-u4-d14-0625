package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Animal;

public class AnimalsDAO {
	private final EntityManager em;

	public AnimalsDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Animal newAnimal) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newAnimal);

		transaction.commit();

		System.out.println("L'animale " + newAnimal.getName() + " è stato salvato correttamente!");
	}
}
