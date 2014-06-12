/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp;

import com.jayway.restassured.specification.ResponseSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class ExpectationUtils {

    /**
     * Minor convenience for getting a {@link JSONObject} out of a
     * {@link JSONArray} (just saves you a cast).
     *
     * @param index
     * @param fixture
     * @return
     */
    public static JSONObject expectedObjectAtIndex(int index, JSONArray fixture) {
        return  (JSONObject) fixture.get(index);
    }

    /**
     * Minor convenience for a <em>shallow</em> copy of the result of
     * {@link #expectedObjectAtIndex(int, org.json.simple.JSONArray)}
     * (just saves you some casts).
     *
     * @param index
     * @param fixture
     * @return
     */
    public static JSONObject copyOfExpectedObjectAtIndex(int index, JSONArray fixture) {
        return (JSONObject) ((JSONObject) fixture.get(index)).clone();
    }

    /**
     * Minor convenience for getting at the value of the named field in the
     * {@link JSONObject} at the given index in a {@link JSONArray} as a
     * {@code String}.
     *
     * @param fieldName
     * @param index
     * @param fixture
     * @return
     */
    public static String expectedStringFieldValueAtIndex(String fieldName, int index, JSONArray fixture) {
        final Object rawValue = ((JSONObject) fixture.get(index)).get(fieldName);
        return rawValue instanceof String ? (String) rawValue : rawValue.toString();
    }

    /**
     * Same as {@link #expectListResponseObjectAtIndex(com.jayway.restassured.specification.ResponseSpecification, int, org.json.simple.JSONArray, int)}
     * but assumes the same index can be used on the response and the expectation fixture.
     *
     * @param spec
     * @param index
     * @param fixture
     * @return
     * @see #expectResponseObjectAtIndex(com.jayway.restassured.specification.ResponseSpecification, int, org.json.simple.JSONArray, String)
     */
    public static ResponseSpecification expectListResponseObjectAtIndex(ResponseSpecification spec, int index, JSONArray fixture) {
        return expectListResponseObjectAtIndex(spec, index, fixture, index);
    }

    /**
     * Appends {@code body} {@link JSONPath} assertions to the given
     * {@link ResponseSpecification} for verifying every top-level field in a
     * list response element at a particular index. That index applies both to
     * the actual object in the list response and the given {@link JSONArray}
     * fixture representing the caller's expectations about the underlying
     * system's state.
     *
     * @param spec
     * @param responseIndex
     * @param fixture
     * @param fixtureIndex
     * @return
     * @see #expectResponseObjectAtIndex(com.jayway.restassured.specification.ResponseSpecification, int, org.json.simple.JSONArray, String)
     */
    public static ResponseSpecification expectListResponseObjectAtIndex(ResponseSpecification spec, int responseIndex, JSONArray fixture, int fixtureIndex) {
        return expectResponseObjectAtIndex(spec, fixture, fixtureIndex, "rows[" + responseIndex + "].");
    }

    /**
     * Similar to {@link #expectListResponseObjectAtIndex(com.jayway.restassured.specification.ResponseSpecification, int, org.json.simple.JSONArray)}
     * but for asserting on the complete response body rather than an object
     * at a particular index of a list response. Like that method, the
     * granularity of assertions is limited to top-level fields. (Entire
     * field values will be checked recursively, but failures will be reported
     * in terms of the top-level field.)
     *
     * @param spec
     * @param fixture
     * @param fixtureIndex
     * @return
     * @see #expectResponseObjectAtIndex(com.jayway.restassured.specification.ResponseSpecification, int, org.json.simple.JSONArray, String)
     */
    public static ResponseSpecification expectResponseObjectAtIndex(ResponseSpecification spec, JSONArray fixture, int fixtureIndex) {
        return expectResponseObjectAtIndex(spec, fixture, fixtureIndex, null);
    }

    /**
     * Generates assertions against the response {@link body} for every top-level
     * field in the {@link JSONObject} at the given index in the given
     * {@link JSONArray}. (Entire field values will be checked recursively, but
     * failures will be reported in terms of the top-level field.)
     *
     * <p>The {@code JSONPath} at which assertions will be targeted can be
     * given an optional {@code jsonPathPrefix}, e.g. to target a
     * {@link JSONObject} at a particular index of a list response.</p>
     *
     * @param spec
     * @param fixture
     * @param fixtureIndex
     * @param jsonPathPrefix
     *
     * @return
     */
    public static ResponseSpecification expectResponseObjectAtIndex(ResponseSpecification spec, JSONArray fixture, int fixtureIndex, String jsonPathPrefix) {
        final JSONObject expectedRecord = expectedObjectAtIndex(fixtureIndex, fixture);
        for ( Object entry : expectedRecord.entrySet() ) {
            Map.Entry<Object,Object> mapEntry = (Map.Entry<Object,Object>)entry;
            spec = expectValueAtJsonPath(spec,
                    (jsonPathPrefix == null ? "" : jsonPathPrefix) + (String)mapEntry.getKey(),
                    mapEntry.getValue());
        }
        return spec;
    }

    private static ResponseSpecification expectValueAtJsonPath(ResponseSpecification spec, String jsonPath, Object value) {
        return spec.body(jsonPath, equalTo(value));
    }

}
