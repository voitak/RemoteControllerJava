import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;

public class Server implements ActionListener {

    private static ServerSocket server = null;
    private static Socket client = null;
    private static BufferedReader in = null;
    private static String line;
    private static boolean isConnected = false;
    private static Robot robot;
    private static final int SERVER_PORT = 8998;
    private static String addr;
    private static JButton button;
    private static JButton buttonStop;
    InetAddress serverAddr;



    public static void main(String[] args) throws SocketException {
        boolean check = true;
        addr = "";

        //Поиск подходящего адреса
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                //System.out.println(i.getHostAddress());
                if (i.getHostAddress().contains("192.168.") && i.getHostName().contains("192.168.")){
                    addr = i.getHostAddress();
                    //System.out.println(i.getHostAddress() + " " + i.getCanonicalHostName());
                }
            }
        }
        if (addr.equals("")){
            addr = "Can't detect IP. Restart application";
            check = false;
        }

        //Создание графического интерфейса
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        button = new JButton("Turn on");
        button.setBounds(100,100,100,25);
        button.addActionListener(new Server());
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        buttonStop = new JButton("Stop");
        buttonStop.setBounds(200,100,100,25);
        buttonStop.addActionListener(new Server());
        buttonStop.setFocusPainted(false);
        buttonStop.setMargin(new Insets(0, 0, 0, 0));
        buttonStop.setContentAreaFilled(false);
        buttonStop.setOpaque(false);

        if(check){
            panel.add(button);
            panel.add(buttonStop);
        }

        JLabel label = new JLabel(addr);
        label.setBounds(150,20,150,50);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t = null;
        if (e.getSource() == button) {
            boolean leftpressed=false;
            boolean rightpressed=false;

            String[] subStr;
            try {
                robot = new Robot();
                button.setText("Connection...");
                button.setEnabled(false);
                //Создание соединения с мобильным приложением
                server = new ServerSocket(SERVER_PORT);
                //server.setSoTimeout(1000);
                t = new ClientHandler();
                t.start();
            } catch (AWTException | IOException awtException) {

            }
        }
        else if (e.getSource() == buttonStop && isConnected || button.getText().equals("Connection...")){
            isConnected = false;

            if (button.getText().equals("Connection...")) {
                try {
                    server.close();
                } catch (IOException ioException) {
                }
            } else {
                try {
                    server.close();
                } catch (IOException ioException) {
                }
                try {
                    client.close();
                } catch (IOException ioException) {
                }
            }

            button.setText("Turn on");
            button.setEnabled(true);
        }

    }


    //Поток для создания подключения и обработки полученной информации
    class ClientHandler extends Thread
    {

        @Override
        public void run()
        {
            String[] subStr;
            VirtualKeyBoard kb = null;
            try {
                kb = new VirtualKeyBoard();
            } catch (AWTException awtException) {
                awtException.printStackTrace();
            }
            try {
                client = server.accept();
                in = new BufferedReader(new InputStreamReader(client.getInputStream())); //the input stream where data will come from client
                button.setText("Connected");
                button.setEnabled(false);
                isConnected = true;
            } catch (SocketTimeoutException es){
                button.setText("Turn on");
                button.setEnabled(true);
            }
            catch (IOException er) {
                System.out.println("Error in opening Socket");
                button.setText("Turn on");
                button.setEnabled(true);
            }

            //read input from client while it is connected
            while(isConnected){
                try {
                    line = in.readLine(); //read input from client
                    System.out.println(line); //print whatever we get from client

                    if (line != null) {
                        //if user clicks on next
                        if (line.equalsIgnoreCase("left_click")) {
                            //Simulate press and release of key 'n'
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        }
                        //if user clicks on previous
                        else if (line.equalsIgnoreCase("right_click")) {
                            //Simulate press and release of key 'p'
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        }
                        //input will come in x,y format if user moves mouse on mousepad
                        else if (line.contains(",")) {
                            float movex = Float.parseFloat(line.split(",")[0]);//extract movement in x direction
                            float movey = Float.parseFloat(line.split(",")[1]);//extract movement in y direction
                            Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
                            float nowx = point.x;
                            float nowy = point.y;
                            robot.mouseMove((int) (nowx + movex), (int) (nowy + movey));//Move mouse pointer to new location
                        }
                        //Exit if user ends the connection
                        else if (line.equalsIgnoreCase("exit")) {
                            isConnected = false;
                            //Close server and client socket
                            server.close();
                            client.close();
                            button.setText("Turn on");
                            button.setEnabled(true);
                        } else {
                            subStr = line.split("\\+", 2);
                            if (subStr[0].equals("press")) {
                                kb.pressKeys(subStr[1]);
                            } else if (subStr[0].equals("release")) {
                                kb.releaseKeys(subStr[1]);
                            }
                        }
                    }
                } catch (IOException er) {
                    System.out.println("Read failed");
                }
            }
        }
    }

}