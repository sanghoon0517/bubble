package bubble.test.ex06;

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
	private final int JUMPSPEED = 2;
	
	public Player() {
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}
	
	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}
	
	private void initSetting() {
		x = 80;
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
	
	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}

	//이벤트 핸들러
	@Override
	public void up() {
		// TODO Auto-generated method stub
		System.out.println("up");
		up = true;
		new Thread(() -> {
			for(int i = 0; i<130/JUMPSPEED; i++) {
				//왼쪽 상단이 (0,0)이기 때문에 점프할때는 -y를 해야한다.
				y = y-JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//점프의 상태 바꾸기
			up = false;
			
			//점프했다가 바닥에 착지 하기 위함
			down();
		}).start();
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		System.out.println("down");
		down = true;
		new Thread(() -> {
			for(int i = 0; i<130/JUMPSPEED; i++) {
				//왼쪽 상단이 (0,0)이기 때문에 점프할때는 -y를 해야한다.
				y = y+JUMPSPEED;
				setLocation(x,y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//상태변경
			down = false;
		}).start();
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
