package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.entities.Owner;
import riccardogulin.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

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

	public Animal findById(String animalId) {
		Animal found = em.find(Animal.class, UUID.fromString(animalId));
		// SELECT * FROM animals WHERE id = :animalId <-- SINGLE TABLE
		// SELECT * FROM animals JOIN dogs WHERE id = :animalId <-- JOINED
		// SELECT * FROM animals UNION ALL ... WHERE id = :animalId <-- TABLE PER CLASS
		if (found == null) throw new NotFoundException(animalId);
		return found;
	}

	public Dog findDogById(String dogID) {
		Dog found = em.find(Dog.class, UUID.fromString(dogID));
		// SELECT * FROM animals WHERE id = :dogID AND animal_type = 'cane' <-- SINGLE TABLE
		// SELECT * FROM animals JOIN dogs ... WHERE id = :dogID <-- JOINED
		// SELECT * FROM dogs WHERE id = :dogID <-- TABLE PER CLASS
		if (found == null) throw new NotFoundException(dogID);
		return found;
	}

	public Cat findCatById(String catID) {
		Cat found = em.find(Cat.class, UUID.fromString(catID));
		// SELECT * FROM animals WHERE id = :catID AND animal_type = 'gatto' <-- SINGLE TABLE
		// SELECT * FROM animals JOIN dogs ... WHERE id = :catID <-- JOINED
		// SELECT * FROM cats WHERE id = :catID <-- TABLE PER CLASS
		if (found == null) throw new NotFoundException(catID);
		return found;
	}

	public List<Animal> findAllAnimals() {
		TypedQuery<Animal> query = em.createNamedQuery("findAllAnimals", Animal.class);
		// La query equivale a:
		// - SELECT * FROM animals <-- SINGLE TABLE
		// - Come sopra con in più le JOIN per ottenere i dati di cani e gatti <-- JOINED
		// - SELECT * FROM dogs poi SELECT * FROM cats e unisce tutto con UNION ALL <-- TABLE PER CLASS
		return query.getResultList();
	}

	public List<Dog> findAllDogs() {
		TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
		// La query equivale a:
		// - SELECT * FROM animals WHERE animal_type = 'cane'  <-- SINGLE TABLE
		// - Come sopra con in più la JOIN su dogs  <-- JOINED
		// - SELECT * FROM dogs <-- TABLE PER CLASS
		return query.getResultList();
	}

	public List<Animal> findAnimalsNameStartsWith(String partialName) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(:parametro)", Animal.class);
		query.setParameter("parametro", partialName + "%");
		return query.getResultList();
	}

	public void findAnimalsByNameAndUpdateName(String currentName, String newName) {
		// Siccome qua stiamo SCRIVENDO e non semplicemente LEGGENDO, le operazioni di update dovranno essere collocate in una transazione
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Query query = em.createQuery("UPDATE Animal a SET a.name = :newName WHERE a.name = :currName ");
		query.setParameter("newName", newName);
		query.setParameter("currName", currentName);

		query.executeUpdate(); // <-- Eseguo effettivamente la query di update

		transaction.commit();

		System.out.println("Aggiornamento fatto!");
	}

	public void findAnimalsByNameAndDelete(String name) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Query query = em.createQuery("DELETE FROM Animal a WHERE a.name = :name");
		query.setParameter("name", name);

		query.executeUpdate(); // <-- Eseguo effettivamente la query di delete

		transaction.commit();

		System.out.println("Cancellati!");
	}

	public List<Animal> findAnimalsByOwnersName(String name) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.owner.name = :name", Animal.class);
		// Tramite la Dot Notation di JPQL posso addirittura attraversare le relazioni andando ad accedere agli attributi delle entità collegate
		query.setParameter("name", name);
		return query.getResultList();
	}

	public List<Animal> findAnimalsByOwner(Owner owner) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.owner = :owner", Animal.class);
		query.setParameter("owner", owner);
		return query.getResultList();
	}


}
