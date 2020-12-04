package com.fastcode.testdocbuild11.restcontrollers.extended;

import com.fastcode.testdocbuild11.restcontrollers.core.PaymentControllerTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class PaymentControllerTestExtended extends PaymentControllerTest {
    //Add your custom code here
}
