package de.artus.proxy.packets.fieldtypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


@NoArgsConstructor
@AllArgsConstructor
public class LongField implements FieldType<Long> {

    @Accessors(chain = true)
    @Getter @Setter
    private Long value;

    @Override
    public LongField read(DataInputStream stream) throws IOException {
        setValue(stream.readLong());
        return this;
    }

    @Override
    public LongField write(DataOutputStream stream) throws IOException {
        stream.writeLong(getValue());
        return this;
    }
}
