package mvc;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

public class GetRegionByPhone {
	
    
    
    public static void main(String[] args) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();
        PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
        
        String language ="CN";
        PhoneNumber referencePhonenumber = null;
        String phoneNum = "13760228397";
        try {        	
            referencePhonenumber = phoneUtil.parse(phoneNum, language);
            Boolean b = phoneUtil.isValidNumber(referencePhonenumber);
            System.out.println(b);
            
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        //�ֻ������������ referenceRegion 
        String referenceRegion = phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber,Locale.CHINESE);
        String s = phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber,Locale.CHINESE, "5181");
        //phoneNumberOfflineGeocoder.get
        System.out.println(referenceRegion+"-----"+s);
	}  
	
    
}
