package javaUtils;

public class SMSSender {
	public String phone;
	public String message;
	public SMSSender (String phone, String message) {
		this.phone = phone;
		this.message = message;
	}
	public String sendSMS() {
		String phoneT = phone.replaceAll("\\s","");
		String phones[] = phoneT.split(";");
		String error = "";
		for (int i=0; i < phones.length - 1; i++)
		{
		  SimpleSMSSend sms = new SimpleSMSSend(phones[i], message);
		  error = sms.getResult();
		}
		return error;
	}
}
