package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type")

/*

- SINGLE TABLE: è la più semplice. Genera una sola tabella con tutti gli animali dentro
La comodità è quella di avere tutti gli animali in un unico posto quindi non serve che vengano fatti dei JOIN per reperire tutti
i dati degli animali, quindi in generale potrebbe essere più performante delle altre strategie

Di contro potrebbe però portare ad una tabella molto disordinata, una sorta di "colabrodo" con tanti valori null sparsi al suo interno
(questo dipende da quanto sono diversi tra loro i figli e quanti figli ci sono). Questo significa non poter neanche inserire quindi
dei vincoli NON NULL

- JOINED: otteniamo una tabella per la classe padre con tutti gli attributi in comune a tutta la gerarchia e otteniamo anche una tabella
per ogni figlio contenente solo gli attributi specifici di ogni figlio. Nel nostro caso: 1 padre 2 figli --> 3 tabelle

Il vantaggio è quello di ottenere una struttura più pulita e consistente e di non avere il problema dei valori null, quindi posso anche
utilizzare a piacimento dei vincoli NON NULL

Di contro però, con la joined se volessi ottenere i dati completi di un certo animale dovrei utilizzare dei JOIN (lo farà JPA dietro le quinte)

- TABLE PER CLASS: si potrebbe chiamare anche TABLE_PER_CONCRETE_CLASS perché crea tabelle solo per le classi concrete e non per quelle astratte
Nel nostro caso ha creato una tabella per i cats e una per i dogs. Non ha i problemi di null della single table e non ha i problemi di join
della joined, quindi è una scelta sicuramente pulita

I contro di questa strategia sono particolarmente pesanti, sia in termini di prestazioni che di relazioni.
Quando eseguo ad esempio delle queries che coinvolgano tutti gli animali, il DB dovrà fare del super lavoro extra dietro le quinte per
recuperare tutti i cani poi tutti i gatti e metterli assieme in una sorta di "tabella virtuale" che poi ordinerò/filtrerò ecc ecc..
Un secondo problema importante sono le relazioni, perché se ci dovesse essere una terza tabella da mettere in relazione con gli animali,
cioè se questa tabella dovesse avere una FK che deve puntare ad una PK degli animali, questo non si potrebbe fare a livello di DB in quanto
le relazioni non possono biforcarsi (in realtà JPA è in grado di gestire questa situazione simulando questo tipo di relazione ma facendo delle
mosse distruttive a livello di performance)

*/

public abstract class Animal {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private int age;

	protected Animal() {
	}

	public Animal(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Animal{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
