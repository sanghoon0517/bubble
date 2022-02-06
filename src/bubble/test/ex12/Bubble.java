package bubble.test.ex12;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	//의존성 컴포지션 : 플레이어에 의존적이다. 왜? 플레이어의 위치에 의해서 물방울의 위치가 결정되기때문에.
	private Player player;
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
	
	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
		initThread();
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
	
	private void initThread() {
		//스레드가 하나만 필요
		new Thread(()->{
			if(player.getPlayerWay() == PlayerWay.LEFT) {
				left();
			} else {
				right();
			}
		}).start();
	}

	@Override
	public void up() {
		up = true;
		while(true) {
			y--;
			setLocation(x, y);
			if(backgroundBubbleService.topWall()) break;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void left() {
		left = true;
		for(int i = 0; i <400; i++ ) {
			x--;
			setLocation(x, y);
			if(backgroundBubbleService.leftWall()) break;
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
			if(backgroundBubbleService.rightWall()) break;
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
