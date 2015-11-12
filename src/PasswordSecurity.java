import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class PasswordSecurity {
	public static String encode_password(String pass)
	{
		try
		{
			String tmp = new String(URLEncoder.encode(pass, "UTF-8"));
			return tmp;
		}
		catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
		}
		return "";
		
	}
	public static String decode_password(String pass)
	{
		try
		{
			String tmp = new String(URLDecoder.decode(pass, "UTF-8"));
			return tmp;
		}
		catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
}
