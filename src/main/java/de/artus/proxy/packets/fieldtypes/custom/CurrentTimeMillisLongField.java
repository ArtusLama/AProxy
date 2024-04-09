package de.artus.proxy.packets.fieldtypes.custom;

import de.artus.proxy.packets.fieldtypes.LongField;

import java.io.DataOutputStream;
import java.io.IOException;

public class CurrentTimeMillisLongField extends LongField {

    @Override
    public LongField write(DataOutputStream stream) throws IOException {
        setValue(System.currentTimeMillis());
        return super.write(stream);
    }
}
