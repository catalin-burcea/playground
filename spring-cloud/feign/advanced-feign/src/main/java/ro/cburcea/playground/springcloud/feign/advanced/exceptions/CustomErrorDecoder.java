package ro.cburcea.playground.springcloud.feign.advanced.exceptions;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Date repeatAfter = new Date(currentTimeMillis());
        switch (response.status()) {
            case 400:
                return new BadRequestException();
            case 404:
            case 408:
                return new RetryableException(
                        response.status(),
                        response.reason(),
                        response.request().httpMethod(),
                        null,
                        repeatAfter,
                        response.request()
                );
            default:
                return new Exception("Generic error");
        }
    }
}