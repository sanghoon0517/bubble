package bubble.test.ex18;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable{
	
	private BubbleFrame mContext;
	
	//위치상태
	private int x;
	private int y;
	
	//플레이어의 방향
	private EnemyWay enemyWay;
	
	//움직임상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;	
	
	private int state; //0(살아있음) 1(물방울에 갇힘)
	
	private ImageIcon enemyR, enemyL;
	
	//적군의 속도 상태
	private final int SPEED = 3;
	private final int JUMPSPEED = 1;
	
	public Enemy(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
		right();
	}
	
	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}
	
	private void initSetting() {
		x = 480;
		y = 178;
		
		//처음에는 안움직이니까 모두 false로 초기화
		left = false;
		right = false;
		up = false;
		down = false;
		
		state = 0;
		
		enemyWay = EnemyWay.RIGHT;
		setIcon(enemyR);
		setSize(50,50);
		setLocation(x,y);
	}
	
	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}
	
	@Override
	public void attack() {
		new Thread(() -> {
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			if(enemyWay == EnemyWay.LEFT) {
				bubble.left();
			} else {
				bubble.right();
			}
		}).start();
	}

	//이벤트 핸들러
	@Override
	public void up() {
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
		down = true;
		new Thread(() -> {
			while(down) {
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
		enemyWay = EnemyWay.LEFT;
		left = true;
		new Thread(() -> {
			//람다식
			while(left) {
				setIcon(enemyL);
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
		enemyWay = EnemyWay.RIGHT;
		right = true;
		new Thread(() -> {
			//람다식
			while(right) {
				setIcon(enemyR);
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
