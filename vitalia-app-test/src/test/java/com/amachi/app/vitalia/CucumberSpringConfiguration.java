
package com.amachi.app.vitalia;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest//(classes = Application.class) averiguar como cargar el context de Application.class
public class CucumberSpringConfiguration  {

}