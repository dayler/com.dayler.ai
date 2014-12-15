/*
 * AgiVars.java
 */
package com.nuevatel.ai.agi;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The AgiVars class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public class AgiVars {

    /* constants */
    static String AGI_REQUEST = "agi_request";
    static String AGI_CHANNEL = "agi_channel";
    static String AGI_LANGUAGE = "agi_language";
    static String AGI_TYPE = "agi_type";
    static String AGI_UNIQUE_ID = "agi_uniqueid";
    static String AGI_VERSION = "agi_version";
    static String AGI_CALLER_ID = "agi_callerid";
    static String AGI_CALLER_ID_NAME = "agi_calleridname";
    static String AGI_CALLING_PRES = "agi_callingpres";
    static String AGI_CALLING_ANI2 = "agi_callingani2";
    static String AGI_CALLING_TON = "agi_callington";
    static String AGI_CALLING_TNS = "agi_callingtns";
    static String AGI_DNID = "agi_dnid";
    static String AGI_RDNIS = "agi_rdnis";
    static String AGI_CONTEXT = "agi_context";
    static String AGI_EXTENSION = "agi_extension";
    static String AGI_PRIORITY = "agi_priority";
    static String AGI_ENHANCED = "agi_enhanced";
    static String AGI_ACCOUNT_CODE = "agi_accountcode";
    static String AGI_THREAD_ID = "agi_threadid";
    static String AGI_ARG_ = "agi_arg_";

    /* private variables */
    private String agiRequest;
    private String agiChannel;
    private String agiLanguage;
    private String agiType;
    private String agiUniqueId;
    private String agiVersion;
    private String agiCallerId;
    private String agiCallerIdName;
    private String agiCallingPres;
    private String agiCallingAni2;
    private String agiCallingTon;
    private String agiCallingTns;
    private String agiDnid;
    private String agiRdnis;
    private String agiContext;
    private String agiExtension;
    private String agiPriority;
    private String agiEnhanced;
    private String agiAccountCode;
    private String agiThreadId;
    private List<String> agiArgs;

    /**
     * Creates a new instance of AgiVars.
     *
     * @param vars List<String>
     */
    AgiVars(List<String> vars) {
        agiArgs = new ArrayList<String>();
        for (String var : vars) {
            if (agiRequest == null && var.startsWith(AGI_REQUEST)) agiRequest = var.substring(AGI_REQUEST.length() + 2);
            else if (agiChannel == null && var.startsWith(AGI_CHANNEL))
                agiChannel = var.substring(AGI_CHANNEL.length() + 2);
            else if (agiLanguage == null && var.startsWith(AGI_LANGUAGE))
                agiLanguage = var.substring(AGI_LANGUAGE.length() + 2);
            else if (agiType == null && var.startsWith(AGI_TYPE)) agiType = var.substring(AGI_TYPE.length() + 2);
            else if (agiUniqueId == null && var.startsWith(AGI_UNIQUE_ID))
                agiUniqueId = var.substring(AGI_UNIQUE_ID.length() + 2);
            else if (agiVersion == null && var.startsWith(AGI_VERSION))
                agiVersion = var.substring(AGI_VERSION.length() + 2);
            else if (agiCallerId == null && var.startsWith(AGI_CALLER_ID))
                agiCallerId = var.substring(AGI_CALLER_ID.length() + 2);
            else if (agiCallerIdName == null && var.startsWith(AGI_CALLER_ID_NAME))
                agiCallerIdName = var.substring(AGI_CALLER_ID_NAME.length() + 2);
            else if (agiCallingPres == null && var.startsWith(AGI_CALLING_PRES))
                agiCallingPres = var.substring(AGI_CALLING_PRES.length() + 2);
            else if (agiCallingAni2 == null && var.startsWith(AGI_CALLING_ANI2))
                agiCallingAni2 = var.substring(AGI_CALLING_ANI2.length() + 2);
            else if (agiCallingTon == null && var.startsWith(AGI_CALLING_TON))
                agiCallingTon = var.substring(AGI_CALLING_TON.length() + 2);
            else if (agiCallingTns == null && var.startsWith(AGI_CALLING_TNS))
                agiCallingTns = var.substring(AGI_CALLING_TNS.length() + 2);
            else if (agiDnid == null && var.startsWith(AGI_DNID)) agiDnid = var.substring(AGI_DNID.length() + 2);
            else if (agiRdnis == null && var.startsWith(AGI_RDNIS)) agiRdnis = var.substring(AGI_RDNIS.length() + 2);
            else if (agiContext == null && var.startsWith(AGI_CONTEXT))
                agiContext = var.substring(AGI_CONTEXT.length() + 2);
            else if (agiExtension == null && var.startsWith(AGI_EXTENSION))
                agiExtension = var.substring(AGI_EXTENSION.length() + 2);
            else if (agiPriority == null && var.startsWith(AGI_PRIORITY))
                agiPriority = var.substring(AGI_PRIORITY.length() + 2);
            else if (agiEnhanced == null && var.startsWith(AGI_ENHANCED))
                agiEnhanced = var.substring(AGI_ENHANCED.length() + 2);
            else if (agiAccountCode == null && var.startsWith(AGI_ACCOUNT_CODE))
                agiAccountCode = var.substring(AGI_ACCOUNT_CODE.length() + 2);
            else if (agiThreadId == null && var.startsWith(AGI_THREAD_ID))
                agiThreadId = var.substring(AGI_THREAD_ID.length() + 2);
            else if (var.startsWith(AGI_ARG_)) {
                int tmpIndex = var.indexOf(": ");
                if (tmpIndex > 0) agiArgs.add(var.substring(tmpIndex + 2));
                else agiArgs.add("");
            }
        }
    }

    /**
     * Returns the agiRequest.
     *
     * @return String
     */
    public String getAgiRequest() {
        return agiRequest;
    }

    /**
     * Returns the agiChannel.
     *
     * @return String
     */
    public String getAgiChannel() {
        return agiChannel;
    }

    /**
     * Returns the agiLanguage.
     *
     * @return String
     */
    public String getAgiLanguage() {
        return agiLanguage;
    }

    /**
     * Returns the agiType.
     *
     * @return String
     */
    public String getAgiType() {
        return agiType;
    }

    /**
     * Returns the agiUniqueId.
     *
     * @return String
     */
    public String getAgiUniqueId() {
        return agiUniqueId;
    }

    /**
     * Returns the agiVersion.
     *
     * @return String
     */
    public String getAgiVersion() {
        return agiVersion;
    }

    /**
     * Returns the agiCallerId.
     *
     * @return String
     */
    public String getAgiCallerId() {
        return agiCallerId;
    }

    /**
     * Returns the agiCallerIdName.
     *
     * @return String
     */
    public String getAgiCallerIdName() {
        return agiCallerIdName;
    }

    /**
     * Returns the agiCallingPres.
     *
     * @return String
     */
    public String getAgiCallingPres() {
        return agiCallingPres;
    }

    /**
     * Returns the agiCallingAni2.
     *
     * @return String
     */
    public String getAgiCallingAni2() {
        return agiCallingAni2;
    }

    /**
     * Returns the agiCallingTon.
     *
     * @return String
     */
    public String getAgiCallingTon() {
        return agiCallingTon;
    }

    /**
     * Returns the agiCallingTns.
     *
     * @return String
     */
    public String getAgiCallingTns() {
        return agiCallingTns;
    }

    /**
     * Returns the agiDnid.
     *
     * @return String
     */
    public String getAgiDnid() {
        return agiDnid;
    }

    /**
     * Returns the agiRdnis.
     *
     * @return String
     */
    public String getAgiRdnis() {
        return agiRdnis;
    }

    /**
     * Returns the agiContext.
     *
     * @return String
     */
    public String getAgiContext() {
        return agiContext;
    }

    /**
     * Returns the agiExtension.
     *
     * @return String
     */
    public String getAgiExtension() {
        return agiExtension;
    }

    /**
     * Returns the agiPriority.
     *
     * @return String
     */
    public String getAgiPriority() {
        return agiPriority;
    }

    /**
     * Returns the agiEnhanced.
     *
     * @return String
     */
    public String getAgiEnhanced() {
        return agiEnhanced;
    }

    /**
     * Returns the agiAccountCode.
     *
     * @return String
     */
    public String getAgiAccountCode() {
        return agiAccountCode;
    }

    /**
     * Returns the agiThreadId.
     *
     * @return String
     */
    public String getAgiThreadId() {
        return agiThreadId;
    }

    /**
     * Returns the agiArgs.
     *
     * @return String
     */
    public List<String> getAgiArgs() {
        return agiArgs;
    }
}