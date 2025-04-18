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
package org.apache.camel.quarkus.component.langchain4jit;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
class LangChain4jTest {

    @Test
    void camelAnnotationsShouldWorkAsExpected() {
        RestAssured.given()
                .body("{\"included\": \"included-content\", \"ignored\": \"ignored-content\"}")
                .post("/langchain4j/camel-annotations-should-work-as-expected")
                .then()
                .statusCode(200)
                .body("fromJsonPath", is("included-content"))
                .body("fromHeader", is("headerValue"));
    }

    @Test
    void aiServiceShouldBeResolvedByInterface() {
        RestAssured.given()
                .get("/langchain4j/ai-service-should-be-resolvable-by-interface")
                .then()
                .statusCode(200)
                .body(is("AiServiceResolvedByInterface has been resolved"));
    }

    @Test
    void aiServiceShouldBeResolvedByName() {
        RestAssured.given()
                .get("/langchain4j/ai-service-should-be-resolvable-by-name")
                .then()
                .statusCode(200)
                .body(is("AiServiceResolvedByName has been resolved"));
    }
}
