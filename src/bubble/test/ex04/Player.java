package bubble.test.ex04;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends JLabel implements Moveable{
	
	//위치상태
	private int x;
	private int y;
	
	//움직임상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private ImageIcon playerR, playerL;
	
	private final int SPEED = 3;
	
	public Player() {
		initObject();
		initSetting();
	}
	
	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}
	
	private void initSetting() {
		x = 55;
		y = 535;
		
		//처음에는 안움직이니까 모두 false로 초기화
		left = false;
		right = false;
		up = false;
		down = false;
		
		setIcon(playerR);
		setSize(50,50);
		setLocation(x,y);
	}

	//이벤트 핸들러
	@Override
	public void up() {
		// TODO Auto-generated method stub
		System.out.println("UP");
		up = true;
		new Thread(() -> {
			
		}).start();
		
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		System.out.println("left");
		left = true;
		new Thread(() -> {
			//람다식
			while(left) {
				setIcon(playerL);
				x = x-SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
//		new Thread(new Runnable() { 객체명지운 괄호"()" 만 남겨두고 "->"표시로 "{}"를 대신하고 run()메소드 대신 "{}"를 사용
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			}
//		}).start();
		System.out.println("right");
		right = true;
		new Thread(() -> {
			//람다식
			while(right) {
				setIcon(playerR);
				x = x+SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
