package bubble.test.ex06;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//메인 스레드 - 키보드 이벤트 처리하기 바쁨
//백그라운드에서 계속 관찰하는 스레드
public class BackgroundPlayerService implements Runnable{
	
	private BufferedImage image;
	private Player player;
	
	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//색상확인
		while(true) {
			Color leftColor = new Color(image.getRGB(player.getX()-10, player.getY()+25));
			Color rightColor = new Color(image.getRGB(player.getX()+50+15, player.getY()+25));
			System.out.println("왼쪽색상 : "+leftColor);
			System.out.println("오른쪽색상 : "+rightColor);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
