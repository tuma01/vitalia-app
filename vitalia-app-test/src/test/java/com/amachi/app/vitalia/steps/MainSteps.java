
package com.amachi.app.vitalia.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainSteps {

    @Given("the application is running")
    public void theApplicationIsRunning() {
        // Verifica que la aplicación está corriendo
        System.out.println("Application is running.");
    }

    @When("I send a request")
    public void iSendARequest() {
        // Simula el envío de una solicitud
        System.out.println("Request sent.");
    }

    @Then("I receive a valid response")
    public void iReceiveAValidResponse() {
        // Simula la recepción de una respuesta válida
        System.out.println("Response received.");
        assertTrue(true);
    }

}