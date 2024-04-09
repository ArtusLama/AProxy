package de.artus.proxy.packets.fieldtypes;

import com.google.gson.Gson;
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
    public JsonTextComponentField read(DataInputStream stream) throws IOException {
        String jsonString = new StringField().read(stream).getValue();
        setValue(new Gson().fromJson(jsonString, TextComponent.class));
        return this;
    }

    @Override
    public JsonTextComponentField write(DataOutputStream stream) throws IOException {
        String jsonString = new Gson().toJson(getValue());
        new StringField(jsonString).write(stream);
        return this;
    }
}
