package de.artus.proxy.packets.fieldtypes;

import de.artus.proxy.util.textcomponent.TextComponent;
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
public class JsonTextComponentField implements FieldType<TextComponent> {


    @Accessors(chain = true)
    @Getter @Setter
    private TextComponent value;

    @Override
    public FieldType<TextComponent> read(DataInputStream stream) throws IOException {
        return null;
    }

    @Override
    public FieldType<TextComponent> write(DataOutputStream stream) throws IOException {
        return null;
    }
}
