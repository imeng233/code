import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * MessageInterfaceImpl
 *
 * @author song
 * @version 1.0
 * @see java.rmi.server.UnicastRemoteObject
 * @see MessageInterface
 */

public class MessageInterfaceImpl extends UnicastRemoteObject implements MessageInterface {

    private static List<User> users = new Vector<>();
    private static List<Message> messages = new Vector<>();

    //定义时间格式
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

    /**
     * 构造函数
     *
     * @throws RemoteException
     */
    protected MessageInterfaceImpl() throws RemoteException {
        super();
    }

    /**
     * register
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册是否成功
     * @throws RemoteException
     */
    @Override
    public String register(String username, String password) throws RemoteException {
        //注册失败
        if (isUserExist(username)){
            return "注册失败,用户已存在";
        }
        //注册成功
        User user = new User(username, password);
        users.add(user);
        return "注册成功";
    }

    /**
     * showUsers
     *
     * @return 所有注册用户名列表
     * @throws RemoteException
     */
    @Override
    public String showUsers() throws RemoteException {
        //没有用户
        if (users.isEmpty()){
            return "没有注册的用户";
        }
        //有用户
        else {
            String info = "所有注册用户如下:";
            for (User user : users) {
                info += user.getUsername();
                info += "\r\n";
            }
            return info;
        }
    }

    /**
     * checkMessage
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户所有留言列表
     * @throws RemoteException
     */
    @Override
    public String checkMessage(String username, String password) throws RemoteException {
        String info = "留言信息：";
        boolean hasMessage = false;

        //判断用户账户
        if (!isUserCorrect(username, password)){
            return "用户名或密码错误";
        }else {

            for (Message message : messages){
                if (message.getReceiverName().equals(username)){
                    hasMessage = true;
                    info += message.toString();
                    info += "\r\n";
                }
            }
        }

        //有留言
        if (hasMessage){
            return info;
        }else
            //没有留言
            return "没有消息";

    }

    /**
     * leaveMessage
     *
     * @param username      用户名
     * @param password      密码
     * @param receiver_name 接收者
     * @param message_txt   留言信息
     * @return 留言返回消息
     * @throws RemoteException
     */
    @Override
    public String leaveMessage(String username, String password, String receiver_name, String message_txt)
            throws RemoteException {

        //判断用户账号与接收者账号
        if (!isUserCorrect(username, password)){
            return "用户名或密码错误";
        }else if (!isUserExist(receiver_name)){
            return "用户名不存在";
        }

        //留言
        Message message = new Message(username, receiver_name, dateFormat.format(new Date()), message_txt);
        messages.add(message);
        return "留言成功";
    }

    private boolean isUserExist(String username){
        for (User user: users){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    private boolean isUserCorrect(String username, String password){
        User currentUser = new User(username, password);
        for (User user : users) {
            if (currentUser.equals(user)){
                return true;
            }
        }
        return false;
    }
}
