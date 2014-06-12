/*
 * 姓名：林楷翔
 * 學號：F84006024
 * 描述：需給予四個參數，第一個是資料來源的json檔案連結，後面三個則是搜尋的條件，分別為鄉鎮市區、地址關鍵字、交易年分(以後)。
 *  從給予的連結抓下一個json的檔案，找出並計算所有符合條件的物件的交易價格平均。
 * 
*/
import java.net.*;

import org.json.*;
import java.io.*;


public class TocHw3 {

	public static void main(String[] args) 
	{
		if(args.length < 4)
		{	
			System.out.println("No input!");
			return;
		}
		download(args[0]);

		//System.out.println(args[1]+" "+args[2]+" "+args[3]);

		File inFile = new File("downFile.json");

		String match = new String();
		int count = 0;
		long totalprice = 0; 
		try 
		{
			JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new InputStreamReader(new FileInputStream(inFile),"UTF-8")));

			for(int i = 0 ; i < jsonRealPrice.length(); i++)
			{
				JSONObject jsontmp = jsonRealPrice.getJSONObject(i);
				int year;
				String addr;


				//	System.out.println(jsontmp.getString("鄉鎮市區")); 

				if( !jsontmp.isNull("鄉鎮市區") && !jsontmp.isNull("交易年月") && !jsontmp.isNull("土地區段位置或建物區門牌"))
						if( args[1].equals(jsontmp.getString("鄉鎮市區")) && (year = jsontmp.getInt("交易年月")) > Integer.parseInt(args[3]) * 100 && (addr = jsontmp.getString("土地區段位置或建物區門牌")).indexOf(args[2])!= -1)
				{
					addr = jsontmp.getString("土地區段位置或建物區門牌");
					int price = jsontmp.getInt("總價元");
					count++;
					totalprice += price;
					match += count + ":" + addr + " " + year + " " + price + "\n";
				}


			}
			//System.out.println(match);

			/*
			Scanner fs = new Scanner(new FileInputStream(inFile));
			
			System.out.println(fs.nextLine());
			System.out.println(fs.nextLine());
			*/

		}

		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double avgprice = totalprice / count;
		System.out.println((int)(avgprice));


	}

	static void download(String url)
	{
		//System.out.println("正在下載資料......");
		try 
		{
			URL sourse = new URL(url);

			HttpURLConnection consrc = (HttpURLConnection) sourse.openConnection();

			consrc.connect();

			BufferedInputStream rStream = new BufferedInputStream(sourse.openStream());

			File downFile = new File("downFile.json");

			BufferedOutputStream oStream = new BufferedOutputStream(new FileOutputStream(downFile));

			byte[] temp = new byte[4096];
			int rlength;
			while( (rlength = rStream.read(temp,0,4096)) != -1)
			{
				oStream.write(temp, 0, rlength);
			}
			oStream.flush();
			rStream.close();
			oStream.close();


		//	System.out.println("下載完成!!");



		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}