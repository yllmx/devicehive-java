package com.devicehive.websockets.util;


import com.devicehive.auth.HivePrincipal;
import com.devicehive.messages.subscriptions.CommandSubscription;
import com.devicehive.messages.subscriptions.NotificationSubscription;
import com.google.gson.JsonElement;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebsocketSession {
    public static final String COMMANDS_SUBSCRIPTIONS = "COMMANDS_SUBSCRIPTIONS";
    public static final String COMMANDS_SUBSCRIPTION_LOCK = "COMMANDS_SUBSCRIPTION_LOCK";
    public static final String COMMAND_UPDATES_SUBSCRIPTION_LOCK = "COMMAND_UPDATES_SUBSCRIPTION_LOCK";
    public static final String NOTIFICATIONS_LOCK = "NOTIFICATIONS_LOCK";
    public static final String NOTIFICATIONS_SUBSCRIPTIONS = "NOTIFICATIONS_SUBSCRIPTIONS";
    public static final String QUEUE = "QUEUE";
    private static final String PRINCIPAL = "HIVE_PRINCIPAL";
    private static final String QUEUE_LOCK = "QUEUE_LOCK";

    public static Lock getQueueLock(Session session) {
        return (Lock) session.getUserProperties().get(QUEUE_LOCK);
    }

    public static HivePrincipal getPrincipal(Session session) {
        return (HivePrincipal) session.getUserProperties().get(PRINCIPAL);
    }

    public static void setPrincipal(Session session, HivePrincipal principal) {
        session.getUserProperties().put(PRINCIPAL, principal);
    }

    public static Lock getCommandsSubscriptionsLock(Session session) {
        return (Lock) session.getUserProperties().get(COMMANDS_SUBSCRIPTION_LOCK);
    }

    public static void createCommandsSubscriptionsLock(Session session) {
        if (!session.getUserProperties().containsKey(COMMANDS_SUBSCRIPTION_LOCK)) {
            session.getUserProperties().put(COMMANDS_SUBSCRIPTION_LOCK, new ReentrantLock(true));
        }
    }

    public static Lock getCommandUpdatesSubscriptionsLock(Session session) {
        return (Lock) session.getUserProperties().get(COMMAND_UPDATES_SUBSCRIPTION_LOCK);
    }

    public static void createCommandUpdatesSubscriptionsLock(Session session) {
        if (!session.getUserProperties().containsKey(COMMAND_UPDATES_SUBSCRIPTION_LOCK)) {
            session.getUserProperties().put(COMMAND_UPDATES_SUBSCRIPTION_LOCK, new ReentrantLock(true));
        }
    }

    public static Lock getNotificationSubscriptionsLock(Session session) {
        return (Lock) session.getUserProperties().get(NOTIFICATIONS_LOCK);
    }

    public static void createNotificationSubscriptionsLock(Session session) {
        if (!session.getUserProperties().containsKey(NOTIFICATIONS_LOCK)) {
            session.getUserProperties().put(NOTIFICATIONS_LOCK, new ReentrantLock(true));
        }
    }

    public static void createQueueLock(Session session) {
        if (!session.getUserProperties().containsKey(QUEUE)) {
            session.getUserProperties().put(QUEUE, new ConcurrentLinkedQueue<JsonElement>());
        }
        if (!session.getUserProperties().containsKey(QUEUE_LOCK)) {
            session.getUserProperties().put(QUEUE_LOCK, new ReentrantLock(true));
        }
    }

    public static Set<UUID> getCommandSubscriptions(Session session) {
        return (Set<UUID>) session.getUserProperties().get(COMMANDS_SUBSCRIPTIONS);
    }

    public static Set<UUID> getNotificationSubscriptions(Session session) {
        return (Set<UUID>) session.getUserProperties().get(NOTIFICATIONS_SUBSCRIPTIONS);
    }

    public static void createSubscriptions(Session session){
        session.getUserProperties().put(COMMANDS_SUBSCRIPTIONS, new HashSet<UUID>());
        session.getUserProperties().put(NOTIFICATIONS_SUBSCRIPTIONS, new HashSet<UUID>());
    }

    public static void addMessagesToQueue(Session session, JsonElement... jsons) {
        @SuppressWarnings("unchecked")
        ConcurrentLinkedQueue<JsonElement> queue = (ConcurrentLinkedQueue) session.getUserProperties().get(QUEUE);
        Collections.addAll(queue, jsons);
    }

    public static void setCommandSubscriptions(Session session, List<CommandSubscription> csList) {
        Set<UUID> subs = getCommandSubscriptions(session);
        for (CommandSubscription cs : csList) {
            subs.add(UUID.fromString(cs.getSubscriptionId()));
        }
        session.getUserProperties().put(COMMANDS_SUBSCRIPTIONS, subs);
    }

    public static void setNotificationSubscriptions(Session session, List<NotificationSubscription> nsList) {
        Set<UUID> subs = getNotificationSubscriptions(session);
        for (NotificationSubscription ns : nsList) {
            subs.add(UUID.fromString(ns.getSubscriptionId()));
        }
        session.getUserProperties().put(NOTIFICATIONS_SUBSCRIPTIONS, subs);
    }
}


