import java.io.*;
import java.net.Socket;

class ServerService implements Runnable {


    private BufferedReader in=null; //封装socket的inputStream

    private OutputStream out=null; //保存socket的outputStream

    private int threadIndex=-1; //线程索引，默认是-1

    private String requestPage; //代理请求的网页

    private String hostName; //代理请求的服务器

    private int CONNECTPORT=80; //代理请求的服务器的端口号,默认端口80
    /**
     *
     * @param socket 套接字描述符
     * @param threadIndex 线程索引
     * 构造函数
     */
    public ServerService(Socket socket, int threadIndex)
    {
        try{
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out=socket.getOutputStream();
            this.threadIndex=threadIndex;
            System.out.println("server-thread index "+this.threadIndex+" :"+"线程启动");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 服务器线程的service方法，对客户端的请求进行处理的方法
     */
    public void run()
    {
        try{
            String CRLF = "\r\n";

            String recline=in.readLine(); //读取请求行的内容

            String resMessage=""; //响应报文

            String url=""; //客户请求的URL

            String method=""; //客户请求使用的方法，默认为GET

            System.out.println("server-thread index "+this.threadIndex+" 请求行内容"+recline);
            if(recline==null)
                return;
            else
            {
                if(recline.split("\\s").length==3) //正则表达式解析返回报文
                {
                    method=recline.split("\\s")[0];
                    url=recline.split("\\s")[1];
                    if(method.toUpperCase().equals("GET"))
                    {
                        getHostAndReqPage(url);
                        try{
                            Socket proxyClient=new Socket(hostName,CONNECTPORT);
                            OutputStream os=proxyClient.getOutputStream();
                            InputStream is=proxyClient.getInputStream();
                            String requestHeader="GET "+requestPage+" HTTP/1.0"+CRLF+
                                    "Host: "+hostName+CRLF+
                                    "Connection: close"+CRLF+
                                    "User-agent: Mozilla /4.0"+CRLF+CRLF;
                            os.write(requestHeader.getBytes());
                            byte[] buf=new byte[1024];
                            int readCount=0;
                            while((readCount=is.read(buf))!=-1)
                                out.write(buf, 0, readCount);
                            os.close();
                            is.close();
                            proxyClient.close();
                            out.close();
                        }
                        catch(Exception e)
                        {
                            resMessage="HTTP/1.0 400 Bad Request"+CRLF+CRLF;
                            out.write(resMessage.getBytes());
                            out.close();
                        }
                    }
                    else
                    {
                        resMessage="HTTP/1.0 400 Bad Request"+CRLF+CRLF;
                        out.write(resMessage.getBytes());
                        out.close();
                    }
                }
                else
                {
                    resMessage="HTTP/1.0 400 Bad Request"+CRLF+CRLF;
                    out.write(resMessage.getBytes());
                    out.close();
                }
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        WebProxyServer.setConnectionCount(this.threadIndex);
    }

    /**
     * @param url
     * 这个函数从请求的url中提取出请求的文件，服务器名称，端口号
     */
    public void getHostAndReqPage(String url)
    {
        if(url.toUpperCase().startsWith("HTTP://"))
        {
            url=url.substring(7);
        }
        int index=0;
        String hostMessage=null;
        if((index=url.indexOf('/'))==-1)
        {
            hostMessage=url;
            requestPage="/";
        }
        else
        {
            hostMessage=url.substring(0,index);
            requestPage=url.substring(index);
        }
        //查看请求的主机信息中是否包含端口信息
        if((index=hostMessage.indexOf(':'))==-1)
        {
            hostName=hostMessage;
        }
        else
        {
            hostName=hostMessage.substring(0,index);
            CONNECTPORT=Integer.parseInt(hostMessage.substring(index+1));
        }
        System.out.println("proxy:"+hostName+" [HOST]");
        System.out.println("proxy:"+CONNECTPORT+" [PORT]");
    }
}