/*
 * �m�W�G�L����
 * �Ǹ��GF84006024
 * �y�z�G�ݵ����|�ӰѼơA�Ĥ@�ӬO��ƨӷ���json�ɮ׳s���A�᭱�T�ӫh�O�j�M������A���O���m���ϡB�a�}����r�B����~��(�H��)�C
 *  �q�������s����U�@��json���ɮסA��X�íp��Ҧ��ŦX���󪺪��󪺥�����業���C
 * 
*/
import java.net.*;

import org.json.*;
import java.io.*;


public class TocHW3 {

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
				
				
				//	System.out.println(jsontmp.getString("�m����")); 
				
				if( !jsontmp.isNull("�m����") && !jsontmp.isNull("����~��") && !jsontmp.isNull("�g�a�Ϭq��m�Ϋت��Ϫ��P"))
						if( args[1].equals(jsontmp.getString("�m����")) && (year = jsontmp.getInt("����~��")) > Integer.parseInt(args[3]) * 100 && (addr = jsontmp.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P")).indexOf(args[2])!= -1)
				{
					addr = jsontmp.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P");
					int price = jsontmp.getInt("�`����");
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
		//System.out.println("���b�U�����......");
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
			
			
		//	System.out.println("�U������!!");
			
			
			
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
