/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.zendesk.it;

import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@WithTestResource(ZendeskTestResource.class)
class ZendeskTest {

    @Test
    public void testZendDeskComponent() {
        final String description = "Camel Quarkus Ticket";

        // Create ticket
        String ticketId = RestAssured.given()
                .body(description)
                .post("/zendesk/ticket")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        // Read ticket
        RestAssured.given()
                .queryParam("ticketId", ticketId)
                .get("/zendesk/ticket")
                .then()
                .statusCode(200)
                .body(is(description));

        // Delete
        RestAssured.given()
                .queryParam("ticketId", ticketId)
                .delete("/zendesk/ticket")
                .then()
                .statusCode(204);

        // Confirm deletion
        RestAssured.given()
                .queryParam("ticketId", ticketId)
                .get("/zendesk/ticket")
                .then()
                .statusCode(404);
    }
}
