package com.goeuro.inventory.bdd;

import com.goeuro.inventory.ApplicationBBD;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {ApplicationBBD.class})
public class CucumberSpringContextConfig {}
