package de.artus.proxy.packets.packet.s2c;

import de.artus.proxy.packets.fieldtypes.ByteArrayField;
import de.artus.proxy.packets.fieldtypes.JsonTextComponentField;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class S2CEncryptionRequest extends Packet {


    private StringField serverId = new StringField(); // appears to be empty
    private VarIntField publicKeyLength = new VarIntField();
    private ByteArrayField publicKey = new ByteArrayField();
    private VarIntField verifyTokenLength = new VarIntField();
    private ByteArrayField verifyToken = new ByteArrayField();

    @Override
    public S2CEncryptionRequest read(DataInputStream stream) throws IOException {
        setServerId(new StringField().read(stream));

        setPublicKeyLength(new VarIntField().read(stream));
        setPublicKey(new ByteArrayField().setLength(getPublicKeyLength().getValue()).read(stream));

        setVerifyTokenLength(new VarIntField().read(stream));
        setVerifyToken(new ByteArrayField().setLength(getPublicKeyLength().getValue()).read(stream));

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        getServerId().write(stream);

        getPublicKeyLength().setValue(getPublicKey().getLength()).write(stream);
        getPublicKey().write(stream);

        getPublicKeyLength().setValue(getVerifyToken().getLength()).write(stream);
        getVerifyToken().write(stream);
    }


}
