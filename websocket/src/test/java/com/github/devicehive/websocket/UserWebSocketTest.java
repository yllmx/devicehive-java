package com.github.devicehive.websocket;

import com.github.devicehive.rest.model.JsonStringWrapper;
import com.github.devicehive.rest.model.NetworkId;
import com.github.devicehive.rest.model.RoleEnum;
import com.github.devicehive.rest.model.StatusEnum;
import com.github.devicehive.rest.model.UserUpdate;
import com.github.devicehive.rest.model.UserWithNetwork;
import com.github.devicehive.websocket.api.UserWS;
import com.github.devicehive.websocket.listener.UserListener;
import com.github.devicehive.websocket.model.repsonse.ErrorResponse;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.UserGetCurrentResponse;
import com.github.devicehive.websocket.model.repsonse.UserGetNetworkResponse;
import com.github.devicehive.websocket.model.repsonse.UserGetResponse;
import com.github.devicehive.websocket.model.repsonse.UserInsertResponse;
import com.github.devicehive.websocket.model.repsonse.UserListResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserWebSocketTest extends Helper {
    private static final String JSON_DATA = "{\"jsonString\": \"NEW STRING DATA\"}";
    private static final String LOGIN = "WS_L0G1N_DAT_1Z_UN1CK_";
    private static final String PASSWORD = "PASSWORD";
    private static final String NETWORK_NAME = "WS T3ZT NE7W0K ";
    private static final RoleEnum ROLE = RoleEnum.CLIENT;
    private static final StatusEnum STATUS = StatusEnum.ACTIVE;
    private static Long userId;
    private static Long networkId;
    private static final RESTHelper restHelper = new RESTHelper();
    private CountDownLatch latch;
    private UserWS userWS;

    @Before
    public void preTest() throws InterruptedException, IOException {
        authenticate();
        latch = new CountDownLatch(1);
        userWS = client.createUserWS();
    }

    @Test
    public void A_insertUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userId = response.getUser().getId();
                latch.countDown();
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteUsers(userId);
        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void B_getUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                UserWithNetwork user = response.getUser();
                Assert.assertEquals(userId, user.getId());
                latch.countDown();
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userId = response.getUser().getId();
                userWS.get(null, userId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);

        latch.await(awaitTimeout, awaitTimeUnit);

        restHelper.deleteUsers(userId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void C_updateUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userId = response.getUser().getId();

                JsonStringWrapper updatedData = new JsonStringWrapper();
                updatedData.setJsonString(JSON_DATA);
                UserUpdate userUpdate = new UserUpdate();
                userUpdate.setData(updatedData);

                userWS.update(null, userId, userUpdate);
            }

            @Override
            public void onUpdate(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteUsers(userId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void D_deleteUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userId = response.getUser().getId();
                userWS.delete(null, userId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void E_getCurrentUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        userWS.getCurrent(null);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void F_updateCurrentUser() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {

            }

            @Override
            public void onInsert(UserInsertResponse response) {

            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        JsonStringWrapper updatedData = new JsonStringWrapper();
        updatedData.setJsonString(JSON_DATA);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setData(updatedData);

        userWS.updateCurrent(null, userUpdate);
        latch.await(awaitTimeout, awaitTimeUnit);

        Assert.assertEquals(0, latch.getCount());
    }



    @Test
    public void G_listUsers() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                Assert.assertNotEquals(0, response.getUsers().size());
                latch.countDown();
            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userId = response.getUser().getId();
                userWS.list(null, null, LOGIN + "%", null, null, null, null, 30, 0);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {

            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

        });
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);
        latch.await(awaitTimeout, awaitTimeUnit);
        restHelper.deleteUsers(userId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void H_assignNetwork() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());

                userId = response.getUser().getId();
                userWS.assignNetwork(null, userId, networkId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });
        networkId = createTestNetwork();
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);

        latch.await(awaitTimeout, awaitTimeUnit);

        restHelper.deleteUsers(userId);
        restHelper.deleteNetworks(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void I_unassignNetwork() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());

                userId = response.getUser().getId();
                userWS.assignNetwork(null, userId, networkId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {

            }

            @Override
            public void onAssignNetwork(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userWS.unassignNetwork(null, userId, networkId);
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });


        networkId = createTestNetwork();
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);

        latch.await(awaitTimeout, awaitTimeUnit);

        restHelper.deleteUsers(userId);
        restHelper.deleteNetworks(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    @Test
    public void J_getNetwork() throws InterruptedException, IOException {
        userWS.setListener(new UserListener() {
            @Override
            public void onList(UserListResponse response) {

            }

            @Override
            public void onGet(UserGetResponse response) {
            }

            @Override
            public void onInsert(UserInsertResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());

                userId = response.getUser().getId();
                userWS.assignNetwork(null, userId, networkId);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onGetCurrent(UserGetCurrentResponse response) {

            }

            @Override
            public void onUpdateCurrent(ResponseAction response) {

            }

            @Override
            public void onGetNetwork(UserGetNetworkResponse response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                latch.countDown();
            }

            @Override
            public void onAssignNetwork(ResponseAction response) {
                Assert.assertEquals(ResponseAction.SUCCESS, response.getStatus());
                userWS.getNetwork(null, userId, networkId);
            }

            @Override
            public void onUnassignNetwork(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error.toString());
            }

        });
        networkId = createTestNetwork();
        UserUpdate user = createNewUserUpdate();
        userWS.insert(null, user);

        latch.await(awaitTimeout, awaitTimeUnit);

        restHelper.deleteUsers(userId);
        restHelper.deleteNetworks(networkId);

        Assert.assertEquals(0, latch.getCount());
    }

    private UserUpdate createNewUserUpdate() {
        return createNewUserUpdate(LOGIN);
    }

    private UserUpdate createNewUserUpdate(String userLogin) {
        UserUpdate userUpdate = new UserUpdate();
        String login = userLogin + new Random().nextLong();
        System.out.println("User login for test: " + login);
        userUpdate.setLogin(login);
        userUpdate.setPassword(PASSWORD);
        userUpdate.setRole(ROLE);
        userUpdate.setStatus(STATUS);
        return userUpdate;
    }

    private Long createTestNetwork() throws IOException {
        String networkName = NETWORK_NAME + new Random().nextLong();
        System.out.println("Network name for test: " + networkName);
        NetworkId networkId = restHelper.createNetwork(networkName);
        return networkId.getId();
    }


}
