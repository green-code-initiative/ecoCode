package io.ecocode.java.checks.bottleneck;

import io.ecocode.java.checks.helpers.OpeningClosingMethodCheck;
import org.sonar.check.Rule;

/**
 * Check that WifiManager.MulticastLock#release() method is closed after WifiManager.MulticastLock#acquire().
 *
 * @see OpeningClosingMethodCheck
 */
@Rule(key = "EBOT002", name = "ecoCodeWifiMulticastLock")
public class WifiMulticastLockRule extends OpeningClosingMethodCheck {
    private static final String ERROR_MESSAGE = "Failing to call WifiManager.MulticastLock#release() can cause a noticeable battery drain.";

    public WifiMulticastLockRule() {
        super("acquire", "release", "android.net.wifi.WifiManager$MulticastLock");
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
