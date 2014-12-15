package com.dayler.ai.ami.event;

import java.util.Map;

public class NewExtenEvent extends AmiEvent {

    public static final String RESPONSE_NAME = "Newexten";

    private String channel;
    private String context;
    private String extension;
    private String priority;
    private String application;
    private String appData;


    public NewExtenEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setChannel(metadata.get(AmiResponseField.Channel.name()));
        setContext(metadata.get(AmiResponseField.Context.name()));
        setExtension(metadata.get(AmiResponseField.Extension.name()));
        setPriority(metadata.get(AmiResponseField.Priority.name()));
        setApplication(metadata.get(AmiResponseField.Application.name()));
        setAppData(metadata.get(AmiResponseField.AppData.name()));
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    @Override
    public String toString() {
        return toString(String.format("channel=%s, context=%s, extension=%s priority=%s, "
                + "application=%s, appData=%s",
                channel,
                context,
                extension,
                priority,
                application,
                appData));
    }
}