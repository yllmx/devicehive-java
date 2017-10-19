/*
 *
 *
 *   JwtTokenApiTest.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.rest;

import com.github.devicehive.rest.api.AuthApi;
import com.github.devicehive.rest.model.JwtRequest;
import com.github.devicehive.rest.model.JwtToken;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

public class AuthApiTest extends Helper {


    @Test
    public void getToken() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
       JwtRequest requestBody = new JwtRequest();
        requestBody.setLogin("***REMOVED***");
        requestBody.setPassword("***REMOVED***");

        System.out.println(api.login(requestBody).request().url());
        Response<JwtToken> response = api.login(requestBody).execute();

        Assert.assertTrue(response.isSuccessful());
        JwtToken tokenVO = response.body();
        Assert.assertTrue(tokenVO != null);
        Assert.assertTrue(tokenVO.getAccessToken() != null);
        Assert.assertTrue(tokenVO.getAccessToken().length() > 0);
    }

    @Test
    public void getTokenIncorrectCredentials() throws IOException {
        AuthApi api = client.createService(AuthApi.class);
        com.github.devicehive.rest.model.JwtRequest requestBody = new com.github.devicehive.rest.model.JwtRequest();
        requestBody.setLogin("incorrectLogin");
        requestBody.setPassword("incorrectPassword");
        Response<JwtToken> response = api.login(requestBody).execute();
        Assert.assertTrue(!response.isSuccessful());
        Assert.assertTrue(response.body() == null);
    }


}