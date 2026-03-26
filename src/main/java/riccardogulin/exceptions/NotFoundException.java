package riccardogulin.exceptions;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String uuid) {
		super("Record con id " + uuid + " non trovato");
	}
}
