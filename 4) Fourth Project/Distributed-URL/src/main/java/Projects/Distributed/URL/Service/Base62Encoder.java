package Projects.Distributed.URL.Service;


//instead of a long number like 324981237461234, you can represent it as a Base62 string like "A9xZk3"
public class Base62Encoder {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // Convert a Snowflake ID to a Base62 string
    public static String encode(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62.charAt((int) (id % 62)));
            id /= 62;
        }
        return sb.reverse().toString();
    }

    // Convert a Base62 string back to a Snowflake ID
    public static long decode(String base62) {
        long id = 0;
        for (char c : base62.toCharArray()) {
            id = id * 62 + BASE62.indexOf(c);
        }
        return id;
    }
}
