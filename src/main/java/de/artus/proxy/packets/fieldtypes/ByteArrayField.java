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
public class ByteArrayField implements FieldType<byte[]> {

    @Accessors(chain = true)
    @Getter @Setter
    private byte[] value;

    @Accessors(chain = true)
    @Getter @Setter
    private int length;

    @Override
    public ByteArrayField read(DataInputStream stream) throws IOException {
        setValue(stream.readNBytes(getLength()));

        return this;
    }

    @Override
    public ByteArrayField write(DataOutputStream stream) throws IOException {
        stream.write(getValue());

        return this;
    }

}
