import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Main {
    static JFrame frame = new JFrame();
    static JLabel l;
    static int width = 500, height = 500;
    static int change=50;

    static public void move(KeyEvent e){
        int thisChange = e.isShiftDown()?change*2:change; // Если нажата кнопка shift, то двигаемся вдвое быстрее
        switch (e.getKeyCode()){ // Делаем проверку на границы формы, пользуясь реальным размером окна, а не первоначально заданным, а также заданным смещением, чтобы вписаться в panel
            case (KeyEvent.VK_LEFT):
                if (l.getX()-thisChange>=0) // Проверяем, вписываемся ли в окно, или надо будет перейти его границу
                    l.setLocation(l.getX()-thisChange,l.getY());
                else { // Если переходим границу то проверяем, нажат ли shift и находится ли картинка на расстоянии менее себя от границы
                    if (e.isShiftDown() && l.getX() <= l.getWidth())
                        l.setLocation((int) frame.getContentPane().getSize().getWidth() - l.getWidth()*2 - 5, l.getY()); // Если да, то к ставим картинку не просто на противоположенную границу
                    else
                        l.setLocation((int) frame.getContentPane().getSize().getWidth() - l.getWidth() - 5, l.getY()); // Если нет, то ставим картинку возле противоположенной границы
                }
                break;
            case (KeyEvent.VK_RIGHT):
                if (l.getX()+thisChange<(int) frame.getContentPane().getSize().getWidth()-l.getWidth())
                    l.setLocation(l.getX()+thisChange,l.getY());
                else {
                    if (e.isShiftDown()&&frame.getContentPane().getSize().getWidth()-l.getX()<=l.getWidth()*2)
                        l.setLocation(5+l.getWidth(), l.getY());
                    else
                        l.setLocation(5, l.getY());
                }
                break;
            case (KeyEvent.VK_UP):
                if (l.getY()-thisChange>=0)
                    l.setLocation(l.getX(),l.getY()-thisChange);
                else {
                    if (e.isShiftDown() && l.getY() <= l.getHeight())
                        l.setLocation(l.getX(), (int) frame.getContentPane().getSize().getHeight() - l.getHeight()*2 + 5);
                    else
                        l.setLocation(l.getX(), (int) frame.getContentPane().getSize().getHeight() - l.getHeight() + 5);
                }
                break;
            case (KeyEvent.VK_DOWN):
                if (l.getY()+thisChange<(int) frame.getContentPane().getSize().getHeight()-l.getHeight())
                    l.setLocation(l.getX(),l.getY()+thisChange);
                else {
                    if (e.isShiftDown()&&frame.getContentPane().getSize().getHeight()-l.getY()<=l.getHeight()*2)
                        l.setLocation(l.getX(), 5+l.getHeight());
                    else
                        l.setLocation(l.getX(), 5);
                }
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Перемещение с кнопкой shift");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dim.width / 2 - width / 2, dim.height / 2 - height / 2, width, height);
        BufferedImage im = ImageIO.read(new URL("https://pp.userapi.com/c846124/v846124246/de82f/dbLAZsmnoy0.jpg?ava=1"));
        JPanel panel = new JPanel (new FlowLayout(FlowLayout.LEFT)); // Создаем панель, чтобы ей отлавливать события клавиатуры, ставим ее слева
        panel.setFocusable(true); // Делаем у панели возможность принимать фокус, иначе она не сможет отловить события клавиатуры
        l = new JLabel(new ImageIcon(im),JLabel.RIGHT); // Создаем объект слева
        panel.add(l, BorderLayout.NORTH); // Добавляем на панель
        frame.add(panel); // Добавляем панель на форму
        panel.addKeyListener(new KeyAdapter() { // Добавляем слушателя на панель
            public void keyReleased(KeyEvent e) {
                move(e); // Метод движения
            }
        });
        frame.setVisible(true);
    }
}