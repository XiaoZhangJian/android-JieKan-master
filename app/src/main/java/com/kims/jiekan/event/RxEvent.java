package com.kims.jiekan.event;

/**
 * Created by zhangjian on 2017/5/26.
 */

public class RxEvent {

    public int reciveType;
    public int eventType;
    public String eventAction;
    public Object event;

    public RxEvent() {
    }

    /**
     * rxBus 事件
     *
     * @param reciveType  接受者类型
     * @param eventType   事件类型
     * @param eventAction 事件Action
     * @param event       事件
     */
    public RxEvent(int reciveType, int eventType, String eventAction, Object event) {
        this.reciveType = reciveType;
        this.eventType = eventType;
        this.eventAction = eventAction;
        this.event = event;
    }


    public Object getEvent() {
        return event;
    }
}
