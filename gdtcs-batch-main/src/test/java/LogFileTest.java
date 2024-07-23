import com.gdtcs.batch.dataCollectors.util.LogFileUtil;

public class LogFileTest {
	public static void main(String[] args) {
		System.out.println("LogFileTest");
		LogFileUtil.logWrite("/Users/djjeong/Desktop/log", "LogFileTest", "LogFileTest|01|02", "UTF-8");
	}
}
