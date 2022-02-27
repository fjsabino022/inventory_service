package com.goeuro.service.util;

import com.goeuro.service.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(properties = "spring.main.banner-mode=off")
public abstract class SpringTestSetup {

}
