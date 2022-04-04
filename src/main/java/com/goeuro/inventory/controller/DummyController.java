package com.goeuro.inventory.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

  @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/")
  public ResponseEntity<Object> index(
      HttpServletRequest request, @RequestHeader HttpHeaders headers) {
    Map<String, Object> response = new HashMap<>();
    response.put("request_uri", request.getRequestURL());
    response.put("request_headers", headers.toSingleValueMap());
    response.put("remote_addr", request.getRemoteAddr());
    response.put("properties", System.getProperties());
    response.put("env", System.getenv());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
