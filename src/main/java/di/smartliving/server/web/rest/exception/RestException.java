package di.smartliving.server.web.rest.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	private RestException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public static RestException create(HttpStatus httpStatus, String message) {
		return new RestException(httpStatus, message);
	}

}
