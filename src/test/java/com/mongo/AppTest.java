package com.mongo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Json compressor App.
 */
public class AppTest 
{
    @Test
    public void reformatJsonCompressValidJson() {
        String json = "{\"a\":1,\"b\":true,\"c\":{\"d\":3,\"e\":\"test\"}}";
        String formattedJson = App.reformatJson(json);
        String compressedJson = "{\"a\":1,\"b\":true,\"c.d\":3,\"c.e\":\"test\"}";
        assertTrue( formattedJson.equals(compressedJson) );

        String json2 = "{\"a\":1,\"b\":true,\"c\":{\"d\":3,\"e\":\"test\",\"f\":{\"g\":3,\"h\":{\"asd\":123,\"fggdfs\":true,\"asdsdfsdf\":123.1}}}}";
        String formattedJson2 = App.reformatJson(json2);
        String compressedJson2 = "{\"a\":1,\"b\":true,\"c.d\":3,\"c.f.h.fggdfs\":true,\"c.e\":\"test\",\"c.f.h.asdsdfsdf\":123.1,\"c.f.g\":3,\"c.f.h.asd\":123}";
        assertTrue( formattedJson2.equals(compressedJson2) );
    }

    @Test
    public void reformatJsonCompressEmptyJson() {
        String json = "{}";
        String formattedJson = App.reformatJson(json);
        String compressedJson = "{}";
        assertTrue( formattedJson.equals(compressedJson) );
    }

    @Test(expected = NullPointerException.class)
    public void reformatJsonCompressInvalidJson() {
        String json = "{this is not a valid json}";
        App.reformatJson(json);
    }
}
