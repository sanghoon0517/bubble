package bubble.test.ex17;

/**
 * @author 전상훈
 * 
 * default를 걸면 인터페이스도 몸체가 있는 메서드를 만들 수 있다.
 * 왜? 자바는 기본적으로 다중상속이 안된다. 이를 보완하기 위해서 default를 걸면 몸체가 있는 메서드를 만들 수 있다.
 * 그래서 어댑터 패턴을 사용하는 것보다 default를 사용하는 것이 좋다.
 */
public interface Moveable {
	void up();
	default void down() {};
	void left();
	void right();
	default void attack() {};
}
