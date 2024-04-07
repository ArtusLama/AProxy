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
public class BooleanField implements FieldType<Boolean> {

    @Accessors(chain = true)
    @Getter @Setter
    private Boolean value;


    @Override
    public BooleanField read(DataInputStream stream) throws IOException {
        setValue(stream.readBoolean());

        return this;
    }

    @Override
    public BooleanField write(DataOutputStream stream) throws IOException {
        stream.writeBoolean(getValue());
        return this;
    }
}
