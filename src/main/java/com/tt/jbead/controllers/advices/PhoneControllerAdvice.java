package com.tt.jbead.controllers.advices;

import com.tt.jbead.controllers.PhoneController;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.exceptions.NotFoundExceptionCity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Ha valami Contorller-ből kirepül egy Exception, akkor ez el tudja kapni, és lekezeli
 */

// Bean lesz így belőle, a Spring meg másrészt tudni fogja, hogy ő azért felelős, hogy Controlleres Exceptionokat kapjon el
@RestControllerAdvice(assignableTypes = PhoneController.class)          // (assignableTypes = MovieController.class) :: e nélkül az összes Controller fájlért ő felel
public class PhoneControllerAdvice {

    @ExceptionHandler(value = NotFoundExceptionCity.class)                                                 // Ebből tudja, hogy Exceptiont kell kezelnie;;  ő csak ezért felelős : MovieNotFoundException.class
    public ResponseEntity<Void> handleCityNotFoundException(){
        return ResponseEntity.badRequest().build();                                                         // 404-es HTTP status code;   build() : üres response body-val
    }

    @ExceptionHandler(value = InvalidEntityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCityException(InvalidEntityException exception){      // az Exceptiont úgy kapom el, hogy ide beírom paraméterként
        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return null;
            }

            @Override
            public ProblemDetail getBody() {
                return null;
            }
        };   //exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);

    }
}