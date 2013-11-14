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

package org.jasig.ssp.security;


import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.fail;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;


public class PortalLoginServiceImpl implements PortalLoginService {

    private static final String PORTAL_NAME = "/ssp-platform/";
    private static final String LOGIN_POST_URI = PORTAL_NAME + "Login";
    private static final String LOGOUT_GET_URI = PORTAL_NAME +"Logout";
    private static final String PORTLET_COOKIE = "org.jasig.portal.PORTLET_COOKIE";
    private static final String JSESSION_COOKIE = "JSESSIONID";
    private static final Map<String, Map> USERS;
    private static final String LOGIN_CHECK_API = "/ssp/api/1/session/getAuthenticatedPerson";
    private static final int LOGIN_CHECK_ATTEMPTS = 200;

    private static String currentlyLoggedInUser;

    private String baseURI;
    private int port;
    private String jSessionId;
    private String portletCookie;


    private static final Logger LOGGER = LoggerFactory
            .getLogger(PortalLoginServiceImpl.class);


    static {

        USERS = new HashMap<String, Map>();

        final Map studentLogin = new HashMap<String,String>();
        studentLogin.put("username", "dmartin0");
        studentLogin.put("password", "password0");
        studentLogin.put("firstName", "David");
        studentLogin.put("lastName", "Martin");

        final Map facultyLogin = new HashMap<String,String>();
        facultyLogin.put("username", "faculty1");
        facultyLogin.put("password", "password0");
        facultyLogin.put("firstName", "Demo");
        facultyLogin.put("lastName", "Faculty1");

        final Map coachLogin = new HashMap<String,String>();
        coachLogin.put("username", "coach1");
        coachLogin.put("password", "password0");
        coachLogin.put("firstName", "Demo");
        coachLogin.put("lastName", "Coach1");

        final Map supportStaffLogin = new HashMap<String,String>();
        supportStaffLogin.put("username", "supportStaff1");
        supportStaffLogin.put("password", "password0");
        supportStaffLogin.put("firstName", "Demo");
        supportStaffLogin.put("lastName", "SupportStaff1");

        final Map managerLogin = new HashMap<String,String>();
        managerLogin.put("username", "manager1");
        managerLogin.put("password", "password0");
        managerLogin.put("firstName", "Demo");
        managerLogin.put("lastName", "manager1");

        final Map developerLogin = new HashMap<String,String>();
        developerLogin.put("username", "developer1");
        developerLogin.put("password", "password0");
        developerLogin.put("firstName", "Demo");
        developerLogin.put("lastName", "Developer1");

        final Map mapTemplateAdminLogin = new HashMap<String,String>();
        mapTemplateAdminLogin.put("username", "mapTemplateAdmin1");
        mapTemplateAdminLogin.put("password", "password0");
        mapTemplateAdminLogin.put("firstName", "Demo");
        mapTemplateAdminLogin.put("lastName", "MapTemplateAdmin1");

        final Map adminLogin = new HashMap<String,String>();
        adminLogin.put("username", "admin");
        adminLogin.put("password", "admin");
        adminLogin.put("firstName", "Amy");
        adminLogin.put("lastName", "Administrator");

        USERS.put("student", studentLogin);
        USERS.put("faculty", facultyLogin);
        USERS.put("coach", coachLogin);
        USERS.put("supportStaff", supportStaffLogin);
        USERS.put("manager", managerLogin);
        USERS.put("developer", developerLogin);
        USERS.put("mapTemplateAdmin", mapTemplateAdminLogin);
        USERS.put("admin", adminLogin);

    }


    public PortalLoginServiceImpl ( final String baseURI, final int port ) {
        this.setBaseURI(baseURI);
        this.setPort(port);
    }


