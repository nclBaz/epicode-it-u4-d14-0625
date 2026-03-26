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

//		ad.save(ringhio);
//		ad.save(rex);
//		ad.save(tom);

//		try {
//			Animal animal = ad.findById("a82ab3c2-b6f7-40c6-a702-81c180a366e2");
//			System.out.println(animal);
//
//			Dog dog = ad.findDogById("a82ab3c2-b6f7-40c6-a702-81c180a366e2");
//			System.out.println(dog);
//
//
//		} catch (NotFoundException ex) {
//			System.out.println(ex.getMessage());
//		}

//		ad.findAllAnimals().forEach(System.out::println);

//		ad.findAnimalsNameStartsWith("re").forEach(System.out::println);

//		ad.findAnimalsByNameAndUpdateName("Rex", "Gianfranco");


		// ad.findAnimalsByNameAndDelete("Gianfranco");
		ad.findAnimalsByOwnersName("Aldo").forEach(System.out::println);
	}
}
