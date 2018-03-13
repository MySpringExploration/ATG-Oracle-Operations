package org.iqra.operationsapp.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class BCryptEncoderUtil {
	
		public String encrypt(String passowrd) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.encode(passowrd);
			
		}
		
		/*public static void main(String args[]) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println(encoder.encode("Admin"));
		}*/
}
