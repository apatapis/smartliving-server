package di.smartliving.server.web.rest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = RestException.class)
	protected ResponseEntity<String> resolve(RuntimeException ex, WebRequest request) {
		RestException restException = (RestException) ex;
		return new ResponseEntity<String>(restException.getMessage(), restException.getHttpStatus());
	}

}
