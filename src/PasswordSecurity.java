
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSecurity {
	public static String Encrypt(String password)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i<byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Incorrect Hashing Algorithm");
			//e.printStackTrace();
		}
		return "";
	}
}
