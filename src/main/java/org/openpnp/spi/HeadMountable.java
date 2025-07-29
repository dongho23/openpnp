package org.openpnp.spi;

import org.openpnp.model.Identifiable;
import org.openpnp.model.Location;
import org.openpnp.model.Named;
import org.openpnp.model.Solutions;
import org.openpnp.spi.MotionPlanner.CompletionType;

public interface HeadMountable extends MovableMountable, Identifiable, Named, Solutions.Subject {
    /**
     * Gets the Head that this HeadMountable is attached to. If it is not attached to a Head this
     * method returns null.
     * 
     * @return
     */
    Head getHead();

    /**
     * Set the Head that this HeadMountable is attached to. Called by the Head when the
     * HeadMountable is added to it.
     */
    void setHead(Head head);
    
    /**
     * Get the tool specific calibrated offset for the camera.
     * @see org.openpnp.spi.Camera.getLocation(HeadMountable)
     * 
     * @param camera
     * @return
     */
    Location getCameraToolCalibratedOffset(Camera camera);

    /**
     * Transform the specified HeadMountable location to a Head location. This will typically
     * apply the Head Offset and apply other transformations such as Runout Compensation on a Nozzle. 
     * 
     * @param location The HeadMountable location.
     * @param options Location approximation options @see org.openpnp.spi.Movable.LocationOption
     * @return
     * @throws Exception 
     */
    Location toHeadLocation(Location location, LocationOption... options) throws Exception;

    /**
     * Transform the specified Head location to a HeadMountable location. This will typically
     * unapply the Head Offset and unapply other transformations such as Runout Compensation on a Nozzle.
     * 
     * @param location The Head location.
     * @param options Location approximation options @see org.openpnp.spi.Movable.LocationOption
     * @return
     */
    Location toHeadMountableLocation(Location location, LocationOption... options);

    /**
     * Wait for any motion of this HeadMountable to be completed according to the completionType. 
     * If this is not mounted to any Head (e.g. the bottom camera), the wait must encompass the whole 
     * machine, as any HeadMountable could then be the camera's subject. 
     * 
     * @param completionType
     * @throws Exception
     */
    void waitForCompletion(CompletionType completionType) throws Exception;
    
    /**
     * Get the head offsets for the object
     */
    Location getHeadOffsets();

    /**
     * Set the head offsets for the object
     * @param headOffsets The offsets in XYZ
     */
    void setHeadOffsets(Location headOffsets);
    
    /**
     * Wait for a given time before continuing with the next command. The delay my be executed
     * using the OS (via Thread.sleep()) or - if supported - by the driver. 
     * If addition head mountables are given, they are taken into account. This shall be use
     * to indicate, that eg a nozzle is in relation to it's vacuum valve when timing a dwell
     * delay.
     *
     * @param milliseconds
     * @param hms
     * @throws Exception
     */
    void delay(int milliseconds, HeadMountable... hms) throws Exception;
}
