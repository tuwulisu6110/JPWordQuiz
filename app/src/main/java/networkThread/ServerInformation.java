package networkThread;

/**
 * Created by tuwulisu on 2015/2/28.
 */
public class ServerInformation
{
    static final String serverURL = "http://220.135.188.70:5000";
    static final String serverLoginURL = serverURL+"/login";
    static final String serverRegisterURL = serverURL+"/register";
    static final String serverListSourceURL = serverURL+"/listSource";
    static final String serverAddSourceURL = serverURL+"/addSource";
    static final String serverAddWordURL = serverURL+"/addWord";
    static final String serverSearchURL = serverURL+"/searchWordByWordAndReading";
    static final String serverDeleteWordURL = serverURL+"/deleteWord";
    static final String serverRandomWordURL = serverURL+"/randomWord";
    static final String serverRecordAnswerResultURL = serverURL+"/recordAnswerResult";
    static final String serverListAllReadingByWordURL = serverURL+"/listAllReadingByWord";
}
