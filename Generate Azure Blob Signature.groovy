/*
Documents:
https://docs.microsoft.com/en-us/rest/api/storageservices/list-blobs
https://docs.microsoft.com/en-us/rest/api/storageservices/get-blob
https://docs.microsoft.com/en-us/rest/api/storageservices/put-blob
https://docs.microsoft.com/en-us/rest/api/storageservices/delete-blob
https://docs.microsoft.com/en-us/rest/api/storageservices/authorize-with-shared-key
*/

// Import required classes
import java.util.Properties;
import java.io.InputStream;
import com.boomi.execution.ExecutionUtil;
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.nio.charset.StandardCharsets

// Define variables
String accountName = ExecutionUtil.getDynamicProcessProperty("Account Name DPP");
String key = ExecutionUtil.getDynamicProcessProperty("Access Key DPP");
String container = ExecutionUtil.getDynamicProcessProperty("Container Name DPP");
String httpVerb = ExecutionUtil.getDynamicProcessProperty("HTTP Verb DPP");
String operation = ExecutionUtil.getDynamicProcessProperty("Operation DPP");
String contentType = ExecutionUtil.getDynamicProcessProperty("Content Type DPP") ?: "";

// Get input data stream
for (int i = 0; i < dataContext.getDataCount(); i++) {
    InputStream is = dataContext.getStream(i);
    Properties props = dataContext.getProperties(i);
	
	// Set the value of variables
    String contentLength = is.text.length();
    String blobName = props.getProperty("document.dynamic.userdefined.Blob Name DDP") ?: "";
    String longDate = setLongDate();
    String stringToSign = getStringToSign(operation, httpVerb, contentLength, contentType, longDate, accountName, container, blobName)
    String authorization = "SharedKey " + accountName + ":" + getHMAC256(key, stringToSign);
	
	// Reset the value of variables
    is.reset();
	
	// Set headers for HTTP request
    props.setProperty("document.dynamic.userdefined.Authorization", authorization);
    props.setProperty("document.dynamic.userdefined.x-ms-date", longDate);

    dataContext.storeStream(is, props);
}

// Generate Signature
String getHMAC256(String base64Key, String stringToSign) {
    byte[] key = Base64.getDecoder().decode(base64Key);
    Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
    hmacSHA256.init(new SecretKeySpec(key, "HmacSHA256"));
    byte[] utf8Bytes = stringToSign.getBytes(StandardCharsets.UTF_8);
    byte[] output = hmacSHA256.doFinal(utf8Bytes);
    return Base64.getEncoder().encodeToString(output);
}

// Generate Current Date
String setLongDate() {
    DateFormat longDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss zz");
    longDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date date = new Date();
    return longDateFormat.format(date);
}

// Generate StringToSign value
String getStringToSign(String operation, String httpVerb, String contentLength, String contentType, String longDate, String accountName, String container, String blobName) {
    
    String stringToSign
    switch (operation) {
        case "GET": stringToSign = httpVerb + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "x-ms-date:" + longDate + "\n" +
                "x-ms-version:2020-10-02\n" +
                "/" + accountName + "/" + container + "/" + blobName;
            break;
        case "CREATE": stringToSign = httpVerb + "\n" +
                "\n" +
                "\n" + contentLength +
                "\n" +
                "\n" + contentType +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "x-ms-blob-type:BlockBlob" + "\n" +
                "x-ms-date:" + longDate + "\n" +
                "x-ms-version:2020-10-02\n" +
                "/" + accountName + "/" + container + "/" + blobName;
            break;
        case "DELETE": stringToSign = httpVerb + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "x-ms-date:" + longDate + "\n" +
                "x-ms-version:2020-10-02\n" +
                "/" + accountName + "/" + container + "/" + blobName
            break;
        case "LIST": stringToSign = httpVerb + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "x-ms-date:" + longDate + "\n" +
                "x-ms-version:2020-10-02\n" +
                "/" + accountName + "/" + container + "\n" +
                "comp:list\n" +
                "restype:container";
            break;
        default: throw new Exception(httpVerb + " is not a valid Operation DPP. Valid values are GET, CREATE, DELETE, or LIST.");
    }
    
    return stringToSign;

}