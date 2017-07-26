package examples;

import com.devicehive.rest.ApiClient;
import com.devicehive.rest.api.ApiInfoVOApi;
import com.devicehive.rest.api.DeviceApi;
import com.devicehive.rest.api.DeviceCommandApi;
import com.devicehive.rest.api.DeviceNotificationApi;
import com.devicehive.rest.model.DeviceCommandWrapper;
import com.devicehive.rest.model.DeviceNotification;
import com.devicehive.rest.model.DeviceVO;
import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.rest.utils.Const;
import org.joda.time.DateTime;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimerClient {
    private ScheduledExecutorService ses;
    private ApiClient client;

    private DeviceNotificationApi notificationApi;
    private DeviceCommandApi commandApi;
    private ApiInfoVOApi infoApi;
    private DeviceApi deviceApi;

    private String deviceId = null;
    private DateTime timestamp = null;

    public TimerClient(ApiClient client) {
        this.client = client;
        ses = Executors.newScheduledThreadPool(2);
        inflateApi();
    }

    private void inflateApi() {
        notificationApi = client.createService(DeviceNotificationApi.class);
        commandApi = client.createService(DeviceCommandApi.class);
        infoApi = client.createService(ApiInfoVOApi.class);
        deviceApi = client.createService(DeviceApi.class);
    }

    void run() {
        if (deviceId == null) {
            try {
                Response<List<DeviceVO>> response = deviceApi
                        .list(null, null, null, null, null,
                                null, 20, 0).execute();

                if (!response.isSuccessful()) {
                    throw new IOException(response.errorBody().string());
                }

                List<DeviceVO> devices = response.body();
                System.out.println(devices);
                if (devices.size() == 0) {
                    System.out.println("No devices was found");
                } else {
                    deviceId = devices.get(0).getId();
                    try {
                        timestamp = infoApi.getApiInfo().execute().body().getServerTimestamp();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    setTimer(Const.DEVICE_ID, true, DateTime.now().plusSeconds(5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    pollNotifications(timestamp.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void pollNotifications(String timestamp) throws IOException {
        List<DeviceNotification> deviceNotifications =
                notificationApi.poll(Const.DEVICE_ID, null, timestamp, 30L).execute().body();
        if (deviceNotifications.size() != 0) {
            Collections.sort(deviceNotifications);
            DeviceNotification notification = deviceNotifications.get(deviceNotifications.size() - 1);
            updateTimestamp(notification.getTimestamp());
        }
        for (DeviceNotification item : deviceNotifications) {
            if (item.getNotification().equals(Const.ALARM)) {
                System.out.println(item);
            }
        }
    }

    //Command updating
    private void setTimer(String guid, Boolean isOnTimer, DateTime time)
            throws IOException {
        DeviceCommandWrapper deviceCommandWrapper = new DeviceCommandWrapper();
        if (isOnTimer) {
            deviceCommandWrapper.setCommand(Const.ON);
            JsonStringWrapper jsonStringWrapper = new JsonStringWrapper();
            jsonStringWrapper.setJsonString(time.toString());
            deviceCommandWrapper.setParameters(jsonStringWrapper);
        }
        commandApi.insert(guid, deviceCommandWrapper).execute();
    }

    private void updateTimestamp(DateTime timestamp) {
        if (this.timestamp == null) {
            this.timestamp = timestamp;
        } else if (this.timestamp.isBefore(timestamp)) {
            this.timestamp = timestamp;
        }
    }
}

