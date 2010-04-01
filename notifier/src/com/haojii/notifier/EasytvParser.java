package com.haojii.notifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class EasytvParser extends Observable {
	private Item item;
	
	public EasytvParser(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	
	
	public void checkForUpdate() {
		try {
			
			//URI uri = URIUtils.createURI("http", "easytv.echinatv.com.cn/", -1,"/ItemDet.aspx", "IID=" + id, null);
			URI uri = new URI(item.url);
			HttpGet httpget = new HttpGet(uri);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				entity = new BufferedHttpEntity(entity);
				System.out.println(entity.getContentLength());
				InputStream instream = entity.getContent();
				try {

					BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
					// do something useful with the response
					String line = reader.readLine();
					boolean startSearching = false;
					//StringBuilder sb = new StringBuilder();
					Pattern p = Pattern.compile(
							"href\\s*=\\s*\"(.+?)\"\\s*>(.+?)</\\s*a\\s*>",
							Pattern.UNICODE_CASE);
					Pattern p2 = Pattern
							.compile("(asf|avi|rm|rmvb|mp3|mp4|avi|wma|wmp|wmv|mov+3gp|mpg|mpeg|rar|zip){1}");

					while (line != null) {
						if (line.contains("<div id=\"download-list\">")) {
							startSearching = true;
						}
						if (startSearching) {
							if (line.contains("</div>"))
								startSearching = false;
								Matcher m = p.matcher(line);
								if (m.find() && p2.matcher(line).find()) {
									
									System.out.println("1->" + m.group(1));
									System.out.println("2->" + m.group(2));
								}

							//sb.append(line.trim());
							//sb.append("\n");
						}
						line = reader.readLine();
					}

				} catch (IOException ex) {

					// In case of an IOException the connection will be released
					// back to the connection manager automatically
					throw ex;

				} catch (RuntimeException ex) {

					// In case of an unexpected exception you may want to abort
					// the HTTP request in order to shut down the underlying
					// connection and release it back to the connection manager.
					httpget.abort();
					throw ex;

				} finally {

					// Closing the input stream will trigger connection release
					instream.close();
				}

				// When HttpClient instance is no longer needed,
				// shut down the connection manager to ensure
				// immediate deallocation of all system resources
				httpclient.getConnectionManager().shutdown();
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
