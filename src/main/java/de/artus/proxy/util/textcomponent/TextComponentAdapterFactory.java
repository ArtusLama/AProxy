package de.artus.proxy.util.textcomponent;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class TextComponentAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        //noinspection unchecked
        return (TypeAdapter<T>) new TextComponentAdapter(gson);
    }
}
