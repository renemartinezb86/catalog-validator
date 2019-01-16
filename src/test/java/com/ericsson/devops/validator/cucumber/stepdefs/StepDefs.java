package com.ericsson.devops.validator.cucumber.stepdefs;

import com.ericsson.devops.validator.CatalogValidatorApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CatalogValidatorApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
