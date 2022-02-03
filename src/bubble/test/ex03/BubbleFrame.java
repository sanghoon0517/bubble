package bubble.test.ex03;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BubbleFrame  extends JFrame{
	
	private JLabel backgroundMap;
	private Player player;
	
	public BubbleFrame() {
		// TODO Auto-generated constructor stub
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}
	
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap); //JPanel = JLabel 로 변경(덮어씌움)
		
		player = new Player();
		add(player);
//		backgroundMap.setSize(1000,600);
//		add(backgroundMap); //JFrame에 JLabel이 그려진다.
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); //null을 받으면 absolute로 설정된다. (자유롭게 그림을 그릴 수 있다는 뜻)
		setLocationRelativeTo(null); //JFRame 가운데로 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼으로 창 끌 때 JVM같이 종료하기
	}
	
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				
				//아래키를 누를 필요가 없기 때문에 down은 지움
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: //37
						player.left();
						break;
					case KeyEvent.VK_RIGHT: //39
						player.right();
						break;
					case KeyEvent.VK_UP: //38
						player.up();
						break;
				}
				
			}
		});
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BubbleFrame();
	}
}
