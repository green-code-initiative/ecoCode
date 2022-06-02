import android.location.Location;  // Noncompliant {{Use com.google.android.gms.location instead of android.location to maximize battery life.}}
import android.location.GnssStatus; // Noncompliant {{Use com.google.android.gms.location instead of android.location to maximize battery life.}}
import android.location.*; // Noncompliant {{Use com.google.android.gms.location instead of android.location to maximize battery life.}}
import com.location.*;
import com.location.GnssStatus;
import android.GnssStatus;

public class Dijon {
}
