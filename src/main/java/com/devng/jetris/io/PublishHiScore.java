/* PublishHiScore created on 25.09.2006 */
package com.devng.jetris.io;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class PublishHiScore {

    private static final String BASE_URL = "http://jetris.sourceforge.net";

    private static final BASE64Encoder B64E = new BASE64Encoder();

    public static void publish(HiScore hs) throws Exception {
        if (hs.score == 0) {
            return;
        }

        // Construct the full URL
        StringBuffer sb = new StringBuffer(100);
        sb.append(BASE_URL);
        sb.append("/update.php?pass=26195&sc=");
        sb.append(hs.score);
        sb.append("&ln=");
        sb.append(hs.lines);
        sb.append("&name=");
        if ((hs.name != null) && (hs.name.compareTo("") != 0)) {
            sb.append(B64E.encode(hs.name.getBytes()));
        } else {
            sb.append(B64E.encode("<empty>".getBytes()));
        }

        // Send a GET request
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(sb.toString()).openStream()));

        // Check the response, it must == 'ok'
        boolean hasError = false;
        if ((char) in.read() != 'o') {
            hasError = true;
        }
        if ((char) in.read() != 'k') {
            hasError = true;
        }

        in.close();

        if (hasError) {
            throw new Exception("Could not update");
        }
    }
}
