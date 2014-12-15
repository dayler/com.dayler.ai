/**
 *
 */
package com.nuevatel.ai.ami.event;

import java.util.Map;

/**
 * @author asalazar
 */
public abstract class AmiEvent implements Response {

    private String name;

    private String uniqueId;

    private String privilege;

    public void build(Map<String, String> metadata) {
        if (!getName().equals(metadata.get(AmiResponseField.Event.name()))) {
            throw new IllegalStateException(String.format("Invalid event name %s for %s.",
                    metadata.get(AmiResponseField.Event.name()), getName()));
        }

        setUniqueId(metadata.get(AmiResponseField.Uniqueid.name()));
        setPrivilege(metadata.get(AmiResponseField.Privilege.name()));
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    protected void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPrivilege() {
        return privilege;
    }

    protected void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public final String toString(String metadata) {
        return String.format("%s: { name=%s, uniqueId=%s, privilege=%s, %s}",
                            getClass().getName(), name, uniqueId, privilege, metadata);
    }
}
