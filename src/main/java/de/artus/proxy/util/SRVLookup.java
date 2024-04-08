package de.artus.proxy.util;

import lombok.extern.slf4j.Slf4j;
import org.xbill.DNS.Record;
import org.xbill.DNS.*;

import java.net.InetSocketAddress;

@Slf4j
public class SRVLookup {

    private static final String SRV_QUERY_PREFIX = "_minecraft._tcp.%s";


    public static InetSocketAddress lookup(ServerAddress server) {
        return lookup(server.getHost(), server.getPort());
    }
    public static InetSocketAddress lookup(String hostname, int port) {
        try {
            Lookup lookup = new Lookup(String.format(SRV_QUERY_PREFIX, hostname), Type.SRV);
            Record[] records = lookup.run();
            if (records == null || records.length == 0) {
                log.info("No SRV Records found for {}! Using {}:{}", hostname, hostname, port);
                return new InetSocketAddress(hostname, port);
            }

            SRVRecord srvRecord = (SRVRecord) records[0];
            return new InetSocketAddress(srvRecord.getTarget().toString(), srvRecord.getPort());
        } catch (TextParseException e) {
            log.error("Error while parsing SRV Record!", e);
            return new InetSocketAddress(hostname, port);
        }
    }
}
