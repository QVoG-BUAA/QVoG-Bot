/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.services.impl;

import cn.edu.buaa.qvog.bot.models.entities.WebhookRequest;

import java.util.LinkedList;
import java.util.Queue;

/**
 * To mimic a message queue. Currently, it has infinite capacity.
 */
public class TaskQueue {
    private final Queue<WebhookRequest> queue = new LinkedList<>();
    private final Object IS_NOT_FULL = new Object();
    private final Object IS_NOT_EMPTY = new Object();

    public void waitTillNotFull() throws InterruptedException {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.wait();
        }
    }

    public void waitTillNotEmpty() throws InterruptedException {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.wait();
        }
    }

    public void offer(WebhookRequest task) {
        queue.offer(task);
        notifyIsNotEmpty();
    }

    public WebhookRequest poll() {
        WebhookRequest task = queue.poll();
        notifyIsNotFull();
        return task;
    }

    private void notifyIsNotFull() {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.notify();
        }
    }

    private void notifyIsNotEmpty() {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.notify();
        }
    }
}
