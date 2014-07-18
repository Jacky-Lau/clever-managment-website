package edu.zju.bme.clever.website.exception;

public class AppLibraryPersistException extends Exception {
	public AppLibraryPersistException() {
		super();
	}

	public AppLibraryPersistException(String message) {
		super(message);
	}

	public AppLibraryPersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppLibraryPersistException(Throwable cause) {
		super(cause);
	}
}
