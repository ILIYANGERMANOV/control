package custom.extras.values;

public class Constants {
    public static final String DEBUG_TAG = "ddq";
    public static final String ERROR = "Error";

    public static final String OPERATOR = "Operator";
    public static final String SYSTEM_MESSAGE = "System message";
    public static final String FROM_OPERATOR = "anon_to_Operator";
    public static final String OPERATOR_UNKNOWN = "Unknown";


    public static final String HARDCODED_ENCRYPTED_MESSAGE_7001 =
            "4ff94db9d96bdd7f88bbebe97a34ec2844712fba6e5ea0bbd4fe8a630650d513";

    public static final String HARDCODED_ENCRYPTED_MESSAGE_7009 =
            "0944c2a7b4e05353ec0409060bb8b6cd9b62c0dad64e790647c8f7689786f53c";

    public static final String ENCRYPTION_STATUS_OK = "#status_ok";
    public static final String ENCRYPTION_STATUS_WRONG_PASS = "#wrong_password___";
    public static final int ENCRYPTION_STATUS_OK_LENGTH = ENCRYPTION_STATUS_OK.length();

    public static final int MAX_PASSWORD_TRIES = 5;

    public static final String SHOW_PASSWORD_TEXT = "Show password";
    public static final String PASSWORD_DIALOG_TITLE = "Enter password:";
    public static final String PASSWORD_DIALOG_POSITIVE = "Continue";
    public static final String PASSWORD_DIALOG_NEGATIVE = "Cancel";
    public static final String ENTER_PASSWORD_MESSAGE = "Enter your communication password.";
    public static final String LAST_ATTEMPT_MESSAGE =
            "WARNING: You have entered " + MAX_PASSWORD_TRIES + " times wrong password.\n" +
                    "Next time you enter wrong password your conversations will be wiped!";

    public static final String PASSWORD_EXTRA = "password_extra";
    public static final int INIT_CONVERSATION_RES_CODE = 5;

    public static final String NO_DATE = "no_date";
    public static final long NO_DOWNLOAD_ID = 0;

    public static final String SERVER_IP = "http://192.168.0.100:1312";
    public static final String VERSION_API = "/version";
    public static final String DOWNLOAD_API = "/static/file.apk";
}
