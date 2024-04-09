package de.artus.proxy.packets.fieldtypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UUIDField implements FieldType<UUID> {

    // TODO CHECK IF UUID READ + WRITE WORKS / IS CORRECT

    @Accessors(chain = true)
    @Getter @Setter
    private UUID value;

    @Override
    public UUIDField read(DataInputStream stream) throws IOException {
        long mostSigBits = stream.readLong();
        long leastSigBits = stream.readLong();
        setValue(new UUID(mostSigBits, leastSigBits));


        return this;
    }

    @Override
    public UUIDField write(DataOutputStream stream) throws IOException {
        stream.writeLong(getValue().getMostSignificantBits());
        stream.writeLong(getValue().getLeastSignificantBits());

        return this;
    }
}
