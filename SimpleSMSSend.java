package javaUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL; 

public class SimpleSMSSend {
	public String phoneNumber;
	public String message;
	// provide your license key
	private String licenseKey = "db6ca563-814f-42bb-ae2f-6f433d8bxxxx";
	
	public SimpleSMSSend() {
		
	}
	
	public SimpleSMSSend(String phoneNumber, String message) {
	   this.phoneNumber = phoneNumber.replaceAll("[^0-9]","");
	   this.message = message.replace(" ", "%20");
    }
	
	//
	public String getResult() {
		String result = "";
		try {
			URL url = new URL("http://sms2.cdyne.com/sms.svc/SimpleSMSsend?"
	                + "PhoneNumber=" + phoneNumber
	                + "&Message=" + message
	                + "&LicenseKey=" + licenseKey);
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null)
		            System.out.println(inputLine);
		        in.close();
				    
			} catch (java.io.IOException e1) {
				System.out.println(e1.getMessage());
				result = e1.getMessage();
			}
				
		}  catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			result = e.getMessage();
		}
		return result;
	}
	//
}
