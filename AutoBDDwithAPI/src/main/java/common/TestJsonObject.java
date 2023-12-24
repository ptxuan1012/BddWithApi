package common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestJsonObject {
	public static JSONObject jsonToObject(String json) {
		JSONParser parser = new JSONParser();
		try {
			return (JSONObject) parser.parse(json);
		} catch (ParseException e) {
			return null;
		}
	}

	/*
	 * key: "abc.yzn"
	 */
	public static String replateJSONObjectValue(JSONObject request, String key,String value) {
		String[] splitKey = key.split("\\.");
		JSONObject parentOfKey = request;
		for (int i = 0; i < splitKey.length - 1; ++i) {
			String subKey = splitKey[i];

			Object subValue = parentOfKey.get(subKey);
			if (subValue instanceof JSONObject) {
				parentOfKey = (JSONObject) subValue;
			}
		}

		if (parentOfKey == null) {
			throw new RuntimeException("key sai rồi bạn ơi");
		}
		if (value.equals("missing")) {
			parentOfKey.remove(splitKey[splitKey.length - 1]);
		} else if (value.equals("\"\"")) {
			parentOfKey.put(splitKey[splitKey.length - 1], "");
		} else if (value.equals("null")) {
			parentOfKey.put(splitKey[splitKey.length - 1], null);
		} else if (isNumeric(value)) {
			parentOfKey.put(splitKey[splitKey.length - 1], Double.parseDouble(value));
		} else {
			parentOfKey.put(splitKey[splitKey.length - 1], value);
		}
		return request.toJSONString();
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static String getValueByKey(JSONObject response, String key) {
		String value = "";
		String[] splitKey = key.split("\\.");
		JSONObject parentOfKey = response;
		for (int i = 0; i < splitKey.length - 1; i++) {
			String subKey = splitKey[i];
			Object subValue = parentOfKey.get(subKey);
			if (subValue instanceof JSONObject) {
				parentOfKey = (JSONObject) subValue;
			}
		}
		if (parentOfKey == null) {
			// "truyen sai key roi"
			return null;
		}
		value = parentOfKey.get(splitKey[splitKey.length - 1]).toString();
		System.out.println("text la: " + value);
//		} catch (Exception e) {
//			System.out.println("Response body is null.");
//			e.printStackTrace();
//		}
		return value;
	}

	public static void main(String[] args) {
		String body = "{\n" + "    \"header\": {\n" + "        \"reqType\": \"REQUEST\",\n"
				+ "        \"api\": \"FT_LIMIT_DEST_API\",\n" + "        \"apiKey\": \"QMKLFONI1EZXLF2CKPYGPFX248\",\n"
				+ "        \"priority\": \"3\",\n" + "        \"channel\": \"SEANET\",\n"
				+ "        \"subChannel\": \"SEAPORTAl\",\n" + "        \"location\": \"10.9.12.90\",\n"
				+ "        \"context\": \"PC\",\n" + "        \"trusted\": \"false\",\n"
				+ "        \"userID\": \"FT_LIMIT_DEST_API\",\n" + "        \"requestAPI\": \"FT_LIMIT_DEST_API\",\n"
				+ "        \"requestNode\": \"02\",\n" + "        \"synasyn\": \"true\"\n" + "    },\n"
				+ "    \"body\": {\n" + "        \"command\": \"GET_TRANSACTION\",\n" + "        \"transaction\": {\n"
				+ "            \"authenType\": \"CRITERIA_LIST\",\n" + "            \"method\": \"R\",\n"
				+ "            \"key\": \"CATEGORY1\",\n" + "            \"value\": \"Mo ta cho CATEGORY\"\n"
				+ "        }\n" + "    }\n" + "}";
		String response = "{\"body\": {\r\n" + "        \"command\": \"GET_TRANSACTION\",\r\n"
				+ "        \"transaction\": {\r\n" + "            \"authenType\": \"CRITERIA_LIST\",\r\n"
				+ "            \"method\": \"R\",\r\n" + "            \"key\": \"CATEGORY1\"\r\n" + "        }}";

		String value = "{\n" + "        \"coin_amount\": \"10000000\",\n"
				+ "        \"payment_method\": \"local_bank\",\n" + "        \"country_code\": \"vn\",\n"
				+ "        \"bank_name\": \"Vietcombank\",\n" + "        \"user_level\": 3\n" + "    }";

		System.out.println(replateJSONObjectValue(jsonToObject(body), "body.command", "null"));
		//System.out.println(getValueByKey(jsonToObject(response), "body.command"));
		// System.out.println(replateJSONObjectValue(jsonToObject(body),
		// "body.transaction.hung", jsonToObject(value)));
	}
}