    /**
     * Performs uPortal/SSP-Platform standard authentication and stores the JSessionId
     * @return false if login failed (i.e. credentials are invalid)
     */
    @Override
    public boolean login () {

        boolean loginResult = true;

        Response loginFormRedirectResponse;

        //request login form (needs to be requested before login POST for ssp-platform authentication)
        loginFormRedirectResponse =
                given()
                    .redirects().follow(false)
                    .redirects().allowCircular(false)
                .get(baseURI + ":" + port + LOGIN_POST_URI);


        portletCookie = loginFormRedirectResponse.getCookie(PORTLET_COOKIE);


        //send login POST
        Response loginPostRedirectResponse =
                given()
                    .redirects().follow(false)
                    .redirects().allowCircular(false)
                    .port(port).urlEncodingEnabled(true)
                    .cookie(PORTLET_COOKIE, portletCookie)
                    .param("userName", USERS.get("admin").get("username"))
                    .param("password", USERS.get("admin").get("password"))
                .when()
                    .post(baseURI + ":" + port + LOGIN_POST_URI);

        jSessionId = loginPostRedirectResponse.getCookie(JSESSION_COOKIE);
        RestAssured.sessionId = jSessionId;


        //verify login credentials were correct, if redirected back it was not
        if ( !loginPostRedirectResponse.getHeaders().getValue("Location").equals(
                loginFormRedirectResponse.getHeaders().getValue("Location")) ) {

            //manually follow the redirect to complete authentication
            given()
                .redirects().follow(true)
                .redirects().allowCircular(true)
                .cookie(PORTLET_COOKIE, portletCookie)
            .when()
                .get(loginPostRedirectResponse.getHeaders().getValue("Location"));

            int loginCheckAttemptCount = 0;
            do {
                   loginFormRedirectResponse = given()
                        .redirects().follow(true)
                        .redirects().allowCircular(true)
                        .cookie(PORTLET_COOKIE, portletCookie)
                    .when()
                        .get(baseURI + ":" + port + LOGIN_CHECK_API);

                loginCheckAttemptCount++;
                LOGGER.debug("LOGIN CHECK " + loginCheckAttemptCount);

            } while ( loginFormRedirectResponse.getContentType().isEmpty() &&
                    loginCheckAttemptCount < LOGIN_CHECK_ATTEMPTS );

            currentlyLoggedInUser = "admin";
            USERS.get(currentlyLoggedInUser).put("id", loginFormRedirectResponse.getBody()
                    .jsonPath().getJsonObject("id"));

        } else {
            LOGGER.error("Login Credentials Failed! Check data and database. Login: " +
                    USERS.get("admin").get("username") + "  Password: " + USERS.get("admin").get("password") +"\n");
            loginResult = false;
            fail("Login Failed");
        }

        return loginResult;
    }

    @Override
    public boolean logout() {

        boolean logoutResult = true;

        //logout
        given()
            .redirects().follow(true)
            .redirects().allowCircular(true)
            .cookie(PORTLET_COOKIE, portletCookie)
            .cookie(JSESSION_COOKIE, jSessionId)
        .when()
            .get(baseURI + ":" + port + LOGOUT_GET_URI);


        //follow redirect to complete logout
        given()
            .redirects().follow(true)
            .redirects().allowCircular(true)
            .cookie(JSESSION_COOKIE, jSessionId)
        .when()
            .get(baseURI + ":" + port + PORTAL_NAME);

        return logoutResult;
    }

    /**
     * Returns the jSession Id issued at login
     * @return
     */
    @Override
    public final String getJSessionId() {
        return jSessionId;
    }

    @Override
    public final void setBaseURI (final String baseURI) {
        this.baseURI = baseURI;
    }

    @Override
    public final void setPort (final int port) {
        this.port = port;
    }

    /**
     * Returns map of the currently logged in user's information.
     * Information available by keys: username, password, firstName and lastName.
     * @return
     */
    @Override
    public final Map<String,String> getCurrentlyLoggedInUserInfo() { return USERS.get(currentlyLoggedInUser); }
}
