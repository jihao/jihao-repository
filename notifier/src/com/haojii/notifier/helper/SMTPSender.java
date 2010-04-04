package com.haojii.notifier.helper;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;

class EmailConfig {
	String user, password;
	String to, subject, from, cc, bcc;
	String mailhost;
	boolean auth;
	String mailer;
	String prot;
}

public class SMTPSender {

	private static EmailConfig emailConfig = new EmailConfig();
	private static boolean debug = false;
	static {
		Properties p = new Properties();
		try {
			p.load(SMTPSender.class.getResourceAsStream("email.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		emailConfig.prot = p.getProperty("prot", "smtps");
		emailConfig.user = p.getProperty("user");
		emailConfig.password = p.getProperty("password");
		emailConfig.subject = p.getProperty("subject");
		emailConfig.from = p.getProperty("from");
		emailConfig.cc = p.getProperty("cc");
		emailConfig.bcc = p.getProperty("bcc");
		emailConfig.auth = Boolean.parseBoolean(p.getProperty("auth"));
		emailConfig.mailer = p.getProperty("mailer");
		emailConfig.mailhost = p.getProperty("mailhost");
	}

	private static void buildHTMLMsg(String subject, String bodyText, Message msg) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		sb.append("<BODY>\n");
		sb.append("<H1>" + subject + "</H1>" + "\n");
		
		sb.append(bodyText);
		sb.append("\n");
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		msg.setDataHandler(new DataHandler(new ByteArrayDataSource(sb.toString(), "text/html")));
	}

	public static void send(String to, String subject, String bodyText) {
		if ((to != null) && (!to.isEmpty()))
			emailConfig.to = to;
		
		if ((subject != null) && (!subject.isEmpty()))
			emailConfig.subject = subject;

		try {
			/*
			 * Initialize the JavaMail Session.
			 */
			Properties props = System.getProperties();
			if (emailConfig.mailhost != null)
				props.put("mail." + emailConfig.prot + ".host",
						emailConfig.mailhost);
			if (emailConfig.auth)
				props.put("mail." + emailConfig.prot + ".auth", "true");

			// Get a Session object
			Session session = Session.getInstance(props, null);
			if (debug)
				session.setDebug(true);

			/*
			 * Construct the message and send it.
			 */
			Message msg = new MimeMessage(session);
			if (emailConfig.from != null)
				msg.setFrom(new InternetAddress(emailConfig.from));
			else
				msg.setFrom();

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
					emailConfig.to, false));
			if (emailConfig.cc != null)
				msg.setRecipients(Message.RecipientType.CC, InternetAddress
						.parse(emailConfig.cc, false));
			if (emailConfig.bcc != null)
				msg.setRecipients(Message.RecipientType.BCC, InternetAddress
						.parse(emailConfig.bcc, false));

			msg.setSubject(emailConfig.subject);
			
			//msg.setText(bodyText);
			buildHTMLMsg(emailConfig.subject,bodyText,msg);
			
			msg.setHeader("X-Mailer", emailConfig.mailer);
			msg.setSentDate(new Date());

			// send the thing off
			/*
			 * The simple way to send a message is this:
			 * 
			 * Transport.send(msg);
			 * 
			 * But we're going to use some SMTP-specific features for
			 * demonstration purposes so we need to manage the Transport object
			 * explicitly.
			 */
			SMTPTransport t = (SMTPTransport) session.getTransport(emailConfig.prot);
			try {
				if (emailConfig.auth)
					t.connect(emailConfig.mailhost, emailConfig.user, emailConfig.password);
				else
					t.connect();

				t.sendMessage(msg, msg.getAllRecipients());

			} finally {
				if (debug)
					System.out.println("Response: " + t.getLastServerResponse());
				t.close();
			}

			System.out.println("\nMail was sent successfully.");

		} catch (Exception e) {
			/*
			 * Handle SMTP-specific exceptions.
			 */
			if (e instanceof SendFailedException) {
				MessagingException sfe = (MessagingException) e;
				if (sfe instanceof SMTPSendFailedException) {
					SMTPSendFailedException ssfe = (SMTPSendFailedException) sfe;
					System.out.println("SMTP SEND FAILED:");
					if (debug)
						System.out.println(ssfe.toString());

					System.out.println("  Command: " + ssfe.getCommand());
					System.out.println("  RetCode: " + ssfe.getReturnCode());
					System.out.println("  Response: " + ssfe.getMessage());
				} else {
					if (debug)
						System.out.println("Send failed: " + sfe.toString());
				}
				Exception ne;
				while ((ne = sfe.getNextException()) != null
						&& ne instanceof MessagingException) {
					sfe = (MessagingException) ne;
					if (sfe instanceof SMTPAddressFailedException) {
						SMTPAddressFailedException ssfe = (SMTPAddressFailedException) sfe;
						System.out.println("ADDRESS FAILED:");
						if (debug)
							System.out.println(ssfe.toString());
						System.out.println("  Address: " + ssfe.getAddress());
						System.out.println("  Command: " + ssfe.getCommand());
						System.out
								.println("  RetCode: " + ssfe.getReturnCode());
						System.out.println("  Response: " + ssfe.getMessage());
					} else if (sfe instanceof SMTPAddressSucceededException) {
						System.out.println("ADDRESS SUCCEEDED:");
						SMTPAddressSucceededException ssfe = (SMTPAddressSucceededException) sfe;
						if (debug)
							System.out.println(ssfe.toString());
						System.out.println("  Address: " + ssfe.getAddress());
						System.out.println("  Command: " + ssfe.getCommand());
						System.out
								.println("  RetCode: " + ssfe.getReturnCode());
						System.out.println("  Response: " + ssfe.getMessage());
					}
				}
			} else {
				System.out.println("Got Exception: " + e);
				if (debug)
					e.printStackTrace();
			}
		}
	}


}
/*
 * 
 * Process command line arguments. BufferedReader in = new BufferedReader(new
 * InputStreamReader(System.in)); int optind; System.out.println(argv.length);
 * for (optind = 0; optind < argv.length; optind++) {
 * System.out.println(argv[optind]); if (argv[optind].equals("-T")) { protocol =
 * argv[++optind]; } else if (argv[optind].equals("-H")) { host =
 * argv[++optind]; } else if (argv[optind].equals("-U")) { user =
 * argv[++optind]; } else if (argv[optind].equals("-P")) { password =
 * argv[++optind]; } else if (argv[optind].equals("-M")) { mailhost =
 * argv[++optind]; } else if (argv[optind].equals("-f")) { record =
 * argv[++optind]; } else if (argv[optind].equals("-a")) { file =
 * argv[++optind]; } else if (argv[optind].equals("-s")) { subject =
 * argv[++optind]; System.out.println(subject); } else if
 * (argv[optind].equals("-o")) { // originator from = argv[++optind]; } else if
 * (argv[optind].equals("-c")) { cc = argv[++optind]; } else if
 * (argv[optind].equals("-b")) { bcc = argv[++optind]; } else if
 * (argv[optind].equals("-L")) { url = argv[++optind]; } else if
 * (argv[optind].equals("-d")) { debug = true; } else if
 * (argv[optind].equals("-v")) { verbose = true; } else if
 * (argv[optind].equals("-A")) { auth = true; } else if
 * (argv[optind].equals("-S")) { prot = "smtps"; } else if
 * (argv[optind].equals("--")) { optind++; break; } else if
 * (argv[optind].startsWith("-")) { System.out.println(
 * "Usage: smtpsend [[-L store-url] | [-T prot] [-H host] [-U user] [-P passwd]]"
 * ); System.out.println(
 * "\t[-s subject] [-o from-address] [-c cc-addresses] [-b bcc-addresses]");
 * System.out.println(
 * "\t[-f record-mailbox] [-M transport-host] [-d] [-a attach-file]");
 * System.out.println( "\t[-v] [-A] [-S] [address]"); System.exit(1); } else {
 * //break; } }
 */
