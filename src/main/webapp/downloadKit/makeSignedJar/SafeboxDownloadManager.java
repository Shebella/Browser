import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SafeboxDownloadManager extends JApplet{
	private JTextArea jta=new JTextArea();
	private JProgressBar jpb=new JProgressBar();
	private JLabel jlb=new JLabel("Downloading ");
	private String releasehost;
	
	
	
	public void init(){
		this.releasehost=this.getParameter("releaseServer");
		this.getContentPane().setLayout(null);
		
		
		
		this.jta.setSize(300,300);
		this.jta.setLocation(0,50);
		this.jta.setVisible(true);
		this.jta.setEditable(false);
		this.jta.setLineWrap(true);
		this.getContentPane().add(jta);
		
		this.jpb.setSize(300,30);
		this.jpb.setLocation(90,0);
		this.jpb.setVisible(true);
		this.jpb.setValue(0);
		this.jpb.setMinimum(0);
		this.jpb.setMaximum(100);
		this.getContentPane().add(jpb);
		
		this.jlb.setLocation(0,0);
		this.jlb.setSize(80,30);
		this.jlb.setVisible(true);
		this.jlb.setText("Downloading");
		this.getContentPane().add(jlb);
		
		
		this.getContentPane().setBackground(Color.white);
	}
	protected URL getURL(String OS) throws MalformedURLException{
		System.out.println(OS);
		URL u=null;
		if(OS.toLowerCase().contains("windows")){		
			u=new URL("http://"+this.releasehost+"/safebox/branches/alpha_1_0/Safebox_setup_win.exe");
		}else{

			u=new URL("http://"+this.releasehost+"/safebox/branches/alpha_1_0/Safebox_setup_linux.bin");
		}
		return u;
	}
	
	
	public void start(){
		String OS=System.getProperty("os.name");
		String CSSIp=this.getCodeBase().getHost();

		
		
		try {
			URL url=this.getURL(OS);
			
			InputStream in=url.openStream();
			
			File f=File.createTempFile("safebox_temp",".exe");
			
			java.io.OutputStream on=new FileOutputStream(f);
			
			byte[]  ba=new byte[10000];
			int b=0;
			while((b=in.read(ba))>-1){
				on.write(ba,0,b);
				this.jpb.setValue(this.jpb.getValue()+(b/1024/41984));
				this.repaint();
				
			}
			
			this.jpb.setValue(100);
			
			on.close();
			in.close();
			
			Process p=null;
		
			if(OS.toLowerCase().contains("windows")){
				Runtime.getRuntime().exec("cmd /c "+f.getAbsolutePath()+" -Dinstall_time_server_ip="+CSSIp);
			}else{
				
				Runtime.getRuntime().exec(""+f.getAbsolutePath()+" -Dinstall_time_server_ip="+CSSIp);
			}
			
			
			
			
			
			
			this.jta.setText("Success to download.("+System.getProperty("os.name")+", release server="+this.releasehost+",CSS Server="+this.getCodeBase().getHost()+")");
		}catch(Throwable th){
			this.jta.setText(("Exception:"+th.getMessage()));
			JOptionPane.showMessageDialog(this,"due to some problem,installer can not be installed,please contact the system administrator.");
		}
		
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
