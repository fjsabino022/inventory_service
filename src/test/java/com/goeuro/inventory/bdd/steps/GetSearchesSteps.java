package com.goeuro.inventory.bdd.steps;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldAndFormat;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import io.cucumber.java8.En;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetSearchesSteps implements En {

    private ElasticsearchClient elasticsearchClient;
    private List<Hit<OmioSearch>> result;

    public GetSearchesSteps() {
        Given(
                "^I am authenticated$",
                () -> {
                    final var transport =
                            new RestClientTransport(createRestClient(), new JacksonJsonpMapper());
                    elasticsearchClient = new ElasticsearchClient(transport);


                });

        When("^I want to get the logs$", () -> {
            final var searchRequest = createSearchRequest();
            SearchResponse<OmioSearch> search = null;
            try {
                search = elasticsearchClient.search(searchRequest, OmioSearch.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = search.hits().hits();

        });

        Then("^Logs are returned$", () -> {
            assertThat(result).isNotEmpty();
            result.stream()
                    .map(Hit::source)
                    .forEach(
                            omioSearch -> {
                                assertThat(omioSearch.message()).isNotBlank();
                                assertThat(omioSearch.searchEvent()).isNotBlank();
                                assertThat(omioSearch.searchId()).isNotBlank();
                                assertThat(omioSearch.arrivalPositionCountry()).isNotBlank();
                                assertThat(omioSearch.arrivalPositionId()).isNotNull();
                                assertThat(omioSearch.arrivalPositionName()).isNotBlank();
                                assertThat(omioSearch.departurePositionCountry()).isNotBlank();
                                assertThat(omioSearch.departurePositionId()).isNotNull();
                                assertThat(omioSearch.departurePositionName()).isNotBlank();
                                assertThat(omioSearch.ferryStatus()).isNotBlank();
                                assertThat(omioSearch.flightStatus()).isNotBlank();
                                assertThat(omioSearch.trainStatus()).isNotBlank();
                                assertThat(omioSearch.busStatus()).isNotBlank();
                                assertThat(omioSearch.numberOfBusResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfFerryResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfFlightResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfTrainResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.totalResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedBusResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedFerryResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedFlightResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedMixedModeResults())
                                        .isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedRoutes()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.numberOfRoutedTrainResults()).isGreaterThanOrEqualTo(0);
                                assertThat(omioSearch.totalRoutedResults()).isGreaterThanOrEqualTo(0);

                            });
        });
    }

    private RestClient createRestClient() {
        RestClient restClient =
                RestClient.builder(new HttpHost("es-hot.internal-haproxy-be.goeuro.ninja", 80)).build();
        return restClient;
    }

    private SearchRequest createSearchRequest() {
        final var filters =
                List.of(
                        new Query.Builder()
                                .matchPhrase(
                                        new MatchPhraseQuery.Builder()
                                                .field("k8s_namespace")
                                                .query("goeuroapi")
                                                .build())
                                .build(),
                        new Query.Builder()
                                .matchPhrase(
                                        new MatchPhraseQuery.Builder()
                                                .field("logger_name")
                                                .query("com.goeuro.logic.search.SearchContainer")
                                                .build())
                                .build(),
                        new Query.Builder()
                                .matchPhrase(
                                        new MatchPhraseQuery.Builder().field("level").query("INFO").build())
                                .build(),
                        new Query.Builder()
                                .matchPhrase(
                                        new MatchPhraseQuery.Builder()
                                                .field("searchEvent")
                                                .query("SearchCompleted")
                                                .build())
                                .build(),
                        new Query.Builder()
                                .range(
                                        new RangeQuery.Builder()
                                                .format("strict_date_optional_time")
                                                .field("@timestamp")
                                                .gte(JsonData.of("2022-03-03T13:31:57.588Z"))
                                                .lte(JsonData.of("2022-03-03T14:31:57.588Z"))
                                                .build())
                                .build());
        final var queries =
                List.of(
                        new Query.Builder()
                                .queryString(
                                        new QueryStringQuery.Builder()
                                                .query("message: \\\"Search\\\" AND message: \\\"completed\\\"")
                                                .analyzeWildcard(true)
                                                .timeZone("Europe/Berlin")
                                                .build())
                                .build());
        final var queryBool = new BoolQuery.Builder().must(queries).filter(filters).build();
        final var query = new Query.Builder().bool(queryBool).build();
        return
                new SearchRequest.Builder()
                        .query(query)
                        .sort(
                                List.of(
                                        new SortOptions.Builder()
                                                .field(
                                                        new FieldSort.Builder()
                                                                .field("@timestamp")
                                                                .order(SortOrder.Desc)
                                                                .unmappedType(FieldType.Boolean)
                                                                .build())
                                                .build()))
                        .size(10000)
                        .version(Boolean.TRUE)
                        .storedFields(List.of("*"))
                        .source(new SourceConfig.Builder().fetch(Boolean.TRUE).build())
                        .fields(List.of(new FieldAndFormat.Builder().field("searchId").build()))
                        .build();
    }
}