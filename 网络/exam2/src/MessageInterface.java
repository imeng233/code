import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * MessageInterface
 *
 * @author song
 * @version 1.0
 * @see java.rmi.Remote
 *
 */

public interface MessageInterface extends Remote {
    //该方法用于新用户的注册。如果用户名已存在，则提示用户选择另一个用户名。
    /**
     * register
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册是否成功
     * @throws RemoteException
     */
    public String register(String username, String password) throws RemoteException;

    //该方法用于显示所有注册的用户。
    /**
     * showUsers
     *
     * @return 所有注册用户名列表
     * @throws RemoteException
     */
    public String showUsers() throws RemoteException;

    //该方法打印用户的所有留言，留言信息包括：留言者、日期和时间、留言内容。
    // 注意，如果用户名和密码不对，应有相应的提示信息；如果该用户没有任何留言，也应该有提示。
    /**
     * checkMessage
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户所有留言列表
     * @throws RemoteException
     */
    public String checkMessage(String username, String password) throws RemoteException;

    //该方法用于给其他用户留言，首先需校验用户名和密码是否正确。若不正确，请给出相应的提示，留言不成功；
    // 接着需校验接收者用户名是否存在，若不存在，请给出相应的提示，留言不成功；若以上校验均正确，
    // 则系统记录留言的日期和时间、留言内容等信息。
    /**
     * leaveMessage
     *
     * @param username 用户名
     * @param password 密码
     * @param receiver_name 接收者
     * @param message_txt 留言信息
     * @return 留言返回消息
     * @throws RemoteException
     */
    public String leaveMessage(String username, String password, String receiver_name,
                               String message_txt) throws RemoteException;
}
