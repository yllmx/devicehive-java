package com.devicehive.service;

import com.devicehive.auth.HivePrincipal;
import com.devicehive.controller.util.ResponseFactory;
import com.devicehive.dao.DeviceDAO;
import com.devicehive.json.strategies.JsonPolicyDef;
import com.devicehive.messages.bus.redis.RedisNotificationService;
import com.devicehive.messages.kafka.Notification;
import com.devicehive.model.Device;
import com.devicehive.model.DeviceNotification;
import com.devicehive.model.SpecialNotifications;
import com.devicehive.model.wrappers.DeviceNotificationWrapper;
import com.devicehive.util.LogExecutionTime;
import com.devicehive.util.ServerResponsesFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.*;

@Stateless
@LogExecutionTime
public class DeviceNotificationService {

    @EJB
    private DeviceEquipmentService deviceEquipmentService;
    @EJB
    private DeviceService deviceService;
    @EJB
    private TimestampService timestampService;
    @EJB
    private DeviceDAO deviceDAO;
    @EJB
    private RedisNotificationService redisNotificationService;

    @Inject
    @Notification
    private Event<DeviceNotification> deviceNotificationMessageReceivedEvent;

    public DeviceNotification findByIdAndGuid(final Long id, final String guid) {
        return redisNotificationService.getByIdAndGuid(id, guid);
    }

    public List<DeviceNotification> getDeviceNotificationsList(Collection<String> devices, final Collection<String> names,
                                                     final Timestamp timestamp,
                                                     HivePrincipal principal) {
        List<DeviceNotification> notifications;
        if (devices != null) {
            final List<String> availableDevices = deviceService.findGuidsWithPermissionsCheck(devices, principal);
            notifications = redisNotificationService.getByGuids(availableDevices);
        } else {
            notifications = redisNotificationService.getAll();
        }
        if (timestamp != null) {
            CollectionUtils.filter(notifications, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    return timestamp.compareTo(((DeviceNotification) o).getTimestamp()) <= 0;
                }
            });
        }
        if (CollectionUtils.isNotEmpty(names)) {
            CollectionUtils.filter(notifications, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    return names.contains(((DeviceNotification) o).getNotification());
                }
            });
        }
        return notifications;
    }

    public void submitDeviceNotification(final DeviceNotification notification, final Device device) {
        List<DeviceNotification> proceedNotifications = processDeviceNotification(notification, device);
        for (DeviceNotification currentNotification : proceedNotifications) {
            redisNotificationService.save(notification);
            deviceNotificationMessageReceivedEvent.fire(currentNotification);
        }
    }

    public void submitDeviceNotification(final DeviceNotification notification, final String deviceGuid) {
        notification.setTimestamp(timestampService.getTimestamp());
        notification.setId(Math.abs(new Random().nextInt()));
        notification.setDeviceGuid(deviceGuid);
        redisNotificationService.save(notification);
        deviceNotificationMessageReceivedEvent.fire(notification);
    }

    public List<DeviceNotification> processDeviceNotification(DeviceNotification notificationMessage, Device device) {
        List<DeviceNotification> notificationsToCreate = new ArrayList<>();
        switch (notificationMessage.getNotification()) {
            case SpecialNotifications.EQUIPMENT:
                deviceEquipmentService.refreshDeviceEquipment(notificationMessage, device);
                break;
            case SpecialNotifications.DEVICE_STATUS:
                notificationsToCreate.add(refreshDeviceStatusCase(notificationMessage, device));
                break;
            default:
                break;

        }
        notificationMessage.setDeviceGuid(device.getGuid());
        notificationMessage.setTimestamp(timestampService.getTimestamp());
        notificationsToCreate.add(notificationMessage);
        return notificationsToCreate;

    }

    public DeviceNotification convertToMessage(DeviceNotificationWrapper notificationSubmit, Device device) {
        DeviceNotification message = new DeviceNotification();
        message.setId(Math.abs(new Random().nextInt()));
        message.setDeviceGuid(device.getGuid());
        message.setTimestamp(timestampService.getTimestamp());
        message.setNotification(notificationSubmit.getNotification());
        message.setParameters(notificationSubmit.getParameters());
        return message;
    }

    public DeviceNotification refreshDeviceStatusCase(DeviceNotification notificationMessage, Device device) {
        device = deviceDAO.findByUUIDWithNetworkAndDeviceClass(device.getGuid());
        String status = ServerResponsesFactory.parseNotificationStatus(notificationMessage);
        device.setStatus(status);
        return ServerResponsesFactory.createNotificationForDevice(device, SpecialNotifications.DEVICE_UPDATE);
    }

    public void submitEmptyResponse(final AsyncResponse asyncResponse) {
        asyncResponse.resume(ResponseFactory.response(Response.Status.OK, Collections.emptyList(),
                JsonPolicyDef.Policy.NOTIFICATION_TO_CLIENT));
    }
}
