package ScSDK.MapXML;

/**
 * Exception for level parameters going wonky
 *
 * @author clarence
 */
class LevelParseException extends Exception {

    public LevelParseException() {
    }

    public LevelParseException(String message) {
        super(message);
    }

    public LevelParseException(Throwable cause) {
        super(cause);
    }

    public LevelParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
