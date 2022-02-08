package bubble.game.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Moveable;
import bubble.game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	//의존성 컴포지션 : 플레이어에 의존적이다. 왜? 플레이어의 위치에 의해서 물방울의 위치가 결정되기때문에.
	private BubbleFrame mContext;
	private Player player;
	private Enemy enemy;
	private BackgroundBubbleService backgroundBubbleService;
	
	//위치상태
	private int x;
	private int y;
	
	//움직임상태
	private boolean left;
	private boolean right;
	private boolean up;

	//적군을 맞춘 상태
	private int state; //0(그냥 물방울), 1(적을 가둔 물방울)

	private ImageIcon bubble; //물방울
	private ImageIcon bubbled; //적을 가둔 물방울
	private ImageIcon bomb; //물방울이 터진 상태
	
	public Bubble(BubbleFrame mContext) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemy = mContext.getEnemy();
		initObject();
		initSetting();
//		initThread();
	}
	
	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");
		backgroundBubbleService = new BackgroundBubbleService(this);
	}
	
	private void initSetting() {
		left = false;
		right = false;
		up = false;
		
		x = player.getX();
		y = player.getY();
		
		setIcon(bubble);
		setSize(50,50);
		
		state = 0;
	}
	
//	private void initThread() {
//		//스레드가 하나만 필요
//		new Thread(()->{
//			if(player.getPlayerWay() == PlayerWay.LEFT) {
//				left();
//			} else {
//				right();
//			}
//		}).start();
//	}
	
	private void clearBubble() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(500);
			mContext.remove(this); //BubbleFrame에 있는 bubble이 메모리에서 사라진다.
			mContext.repaint(); //BubbleFrame 전체를 다시 그린다.(메모리에서 없는건 그리지 않음)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearBubbled() {
//		new Thread(() -> {
			try {
				up = false;
				setIcon(bomb);
				Thread.sleep(1000);
				mContext.remove(this);
				mContext.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}).start();
	}

	@Override
	public void up() {
		up = true;
		while(up) {
			y--;
			setLocation(x, y);
			if(backgroundBubbleService.topWall()) {
				//상태변경
				up = false;
				break;
			}
			
			try {
				if(state == 0) { //그냥 일반 물방울
					Thread.sleep(1);
				} else { //적을 가둔 물방울은 천천히 올리겠음
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(state == 0) clearBubble(); //천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
	}
	
	@Override
	public void attack() {
		state = 1;
		enemy.setState(1);
		setIcon(bubbled);
		mContext.remove(enemy); //메모리에서 사라지게 한다. but 가비지 컬렉션이 즉시 발동하지 않는다.
		mContext.repaint(); //화면갱신
	}

	@Override
	public void left() {
		left = true;
		for(int i = 0; i <400; i++ ) {
			x--;
			setLocation(x, y);
			if(backgroundBubbleService.leftWall()) {
				//상태변경
				left = false;
				break;
			}
			
			//물방울과 적군의 거리가 10인 절대값 범위
			if(Math.abs(x - enemy.getX()) < 10 && Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50) {
				System.out.println("물방울이 적군과 충돌");
				if(enemy.getState() == 0) {
					attack();
					break; //맞은 순간에 물방울을 올리기 위함
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for(int i = 0; i <400; i++ ) {
			x++;
			setLocation(x, y);
			if(backgroundBubbleService.rightWall()) {
				//상태변경
				right = false; 
				break;
			}
			
			//물방울과 적군의 거리가 10인 절대값 범위
			if(Math.abs(x - enemy.getX()) < 10  && Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50) {
				System.out.println("물방울이 적군과 충돌");
				if(enemy.getState() == 0) {
					attack();
					break; //맞은 순간에 물방울을 올리기 위함
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		up();
		
	}
}
