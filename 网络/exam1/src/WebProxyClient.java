import java.io.*;
import java.net.Socket;


public class WebProxyClient {

    private String CRLF = "\r\n";

    private Socket socket=null; //保存客户端socket连接

    private BufferedReader br=null; //保存socket的输入流

    private PrintWriter pw=null; //保存socket的输出流

    private BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));//从标准输入设备读入数据输入流

    private PrintWriter stdout=new PrintWriter(new OutputStreamWriter(System.out),true); //向标准输出设备输出数据的输出流

    private int CONNECTPORT=8000; //客户端连接服务器的端口

    private String hostName="localhost"; //服务器名称127.0.0.1

    private String url=""; //请求的URL
    /**
     * 连接服务器
     * @return 返回this
     */
    public WebProxyClient connectServer()
    {
        try{
            stdout.print("client:请输入要请求的链接\nclient:");
            stdout.flush();
            url=stdin.readLine();
            socket=new Socket(hostName,CONNECTPORT);
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            System.out.println("client:"+"连接服务器成功");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 根据用户输入的URL请求主机上的文件
     * @return
     */
    public WebProxyClient request() throws IOException //客户端request请求
    {
        try{
            String requestMessage=null;
            stdout.println("client:请求类型GET"); //默认请求方法是GET
            requestMessage="GET "+url+" HTTP/1.0"+CRLF+"From: nobody@somesite.com"+CRLF //请求报文内容
                    +"User-Agent: HTTPTool/1.0"+CRLF+CRLF;
            pw.println(requestMessage);
            String line=null;
            stdout.println("client GET 响应报文如下:");
            /* 读status line 和header line */
            while((line=br.readLine())!=null)
            {

                if(line.length()==0) //如果读到一行空行说明下一行开始传送响应的数据了
                    break;
                stdout.println(line);
            }
            stdout.println("client 响应的网页如下:");
            while((line=br.readLine())!=null)
                stdout.println(line);
            stdout.println("client :接收完毕");
            br.close();
            pw.close();
            stdin.close();
            stdout.close();

        }catch(IOException e)
        {
            e.printStackTrace();
            br.close();
            pw.close();
            stdin.close();
            stdout.close();
        }
        return this;
    }

    public static void main(String args[]) throws IOException {
        new WebProxyClient()
        .connectServer()
        .request();
    }
}
