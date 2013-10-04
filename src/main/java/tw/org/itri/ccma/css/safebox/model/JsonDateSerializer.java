package tw.org.itri.ccma.css.safebox.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/***
 * 前端轉 JSON 格式專用，將日期型態轉成客製的顯示格式
 * 
 * @author A10138
 * 
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy HH:mm");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException,
			JsonProcessingException {

		String formattedDate = SDF.format(date);

		gen.writeString(formattedDate);
	}
}
