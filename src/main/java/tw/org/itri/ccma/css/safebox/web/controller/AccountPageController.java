package tw.org.itri.ccma.css.safebox.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.org.itri.ccma.css.safebox.model.Bucket;
import tw.org.itri.ccma.css.safebox.model.Member;

/***
 * Account � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class AccountPageController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(AccountPageController.class);
	private static final BigDecimal MAX_BUCKET_SIZE = new BigDecimal("21474836480"); // 20GB
	private static final int ONE_GIGA_IN_BYTE = 1073741824;
	private static final String EBS = "http://127.0.0.1/sbx_svr/rest/EBS/querybucketinfo?";
	//private static final NumberFormat numFormat = new DecimalFormat("0.##");
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, Model model) {
	
		List<Bucket> bucketList = getDataService().getAllBuckets();
		/*
		BigDecimal usedSize = new BigDecimal("0");
		for (Bucket bucket : bucketList) {
			usedSize.add(new BigDecimal(bucket.getUsedSize()));
		}
		*/
		Member loginMember = (Member) request.getAttribute("member");
		String getuserName = loginMember.getUserName();
		String bucketsName = bucketList.get(0).getBucketName();
		
		String bucketInfo = EBS + "account=" + getuserName + "&bucketname=" + bucketsName;
		String totalsUsed = "";
		String maxmumSize = "";

	 	try {
	 		DefaultHttpClient httpClient = new DefaultHttpClient();
     		HttpGet httpGeting = new HttpGet(bucketInfo);
     		HttpResponse httpRespon = httpClient.execute(httpGeting);
     		StatusLine statusLine = httpRespon.getStatusLine();
     		int statusCode = statusLine.getStatusCode();
     		
     		if (statusCode == HttpStatus.SC_OK) {
     			HttpEntity httpEntity = httpRespon.getEntity();
     			String jsonResult = EntityUtils.toString(httpEntity);
     			JSONObject jsonObject = new JSONObject(jsonResult);
     			totalsUsed = jsonObject.getString("totalUsed");
     			maxmumSize = jsonObject.getString("maxSize");
     		}
	 	} catch (ClientProtocolException e) {
     		// TODO Auto-generated catch block
	 		e.printStackTrace();
     	} catch (IOException e) {
     		// TODO Auto-generated catch block
     		e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float usedSizes = Float.valueOf(totalsUsed);
		float totalSize = Float.valueOf(maxmumSize);
		float percentag = usedSizes / totalSize * 100;

		model.addAttribute("totalSize", getGigaSize(MAX_BUCKET_SIZE));
		model.addAttribute("usedSize", String.valueOf((float) Math.round(usedSizes / 1048576) * 100 / 100));
		model.addAttribute("usedPer", String.valueOf((float) Math.round((percentag > 1 ? percentag : 1) * 100) / 100));
		/*
		model.addAttribute("totalSize", getGigaSize(MAX_BUCKET_SIZE));
		model.addAttribute("usedSize", getGigaSize(usedSize));
		model.addAttribute("usedPer", String.valueOf(numFormat.format(usedSize.divide(MAX_BUCKET_SIZE))));
		*/
		return getViewPagePrefix() + "account";
	}

	@RequestMapping(value = "/json/account", method = RequestMethod.GET)
	@ResponseBody
	public List<Bucket> showBucketList() {
		return getDataService().getAllBuckets();
	}

	private String getGigaSize(BigDecimal decimal) {
		return decimal.divide(new BigDecimal(ONE_GIGA_IN_BYTE)) + "G";
	}
	
}
