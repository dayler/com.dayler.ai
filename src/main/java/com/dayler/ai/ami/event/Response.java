/**
 *
 */
package com.dayler.ai.ami.event;

import java.util.Map;

/**
 * @author asalazar
 */
public interface Response {

    void build(Map<String, String> metadata, Object ctx);

    String getUniqueId();

}
