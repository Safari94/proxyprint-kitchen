/*
 * Copyright 2016 Pivotal Software, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.proxyprint.kitchen.utils;

import io.github.proxyprint.kitchen.models.notifications.Notification;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author jose
 */
public class NotificationManager {

    private final ConcurrentHashMap<String, SseEmitter> subscriptions;

    public NotificationManager() {
        this.subscriptions = new ConcurrentHashMap<>();
    }

    public SseEmitter subscribe(String username) {
        SseEmitter sseEmitter = new SseEmitter();
        this.subscriptions.put(username, sseEmitter);
        return sseEmitter;
    }

    public void unsubscribe(String username) {
        this.subscriptions.remove(username);
    }

    public void sendNotification(String username, Notification notification) throws IOException {
        this.subscriptions.get(username).send(notification, MediaType.APPLICATION_JSON);
    }
}
