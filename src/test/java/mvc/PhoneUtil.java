package mvc;

import com.alibaba.fastjson.JSONObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mao, hongtu1993@sina.cn
 * @version Enter version here..., 5:47 PM 2019/12/16
 */
public class PhoneUtil {
  private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

  private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();


  /**
   * ���ݹ��Ҵ�����ֻ��� �ж��ֻ����Ƿ���Ч
   * @param phoneNumber �ֻ�����
   * @param countryCode ����(����)
   * @return true / false
   */
  public static boolean checkPhoneNumber(long phoneNumber, int countryCode) {
    PhoneNumber pn = new PhoneNumber();
    pn.setCountryCode(countryCode);
    pn.setNationalNumber(phoneNumber);
    return phoneNumberUtil.isValidNumber(pn);
  }

  private static Pattern phoneReg = Pattern.compile("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$");

  public static JSONObject getPhoneNumberInfo(String phoneNumber) throws Exception {
    // ����У��
    Matcher matcher = phoneReg.matcher(phoneNumber);
    if (!matcher.find()) {
      throw new Exception("The phone number maybe like:" + "+[National Number][Phone number], but got " + phoneNumber);
    }

    Phonenumber.PhoneNumber referencePhonenumber;
    try {
      String language = "CN";
      referencePhonenumber = phoneNumberUtil.parse(phoneNumber, language);
    } catch (NumberParseException e) {
      throw new Exception(e.getMessage());
    }
    String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(referencePhonenumber);

    if (regionCodeForNumber == null) {
      throw new Exception("Missing region code by phone number " + phoneNumber);
    }

    boolean checkSuccess = PhoneUtil.checkPhoneNumber(referencePhonenumber.getNationalNumber(), referencePhonenumber.getCountryCode());
    if (!checkSuccess) {
      throw new Exception("Not an active number:" + phoneNumber);
    }

    String description = geocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);

    int countryCode = referencePhonenumber.getCountryCode();
    long nationalNumber = referencePhonenumber.getNationalNumber();
    JSONObject resultObject = new JSONObject();
    // ������� Locale : HK, US, CN ...
    resultObject.put("regionCode", regionCodeForNumber);
    // ����: 86, 1, 852 ... @link: https://blog.csdn.net/wzygis/article/details/45073327
    resultObject.put("countryCode", countryCode);
    // ȥ��+�� �� ����/���� ���ʵ�ʺ���
    resultObject.put("nationalNumber", nationalNumber);
    // ���ڵ���������Ϣ
    resultObject.put("description", description);
    // ȥ��+�ź�ĺ��� (���ڰ����Ʒ��Ͷ���)
    resultObject.put("number", String.valueOf(countryCode) + nationalNumber);
    resultObject.put("fullNumber", phoneNumber);

    return resultObject;

  }

  public static void main(String[] args) throws Exception {

    // {"number":"85268476749","regionCode":"HK","nationalNumber":68476749,"countryCode":852,"description":"���","fullNumber":"+85268476749"}
    System.out.println(PhoneUtil.getPhoneNumberInfo("+8613410319647").toJSONString());

    
  }
}

