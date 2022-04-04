package com.goeuro.inventory.bdd.steps;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import io.cucumber.java8.En;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class GetRoutesSteps implements En {
    public GetRoutesSteps() {
        Given("^I am connected with google cloud$", () -> {
            BigQuery bigQuery;
            try (var credentialsStream = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"))) {
                bigQuery = BigQueryOptions.newBuilder()
                        .setCredentials(ServiceAccountCredentials.fromStream(credentialsStream))
                        .build()
                        .getService();
            }
            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(
                            "select country_code from dwh.dim_countries")
                            .setUseLegacySql(false)
                            .build();

            TableResult queryResult = bigQuery.query(queryConfig);
            for (FieldValueList row : queryResult.iterateAll()) {
                String countryCode = row.get("country_code").getStringValue();
                assertThat(countryCode).isNotBlank();
            }
        });
        When("^I want to get the routes information$", () -> {
        });
        Then("^Routes are returned$", () -> {
        });
    }
}

