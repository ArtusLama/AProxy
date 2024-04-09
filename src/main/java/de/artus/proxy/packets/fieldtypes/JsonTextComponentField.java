package de.artus.proxy.packets.fieldtypes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.artus.proxy.util.textcomponent.TextComponent;
import de.artus.proxy.util.textcomponent.TextComponentDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class JsonTextComponentField implements FieldType<TextComponent> {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(TextComponent.class, new TextComponentDeserializer()).create();

    @Accessors(chain = true)
    private TextComponent value;

    @Override
    public JsonTextComponentField read(DataInputStream stream) throws IOException {
        String jsonString = new StringField().read(stream).getValue();

        log.trace("Trying to decode JSON: {}", jsonString);
        //setValue(GSON.fromJson(jsonString, TextComponent.class));
        setValue(new TextComponent(";D i got you!")); // TODO just temporary
        return this;
    }

    @Override
    public JsonTextComponentField write(DataOutputStream stream) throws IOException {
        String jsonString = GSON.toJson(getValue());
        new StringField(jsonString).write(stream);
        return this;
    }
}
