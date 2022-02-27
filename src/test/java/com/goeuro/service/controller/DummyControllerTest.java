package com.goeuro.service.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goeuro.service.util.SpringTestSetup;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class DummyControllerTest extends SpringTestSetup {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() {
        ResponseEntity<ObjectNode> response = template.getForEntity(base.toString(), ObjectNode.class);
        assertThat(response.getBody().get("request_uri").asText(), equalTo(base.toString()));
        List<String> fieldNames = Lists.newArrayList(response.getBody().fieldNames());
        assertThat(fieldNames, containsInAnyOrder("request_uri", "request_headers", "remote_addr", "properties", "env"));
    }
}
