package android.bluetooth;

public final class BluetoothGatt {

    public static final int CONNECTION_PRIORITY_LOW_POWER = 2;
    public static final int CONNECTION_PRIORITY_HIGH = 1;
    public static final int CONNECTION_PRIORITY_BALANCED = 0;
    public static final long CONNECTION_PRIORITY_BALANCED_LONG = 0L;


    public void test() {
        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(CONNECTION_PRIORITY_HIGH); // Noncompliant {{Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.}}
        gatt.requestConnectionPriority(CONNECTION_PRIORITY_BALANCED); // Noncompliant {{Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.}}
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_BALANCED); // Noncompliant {{Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.}}
        gatt.requestConnectionPriority((int) CONNECTION_PRIORITY_BALANCED_LONG); // Noncompliant {{Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.}}
        gatt.requestConnectionPriority(0); // Noncompliant {{Invoking BluetoothGatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER) is recommend to reduce power consumption.}}
        gatt.requestConnectionPriority(CONNECTION_PRIORITY_LOW_POWER);
        gatt.requestConnectionPriority(2);
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_LOW_POWER);
        gatt.requestConnectionPriority((int) 2L);
    }

    public boolean requestConnectionPriority(int connectionPriority) {
        return true;
    }
}
