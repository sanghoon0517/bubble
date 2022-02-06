package bubble.test.ex15;

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
			image = ImageIO.read(new File("image/backgroundMapService.png"));
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
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) + image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5); //가장 오른쪽 하단 //-1
			
			//바닥충돌확인
			if(bottomColor != -2) { //-2가 왼쪽 = -1, 오른쪽 = -1인 경우에 -2가 되기때문
				player.setDown(false);
			} else { //-2일 때 실행. => 내 바닥 색깔이 흰색이라는 것
				if (!player.isUp() && !player.isDown()) {
					player.down();
				}
			}
			
			//외벽충돌확인
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
