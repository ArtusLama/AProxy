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
public class StringField implements FieldType<String> {

    @Accessors(chain = true)
    @Getter @Setter
    private String value;


    @Override
    public StringField read(DataInputStream stream) throws IOException {
        int length = new VarIntField().read(stream).getValue();
        setValue(new String(stream.readNBytes(length)));

        return this;
    }

    @Override
    public StringField write(DataOutputStream stream) throws IOException {
        new VarIntField().setValue(getValue().getBytes().length).write(stream);
        stream.write(getValue().getBytes());

        return this;
    }
}
