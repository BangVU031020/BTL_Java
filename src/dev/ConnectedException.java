package dev;

public class ConnectedException extends Exception {

	private String mess;

	public ConnectedException() {
	}

	public ConnectedException(String message) {
		super(message);
	}

}
