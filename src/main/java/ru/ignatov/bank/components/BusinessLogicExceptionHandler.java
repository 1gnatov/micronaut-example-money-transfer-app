package ru.ignatov.bank.components;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import ru.ignatov.bank.exceptions.BusinessLogicException;
import ru.ignatov.bank.models.rest.ErrorResponse;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {BusinessLogicException.class, ExceptionHandler.class})
public class BusinessLogicExceptionHandler implements ExceptionHandler<BusinessLogicException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, BusinessLogicException exception) {
        return HttpResponse.badRequest(new ErrorResponse(exception.getBusinessErrorMessage()));
    }
}