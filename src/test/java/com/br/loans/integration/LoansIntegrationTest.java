package com.br.loans.integration;

import com.br.loans.adapters.inbound.dto.CustomerDataRequest;
import com.br.loans.adapters.outbound.dto.LoansDTO;
import com.br.loans.adapters.outbound.dto.LoansResponseDTO;
import com.br.loans.application.core.domain.LoanTypes;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoansIntegrationTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + this.port;
    }

    @Test
    void shouldReturnLoansConsignmentWhenGetLoans() {
        final var request = new CustomerDataRequest(31, "275.484.389-23", "Rafael Victoriano", BigDecimal.valueOf(5000), "SP");
        final var responseBodyExpected = new LoansResponseDTO("Rafael Victoriano", List.of(new LoansDTO(LoanTypes.CONSIGNMENT, 2.0)));

        final var responseBodyRequest = given()
                .port(this.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/customer-loans")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(LoansResponseDTO.class);

        assertEquals(responseBodyExpected, responseBodyRequest);
    }

    @Test
    void shouldReturnLoansConsignmentPersonalAndGuaranteedWhenGetLoansBecauseCustomerHasTwentySixYearsOld() {
        final var loansResponse = List.of(new LoansDTO(LoanTypes.PERSONAL, 4.0),
                new LoansDTO(LoanTypes.GUARANTEED, 3.0),
                new LoansDTO(LoanTypes.CONSIGNMENT, 2.0));

        final var request = new CustomerDataRequest(26, "275.484.389-23", "Rafael Victoriano", BigDecimal.valueOf(5000), "SP");
        final var responseBodyExpected = new LoansResponseDTO("Rafael Victoriano", loansResponse);

        final var responseBodyRequest = given()
                .port(this.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/customer-loans")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(LoansResponseDTO.class);

        assertEquals(responseBodyExpected, responseBodyRequest);
    }

    @Test
    void shouldReturnBadRequestWhenGetLoansBecauseRequestIsInvalid() {
        final var request = new CustomerDataRequest(null, null, "", null, null);

        given()
                .port(this.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/customer-loans")
                .then()
                .assertThat()
                .statusCode(400);


    }
}
