/**
 * 
 */
package com.nuevatel.ai.ami.event;

import static com.nuevatel.ai.ami.event.AmiResponseField.*;

import java.util.Map;

/**
 * @author dayler
 *
 */
public class CDREvent extends AmiEvent {

    public static final String RESPONSE_NAME = "Cdr";

    private String accountCode;
    private String source;
    private String destination;
    private String destinationContext;
    private String callerID;
    private String channel;
    private String destinationChannel;
    private String lastApplication;
    private String lastData;
    private String startTime;
    private String answerTime;
    private String endTime;
    private String duration;
    private String billableSeconds;
    private String disposition;
    private String amaFlags;
    private String userField;

    public CDREvent() {
        setName(RESPONSE_NAME);
    }

    /* (non-Javadoc)
     * @see com.nuevatel.ai.ami.event.Response#build(java.util.Map, java.lang.Object)
     */
    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setAccountCode(metadata.get(AccountCode.name()));
        setSource(metadata.get(Source.name()));
        setDestination(metadata.get(Destination.name()));
        setDestinationContext(metadata.get(DestinationContext.name()));
        setCallerID(metadata.get(CallerID.name()));
        setChannel(metadata.get(Channel.name()));
        setDestinationChannel(metadata.get(DestinationChannel.name()));
        setLastApplication(metadata.get(LastApplication.name()));
        setLastData(metadata.get(LastData.name()));
        setStartTime(metadata.get(StartTime.name()));
        setAnswerTime(metadata.get(AnswerTime.name()));
        setEndTime(metadata.get(EndTime.name()));
        setDuration(metadata.get(Duration.name()));
        setBillableSeconds(metadata.get(BillableSeconds.name()));
        setDisposition(metadata.get(Disposition.name()));
        setAmaFlags(metadata.get(AMAFlags.name()));
        setUserField(metadata.get(UserField.name()));
    }

    /**
     * @return the accountCode
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the destinationContext
     */
    public String getDestinationContext() {
        return destinationContext;
    }

    /**
     * @param destinationContext the destinationContext to set
     */
    public void setDestinationContext(String destinationContext) {
        this.destinationContext = destinationContext;
    }

    /**
     * @return the callerID
     */
    public String getCallerID() {
        return callerID;
    }

    /**
     * @param callerID the callerID to set
     */
    public void setCallerID(String callerID) {
        this.callerID = callerID;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the destinationChannel
     */
    public String getDestinationChannel() {
        return destinationChannel;
    }

    /**
     * @param destinationChannel the destinationChannel to set
     */
    public void setDestinationChannel(String destinationChannel) {
        this.destinationChannel = destinationChannel;
    }

    /**
     * @return the lastApplication
     */
    public String getLastApplication() {
        return lastApplication;
    }

    /**
     * @param lastApplication the lastApplication to set
     */
    public void setLastApplication(String lastApplication) {
        this.lastApplication = lastApplication;
    }

    /**
     * @return the lastData
     */
    public String getLastData() {
        return lastData;
    }

    /**
     * @param lastData the lastData to set
     */
    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the answerTime
     */
    public String getAnswerTime() {
        return answerTime;
    }

    /**
     * @param answerTime the answerTime to set
     */
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the billableSeconds
     */
    public String getBillableSeconds() {
        return billableSeconds;
    }

    /**
     * @param billableSeconds the billableSeconds to set
     */
    public void setBillableSeconds(String billableSeconds) {
        this.billableSeconds = billableSeconds;
    }

    /**
     * @return the disposition
     */
    public String getDisposition() {
        return disposition;
    }

    /**
     * @param disposition the disposition to set
     */
    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    /**
     * @return the amaFlags
     */
    public String getAmaFlags() {
        return amaFlags;
    }

    /**
     * @param amaFlags the amaFlags to set
     */
    public void setAmaFlags(String amaFlags) {
        this.amaFlags = amaFlags;
    }

    /**
     * @return the userField
     */
    public String getUserField() {
        return userField;
    }

    /**
     * @param userField the userField to set
     */
    public void setUserField(String userField) {
        this.userField = userField;
    }

    @Override
    public String toString() {
        return toString(String.format(
                  " AccountCode=%s"
                + " Source=%s"
                + " Destination=%s"
                + " DestinationContext=%s"
                + " CallerID=%s"
                + " Channel=%s"
                + " DestinationChannel=%s"
                + " LastApplication=%s"
                + " LastData=%s"
                + " StartTime=%s"
                + " AnswerTime=%s"
                + " EndTime=%s"
                + " Duration=%s"
                + " BillableSeconds=%s"
                + " Disposition=%s"
                + " AMAFlags=%s"
                + " UserField%s",
                accountCode,
                source,
                destination,
                destinationContext,
                callerID,
                channel,
                destinationChannel,
                lastApplication,
                lastData,
                startTime,
                answerTime,
                endTime,
                duration,
                billableSeconds,
                disposition,
                amaFlags,
                userField));
    }
}
