package com.sn.api;

import java.io.IOException;

import org.testng.annotations.Test;

import com.sn.apt.api.ApiActions;
import com.sn.config.BaseConfig;
import com.sn.utils.CommonUtils;

import io.restassured.response.Response;

/**
 * Rest api tests.
 * 
 * @author nap0878
 */
public class RestApiTest extends BaseConfig {

    /**
     * Constant .
     */
    private static final String RESPONSE_CODE_AS_EXPECTED = "Response code as expected";

    /**
     * Constant .
     */
    private static final String REST_API_ISSUE = "rest/api/Issue/";

    /**
     * Constant .
     */
    private static final String REST_API_JOURNAL = "rest/api/Journal/";

    private static final String REST_API_ARTICLE = "rest/api/Article/";

    /**
     * Constant .
     */
    private static final String DEMO_USER_PWD = "demo_user_pwd";

    /**
     * created variable for RESOURCE.
     */
    private String resource;

    /**
     * created variable for RESOURCE_URI.
     */
    private String resourceUri = "http://localhost:8080/";

    /**
     * store current directory .
     */
    private String currentDirectory = System.getProperty("user.dir");

    /**
     * store current test data folder.
     */
    private String filename = currentDirectory + "\\TestData\\";

    /**
     * Post journal .
     * 
     * @author nap0878
     * @throws IOException
     *             .
     */
    @Test
    public void verifyResponseCodeforPostJournal() throws IOException {
        final CommonUtils commonUtilsObj = new CommonUtils();
        resource = REST_API_JOURNAL;
        final ApiActions apiActionsObj = new ApiActions();
        final Response res = apiActionsObj
                .postRequestGetResponse(
                        resourceUri,
                        resource,
                        filename + "journal.json",
                        "MxAdmin",
                        "Welk0m@2023@");
        final int statusCode = res.getStatusCode();
        commonUtilsObj.verifyIfTrue(statusCode == 200, "Journal added successfully");
    }

    /**
     * Post journal .
     * 
     * @author nap0878
     * @throws IOException
     *             .
     */
    @Test
    public void verifyResponseCodeforPostJournal2() throws IOException {
        final CommonUtils commonUtilsObj = new CommonUtils();
        resource = REST_API_JOURNAL;
        final ApiActions apiActionsObj = new ApiActions();
        final Response res = apiActionsObj
                .postRequestGetResponse(
                        resourceUri,
                        resource,
                        filename + "journal.json",
                        "MxAdmin",
                        "Welk0m@2023@");
        final int statusCode = res.getStatusCode();
        commonUtilsObj.verifyIfTrue(statusCode == 400, "Journal added successfully");
    }

}
